package net.xytra.sylvarbo.base;

import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.tapestry5.annotations.Property;

import net.xytra.common.cayenne.persistent.AbstractPersistentWithId;

// Abstract page to list ID'd objects
public abstract class AbstractListPage<T extends AbstractPersistentWithId> extends AbstractTypedPage<T> {
    @Property
    protected T currentObject;

    public List<T> getPersistedObjects() {
        // Get persisted objects to list
        return ObjectSelect.query(getObjectType()).where(getQueryExpression()).select(context());
    }

    protected Expression getQueryExpression() {
        return null;
    }

}
