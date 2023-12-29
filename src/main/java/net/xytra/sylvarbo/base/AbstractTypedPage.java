package net.xytra.sylvarbo.base;

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import net.xytra.common.cayenne.persistent.AbstractPersistentWithId;
import net.xytra.common.tapestry.session.Session;

// Abstract page to list ID'd objects
public abstract class AbstractTypedPage<T extends AbstractPersistentWithId> {
    @Property
    @SessionState
    protected Session session;

    protected ObjectContext context() {
        return session.context();
    }

    protected abstract Class<T> getObjectType();

}
