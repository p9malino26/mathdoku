package com.patryk.mathdoku.util;

import java.util.*;
import java.util.function.Consumer;

public class BoardPosVec {
    //private static GameContext gameContext = GameContext.getInstance();
    //public static int width = GameContext.getBoardWidth();
    public static int boardWidth;
    public static int pixelWidth;


    public static void setBoardWidth(int boardWidth) {
        BoardPosVec.boardWidth = boardWidth;
    }

    public static void setPixelWidth(int pixelWidth) {
        BoardPosVec.pixelWidth = pixelWidth;
    }
    /*public static int pixelWidth;

    public static void setPixelWidth(int pixelWidth) {
        BoardPosVec.pixelWidth = pixelWidth;
    }*/

    public int r, c;

    public BoardPosVec(int row, int col) {
        r = row;
        c = col;
    }

    public BoardPosVec(int index) {
        r = Math.floorDiv(index, boardWidth);
        c = index - r * boardWidth;
    }

    public static int getBoardWidth() {
        return boardWidth;
    }

    public BoardPosVec add(BoardPosVec other) {
        return new BoardPosVec(this.r + other.r, this.c + other.c);
    }

    public BoardPosVec next() {
        return new BoardPosVec(toIndex() + 1);
    }

    public static BoardPosVec zero() {
        return new BoardPosVec(0, 0);
    }

    public boolean isLast() {
        return (r == boardWidth - 1 && c == boardWidth - 1);
    }

    public boolean clampToArea() {
        //---
        int oldRow = r, oldCol = c;

        if ( (oldRow != (r = Util.clampToRange(oldRow, boardWidth))) || (oldCol != (c = Util.clampToRange(c, boardWidth))) ) {
            return true;
        }

        return false;
    }

    public boolean isValid() {
        return r >= 0 && r < boardWidth &&
                c >= 0 && c < boardWidth;
    }

    public static boolean isValid(int r, int c) {
        return r >= 0 && r < boardWidth &&
                c >= 0 && c < boardWidth;
    }

    public int toIndex() {
        return boardWidth * r + c;
    }

    public BoardPosVec toPixelSpace() {
        return new BoardPosVec(Util.boardToPixel(this.r, boardWidth, pixelWidth), Util.boardToPixel(this.c, boardWidth, pixelWidth));
    }

    public BoardPosVec fromPixelToBoardSpace() {

        return new BoardPosVec(Util.pixelToBoard(this.r, boardWidth, pixelWidth), Util.pixelToBoard(this.c, boardWidth, pixelWidth));
    }

    public static void forEveryCellAround(BoardPosVec pos, Random ranObj, Consumer<BoardPosVec> func) {
        BoardPosVec[] around = {
                pos.add(new BoardPosVec(0,1)),
                pos.add(new BoardPosVec(0,-1)),
                pos.add(new BoardPosVec(1,0)),
                pos.add(new BoardPosVec(-1,0)),
        };

        List<BoardPosVec> aroundObj = new ArrayList(List.of(around));
        if (ranObj != null) Collections.shuffle(aroundObj, ranObj);


        for (var vec: aroundObj) {
            if (!vec.isValid()) continue;
            func.accept(vec);
        }
    }

    public static void forEveryCellAround(BoardPosVec pos, Consumer<BoardPosVec> func) {
        forEveryCellAround(pos,null, func);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", r, c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardPosVec that = (BoardPosVec) o;
        return r == that.r && c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}
