package net.xytra.sylvarbo.enums;

public enum RelationshipEventType implements DisplayableEnum {
    ENGAGEMENT("Engagement"),
    WEDDING("Wedding"),
    ANNULMENT("Annulment"),
    SEPARATION("Separation"),
    DIVORCE("Divorce"),
    CIVIL_UNION_START("Civil union start"),
    CIVIL_UNION_END("Civil union end");

    private String displayed;

    private RelationshipEventType(String displayed) {
        this.displayed = displayed;
    }

    public String getDisplayed() {
        return displayed;
    }

}
