package net.xytra.sylvarbo.base;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import java.util.regex.Pattern;

import org.apache.cayenne.Cayenne;
import org.apache.tapestry5.annotations.Property;

import net.xytra.common.cayenne.persistent.AbstractPersistentWithId;

// page to edit a persisted object
public abstract class AbstractViewPage<T extends AbstractPersistentWithId> extends AbstractTypedPage<T> {
    protected static final Pattern NUMBER_PATTERN = Pattern.compile("\\d*");

    @Property
    protected T viewedObject;

    protected String[] onPassivate() {
        return new String[] { onPassivateWithSingleParameter() };
    }

    protected String onPassivateWithSingleParameter() {
        System.err.println("--- AbstractViewPage.onPassivate() with viewedObject="+viewedObject);
        if (viewedObject != null) {
            Number id = viewedObject.getId();
            if (id != null) {
                return id.toString();
            } else {
                return viewedObject.getUuid();
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
        System.err.println("--- AbstractViewPage.activate() with id="+id);
        if (NUMBER_PATTERN.matcher(id).matches()) {
            viewedObject = Cayenne.objectForPK(context(), getObjectType(), parseIdString(id));
        } else if (NEW_OBJECT_ID.equals(id)) {
            viewedObject = context().newObject(getObjectType());
            context().setUserProperty(viewedObject.getUuid(), viewedObject);
        } else { // assume UUID String
            viewedObject = (T)context().getUserProperty(id);
            System.err.println("---- AbstractViewPage.activate() obtained viewedObject="+viewedObject);
        }
    }

    protected Object onActionFromReturn() {
        return getReturnPageObject();
    }

    protected abstract Number parseIdString(String id);

    // --- Abstract methods that define the specifics of the page
    protected Object getReturnPageObject() {
        return getReturnPageType();
    }

    protected Class<? extends AbstractListPage<T>> getReturnPageType() {
        throw new RuntimeException("Not implemented!");
    }

}
