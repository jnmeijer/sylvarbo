package net.xytra.sylvarbo.enums;

/*
 * Not used for exact dates.
 */
public enum DateApproximation {
    LT("<"),
    LTE("<="),
    GT(">"),
    GTE(">="),
    CIRCA("c");

    private String displayed;

    private DateApproximation(String displayed) {
        this.displayed = displayed;
    }

    public String getDisplayed() {
        return displayed;
    }

}
