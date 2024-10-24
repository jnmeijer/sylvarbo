package net.xytra.sylvarbo.persistent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.xytra.sylvarbo.enums.PersonEventType;
import net.xytra.sylvarbo.persistent.auto._Person;

public class Person extends _Person {

    private static final long serialVersionUID = 1L; 

    public List<Long> getEventIDsSortedByDtm() {
        return getEvents().values().stream().sorted(Comparator.comparingLong(PersonEvent::getDtm)).map(e -> e.getId()).collect(Collectors.toList());
    }

}
