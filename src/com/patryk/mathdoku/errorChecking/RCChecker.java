package com.patryk.mathdoku.errorChecking;

import java.util.Arrays;
import com.patryk.mathdoku.global.BoardPosVec;
import com.patryk.mathdoku.UserData;

public class RCChecker {
    int boardWidth;

    RCInfoList rowInfos;
    RCInfoList colInfos;

    public RCChecker(int boardWidth) {
        this.boardWidth = boardWidth;
        rowInfos = new RCInfoList(boardWidth);
        colInfos = new RCInfoList(boardWidth);
    }

    public void onDigitEntered(BoardPosVec cellChanged, int digit) {
        rowInfos.getList()[cellChanged.r].onDigitEntered(digit);
        colInfos.getList()[cellChanged.c].onDigitEntered(digit);
        System.out.printf("Digit %d entered at %s.\nData for row is: %s\nData for column is: %s\n\n", digit, cellChanged, rowInfos.getList()[cellChanged.r], colInfos.getList()[cellChanged.c]);
    }

    public void onDigitRemoved(BoardPosVec cellChanged, int digit) {
        rowInfos.getList()[cellChanged.r].onDigitRemoved(digit);
        colInfos.getList()[cellChanged.c].onDigitRemoved(digit);
        System.out.printf("Digit %d removed at %s.\nData for row is: %s\nData for column is: %s\n\n", digit, cellChanged, rowInfos.getList()[cellChanged.r], colInfos.getList()[cellChanged.c]);
    }

    public void recalculate(UserData userData) {
        //TODO for every cell in the data, call the corresponding on value changed/removed
        reset();
        for (int r = 0; r < boardWidth; r++) {
            for (int c = 0; c < boardWidth; c++) {
                BoardPosVec cell = new BoardPosVec(r, c);
                if (userData.getValueAtCell(cell) != 0)
                    onDigitEntered(cell, userData.getValueAtCell(cell));
            }
        }
    }

    public boolean noErrors() {
        for(RCInfo info: rowInfos.getList()) {
            if (info.hasError())
                return false;
        }

        for(RCInfo info: colInfos.getList()) {
            if (info.hasError())
                return false;
        }

        return true;
    }

    public void reset() {
        //
        //for every row and column, reset their data
        rowInfos.reset();
        colInfos.reset();

    }

    public boolean isRCInvalid(boolean isRow, int index) {
        if (isRow)
            return rowInfos.getList()[index].hasError();
        else
            return colInfos.getList()[index].hasError();
    }


}

class RCInfoList {
    RCInfo[] list;

    public RCInfoList(int size) {
        list = new RCInfo[size];
        for (int i = 0; i < size; i++) {
            list[i] = new RCInfo(size);
        }
    }

    public RCInfo[] getList() {
        return list;
    }

    public void reset() {
        for (RCInfo e: list) {
            e.reset();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(list);

    }
}

class RCInfo {

    int[] digitCounts;
    int uniqueDigitCount = 0;
    int digitCount;
    int boardWidth;

    public RCInfo(int boardWidth) {
        this.boardWidth = boardWidth;
        digitCounts = new int[boardWidth];
    }

    public void onDigitEntered(int digit) {
        int initialCount = digitCounts[digit - 1]++;
        if (initialCount == 0 ) {
            uniqueDigitCount++;
        }
        digitCount++;
    }

    //TODO call it somewhere!
    public void onDigitRemoved(int digit) {
        int finalCount = --digitCounts[digit - 1];
        if (finalCount == 0 ) {
            uniqueDigitCount--;
        }
        digitCount--;
    }

    public boolean isNotReady() {
        return uniqueDigitCount != boardWidth;
    }


    public boolean hasUniqueDigits() {
        return uniqueDigitCount == digitCount;
    }

    public void reset() {
        Arrays.fill(digitCounts, 0);
    }

    public boolean hasError() {
        return digitCount != uniqueDigitCount;
    }

    @Override
    public String toString() {
        return Arrays.toString(digitCounts);
    }
}