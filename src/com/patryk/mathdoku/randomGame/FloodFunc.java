package com.patryk.mathdoku.randomGame;

import com.patryk.mathdoku.cageData.Cell;
import com.patryk.mathdoku.util.BoardPosVec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FloodFunc {
    Cell[] grid;
    int maxCount;
    int id;
    Random ranObj;

    List<Integer> members = new ArrayList();

    public FloodFunc(Cell[] grid, int maxCount, int id, Random ranObj) {
        this.grid = grid;
        this.maxCount = maxCount;
        this.id = id;
        this.ranObj = ranObj;
    }

    public static List<Integer> doit(Cell[] grid, BoardPosVec pos, int maxCount, int id, Random ranObj) {
        var obj = new FloodFunc(grid, maxCount, id, ranObj);
        obj.rec(pos, pos);
        return obj.members;
    }

    public void rec(BoardPosVec pos, BoardPosVec oldPos) {
        //if on cell with other id, return
        //place mark
        //decrement count
        //while you can still colour more fields, for every block around you in random order that you did not come from, recurse

        if (grid[pos.toIndex()].getCageId() != -1) {
            return;
        }

        grid[pos.toIndex()].setCageId(id);
        members.add(pos.toIndex());

        maxCount--;

        BoardPosVec.forEveryCellAround(pos, ranObj, varPos -> {
            if (maxCount > 0) {
                if (varPos.equals(oldPos)) return;
                rec(varPos, pos);
            }
        });

    }
}
