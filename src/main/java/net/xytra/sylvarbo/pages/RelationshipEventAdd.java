package net.xytra.sylvarbo.pages;

import org.apache.cayenne.Cayenne;
import org.apache.tapestry5.SelectModel;

import net.xytra.common.cayenne.persistent.AbstractModifiable;
import net.xytra.sylvarbo.base.AbstractEventAdd;
import net.xytra.sylvarbo.enums.RelationshipEventType;
import net.xytra.sylvarbo.model.PresetModels;
import net.xytra.sylvarbo.persistent.Relationship;
import net.xytra.sylvarbo.persistent.RelationshipEvent;

/**
 * To add a PersonIdentity with names.  Not meant to edit
 */
public class RelationshipEventAdd extends AbstractEventAdd<RelationshipEvent> {
    protected AbstractModifiable tieEventToEventable(Long objectId) {
        // Get the Person to tie this new Event to:
        Relationship relationship = Cayenne.objectForPK(context(), Relationship.class, objectId);

        // Tie the event
        editedObject.setRelationship(relationship);

        return relationship;
    }

    public RelationshipEventType getEventType() {
        return (RelationshipEventType)eventType;
    }

    public void setEventType(RelationshipEventType eventType) {
        this.eventType = eventType;
    }

    // ---- Models
    public SelectModel getEventTypeModel() {
        return PresetModels.RelationshipEventTypeSelectModel;
    }

    // ---- Utility
    @Override
    protected Class<RelationshipEvent> getObjectType() {
        return RelationshipEvent.class;
    }

    @Override
    protected Object getSuccessPageObject() {
        return linkSource.createPageRenderLinkWithContext(RelationshipView.class, objectId);
    }

}
