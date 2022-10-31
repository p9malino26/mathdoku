package com.patryk.mathdoku.gui.drawers;

import com.patryk.mathdoku.cageData.Cage;
import com.patryk.mathdoku.cageData.CageData;
import com.patryk.mathdoku.util.BoardPosVec;
import com.patryk.mathdoku.util.Direction;
import com.patryk.mathdoku.util.Util;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CageDrawer extends Drawer{

    private static final String fontFamily = "Zapfino";
    private CageData data;
    private Font font;


    public void setData(CageData data) {
        this.data = data;

    }

    public void updateFontSize() {
        int size = 1;
        switch (fontSize) {
            case SMALL:
                size = 12;
                break;
            case MEDIUM:
                size = 17;
                break;
            case LARGE:
                size = 21;
                break;
        }
        font = new Font(fontFamily, size);
        draw();
    }

    public void draw() {
        clearCanvas();
        drawMainLines();
        drawCageBoundaries();
        drawCageSigns();
        //drawLineFromPoint(new Util.BoardPosVec(2,1), Util.Direction.EAST);
    }

    private void drawCageBoundaries() {

        gc.setLineWidth(2.0);
        gc.setStroke(Color.BLACK);


        for (int r = 0; r < boardWidth; r++) {
            for (int c = 0; c < boardWidth; c++) {
                for (Direction d: Direction.values()) {
                    BoardPosVec squareVec = new BoardPosVec(r, c);

                    if (!  data.cellConnectsTo(squareVec, d)) {
                        drawLineFromSquare(new BoardPosVec(r, c), d);
                    }
                }
            }
        }
    }


    private void drawMainLines() {
        gc.setLineWidth(1.0);
        gc.setStroke(Color.GRAY);

        //draw back grid

        for (int i = 1; i < boardWidth; i++) {
            int pixelOffset = Util.boardToPixel(i, boardWidth, pixelWidth);
            //vertical
            gc.strokeLine(pixelOffset, 0.0, pixelOffset, pixelWidth);
            //horizontal
            gc.strokeLine(0.0, pixelOffset, pixelWidth, pixelOffset);
        }
    }



    private void drawCageSigns() {

        //draw mathematical signs
        gc.setFill(Color.BLACK);
        gc.setFont(font);


        for (Cage c: data.getCages()) {
            drawCageText(new BoardPosVec(c.getMemberCells().get(0)), c.toString());
        }
    }

    private void drawCageText(BoardPosVec pos, String text) {
        pos = pos.toPixelSpace();
        gc.fillText(text, pos.c, pos.r + font.getSize());
    }
}
