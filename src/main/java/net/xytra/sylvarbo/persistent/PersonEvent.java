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
        return PersonEventType.valueOf(getType()).getDisplayName();
    }

    public String getDisplayedDate() {
        // With approximation
        if (displayedDate == null) {
            displayedDate = DateApproximation.valueOf(getApproximation()).getDisplayed() + ' ' +
                    new SimpleDateFormat(DatePrecision.valueOf(getPrecision()).getDateFormat()).format(getDate());
        }

        return displayedDate;
    }

    /*
     * @return the timestamp as a java.util.Date object.
     */
    private Date getDate() {
        return new Date(getDtm());
    }

}
