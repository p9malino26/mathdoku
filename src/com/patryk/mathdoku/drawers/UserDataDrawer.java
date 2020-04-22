package com.patryk.mathdoku.drawers;

import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.GameGridView;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.global.BoardPosVec;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class UserDataDrawer extends Drawer {
    private UserData userData;
    private final int fontSize = (int) ((double) GameGridView.getSquarePixelWidth() * 0.7 );
    private Font font = new Font("Zapfino", fontSize);

    public UserDataDrawer(GraphicsContext gc, UserData userData) {
        super(gc);
        this.userData = userData;
    }

    public void draw() {
        clearCanvas();
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);

        for (int r = 0; r < GameContext.getBoardWidth(); r++) {
            for (int c = 0; c < GameContext.getBoardWidth(); c++) {
                BoardPosVec pos = new BoardPosVec(r, c);
                int digit = userData.getValueAtCell(pos);
                if (digit != 0)
                    drawUserDigit(pos, digit);
                //drawUserDigit(pos, pos.toIndex());
            }
        }
    }

    public void drawUserDigit(BoardPosVec pos, int digit) {
        pos.r++;
        BoardPosVec pixelPos = pos.toPixelSpace();
        int squareWidth = GameGridView.getSquarePixelWidth();/*
        int offset = (GameContext.getInstance().getSquarePixelWidth() - fontSize) / 2;*/
        pixelPos.c += squareWidth / 2;
        pixelPos.r -= (squareWidth - fontSize) / 2;

        gc.fillText(Integer.toString(digit), pixelPos.c, pixelPos.r);
    }
}
