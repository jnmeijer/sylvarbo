package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import org.apache.cayenne.Cayenne;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractViewPage;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.Relationship;

/**
 * Page to view a relationship
 */
public class RelationshipView extends AbstractViewPage<Relationship> {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private int currentChildIndex;

    @Property
    private int currentEventIndex;

    void onActivate(EventContext eventContext) {
        if (eventContext.getCount() != 1 && eventContext.getCount() != 3) {
            throw new RuntimeException("Invalid number of parameters in onActivate: " + eventContext.getCount());
        }

        String id = eventContext.get(String.class, 0);
        if (eventContext.getCount() == 1) {
            onActivateForId(id);

            if (viewedObject == null) {
                throw new RuntimeException("Object not found for ID: " + id);
            }
        } else { // 3
            if (NEW_OBJECT_ID.equals(id)) {
                String linkType = eventContext.get(String.class, 1);

                int personId = eventContext.get(Integer.class, 2);
                Person person = Cayenne.objectForPK(context(), Person.class, personId);
                if (person == null) {
                    throw new RuntimeException("Invalid person ID: " + personId);
                }

                Relationship relationship = context().newObject(getObjectType());
                if ("c".equals(linkType)) {
                    relationship.addToChildren(person);
                } else if ("p1".equals(linkType)) {
                    relationship.setPrimaryParent(person);
                } else if ("p2".equals(linkType)) {
                    relationship.setSecondaryParent(person);
                } else {
                    throw new RuntimeException("Invalid relationship link type: " + linkType);
                }
                relationship.setCreatedNowBy(session.getUser());
                relationship.setModifiedNowBy(session.getUser());

                // Get this relationship and link created immediately
                context().commitChanges();

                viewedObject = relationship;
            } else {
                throw new RuntimeException("Expecting NEW ID: " + id);
            }
        }
    }

    // --- Actions
    Object onActionFromRemoveChild(int index) {
        Person child = getChildForIndex(index);
        viewedObject.removeFromChildren(child);

        return cleanUpOrphanedRelationshipAndReturnPage();
    }

    Object onActionFromRemovePrimaryParent() {
        viewedObject.setPrimaryParent(null);

        return cleanUpOrphanedRelationshipAndReturnPage();
    }

    Object onActionFromRemoveSecondaryParent() {
        viewedObject.setSecondaryParent(null);

        return cleanUpOrphanedRelationshipAndReturnPage();
    }

    private Object cleanUpOrphanedRelationshipAndReturnPage() {
        boolean orphaned = viewedObject.getPrimaryParent() == null &&
                viewedObject.getSecondaryParent() == null &&
                viewedObject.getChildren().size() == 0;

        if (orphaned) {
            context().deleteObject(viewedObject);
        }

        context().commitChanges();

        if (orphaned) {
            return PersonList.class;
        }

        // Default, not orphaned: return same page
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
