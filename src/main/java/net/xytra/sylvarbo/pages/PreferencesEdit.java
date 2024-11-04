package net.xytra.sylvarbo.pages;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cayenne.validation.ValidationException;
import org.apache.cayenne.validation.ValidationFailure;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import net.xytra.common.cayenne.persistent.Preference;
import net.xytra.common.cayenne.persistent.User;
import net.xytra.sylvarbo.base.AbstractTypedPage;

/**
 *
 */
public class PreferencesEdit extends AbstractTypedPage<User> {
    @Component
    protected Form editForm;

    @Property
    private String currentError;

    private Map<String, String> userPrefMap;

    // --- Passivate/Activate
    void onActivate(EventContext eventContext) {
        if (eventContext.getCount() != 0) {
            throw new RuntimeException("Invalid number of arguments: " + eventContext.getCount());
        }

        userPrefMap = session.getUser().getAllPreferenceValuesOrDefaults();
    }

    // ---- Actions
    protected void onValidateFromEditForm() {
        session.getUser().replaceUserPreferences(userPrefMap);

        try {
            context().commitChanges();
        } catch (ValidationException e) {
            Iterator<ValidationFailure> it = e.getValidationResult().getFailures().iterator();
            while (it.hasNext()) {
                editForm.recordError(it.next().getDescription());
            }
        }
    }

    protected Object onSuccess() {
        // On saving, return the same page:
        return null;
    }

    // ---- Property methods
    public String getDefaultAncestorsViewDepth() {
        return userPrefMap.get(Preference.Keys.ANCESTORS_VIEW_DEPTH);
    }

    public void setDefaultAncestorsViewDepth(String depth) {
        userPrefMap.put(Preference.Keys.ANCESTORS_VIEW_DEPTH, depth);
    }

    public List<String> getValidationErrors() {
        return editForm.getDefaultTracker().getErrors();
    }

    // ---- Utility
    @Override
    protected Class<User> getObjectType() {
        return User.class;
    }

}
