package net.xytra.sylvarbo.search;

public class PickPersonSearchCriteria {
    public String givenName;
    public String patronym;
    public String surname;

    public String toString() {
        return "givenName="+givenName+"; patronym="+patronym+"; surname="+surname;
    }
}
