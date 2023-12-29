package net.xytra.common.tapestry.session;

import org.apache.cayenne.ObjectContext;

import net.xytra.sylvarbo.persistent.CayenneService;

public class Session {
    private final ObjectContext context;
    private ObjectContext childContext = null;

    private boolean isActive = true;

    public Session(ObjectContext objectContext) {
        this.context = objectContext;
    }

    public ObjectContext context() {
        return context;
    }

    public ObjectContext secondaryContext() {
        if (childContext == null) {
            childContext = CayenneService.getInstance().newChildContext(context());
        }

        return (childContext);
    }

    public void resetSecondaryContext() {
        childContext.rollbackChangesLocally();
        childContext = null;
    }

    public boolean isActive() {
        return isActive;
    }

    public void invalidate() {
        isActive = false;
    }
}
