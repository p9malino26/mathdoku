package com.patryk.mathdoku.gui.drawers;

import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.util.BoardPosVec;
import javafx.scene.paint.Color;

public class ErrorsHighlighter extends Drawer {


    public void drawErroneousCage(Cage cage) {
        gc.setFill(Color.RED);
        for (int cell: cage.getMemberCells()) {
            BoardPosVec pos = new BoardPosVec(cell);
            drawSquare(false, pos);
        }
    }

    public void drawErroneousRow(boolean isRow, int index) {
        BoardPosVec start;
        BoardPosVec size  ;
        if (isRow) {
            start   = new BoardPosVec(index, 0);
            size     = new BoardPosVec(1, boardWidth);
        } else { //col
            start   = new BoardPosVec(0, index);
            size     = new BoardPosVec(boardWidth, 1);
        }
        gc.setFill(Color.ORANGE);

        drawRectangle(start, size);

    }


}
