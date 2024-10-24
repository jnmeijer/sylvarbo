package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NUMBER_PATTERN;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cayenne.Cayenne;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractEditPage;
import net.xytra.sylvarbo.enums.DateApproximation;
import net.xytra.sylvarbo.enums.DatePrecision;
import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.model.DisplayableSelectModel;
import net.xytra.sylvarbo.model.PresetModels;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonEvent;

/**
 * To add a PersonIdentity with names.  Not meant to edit
 */
public class PersonEventAdd extends AbstractEditPage<PersonEvent> {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private DateApproximation approximation;

    @Property
    private PersonEventType eventType;

    @Property
    private DatePrecision precision;

    @Property
    private String dateField;

    @Property
    private String location;

    @Property
    private Long personId;

    // --- Passivate/Activate
    protected String[] onPassivate() {
        return new String[] {
                personId.toString(),
                onPassivateWithSingleParameter() };
    }

    void onActivate(EventContext eventContext) {
        // Expect /personId/eventId
        // - personId must be numeric
        // - eventId (new/uuid/numeric)
        System.err.println("--- PersonEventAdd.onActivate() with ec="+eventContext);

        if (eventContext.getCount() != 2) {
            throw new RuntimeException("Invalid number of arguments: " + eventContext.getCount());
        }

        // personId
        String personIdString = eventContext.get(String.class, 0);
        if (NUMBER_PATTERN.matcher(personIdString).matches()) {
            personId = Long.valueOf(personIdString);
        } else {
            throw new RuntimeException("Invalid person ID");
        }

        // eventId
        onActivateForId(eventContext.get(String.class, 1));

        // event properties
        approximation = editedObject.getApproximationEnum();
        eventType = editedObject.getTypeEnum();
        precision = editedObject.getPrecisionEnum();
    }

    // ---- Actions
    @Override
    protected void onValidateFromEditForm() {
        // Set values from dropdowns:
        editedObject.setType(eventType != null ? eventType.toString() : null);
        editedObject.setPrecision(precision != null ? precision.toString() : null);
        editedObject.setApproximation(approximation != null ? approximation.toString() : null);
        editedObject.setLocationDesc(location);

        // Figure out the date if possible
        if (precision != null && StringUtils.isNotBlank(dateField)) {
            try {
                Date date = new SimpleDateFormat(precision.getDateFormat()).parse(dateField);
                editedObject.setDtm(date.getTime());
            } catch (ParseException e) {
                editForm.recordError("Date does not parse");
            }
        }

        // Get the Person to tie this new Event to:
        Person person = Cayenne.objectForPK(context(), Person.class, personId);

        // Tie the event
        editedObject.setPerson(person);

        super.onValidateFromEditForm();

        // The following is required otherwise the new item does not get shown
        // in the events list of PersonView:
        context().invalidateObjects(person);
    }

    // ---- Models
    public SelectModel getApproximationModel() {
        return PresetModels.DateApproximationSelectModel;
    }

    public SelectModel getEventTypeModel() {
        return PresetModels.PersonEventTypeSelectModel;
    }

    public SelectModel getPrecisionModel() {
        return PresetModels.DatePrecisionSelectModel;
    }

    // ---- Utility
    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<PersonEvent> getObjectType() {
        return PersonEvent.class;
    }

    @Override
    protected Object getSuccessPageObject() {
        return linkSource.createPageRenderLinkWithContext(PersonView.class, personId);
    }

}
