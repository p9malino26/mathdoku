package com.patryk.mathdoku.randomGame;

import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.cageData.Cell;
import com.patryk.mathdoku.util.BoardPosVec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RandomPartitionGrid {
    final Cell[] grid;
    final int maxPartitionSize;
    final Random ranObj;

    int cageId = 0;
    List<Cage> cageList = new ArrayList<>();

    public static List<Cage> doit(Cell[] grid, int maxPartitionSize, Random ranObj) {
        var obj = new RandomPartitionGrid(grid, maxPartitionSize, ranObj);
        obj.rec(new BoardPosVec(0, 0));
        return obj.cageList;
    }

    public RandomPartitionGrid(Cell[] grid, int maxPartitionSize, Random ranObj) {
        this.grid = grid;
        this.maxPartitionSize = maxPartitionSize;
        this.ranObj = ranObj;
    }


    private void rec(BoardPosVec pos) {
        Cage cage = new Cage();
        cage.setMemberCells(FloodFunc.doit(grid, pos, maxPartitionSize, cageId, ranObj));
        cageList.add(cage);
        cageId++;
        for (int cellId : cage.getMemberCells()) {
            BoardPosVec.forEveryCellAround(new BoardPosVec(cellId), (BoardPosVec varPos) -> {
                if (grid[varPos.toIndex()].getCageId() == -1) {
                    rec(varPos);
                }
            });
        }
    }


}
