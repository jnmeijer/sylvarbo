package net.xytra.common.cayenne.persistent;

import java.util.Date;

public abstract class AbstractModifiable extends AbstractPersistentWithId {
    public void setCreatedNowBy(User user) {
        setCreatedDtm(new Date());
        setUserCreated(user);
    }

    public void setModifiedNowBy(User user) {
        setModifiedDtm(new Date());
        setUserModified(user);
    }

    public abstract User getUserCreated();
    public abstract Date getCreatedDtm();
    public abstract User getUserModified();
    public abstract Date getModifiedDtm();

    public abstract void setUserCreated(User userCreated);
    public abstract void setCreatedDtm(Date createdDtm);
    public abstract void setUserModified(User userModified);
    public abstract void setModifiedDtm(Date modifiedDtm);

}
