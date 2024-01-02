package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractViewPage;
import net.xytra.sylvarbo.enums.NameStyle;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonEvent;
import net.xytra.sylvarbo.persistent.PersonIdentity;
import net.xytra.sylvarbo.persistent.Relationship;

/**
 * First page when creating a new person is actually creating the primaryIdentity name
 */
public class PersonView extends AbstractViewPage<Person> {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private int currentChildIndex;

    @Property
    private int currentEventIndex;

    @Property
    private int currentIdentityIndex;

    @Property
    private int currentRelationshipIndex;

    @Property
    @Validate("required")
    private NameStyle style;

    private boolean addIdentity = false;

    void onActivate(EventContext eventContext) {
        if (eventContext.getCount() != 1) {
            throw new RuntimeException("Invalid number of parameters in onActivate: " + eventContext.getCount());
        }

        onActivateForId(eventContext.get(String.class, 0));
    }

    void onSelectedFromAddIdentity() { addIdentity = true; }

    protected Object onSuccess() {
        if (addIdentity) {
            return linkSource.createPageRenderLinkWithContext(PersonIdentityAdd.class, viewedObject.getId(), style, NEW_OBJECT_ID);
        } else {
            return null;
        }
    }

    Object onActionFromDeleteEvent(int index) {
        PersonEvent event = getEventForIndex(index);
        viewedObject.removeFromEvents(event);

        context().deleteObject(event);

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

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    Object onActionFromMarkIdentityAsPrimary(int index) {
        viewedObject.setPrimaryIdentity(getIdentityForIndex(index));

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    // --- Child
    public Person getCurrentChildAsPrimary() {
        return getCurrentRelationshipAsPrimary().getChildren().get(currentChildIndex);
    }

    public Person getCurrentChildAsSecondary() {
        return getCurrentRelationshipAsSecondary().getChildren().get(currentChildIndex);
    }

    // --- Event
    public PersonEvent getCurrentEvent() {
        return getEventForIndex(currentEventIndex);
    }

    private PersonEvent getEventForIndex(int index) {
        return viewedObject.getEvents().get(index);
    }

    // --- Identity
    public PersonIdentity getCurrentIdentity() {
        return getIdentityForIndex(currentIdentityIndex);
    }

    private PersonIdentity getIdentityForIndex(int index) {
        return viewedObject.getIdentities().get(index);
    }

    public boolean getCanDeleteIdentities() {
        return viewedObject.getIdentities().size() > 1;
    }

    public boolean getCurrentIdentityIsPrimary() {
        return getCurrentIdentity().equals(viewedObject.getPrimaryIdentity());
    }

    // --- Relationships
    public Relationship getCurrentRelationshipAsPrimary() {
        return viewedObject.getRelationshipsAsPrimary().get(currentRelationshipIndex);
    }

    public Relationship getCurrentRelationshipAsSecondary() {
        return viewedObject.getRelationshipsAsSecondary().get(currentRelationshipIndex);
    }

    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

    @Override
    protected Class<PersonList> getReturnPageType() {
        return PersonList.class;
    }
}
