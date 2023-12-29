package net.xytra.sylvarbo.enums;

public enum NameStyle {
    WESTERN_MODERN("Modern Western", new NameType[] {
        NameType.TITLE,
        NameType.GIVEN,
        NameType.PATRONYM,
        NameType.SURNAME,
        NameType.SUFFIX
     });

    private String name;
    private NameType[] types;

    private NameStyle(String name, NameType[] types) {
        this.name = name;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public NameType[] getTypes() {
        return types;
    }
}
