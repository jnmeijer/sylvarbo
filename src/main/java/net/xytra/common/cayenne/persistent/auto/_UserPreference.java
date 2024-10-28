package net.xytra.common.cayenne.persistent.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import net.xytra.common.cayenne.persistent.Preference;
import net.xytra.common.cayenne.persistent.User;

/**
 * Class _UserPreference was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _UserPreference extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<String> VALUE = Property.create("value", String.class);
    public static final Property<Preference> PREFERENCE = Property.create("preference", Preference.class);
    public static final Property<User> USER = Property.create("user", User.class);

    protected String value;

    protected Object preference;
    protected Object user;

    public void setValue(String value) {
        beforePropertyWrite("value", this.value, value);
        this.value = value;
    }

    public String getValue() {
        beforePropertyRead("value");
        return this.value;
    }

    public void setPreference(Preference preference) {
        setToOneTarget("preference", preference, true);
    }

    public Preference getPreference() {
        return (Preference)readProperty("preference");
    }

    public void setUser(User user) {
        setToOneTarget("user", user, true);
    }

    public User getUser() {
        return (User)readProperty("user");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "value":
                return this.value;
            case "preference":
                return this.preference;
            case "user":
                return this.user;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "value":
                this.value = (String)val;
                break;
            case "preference":
                this.preference = val;
                break;
            case "user":
                this.user = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.value);
        out.writeObject(this.preference);
        out.writeObject(this.user);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.value = (String)in.readObject();
        this.preference = in.readObject();
        this.user = in.readObject();
    }

}