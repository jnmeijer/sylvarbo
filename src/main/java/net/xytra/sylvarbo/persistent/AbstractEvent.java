package net.xytra.sylvarbo.persistent;

import java.util.Calendar;
import java.util.TimeZone;

import net.xytra.common.cayenne.persistent.AbstractModifiable;
import net.xytra.sylvarbo.enums.DateApproximation;
import net.xytra.sylvarbo.enums.DatePrecision;
import net.xytra.sylvarbo.enums.DisplayableEnum;

public abstract class AbstractEvent extends AbstractModifiable {

    private static final long serialVersionUID = 1L; 

    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

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

            Calendar calendar = Calendar.getInstance(UTC);
            calendar.clear();
            calendar.setTimeInMillis(getDtm());
            if (DatePrecision.Y.toString().equals(getPrecision())) {
                sb.append(calendar.get(Calendar.YEAR));
            } else if (DatePrecision.YM.toString().equals(getPrecision())) {
                sb.append(calendar.get(Calendar.YEAR)).append('-').
                    append(String.format("%02d", calendar.get(Calendar.MONTH)+1));
            } else {
                sb.append(calendar.get(Calendar.YEAR)).append('-').
                    append(String.format("%02d", calendar.get(Calendar.MONTH)+1)).append('-').
                    append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
            }

            displayedDate = sb.toString();
        }

        return displayedDate;
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
