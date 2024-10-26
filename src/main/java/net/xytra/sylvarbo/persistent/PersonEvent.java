package net.xytra.sylvarbo.persistent;

import net.xytra.sylvarbo.enums.DisplayableEnum;
import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.persistent.auto._PersonEvent;

public class PersonEvent extends _PersonEvent {

    private static final long serialVersionUID = 1L; 

    public String getDisplayedType() {
        return PersonEventType.valueOf(getType()).getDisplayed();
    }

    public DisplayableEnum getTypeEnum() {
        String typeStr = getType();

        if (typeStr == null) {
            return null;
        } else {
            return PersonEventType.valueOf(typeStr);
        }
    }

}
