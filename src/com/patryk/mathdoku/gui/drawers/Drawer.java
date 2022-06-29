package com.patryk.mathdoku.gui.drawers;

import com.patryk.mathdoku.gui.GameGridView;
import com.patryk.mathdoku.util.Direction;
import com.patryk.mathdoku.util.BoardPosVec;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Drawer {
    protected static GameGridView.FontSize fontSize;

    protected static int pixelWidth;
    protected static int boardWidth;
    protected static int squarePixelWidth;

    public static void setPixelWidth(int pixelWidth) {
        Drawer.pixelWidth = pixelWidth;

    }

    public static void setBoardWidth(int boardWidth) {
        Drawer.boardWidth = boardWidth;
        Drawer.squarePixelWidth = pixelWidth / boardWidth;
    }


    protected GraphicsContext gc;
    private Canvas canvas;


    //protected static GameContext gameContext = GameContext.getInstance();



    protected Drawer() {
        assert (pixelWidth != 0);
        this.canvas = new Canvas(pixelWidth, pixelWidth);
        this.gc = canvas.getGraphicsContext2D();
    }

    public static void setFontSize(GameGridView.FontSize fontSize) {
        Drawer.fontSize = fontSize;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void clearCanvas() {
        //int pixelWidth = GameGridView.getPixelWidth();
        double pixelWidth = gc.getCanvas().getWidth();
        gc.clearRect(0, 0, pixelWidth, pixelWidth);
    }

    protected void drawLineFromSquare (BoardPosVec pos, Direction dir) {
        Direction lineDir = Direction.NORTH;

        if (dir == Direction.EAST || dir == Direction.SOUTH) {
            pos.r++;
            pos.c++;
        }

        switch (dir) {
            case NORTH:
                lineDir = Direction.EAST;
                break;
            case WEST:
                lineDir = Direction.SOUTH;
                break;
            case EAST:
                lineDir = Direction.NORTH;
                break;
            case SOUTH:
                lineDir = Direction.WEST;
                break;

        }

        drawLineFromPoint(pos, lineDir);


    }


    protected void drawLineFromPoint (BoardPosVec pos, Direction dir) {


        BoardPosVec pos2 = pos.add(dir.vector);

        drawLineBetweenTwoPoints(pos, pos2);
    }


    private void drawSquarePixelwise(boolean stroke, BoardPosVec pos1) {
        if (stroke)
            gc.strokeRect(pos1.c, pos1.r, squarePixelWidth, squarePixelWidth);
        else
            gc.fillRect(pos1.c, pos1.r, squarePixelWidth, squarePixelWidth);

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
