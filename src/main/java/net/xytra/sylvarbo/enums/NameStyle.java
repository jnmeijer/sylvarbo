package net.xytra.sylvarbo.enums;

public enum NameStyle {
    WESTERN_MODERN("Modern Western");

    private String name;

    private NameStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
