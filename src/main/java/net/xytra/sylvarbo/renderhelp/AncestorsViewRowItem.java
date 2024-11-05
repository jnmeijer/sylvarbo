package net.xytra.sylvarbo.renderhelp;

public class AncestorsViewRowItem {
    private final Object item;
    private final long identifier;
    private final int colSpan;
    private final int rowId;
    private int rowSpan;

    public AncestorsViewRowItem(Object item, long identifier, int rowId, int colSpan) {
        this.item = item;
        this.identifier = identifier;
        this.rowId = rowId;
        this.colSpan = colSpan;
    }

    public AncestorsViewRowItem(Object item, long identifier, int rowId, int colSpan, int rowSpan) {
        this.item = item;
        this.identifier = identifier;
        this.rowId = rowId;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
    }

    public Object getItem() {
        return item;
    }

    public long getIdentifier() {
        return identifier;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getRowId() {
        return rowId;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

}
