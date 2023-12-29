package net.xytra.sylvarbo.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

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
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private NameStyle style;

    @Property
    private int currentTypeIndex;

    @Property
    private String[] namesArray;

    // --- Passivate/Activate
    protected String[] onPassivate() {
        return new String[] { style != null? style.toString() : null, onPassivateWithSingleParameter() };
    }

    void onActivate(EventContext eventContext) {
        System.err.println("--- NewPerson.onActivate() with ec="+eventContext);

        //ObjectContext context = session.secondaryContext();

        if (eventContext.getCount() != 2) {
            throw new RuntimeException("Invalid number of arguments: " + eventContext.getCount());
        }

        this.style = NameStyle.valueOf(eventContext.get(String.class, 0));
        onActivateForId(eventContext.get(String.class, 1));

        namesArray = new String[style.getTypes().length];
    }

    // ---- Actions
    @Override
    protected void onValidateFromEditForm() {
        // Process the various names
        short seqNum = 0; // Start at zero

        for (int i=0; i<style.getTypes().length; i++) {
            String[] parts = StringUtils.split(namesArray[i]);
            if (parts != null && parts.length > 0) {
                for (String currentPart: parts) {
                    PersonName name = context().newObject(PersonName.class);
                    name.setName(currentPart);
                    name.setSeqNum(seqNum++);
                    name.setType(style.getTypes()[i].toString());
                    editedObject.addToPersonNames(name);
                }
            }
        }

        // Create a Person to tie this new Identity to
        Person person = context().newObject(Person.class);
        person.setPrimaryIdentity(editedObject);
        person.addToPersonIdentities(editedObject);

        super.onValidateFromEditForm();
    }

    // ---- Property methods
    public NameType getCurrentType() {
        return style.getTypes()[currentTypeIndex];
    }

    public String getCurrentTypeNames() {
        return namesArray[currentTypeIndex];
    }

    public void setCurrentTypeNames(String names) {
        namesArray[currentTypeIndex] = names;
    }

    // ---- Utility
    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<PersonIdentity> getObjectType() {
        return PersonIdentity.class;
    }

    @Override
    protected Object getSuccessPageObject() {
        return linkSource.createPageRenderLinkWithContext(PersonView.class, editedObject.getId());
    }

}
