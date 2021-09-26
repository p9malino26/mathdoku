package com.patryk.mathdoku.gui.drawers;

import com.patryk.mathdoku.util.BoardPosVec;
import javafx.scene.paint.Color;

public class SelectedCellDrawer extends  Drawer{

    public SelectedCellDrawer() {
        super();
    }

    public void draw(BoardPosVec markedCell) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3.0);
        clearCanvas();
        drawSquare(true, markedCell);
    }
}
