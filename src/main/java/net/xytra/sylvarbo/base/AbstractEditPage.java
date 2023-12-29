package net.xytra.sylvarbo.base;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.validation.ValidationException;
import org.apache.cayenne.validation.ValidationFailure;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import net.xytra.common.cayenne.persistent.AbstractPersistentWithId;

// page to edit a persisted object
public abstract class AbstractEditPage<T extends AbstractPersistentWithId> extends AbstractTypedPage<T> {
    protected static final Pattern NUMBER_PATTERN = Pattern.compile("\\d*");

    @Component
    private Form editForm;

    @Property
    protected T editedObject;

    @Property
    private String currentError;

    protected String[] onPassivate() {
        return new String[] { onPassivateWithSingleParameter() };
    }

    protected String onPassivateWithSingleParameter() {
        System.err.println("--- AbstractEditPage.onPassivate() with editedObject="+editedObject);
        if (editedObject != null) {
            Number id = editedObject.getId();
            if (id != null) {
                return id.toString();
            } else {
                return editedObject.getUuid();
            }
        } else {
            return NEW_OBJECT_ID;
        }
    }

    /**
     * If id numeric, find Object by id; if "new", create new and store UUID; otherwise, assume UUID and get by UUID.
     * @param id
     */
    protected void onActivateForId(String id) {
        System.err.println("--- AbstractEditPage.activate() with id="+id);
        if (NUMBER_PATTERN.matcher(id).matches()) {
            editedObject = Cayenne.objectForPK(context(), getObjectType(), parseIdString(id));
        } else if (NEW_OBJECT_ID.equals(id)) {
            editedObject = context().newObject(getObjectType());
            context().setUserProperty(editedObject.getUuid(), editedObject);
        } else { // assume UUID String
            editedObject = (T)context().getUserProperty(id);
            System.err.println("---- AbstractEditPage.activate() obtained editedObject="+editedObject);
        }
    }

    protected abstract Number parseIdString(String id);

    public List<String> getValidationErrors() {
        return editForm.getDefaultTracker().getErrors();
    }

    protected void onValidateFromEditForm() {
        try {
            context().commitChanges();

            // Cleanup: remove UUID lookup when object has been saved and assigned a PK
            context().setUserProperty(editedObject.getUuid(), Null.class);
        } catch (ValidationException e) {
            Iterator<ValidationFailure> it = e.getValidationResult().getFailures().iterator();
            while (it.hasNext()) {
                editForm.recordError(it.next().getDescription());
            }
        }
    }

    protected Object onSuccess() {
        return getSuccessPageObject();
    }

    // Cancel button
    protected Object onActionFromCancel() {
        context().rollbackChanges();

        // Cleanup: remove UUID lookup
        context().setUserProperty(editedObject.getUuid(), Null.class);

        return getSuccessPageObject();
    }

    // --- Abstract methods that define the specifics of the page
    protected Object getSuccessPageObject() {
        return getSuccessPageType();
    }

    protected Class<? extends AbstractListPage<T>> getSuccessPageType() {
        throw new RuntimeException("Not implemented!");
    }

}
