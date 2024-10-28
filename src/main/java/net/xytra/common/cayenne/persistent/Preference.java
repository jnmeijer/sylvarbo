package net.xytra.common.cayenne.persistent;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import net.xytra.common.cayenne.persistent.auto._Preference;

public class Preference extends _Preference {

    private static final long serialVersionUID = 1L; 

    public static class Keys {
        public static final String ANCESTORS_VIEW_DEPTH = "ANCESTORS_VIEW_DEPTH";
    }

    public static String getDefaultValueForKey(String key, ObjectContext context) {
        Preference preference = ObjectSelect.query(Preference.class).where(Preference.KEY.eq(key)).selectFirst(context);

        return preference != null ? preference.getDefaultValue() : null;
    }
}
