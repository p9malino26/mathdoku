package com.patryk.mathdoku;

import com.patryk.mathdoku.drawers.Drawer;
import com.patryk.mathdoku.global.BoardPosVec;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ErrorsHighlighter extends Drawer {
    public ErrorsHighlighter(GraphicsContext gc) {
        super(gc);
    }

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
            size     = new BoardPosVec(1, GameContext.getBoardWidth());
        } else { //col
            start   = new BoardPosVec(0, index);
            size     = new BoardPosVec(GameContext.getBoardWidth(), 1);
        }
        gc.setFill(Color.ORANGE);

        drawRectangle(start, size);

    }


}
