package net.xytra.sylvarbo.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractViewPage;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonIdentity;
import net.xytra.sylvarbo.persistent.Relationship;

/**
 * First page when creating a new person is actually creating the primaryIdentity name
 */
public class RelationshipView extends AbstractViewPage<Relationship> {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private int currentChildIndex;

    @Property
    private int currentEventIndex;

    void onActivate(EventContext eventContext) {
        if (eventContext.getCount() != 1) {
            throw new RuntimeException("Invalid number of parameters in onActivate: " + eventContext.getCount());
        }

        onActivateForId(eventContext.get(String.class, 0));
    }

    // --- Actions
    Object onActionFromRemoveChild(int index) {
        Person child = getChildForIndex(index);
        viewedObject.removeFromChildren(child);

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    Object onActionFromRemovePrimaryParent() {
        viewedObject.setPrimaryParent(null);

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    Object onActionFromRemoveSecondaryParent() {
        viewedObject.setSecondaryParent(null);

        // Save changes
        context().commitChanges();

        // Return to same page
        return null;
    }

    // --- Children
    public Person getCurrentChild() {
        return getChildForIndex(currentChildIndex);
    }

    private Person getChildForIndex(int index) {
        return viewedObject.getChildren().get(index);
    }

    // --- Event

    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<Relationship> getObjectType() {
        return Relationship.class;
    }

    @Override
    protected Object getReturnPageObject() {
        return PersonList.class;
    }

}
