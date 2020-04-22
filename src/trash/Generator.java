package com.patryk.mathdoku.cage;

import com.patryk.mathdoku.global.BoardPosVec;
import com.patryk.mathdoku.UserData;

import java.util.BitSet;

public class Generator {
    public interface BoardReadyFunc {
        void onBoardReady(UserData board);
    }

    private BitSet[] colForbidden;
    private BitSet[] rowForbidden;
    private int size;
    private UserData data;
    private BoardReadyFunc boardReadyFunc;

    private static BitSet[] getBitsSetArray(int size) {
        BitSet[] a = new BitSet[size];
        for (int i = 0; i < size; i++) {
            a[i] = new BitSet(size);
        }
        return a;
    }

    private Generator(int size, BoardReadyFunc boardReadyFunc) {
        this.size = size;
        this.data = new UserData(size);
        this.boardReadyFunc = boardReadyFunc;
        colForbidden = getBitsSetArray(size);
        rowForbidden = getBitsSetArray(size);
    }

    public static void perform(int size, BoardReadyFunc boardReadyFunc) {
        new Generator(size, boardReadyFunc).f(BoardPosVec.zero());
    }

    private void f(BoardPosVec cell) {
        //get bit vector of forbidden values for that row and column
        BitSet forbiddenNums = new BitSet();
        forbiddenNums.or(colForbidden[cell.c]);
        forbiddenNums.or(rowForbidden[cell.r]);
        for (int i = 1; i <= size; i++) {
            //if that value is not forbidden
            if (!forbiddenNums.get(i)) {
                //give current cell that number
                data.setValueAtCell(cell, i);
                if (cell.isLast()) {
                    boardReadyFunc.onBoardReady(data);
                    return;
                }
                //make this number forbidden in rows and columns
                colForbidden[cell.c].set(i, true);
                rowForbidden[cell.r].set(i, true);
                //go to next cell and recursively do the same
                f(cell.next());
                //after that is done, make this number no longer forbidden
                colForbidden[cell.c].set(i, false);
                rowForbidden[cell.r].set(i, false);
                //assert(forbiddenNums.equals(colForbidden[cell.c].or(rowForbidden[cell.r])));
                //but don't remove the number; it will be ignored and later overwritten anyway
            }
        }
    }

}