package net.xytra.sylvarbo.persistent.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.exp.Property;

import net.xytra.common.cayenne.persistent.AbstractPersistentWithId;
import net.xytra.sylvarbo.persistent.PersonIdentity;

/**
 * Class _PersonName was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _PersonName extends AbstractPersistentWithId {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<Short> SEQ_NUM = Property.create("seqNum", Short.class);
    public static final Property<String> TYPE = Property.create("type", String.class);
    public static final Property<PersonIdentity> PERSON_IDENTITY = Property.create("personIdentity", PersonIdentity.class);

    protected String name;
    protected short seqNum;
    protected String type;

    protected Object personIdentity;

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void setSeqNum(short seqNum) {
        beforePropertyWrite("seqNum", this.seqNum, seqNum);
        this.seqNum = seqNum;
    }

    public short getSeqNum() {
        beforePropertyRead("seqNum");
        return this.seqNum;
    }

    public void setType(String type) {
        beforePropertyWrite("type", this.type, type);
        this.type = type;
    }

    public String getType() {
        beforePropertyRead("type");
        return this.type;
    }

    public void setPersonIdentity(PersonIdentity personIdentity) {
        setToOneTarget("personIdentity", personIdentity, true);
    }

    public PersonIdentity getPersonIdentity() {
        return (PersonIdentity)readProperty("personIdentity");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "name":
                return this.name;
            case "seqNum":
                return this.seqNum;
            case "type":
                return this.type;
            case "personIdentity":
                return this.personIdentity;
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
            case "name":
                this.name = (String)val;
                break;
            case "seqNum":
                this.seqNum = val == null ? 0 : (short)val;
                break;
            case "type":
                this.type = (String)val;
                break;
            case "personIdentity":
                this.personIdentity = val;
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
        out.writeObject(this.name);
        out.writeShort(this.seqNum);
        out.writeObject(this.type);
        out.writeObject(this.personIdentity);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.name = (String)in.readObject();
        this.seqNum = in.readShort();
        this.type = (String)in.readObject();
        this.personIdentity = in.readObject();
    }

}
