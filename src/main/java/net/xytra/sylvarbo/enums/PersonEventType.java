package net.xytra.sylvarbo.enums;

public enum PersonEventType implements DisplayableEnum {
    BIRTH("Birth"),
    BAPTISM("Baptism"),
    DEATH("Death"),
    BURIAL("Burial");

    private String displayed;

    private PersonEventType(String displayed) {
        this.displayed = displayed;
    }

    public String getDisplayed() {
        return displayed;
    }

}
