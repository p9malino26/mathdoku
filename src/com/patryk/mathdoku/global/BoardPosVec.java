package com.patryk.mathdoku.global;

import com.patryk.mathdoku.GameGridView;
import com.patryk.mathdoku.Util;

public class BoardPosVec {
    //private static GameContext gameContext = GameContext.getInstance();
    //public static int width = GameContext.getBoardWidth();
    public static int boardWidth;

    public static void setBoardWidth(int boardWidth) {
        BoardPosVec.boardWidth = boardWidth;
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

    //TODO test
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

    public int toIndex() {
        return boardWidth * r + c;
    }

    public BoardPosVec toPixelSpace() {
        return new BoardPosVec(this.r * GameGridView.getSquarePixelWidth(), this.c * GameGridView.getSquarePixelWidth());
    }

    public BoardPosVec fromPixelToBoardSpace() {
        int pixelWidth = GameGridView.getSquarePixelWidth();

        return new BoardPosVec(Math.floorDiv(r, pixelWidth), Math.floorDiv(c, pixelWidth));
    }


    @Override
    public String toString() {
        return String.format("(%d, %d)", r, c);
    }
}
