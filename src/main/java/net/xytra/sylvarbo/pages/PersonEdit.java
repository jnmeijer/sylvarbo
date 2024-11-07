package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.enums.NameStyle;
import net.xytra.sylvarbo.persistent.PersonEvent;
import net.xytra.sylvarbo.persistent.PersonIdentity;

/**
 * First page when creating a new person is actually creating the primaryIdentity name
 */
public class PersonEdit extends PersonView {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    @Validate("required")
    private NameStyle style;

    private boolean addIdentity = false;

    void onSelectedFromAddIdentity() { addIdentity = true; }

    protected Object onSuccess() {
        if (addIdentity) {
            return linkSource.createPageRenderLinkWithContext(PersonIdentityAdd.class, viewedObject.getId(), style, NEW_OBJECT_ID);
        } else {
            return null;
        }
    }

    Object onActionFromDeleteEvent(long id) {
        PersonEvent event = viewedObject.getEvents().get(id);
        viewedObject.removeFromEvents(event);

        context().deleteObject(event);

        viewedObject.setModifiedNowBy(session.getUser());

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    Object onActionFromDeleteIdentity(int index) {
        PersonIdentity identity = getIdentityForIndex(index);
        viewedObject.removeFromIdentities(identity);

        // This assumes that Delete can never be called on one remaining identity
        if (identity.equals(viewedObject.getPrimaryIdentity())) {
            // Take the first remaining one
            viewedObject.setPrimaryIdentity(getIdentityForIndex(0));
        }

        context().deleteObject(identity);

        viewedObject.setModifiedNowBy(session.getUser());

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    Object onActionFromMarkIdentityAsPrimary(int index) {
        viewedObject.setPrimaryIdentity(getIdentityForIndex(index));

        viewedObject.setModifiedNowBy(session.getUser());

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    // --- Identity
    public boolean getCanDeleteIdentities() {
        return viewedObject.getIdentities().size() > 1;
    }

}
