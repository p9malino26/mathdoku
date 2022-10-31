package com.patryk.mathdoku.cageData;

import com.patryk.mathdoku.util.Direction;
import com.patryk.mathdoku.util.BoardPosVec;

import java.util.*;


public class CageData {
    int width;
    Cell[] data;

    public List<Cage> getCageList() {
        return cageList;
    }

    List<Cage> cageList = new ArrayList<Cage>();
    int cageCount;

    public CageData(int width) {
        this.width = width;
        data = new Cell[width * width];
        for (int i = 0; i < width * width; i++) {
            data[i] = new Cell(-1);
        }
        this.cageList = new ArrayList<>();

    }
    public CageData(String data) throws DataFormatException{
        parseData(data);
    }

    public Cell[] getData() {
        return data;
    }
    public List<Cage> getCages() {return cageList; }


    public boolean cellConnectsTo(BoardPosVec pos, Direction direction) {

        Cell nextCell = getCellAt(pos.add(direction.vector));
        if (nextCell == null) {
            return false;
        }

        return getCellAt(pos).getCageId() == nextCell.getCageId();

    }

    private void parseData(String rawData) throws DataFormatException {

        //new
        CageParser parser = new CageParser();
        int size = parser.getCellCount(new Scanner(rawData));
        this.data = new Cell[size];
        this.width = (int)Math.sqrt(size);
        if (width * width != size) {
            throw new DataFormatException(-1, "Number of cells not square number.");
        }

        if (size > 81)
            throw new DataFormatException(-1, "Too many cells! Max is 81.");
        initData();
        parser.parseData(new Scanner(rawData), data, cageList);

    }

    private void initData() {
        for (int i = 0; i < data.length; i++) {
            data[i] = new Cell(-1);
        }
    }

    public int getWidth() {
        return width;
    }

    private Cell getCellAt(BoardPosVec posVec) {
        if (!posVec.isValid()) {
            return null;
        }
        return data[posVec.toIndex()];
    }

    public int getValueAtCell(BoardPosVec cell) {
        return data[cell.toIndex()].getCageId();
    }

    public int getCageCount() {
        return cageList.size();
    }


    public void setCageList(List<Cage> cageList) {
        this.cageList = cageList;
    }
}
