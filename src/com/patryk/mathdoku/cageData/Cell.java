package com.patryk.mathdoku.cageData;

public class Cell {
    private int cageID;

    public Cell(int cageID) {
        this.cageID = cageID;
    }

    public int getCageId() {
        return cageID;
    }

    public void setCageId(int cageID) {
        this.cageID = cageID;
    }
}
