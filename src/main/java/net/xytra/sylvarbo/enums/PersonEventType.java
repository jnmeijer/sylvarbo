package net.xytra.sylvarbo.enums;

public enum PersonEventType {
    BIRTH("Birth"),
    BAPTISM("Baptism"),
    DEATH("Death"),
    BURIAL("Burial");

    private String displayName;

    private PersonEventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
