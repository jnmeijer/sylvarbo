package net.xytra.sylvarbo.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;

import net.xytra.sylvarbo.base.AbstractEditPage;
import net.xytra.sylvarbo.enums.NameStyle;
import net.xytra.sylvarbo.enums.NameType;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonIdentity;
import net.xytra.sylvarbo.persistent.PersonName;

/**
 * First page when creating a new person is actually creating the primaryIdentity name
 */
public class NewPerson extends AbstractEditPage<PersonIdentity> {
    @Property
    private NameStyle style;

    @Property
    private String title;

    protected String[] onPassivate() {
        return new String[] { style != null? style.toString() : null, onPassivateWithSingleParameter() };
    }

    void onActivate(EventContext eventContext) {
        System.err.println("--- NewPerson.onActivate() with ec="+eventContext);

        //ObjectContext context = session.secondaryContext();

        if (eventContext.getCount() != 2) {
            throw new RuntimeException("blah blah");
        }

        this.style = NameStyle.valueOf(eventContext.get(String.class, 0));
        onActivateForId(eventContext.get(String.class, 1));
    }

    @Override
    protected void onValidateFromEditForm() {
        // Process the various names
        short seqNum = 0; // Start at zero

        String[] titles = StringUtils.split(title);
        if (titles != null && titles.length > 0) {
            for (String currentTitle: titles) {
                PersonName name = context().newObject(PersonName.class);
                name.setName(currentTitle);
                name.setSeqNum(seqNum++);
                name.setType(NameType.TITLE.toString());
                editedObject.addToPersonNames(name);
            }
        }

        // Create a Person to tie this new Identity to
        Person person = context().newObject(Person.class);
        person.setPrimaryIdentity(editedObject);
        person.addToPersonIdentities(editedObject);

        super.onValidateFromEditForm();
    }

    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<PersonIdentity> getObjectType() {
        return PersonIdentity.class;
    }

    @Override
    protected Object getListPageObject() {
        return PersonList.class;
    }

}
