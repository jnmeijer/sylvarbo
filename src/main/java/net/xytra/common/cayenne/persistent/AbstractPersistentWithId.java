package net.xytra.common.cayenne.persistent;

import java.util.UUID;

import org.apache.cayenne.BaseDataObject;

public abstract class AbstractPersistentWithId extends BaseDataObject {

    private final String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public Number getId() {
        return (Number)getObjectId().getIdSnapshot().get("ID");
    }

    public String getIdOrUuid() {
        Number id = getId();
        if (id != null) {
            return id.toString();
        } else {
            return getUuid();
        }
    }

}
