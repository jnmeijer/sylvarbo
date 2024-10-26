package net.xytra.sylvarbo.base;

import static net.xytra.common.tapestry.CommonTapestryConstants.NUMBER_PATTERN;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.common.cayenne.persistent.AbstractModifiable;
import net.xytra.sylvarbo.enums.DateApproximation;
import net.xytra.sylvarbo.enums.DatePrecision;
import net.xytra.sylvarbo.enums.DisplayableEnum;
import net.xytra.sylvarbo.model.PresetModels;
import net.xytra.sylvarbo.persistent.AbstractEvent;

/**
 * To add a PersonIdentity with names.  Not meant to edit
 */
public abstract class AbstractEventAdd<T extends AbstractEvent> extends AbstractEditModifiablePage<T> {
    @Inject
    protected PageRenderLinkSource linkSource;

    @Property
    private DateApproximation approximation;

    //@Property
    protected DisplayableEnum eventType;

    @Property
    private DatePrecision precision;

    @Property
    private String dateField;

    @Property
    private String originalDate;

    @Property
    private String location;

    @Property
    protected Long objectId;

    // --- Passivate/Activate
    protected String[] onPassivate() {
        return new String[] {
                objectId.toString(),
                onPassivateWithSingleParameter() };
    }

    void onActivate(EventContext eventContext) {
        // Expect /objectId/eventId
        // - objectId must be numeric
        // - eventId (new/uuid/numeric)
        if (eventContext.getCount() != 2) {
            throw new RuntimeException("Invalid number of arguments: " + eventContext.getCount());
        }

        // objectId
        String objectIdString = eventContext.get(String.class, 0);
        if (NUMBER_PATTERN.matcher(objectIdString).matches()) {
            objectId = Long.valueOf(objectIdString);
        } else {
            throw new RuntimeException("Invalid object ID");
        }

        // eventId
        onActivateForId(eventContext.get(String.class, 1));

        // event properties
        approximation = editedObject.getApproximationEnum();
        eventType = editedObject.getTypeEnum();
        precision = editedObject.getPrecisionEnum();
        location = editedObject.getLocationDesc();
        originalDate = editedObject.getOriginalDate();
    }

    // ---- Actions
    @Override
    protected void onValidateFromEditForm() {
        // Set values from dropdowns:
        editedObject.setType(eventType != null ? eventType.toString() : null);
        editedObject.setPrecision(precision != null ? precision.toString() : null);
        editedObject.setApproximation(approximation != null ? approximation.toString() : null);
        editedObject.setLocationDesc(location);
        editedObject.setOriginalDate(originalDate);

        // Figure out the date if possible
        if (precision != null && StringUtils.isNotBlank(dateField)) {
            try {
                Date date = new SimpleDateFormat(precision.getDateFormat()).parse(dateField);
                editedObject.setDtm(date.getTime());
            } catch (ParseException e) {
                editForm.recordError("Date does not parse");
            }
        }

        AbstractModifiable object = tieEventToEventable(objectId);

        super.onValidateFromEditForm();

        // The following is required otherwise the new item does not get shown
        // in the events list of PersonView:
        context().invalidateObjects(object);
    }

    protected abstract AbstractModifiable tieEventToEventable(Long objectId);

    // ---- Models
    public SelectModel getApproximationModel() {
        return PresetModels.DateApproximationSelectModel;
    }

    public abstract SelectModel getEventTypeModel();

    public SelectModel getPrecisionModel() {
        return PresetModels.DatePrecisionSelectModel;
    }

    // ---- Utility
    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

}
