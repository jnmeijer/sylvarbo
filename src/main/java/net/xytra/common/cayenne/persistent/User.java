package net.xytra.common.cayenne.persistent;

import java.util.List;
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

    public static void main(String[] args) {
        System.err.println(encodePassword("admin"));
    }

}
