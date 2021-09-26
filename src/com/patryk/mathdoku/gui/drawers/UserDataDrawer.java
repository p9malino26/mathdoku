package com.patryk.mathdoku.gui.drawers;

import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.util.BoardPosVec;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class UserDataDrawer extends Drawer {
    private UserData data;
    private Font font;

    public void setData(UserData data) {
        this.data = data;
    }

    public void updateFontSize() {
        double sizeRatio = 0.0;
        switch (fontSize) {
            case SMALL:
                sizeRatio = 0.6;
                break;
            case MEDIUM:
                sizeRatio = 0.7;
                break;
            case LARGE:
                sizeRatio = 0.8;
                break;
        }

        int newSize = (int) ((double) squarePixelWidth * sizeRatio );
        font = new Font("Zapfino", newSize);
        draw();
    }

    public void draw() {
        assert(data != null);
        clearCanvas();
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);

        for (int r = 0; r < boardWidth; r++) {
            for (int c = 0; c < boardWidth; c++) {
                BoardPosVec pos = new BoardPosVec(r, c);
                int digit = data.getValueAtCell(pos);
                if (digit != 0)
                    drawUserDigit(pos, digit);
                //drawUserDigit(pos, pos.toIndex());
            }
        }
    }

    public void drawUserDigit(BoardPosVec pos, int digit) {
        pos.r++;
        BoardPosVec pixelPos = pos.toPixelSpace();
/*
        int offset = (GameContext.getInstance().getSquarePixelWidth() - fontSize) / 2;*/
        pixelPos.c += squarePixelWidth / 2;
        pixelPos.r -= (squarePixelWidth - font.getSize()) / 2;

        gc.fillText(Integer.toString(digit), pixelPos.c, pixelPos.r);
    }
}
