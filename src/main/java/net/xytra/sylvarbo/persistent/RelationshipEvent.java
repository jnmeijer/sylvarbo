package net.xytra.sylvarbo.persistent;

import net.xytra.sylvarbo.enums.DisplayableEnum;
import net.xytra.sylvarbo.enums.RelationshipEventType;
import net.xytra.sylvarbo.persistent.auto._RelationshipEvent;

public class RelationshipEvent extends _RelationshipEvent {

    private static final long serialVersionUID = 1L; 

    public String getDisplayedType() {
        return RelationshipEventType.valueOf(getType()).getDisplayed();
    }

    public DisplayableEnum getTypeEnum() {
        String typeStr = getType();

        if (typeStr == null) {
            return null;
        } else {
            return RelationshipEventType.valueOf(typeStr);
        }
    }

}
