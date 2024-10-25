package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;
import static net.xytra.common.tapestry.CommonTapestryConstants.NUMBER_PATTERN;

import org.apache.cayenne.Cayenne;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractEditModifiablePage;
import net.xytra.sylvarbo.enums.NameStyle;
import net.xytra.sylvarbo.enums.NameType;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonIdentity;
import net.xytra.sylvarbo.persistent.PersonName;

/**
 * To add a PersonIdentity with names.  Not meant to edit
 */
public class PersonIdentityAdd extends AbstractEditModifiablePage<PersonIdentity> {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private NameStyle style;

    @Property
    private Long personId;

    @Property
    private int currentTypeIndex;

    @Property
    private String[] namesArray;

    // --- Passivate/Activate
    protected String[] onPassivate() {
        return new String[] {
                personId != null ? personId.toString() : NEW_OBJECT_ID,
                style != null ? style.toString() : null,
                onPassivateWithSingleParameter() };
    }

    void onActivate(EventContext eventContext) {
        System.err.println("--- NewPerson.onActivate() with ec="+eventContext);

        if (eventContext.getCount() != 3) {
            throw new RuntimeException("Invalid number of arguments: " + eventContext.getCount());
        }

        String personIdString = eventContext.get(String.class, 0);
        if (NEW_OBJECT_ID.equals(personIdString)) {
            personId = null;
        } else if (NUMBER_PATTERN.matcher(personIdString).matches()) {
            personId = Long.valueOf(personIdString);
        } else {
            throw new RuntimeException("Invalid person ID");
        }

        this.style = NameStyle.valueOf(eventContext.get(String.class, 1));
        onActivateForId(eventContext.get(String.class, 2));

        namesArray = new String[style.getTypes().length];
    }

    // ---- Actions
    @Override
    protected void onValidateFromEditForm() {
        // Process the various names
        short seqNum = 0; // Start at zero

        for (int i=0; i<style.getTypes().length; i++) {
            String[] parts = StringUtils.split(namesArray[i], ',');
            if (parts != null && parts.length > 0) {
                for (String currentPart: parts) {
                    if (StringUtils.isNotBlank(currentPart)) {
                        PersonName name = context().newObject(PersonName.class);
                        name.setName(currentPart.trim());
                        name.setSeqNum(seqNum++);
                        name.setType(style.getTypes()[i].toString());
                        name.setCreatedNowBy(session.getUser());
                        name.setModifiedNowBy(session.getUser());
                        editedObject.addToNames(name);
                    }
                }
            }
        }

        // Create a Person to tie this new Identity to
        Person person;
        if (personId == null) {
            person = context().newObject(Person.class);
            person.setCreatedNowBy(session.getUser());
        } else {
            person = Cayenne.objectForPK(context(), Person.class, personId);
        }
        person.setModifiedNowBy(session.getUser());

        // Tie the identity
        if (person.getPrimaryIdentity() == null) {
            person.setPrimaryIdentity(editedObject);
        }
        person.addToIdentities(editedObject);

        super.onValidateFromEditForm();
    }

    protected Object onSuccess() {
        // On success, need personId to be set so we can then navigate to PersonView
        personId = editedObject.getPerson().getId().longValue();

        return getSuccessPageObject();
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
        if (personId != null) {
            return linkSource.createPageRenderLinkWithContext(PersonView.class, personId);
        } else {
            return PersonList.class;
        }
    }

}
