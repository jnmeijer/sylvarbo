package net.xytra.sylvarbo.persistent;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.xytra.sylvarbo.enums.DateApproximation;
import net.xytra.sylvarbo.enums.DatePrecision;
import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.persistent.auto._PersonEvent;

public class PersonEvent extends _PersonEvent {

    private static final long serialVersionUID = 1L; 

    private String displayedDate;

    public String getDisplayedType() {
        return PersonEventType.valueOf(getType()).getDisplayed();
    }

    public String getDisplayedDate() {
        // With approximation
        if (displayedDate == null) {
            StringBuilder sb = new StringBuilder();

            DateApproximation approximation = getApproximationEnum();
            if (approximation != null) {
                sb.append(approximation.getDisplayed()).append(' ');
            }

            sb.append(new SimpleDateFormat(DatePrecision.valueOf(getPrecision()).getDateFormat()).format(getDate()));

            displayedDate = sb.toString();
        }

        return displayedDate;
    }

    /*
     * @return the timestamp as a java.util.Date object.
     */
    private Date getDate() {
        return new Date(getDtm());
    }

    // Enum accessors/getters
    public PersonEventType getTypeEnum() {
        String typeStr = getType();

        if (typeStr == null) {
            return null;
        } else {
            return PersonEventType.valueOf(typeStr);
        }
    }

    public DateApproximation getApproximationEnum() {
        String approximationStr = getApproximation();

        if (approximationStr == null) {
            return null;
        } else {
            return DateApproximation.valueOf(approximationStr);
        }
    }

    public DatePrecision getPrecisionEnum() {
        String precisionStr = getPrecision();

        if (precisionStr == null) {
            return null;
        } else {
            return DatePrecision.valueOf(precisionStr);
        }
    }

    public Long getId() {
        return (Long)getObjectId().getIdSnapshot().get("ID");
    }

}
