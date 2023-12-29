package net.xytra.sylvarbo.enums;

public enum NameType {
    TITLE("Title"),
    GIVEN("Given"),
    PATRONYM("Patronym"),
    SURNAME("Surname"),
    SUFFIX("Suffix");

    private String name;

    private NameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
