package com.patryk.mathdoku.drawers;

import com.patryk.mathdoku.GameGridView;
import com.patryk.mathdoku.Util;
import com.patryk.mathdoku.global.BoardPosVec;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Drawer {
    protected GraphicsContext gc;


    //protected static GameContext gameContext = GameContext.getInstance();



    protected Drawer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clearCanvas() {
        //int pixelWidth = GameGridView.getPixelWidth();
        double pixelWidth = gc.getCanvas().getWidth();
        gc.clearRect(0, 0, pixelWidth, pixelWidth);
    }

    protected void drawLineFromSquare (BoardPosVec pos, Util.Direction dir) {
        Util.Direction lineDir = Util.Direction.NORTH;

        if (dir == Util.Direction.EAST || dir == Util.Direction.SOUTH) {
            pos.r++;
            pos.c++;
        }

        switch (dir) {
            case NORTH:
                lineDir = Util.Direction.EAST;
                break;
            case WEST:
                lineDir = Util.Direction.SOUTH;
                break;
            case EAST:
                lineDir = Util.Direction.NORTH;
                break;
            case SOUTH:
                lineDir = Util.Direction.WEST;
                break;

        }

        drawLineFromPoint(pos, lineDir);


    }


    protected void drawLineFromPoint (BoardPosVec pos, Util.Direction dir) {


        BoardPosVec pos2 = pos.add(dir.vector);

        drawLineBetweenTwoPoints(pos, pos2);
    }


    private void drawSquarePixelwise(boolean stroke, BoardPosVec pos1) {
        if (stroke)
            gc.strokeRect(pos1.c, pos1.r, GameGridView.getSquarePixelWidth(), GameGridView.getSquarePixelWidth());
        else
            gc.fillRect(pos1.c, pos1.r, GameGridView.getSquarePixelWidth(), GameGridView.getSquarePixelWidth());

    }


    protected void drawSquare(boolean stroke, BoardPosVec pos) {
        //Util.BoardPosVec tr = pos.add(Util.Direction.EAST.vector);
        //Util.BoardPosVec br = tr.add(Util.Direction.SOUTH.vector);
        //Util.BoardPosVec bl = pos.add(Util.Direction.SOUTH.vector);
        drawSquarePixelwise(stroke, pos.toPixelSpace());

    }

    protected void drawLineBetweenTwoPoints(BoardPosVec pos, BoardPosVec pos2) {
        pos = pos.toPixelSpace();
        pos2 = pos2.toPixelSpace();

        gc.strokeLine(pos.c, pos.r, pos2.c, pos2.r);
    }

    protected void drawRectangle(BoardPosVec start, BoardPosVec size) {
        start = start.toPixelSpace();
        size = size.toPixelSpace();

        gc.fillRect(start.c, start.r, size.c, size.r);
    }

}
