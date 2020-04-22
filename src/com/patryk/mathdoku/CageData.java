package com.patryk.mathdoku;

import com.patryk.mathdoku.global.BoardPosVec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CageData {
    int width;

    Cell[] data;
    List<Cage> cageList = new ArrayList<Cage>();
    int cageCount;

    static CageData instance;

    //todo maybe make rest of the code like this?
    public static CageData me() {
        return instance;
    }

    //always called
    private CageData(int width) {
        instance = this;
        this.width = width;
        data = new Cell[width * width];
    }

    public CageData(String fileName, int width) throws IOException{
        this(width);
        readFromFile(fileName);
    }

    public List<Cage> getCages() {return cageList; }

    public boolean cellConnectsTo(BoardPosVec pos, Util.Direction direction) {

        Cell nextCell = getCellAt(pos.add(direction.vector));
        if (nextCell == null) {
            return false;
        }

        return getCellAt(pos).getCageId() == nextCell.getCageId();

}

    //util methods

    private void readFromFile(String fileName) throws IOException{


        CageParser parser = new CageParser(data);
        Scanner scanner = new Scanner(new File(fileName));

        String line;
        int cageId = 0;
        while (scanner.hasNextLine()) {
            /*take a line that represents each cage, create cage with target, sign, display cell
            and create the cells with that cage in the right positions
             */
            line = scanner.nextLine();

            cageList.add(parser.parse(line, cageId));
            cageId++;
        }

        cageCount = cageId;

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

    //TODO move to parent class BoardData
    public int getValueAtCell(BoardPosVec cell) {
        return data[cell.toIndex()].getCageId();
    }

    //TODO IMPLE
    public int getCageCount() {
        return cageCount;
    }

    //todo
    /*public Cage getCage(int i) {
        todo impl;
    }*/
}

class Cell {
    private int cageID;

    public Cell (int cageID) {
        this.cageID = cageID;
    }

    public int getCageId() {return cageID; }
}

