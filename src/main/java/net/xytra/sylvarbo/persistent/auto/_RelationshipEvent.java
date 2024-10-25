package net.xytra.sylvarbo.persistent.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.cayenne.exp.Property;

import net.xytra.common.cayenne.persistent.User;
import net.xytra.sylvarbo.persistent.AbstractEvent;
import net.xytra.sylvarbo.persistent.Relationship;

/**
 * Class _RelationshipEvent was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _RelationshipEvent extends AbstractEvent {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<String> APPROXIMATION = Property.create("approximation", String.class);
    public static final Property<Date> CREATED_DTM = Property.create("createdDtm", Date.class);
    public static final Property<Long> DTM = Property.create("dtm", Long.class);
    public static final Property<String> LOCATION_DESC = Property.create("locationDesc", String.class);
    public static final Property<Date> MODIFIED_DTM = Property.create("modifiedDtm", Date.class);
    public static final Property<String> ORIGINAL_DATE = Property.create("originalDate", String.class);
    public static final Property<String> PRECISION = Property.create("precision", String.class);
    public static final Property<String> TYPE = Property.create("type", String.class);
    public static final Property<Relationship> RELATIONSHIP = Property.create("relationship", Relationship.class);
    public static final Property<User> USER_CREATED = Property.create("userCreated", User.class);
    public static final Property<User> USER_MODIFIED = Property.create("userModified", User.class);

    protected String approximation;
    protected Date createdDtm;
    protected Long dtm;
    protected String locationDesc;
    protected Date modifiedDtm;
    protected String originalDate;
    protected String precision;
    protected String type;

    protected Object relationship;
    protected Object userCreated;
    protected Object userModified;

    public void setApproximation(String approximation) {
        beforePropertyWrite("approximation", this.approximation, approximation);
        this.approximation = approximation;
    }

    public String getApproximation() {
        beforePropertyRead("approximation");
        return this.approximation;
    }

    public void setCreatedDtm(Date createdDtm) {
        beforePropertyWrite("createdDtm", this.createdDtm, createdDtm);
        this.createdDtm = createdDtm;
    }

    public Date getCreatedDtm() {
        beforePropertyRead("createdDtm");
        return this.createdDtm;
    }

    public void setDtm(long dtm) {
        beforePropertyWrite("dtm", this.dtm, dtm);
        this.dtm = dtm;
    }

    public long getDtm() {
        beforePropertyRead("dtm");
        if(this.dtm == null) {
            return 0;
        }
        return this.dtm;
    }

    public void setLocationDesc(String locationDesc) {
        beforePropertyWrite("locationDesc", this.locationDesc, locationDesc);
        this.locationDesc = locationDesc;
    }

    public String getLocationDesc() {
        beforePropertyRead("locationDesc");
        return this.locationDesc;
    }

    public void setModifiedDtm(Date modifiedDtm) {
        beforePropertyWrite("modifiedDtm", this.modifiedDtm, modifiedDtm);
        this.modifiedDtm = modifiedDtm;
    }

    public Date getModifiedDtm() {
        beforePropertyRead("modifiedDtm");
        return this.modifiedDtm;
    }

    public void setOriginalDate(String originalDate) {
        beforePropertyWrite("originalDate", this.originalDate, originalDate);
        this.originalDate = originalDate;
    }

    public String getOriginalDate() {
        beforePropertyRead("originalDate");
        return this.originalDate;
    }

    public void setPrecision(String precision) {
        beforePropertyWrite("precision", this.precision, precision);
        this.precision = precision;
    }

    public String getPrecision() {
        beforePropertyRead("precision");
        return this.precision;
    }

    public void setType(String type) {
        beforePropertyWrite("type", this.type, type);
        this.type = type;
    }

    public String getType() {
        beforePropertyRead("type");
        return this.type;
    }

    public void setRelationship(Relationship relationship) {
        setToOneTarget("relationship", relationship, true);
    }

    public Relationship getRelationship() {
        return (Relationship)readProperty("relationship");
    }

    public void setUserCreated(User userCreated) {
        setToOneTarget("userCreated", userCreated, true);
    }

    public User getUserCreated() {
        return (User)readProperty("userCreated");
    }

    public void setUserModified(User userModified) {
        setToOneTarget("userModified", userModified, true);
    }

    public User getUserModified() {
        return (User)readProperty("userModified");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "approximation":
                return this.approximation;
            case "createdDtm":
                return this.createdDtm;
            case "dtm":
                return this.dtm;
            case "locationDesc":
                return this.locationDesc;
            case "modifiedDtm":
                return this.modifiedDtm;
            case "originalDate":
                return this.originalDate;
            case "precision":
                return this.precision;
            case "type":
                return this.type;
            case "relationship":
                return this.relationship;
            case "userCreated":
                return this.userCreated;
            case "userModified":
                return this.userModified;
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
            case "approximation":
                this.approximation = (String)val;
                break;
            case "createdDtm":
                this.createdDtm = (Date)val;
                break;
            case "dtm":
                this.dtm = (Long)val;
                break;
            case "locationDesc":
                this.locationDesc = (String)val;
                break;
            case "modifiedDtm":
                this.modifiedDtm = (Date)val;
                break;
            case "originalDate":
                this.originalDate = (String)val;
                break;
            case "precision":
                this.precision = (String)val;
                break;
            case "type":
                this.type = (String)val;
                break;
            case "relationship":
                this.relationship = val;
                break;
            case "userCreated":
                this.userCreated = val;
                break;
            case "userModified":
                this.userModified = val;
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
        out.writeObject(this.approximation);
        out.writeObject(this.createdDtm);
        out.writeObject(this.dtm);
        out.writeObject(this.locationDesc);
        out.writeObject(this.modifiedDtm);
        out.writeObject(this.originalDate);
        out.writeObject(this.precision);
        out.writeObject(this.type);
        out.writeObject(this.relationship);
        out.writeObject(this.userCreated);
        out.writeObject(this.userModified);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.approximation = (String)in.readObject();
        this.createdDtm = (Date)in.readObject();
        this.dtm = (Long)in.readObject();
        this.locationDesc = (String)in.readObject();
        this.modifiedDtm = (Date)in.readObject();
        this.originalDate = (String)in.readObject();
        this.precision = (String)in.readObject();
        this.type = (String)in.readObject();
        this.relationship = in.readObject();
        this.userCreated = in.readObject();
        this.userModified = in.readObject();
    }

}
