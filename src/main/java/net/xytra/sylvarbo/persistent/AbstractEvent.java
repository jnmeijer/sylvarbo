package net.xytra.sylvarbo.persistent;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.xytra.common.cayenne.persistent.AbstractModifiable;
import net.xytra.sylvarbo.enums.DateApproximation;
import net.xytra.sylvarbo.enums.DatePrecision;
import net.xytra.sylvarbo.enums.DisplayableEnum;

public abstract class AbstractEvent extends AbstractModifiable {

    private static final long serialVersionUID = 1L; 

    private String displayedDate;

    public abstract String getApproximation();
    public abstract long getDtm();
    public abstract String getLocationDesc();
    public abstract String getOriginalDate();
    public abstract String getPrecision();
    public abstract String getType();

    public abstract void setApproximation(String approximation);
    public abstract void setDtm(long dtm);
    public abstract void setLocationDesc(String locationDesc);
    public abstract void setOriginalDate(String originalDate);
    public abstract void setPrecision(String precision);
    public abstract void setType(String type);

    // ---
    public abstract String getDisplayedType();

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
    public abstract DisplayableEnum getTypeEnum();

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
