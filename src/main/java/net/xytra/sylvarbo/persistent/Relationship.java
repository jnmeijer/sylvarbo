package net.xytra.sylvarbo.persistent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.xytra.sylvarbo.persistent.auto._Relationship;

public class Relationship extends _Relationship {

    private static final long serialVersionUID = 1L; 

    public List<Long> getEventIDsSortedByDtm() {
        return getEvents().values().stream().sorted(Comparator.comparingLong(RelationshipEvent::getDtm)).map(e -> e.getId()).collect(Collectors.toList());
    }
}
