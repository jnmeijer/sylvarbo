package net.xytra.sylvarbo.enums;

public enum DatePrecision implements DisplayableEnum {
    YMD("yyyy-MM-dd", "YMD"),
    YM("yyyy-MM", "YM"),
    Y("yyyy", "Y");

    private String dateFormat;
    private String displayed;

    private DatePrecision(String dateFormat, String displayed) {
        this.dateFormat = dateFormat;
        this.displayed = displayed;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getDisplayed() {
        return displayed;
    }
}
