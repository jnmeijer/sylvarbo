package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NUMBER_PATTERN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractTypedPage;
import net.xytra.sylvarbo.enums.NameType;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonIdentity;
import net.xytra.sylvarbo.persistent.PersonName;
import net.xytra.sylvarbo.persistent.Relationship;
import net.xytra.sylvarbo.search.PickPersonSearchCriteria;

public class PickPerson extends AbstractTypedPage<Person> {
    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private PersonIdentity currentPersonIdentity;

    @Property
    private Long relationshipId;

    @Property
    private String pickType;

    @SessionState
    @Property
    private PickPersonSearchCriteria criteria;

    @Component
    private Form searchForm;

    // --- Passivate/Activate
    protected String[] onPassivate() {
        return new String[] {
                relationshipId.toString(),
                pickType };
    }

    void onActivate(EventContext eventContext) {
        // Expect /relationshipId/pickType
        // - relationshipId must be numeric
        // - pickType (String)
        System.err.println("--- PickPerson.onActivate() with ec="+eventContext);

        if (eventContext.getCount() != 2) {
            throw new RuntimeException("Invalid number of arguments: " + eventContext.getCount());
        }

        // relationshipId
        String relationshipIdString = eventContext.get(String.class, 0);
        if (NUMBER_PATTERN.matcher(relationshipIdString).matches()) {
            relationshipId = Long.valueOf(relationshipIdString);
        } else {
            throw new RuntimeException("Invalid relationship ID");
        }

        // pickType
        pickType = eventContext.get(String.class, 1);
    }

    // ---- Form data
    public List<PersonIdentity> getResults() {
        System.err.println(criteria);
        //Expression exp = null;
        List<Expression> expList = new ArrayList<Expression>();
        if (criteria.givenName != null) {
            expList.add(createNameMatchExpression(NameType.GIVEN, criteria.givenName));
        }
        if (criteria.patronym != null) {
            expList.add(createNameMatchExpression(NameType.PATRONYM, criteria.patronym));
        }
        if (criteria.surname != null) {
            expList.add(createNameMatchExpression(NameType.SURNAME, criteria.surname));
        }

        // No results returned if no criteria specified
        if (expList.isEmpty()) {
            return new ArrayList<PersonIdentity>();
        } else {
            //List<Person> resultList = ObjectSelect.query(Person.class).select(context());
            //Arrays.asList(Cayenne.objectForPK(context(), Person.class, 241));
            //return resultList;
            //return ObjectSelect.query(Person.class).where(ExpressionFactory.and(expList)).select(context());
            // Get list of Persons from first criteria
            List<PersonName> names = ObjectSelect.query(PersonName.class).where(expList.get(0)).select(context());
            //System.err.println("names: " + names);
            //List<Person> matches = names.stream().map(n -> n.getIdentity().getPerson()).collect(Collectors.toList());
            return names.stream().map(n -> n.getIdentity()).collect(Collectors.toList());
        }
    }

    private Expression createNameMatchExpression(NameType type, String name) {
        return PersonName.TYPE.eq(type.toString())
                .andExp(PersonName.NAME.startsWithIgnoreCase(name));
    }

    // ---- Actions
    protected Object onSuccess() {
        return null;
    }

    public Object onActionFromPick(long personId) {
        System.err.println("--- onActionFromPick: personId="+personId);
        Relationship relationship = Cayenne.objectForPK(context(), Relationship.class, relationshipId);
        Person person = Cayenne.objectForPK(context(), Person.class, personId);

        if (relationship != null && person != null) {
            boolean wasSet = false;

            if ("c".equals(pickType)) {
                relationship.addToChildren(person);
                wasSet = true;
            } else if ("p1".equals(pickType)) {
                relationship.setPrimaryParent(person);
                wasSet = true;
            } else if ("p2".equals(pickType)) {
                relationship.setSecondaryParent(person);
                wasSet = true;
            }

            if (wasSet) {
                relationship.setModifiedNowBy(session.getUser());

                context().commitChanges();
                System.err.println("--- Success!");
            } else {
                throw new RuntimeException("Invalid relationship link type! " + pickType);
            }
        } else {
            System.err.println("--- No object found!");
        }

        return linkSource.createPageRenderLinkWithContext(RelationshipView.class, relationshipId);
    }

    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

}
