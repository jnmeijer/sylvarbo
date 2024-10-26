package net.xytra.sylvarbo.pages;

import org.apache.cayenne.Cayenne;
import org.apache.tapestry5.SelectModel;

import net.xytra.common.cayenne.persistent.AbstractModifiable;
import net.xytra.sylvarbo.base.AbstractEventAdd;
import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.model.PresetModels;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonEvent;

/**
 * To add a PersonIdentity with names.  Not meant to edit
 */
public class PersonEventAdd extends AbstractEventAdd<PersonEvent> {
    protected AbstractModifiable tieEventToEventable(Long objectId) {
        // Get the Person to tie this new Event to:
        Person person = Cayenne.objectForPK(context(), Person.class, objectId);

        // Tie the event
        editedObject.setPerson(person);

        return person;
    }

    public PersonEventType getEventType() {
        return (PersonEventType)eventType;
    }

    public void setEventType(PersonEventType eventType) {
        this.eventType = eventType;
    }

    // ---- Models
    public SelectModel getEventTypeModel() {
        return PresetModels.PersonEventTypeSelectModel;
    }

    // ---- Utility
    @Override
    protected Class<PersonEvent> getObjectType() {
        return PersonEvent.class;
    }

    @Override
    protected Object getSuccessPageObject() {
        return linkSource.createPageRenderLinkWithContext(PersonView.class, objectId);
    }

}
