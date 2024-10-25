package net.xytra.sylvarbo.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.AncestorsViewRowItem;
import net.xytra.sylvarbo.base.AbstractViewPage;
import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.persistent.Person;
import net.xytra.sylvarbo.persistent.PersonEvent;
import net.xytra.sylvarbo.persistent.Relationship;

/**
 * View 5 generations from selected person to great-great-grandparents.
 */
public class AncestorsView extends AbstractViewPage<Person> {
    private final static byte NUM_GENERATIONS = 5;
    private final static int NUM_INDIVIDUALS = 1 << (NUM_GENERATIONS);

    private static final boolean SHOW_EMPTY_ANCESTORS = true;

    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private List<AncestorsViewRowItem> rowItemList;

    @Property
    private AncestorsViewRowItem currentRowItem;

    @Property
    private Integer currentRowId;

    void onActivate(EventContext eventContext) {
        if (eventContext.getCount() != 1) {
            throw new RuntimeException("Invalid number of parameters in onActivate: " + eventContext.getCount());
        }

        onActivateForId(eventContext.get(String.class, 0));

        Map<Long, Person> ancestorsMap = new HashMap<Long, Person>();
        rowItemList = new ArrayList<AncestorsViewRowItem>(NUM_INDIVIDUALS*2);

        ancestorsMap.put(1L, viewedObject);
        discoverParents(1, ancestorsMap, 1, 0);
    }

    private int discoverParents(long identifier, Map<Long, Person> ancestorsMap, int generation, int rowId) {
        Person current = ancestorsMap.get(identifier);
        int initialRowId = rowId;
        AncestorsViewRowItem addedRowItem = new AncestorsViewRowItem(current, identifier, rowId, 1);
        rowItemList.add(addedRowItem);

        Relationship relationship = null;

        if (current != null) {
            relationship = current.getRelationshipAsChild();
            if (relationship != null) {
                ancestorsMap.put(identifier*2, relationship.getPrimaryParent());
                ancestorsMap.put(identifier*2+1, relationship.getSecondaryParent());
            }
        }

        if (generation < NUM_GENERATIONS && (relationship != null || SHOW_EMPTY_ANCESTORS)) {
            rowId = discoverParents(identifier*2, ancestorsMap, generation+1, rowId);
            rowItemList.add(new AncestorsViewRowItem("union placeholder for " + identifier*2 + " and " + (identifier*2+1), 0, ++rowId, NUM_GENERATIONS-generation, 1));
            rowId = discoverParents(identifier*2+1, ancestorsMap, generation+1, ++rowId);    
        }

        addedRowItem.setRowSpan(rowId-initialRowId+1);        

        return rowId;
    }

    public List<Integer> getRowIds() {
        return rowItemList.stream().map(i -> i.getRowId()).distinct().collect(Collectors.toList());
    }

    public List<AncestorsViewRowItem> getItemsForCurrentRowId() {
        return rowItemList.stream().filter(i -> i.getRowId() == currentRowId).collect(Collectors.toList());
    }

    public boolean getIsCurrentRowItemAPerson() {
        return currentRowItem.getItem() instanceof Person;
    }

    public Person getCurrentRowItemPerson() {
        return (Person)currentRowItem.getItem();
    }

    // Details
    public String getBirthOrBaptismLine() {
        PersonEvent birth = null;
        PersonEvent baptism = null;

        for (PersonEvent event: getCurrentRowItemPerson().getEvents().values()) {
            if (event.getTypeEnum() == PersonEventType.BIRTH) {
                birth = event;
            } else if (event.getTypeEnum() == PersonEventType.BAPTISM) {
                baptism = event;
            }
        }

        if (birth != null) {
            return ("n. " + birth.getDisplayedDate());
        } else if (baptism != null) {
            return ("b. " + baptism.getDisplayedDate());
        } else {
            return null;
        }
    }

    public String getDeathOrBurialLine() {
        PersonEvent death = null;
        PersonEvent burial = null;

        for (PersonEvent event: getCurrentRowItemPerson().getEvents().values()) {
            if (event.getTypeEnum() == PersonEventType.DEATH) {
                death = event;
            } else if (event.getTypeEnum() == PersonEventType.BURIAL) {
                burial = event;
            }
        }

        if (death != null) {
            return ("d. " + death.getDisplayedDate());
        } else if (burial != null) {
            return ("s. " + burial.getDisplayedDate());
        } else {
            return null;
        }
    }

    // Headers
    @Property
    private String currentHeader;

    public List<String> getHeaders() {
        List<String> headers = new ArrayList<String>(NUM_GENERATIONS);
        for (int i=0; i<NUM_GENERATIONS; i++) {
            headers.add("Generation " + (i+1));
        }

        return headers;
    }

    public String getHeaderWidth() {
        return String.format("%.1f%%", 100.0/NUM_GENERATIONS);
    }
    // Basic stuff
    @Override
    protected Number parseIdString(String id) {
        return Long.valueOf(id);
    }

    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

}
