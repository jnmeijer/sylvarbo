package net.xytra.sylvarbo.pages;

import org.apache.tapestry5.EventContext;

import net.xytra.sylvarbo.base.AbstractViewPage;
import net.xytra.sylvarbo.persistent.Person;

/**
 * First page when creating a new person is actually creating the primaryIdentity name
 */
public class PersonView extends AbstractViewPage<Person> {

    void onActivate(EventContext eventContext) {
        if (eventContext.getCount() != 1) {
            throw new RuntimeException("Invalid number of parameters in onActivate: " + eventContext.getCount());
        }

        onActivateForId(eventContext.get(String.class, 0));
    }

    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

    @Override
    protected Class<PersonList> getReturnPageType() {
        return PersonList.class;
    }
}
