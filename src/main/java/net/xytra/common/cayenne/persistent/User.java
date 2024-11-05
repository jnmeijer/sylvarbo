package net.xytra.common.cayenne.persistent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.commons.codec.digest.DigestUtils;

import net.xytra.common.cayenne.persistent.auto._User;
import net.xytra.sylvarbo.persistent.CayenneService;

public class User extends _User {

    private static final long serialVersionUID = 1L; 

    public static User getUserForUsernameAndPassword(String username, String password) {
        ObjectContext context = CayenneService.getInstance().newObjectContext();

        return ObjectSelect.
                query(User.class).
                where(User.USERNAME.eq(username)).
                and(User.PASSWORD.eq(encodePassword(password))).
                selectOne(context);
    }

    private static String encodePassword(String password) {
        return DigestUtils.sha256Hex("i@U<" + password);
    }

    public String getPreferenceValueOrDefault(String key) {
        List<UserPreference> userPref = getUserPreferences().stream().filter(up -> up.getPreference().getKey().equals(Preference.Keys.ANCESTORS_VIEW_DEPTH)).collect(Collectors.toList());
        if (userPref.size() == 0) {
            return Preference.getDefaultValueForKey(key, getObjectContext());
        }

        return userPref.get(0).getValue();
    }

    public Map<String, String> getAllPreferenceValuesOrDefaults() {
        Map<String, String> userPrefs = new HashMap<String, String>();

        // First get the user's prefs as they are set:
        Iterator<UserPreference> upIt = getUserPreferences().iterator();
        while (upIt.hasNext()) {
            UserPreference up = upIt.next();
            userPrefs.put(up.getPreference().getKey(), up.getValue());
        }

        // Then set the missing from defaults:
        Iterator<Preference> pIt = ObjectSelect.query(Preference.class).select(getObjectContext()).iterator();
        while (pIt.hasNext()) {
            Preference p = pIt.next();
            if (!userPrefs.containsKey(p.getKey())) {
                userPrefs.put(p.getKey(), p.getDefaultValue());
            }
        }

        return userPrefs;
    }

    public void replaceUserPreferences(Map<String, String> newPrefs) {
        // First remove all old user preferences
        getObjectContext().deleteObjects(getUserPreferences());

        // Get Preference objects
        Map<String, Preference> keyToPrefMap = new HashMap<String, Preference>();
        ObjectSelect.query(Preference.class).select(getObjectContext()).
                stream().forEach(p -> keyToPrefMap.put(p.getKey(), p));

        // Then create and add new UserPreferences
        Iterator<String> keyIt = newPrefs.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = keyIt.next();

            Preference pref = keyToPrefMap.get(key);
            if (pref != null) {
                UserPreference up = getObjectContext().newObject(UserPreference.class);
                up.setPreference(pref);
                up.setUser(this);
                up.setValue(newPrefs.get(key));
            }
        }
    }

    public static void main(String[] args) {
        System.err.println(encodePassword("admin"));
    }

}
