package com.patryk.mathdoku.drawers;

import com.patryk.mathdoku.*;
import com.patryk.mathdoku.global.BoardPosVec;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CageDrawer extends Drawer{



    private CageData data;
    private Font font = new Font("Zapfino", 12);


    public CageDrawer(CageData data, GraphicsContext gc) {
        super(gc);
        this.data = data;
    }

    public void draw() {

        drawMainLines();
        drawCageBoundaries();
        drawCageSigns();
        //drawLineFromPoint(new Util.BoardPosVec(2,1), Util.Direction.EAST);
    }

    private void drawCageBoundaries() {

        gc.setLineWidth(2.0);
        gc.setStroke(Color.BLACK);


        for (int r = 0; r < GameContext.getBoardWidth(); r++) {
            for (int c = 0; c < GameContext.getBoardWidth(); c++) {
                for (Util.Direction d: Util.Direction.values()) {
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

        for (int i = 1; i < GameContext.getBoardWidth(); i++) {
            //vertical
            gc.strokeLine(i * GameGridView.getSquarePixelWidth(), 0.0, i * GameGridView.getSquarePixelWidth(), GameGridView.getPixelWidth());
            //horizontal
            gc.strokeLine(0.0, i * GameGridView.getSquarePixelWidth(), GameGridView.getPixelWidth(), i * GameGridView.getSquarePixelWidth());
        }
    }

    private void drawBoundaries() {
        int SQUARE_WIDTH = GameGridView.getSquarePixelWidth();
        gc.setLineWidth(2.0);

        gc.setStroke(Color.BLACK);
        gc.strokeLine(SQUARE_WIDTH, SQUARE_WIDTH, 2 * SQUARE_WIDTH, SQUARE_WIDTH);

    }

    private void drawCageSigns() {

        //draw mathematical signs
        gc.setFill(Color.BLACK);
        gc.setFont(font);

        /*for (int i = 0; i < nSquares; i++) {
            for (int j = 0; j < nSquares; j++) {
                gc.fillText(String.valueOf(data.getCellAt(j, i).getSign()), i * SQUARE_WIDTH, j * SQUARE_WIDTH + 12);
            }
        }*/

        for (Cage c: data.getCages()) {
            drawCageText(new BoardPosVec(c.getMarkedCell()), c.toString());
        }
    }

    private void drawCageText(BoardPosVec pos, String text) {
        int SQUARE_WIDTH = GameGridView.getSquarePixelWidth();
        gc.fillText(text, pos.c * SQUARE_WIDTH, pos.r * SQUARE_WIDTH + 12);
    }
}
