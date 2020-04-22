package com.patryk.mathdoku.drawers;

import com.patryk.mathdoku.global.BoardPosVec;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectedCellDrawer extends  Drawer{

    public SelectedCellDrawer(GraphicsContext gc) {
        super(gc);
    }

    public void draw(BoardPosVec markedCell) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3.0);
        clearCanvas();
        drawSquare(true, markedCell);
    }
}
