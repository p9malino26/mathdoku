package com.patryk.mathdoku;

public class GameDimensions {
    public int pixelWidth;
    public int boardWidth;

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getSquarePixelWidth() {
        return pixelWidth / boardWidth;
    }

}
