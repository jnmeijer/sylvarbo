package net.xytra.sylvarbo.model;

import org.apache.tapestry5.SelectModel;

import net.xytra.sylvarbo.enums.DateApproximation;
import net.xytra.sylvarbo.enums.DatePrecision;
import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.enums.RelationshipEventType;

public interface PresetModels {
    public static final SelectModel DateApproximationSelectModel =
            new DisplayableSelectModel(DateApproximation.values());

    public static final SelectModel DatePrecisionSelectModel =
            new DisplayableSelectModel(DatePrecision.values());

    public static final SelectModel PersonEventTypeSelectModel =
            new DisplayableSelectModel(PersonEventType.values());

    public static final SelectModel RelationshipEventTypeSelectModel =
            new DisplayableSelectModel(RelationshipEventType.values());

}
