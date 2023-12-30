package net.xytra.sylvarbo.enums;

public enum DatePrecision {
    YMD("yyyy-MM-dd"),
    YM("yyyy-MM"),
    Y("yyyy");

    private String dateFormat;

    private DatePrecision(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
