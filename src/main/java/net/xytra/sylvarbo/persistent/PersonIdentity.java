package net.xytra.sylvarbo.persistent;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.xytra.sylvarbo.persistent.auto._PersonIdentity;

public class PersonIdentity extends _PersonIdentity {

    private static final long serialVersionUID = 1L; 

    private String displayableName;

    public String getDisplayableName() {
        if (displayableName == null) {
            // Sort the names List
            List<PersonName> personNames = getNames();
            Collections.sort(getNames(), new Comparator<PersonName>(){
                public int compare(PersonName n1, PersonName n2) {
                    return n1.getSeqNum() - n2.getSeqNum();
                }
            });

            // Build the visual
            StringBuilder sb = new StringBuilder();

            Iterator<PersonName> pnIt = personNames.iterator();
            if (pnIt.hasNext()) {
                sb.append(pnIt.next().getName());
            }
            while (pnIt.hasNext()) {
                sb.append(' ').append(pnIt.next().getName());
            }

            displayableName = sb.toString();
        }

        return displayableName;
    }
}
