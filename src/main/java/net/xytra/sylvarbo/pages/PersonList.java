package net.xytra.sylvarbo.pages;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import net.xytra.sylvarbo.persistent.CayenneService;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.session.Session;

public class PersonList {
    //@Property
    @SessionState
    protected Session session;

    public PersonList() {
        this.session = new Session(CayenneService.getInstance().sharedContext());
    }

    protected ObjectContext context() {
        return session.context();
    }

    @Property
    protected Person currentObject;

    public List<Person> getPersistedObjects() {
        // Get persisted objects to list
        return ObjectSelect.query(Person.class).select(context());
    }

}
