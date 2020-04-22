package com.patryk.mathdoku;

import com.patryk.mathdoku.global.BoardPosVec;

public class Util {
    /*public static int getNumberOfLinesInFile(String fileName) throws IOException {
        FileReader f = new FileReader(fileName);
        int lines = 1;

        while (true) {
            int charCode = f.read();

            if (charCode == (int)'\n')
                lines++;
            else if (charCode == -1) {
                break;
            }

        }

        return lines;
    }*/

    public static int clampToRange(int x, int maxWidth) {

        if (x < 0) {
            return 0;
        }

        if(x >= maxWidth) {
            return maxWidth - 1;
        }

        return x;
    }

    /*public static int appendDigitToInt(int mainInt, char digit) {
        if (mainInt == -1) {
            return charToInt(digit);
        }
        return Integer.parseInt(Integer.toString(mainInt) + digit);
    }*/

    public static int charToInt(char c) {
        return c & 15;
    }

    // TODO move this to drawer!!!
    /*
    public static void clearCanvas(GraphicsContext gc) {
        int pixelWidth = GameGridView.getPixelWidth();
        gc.clearRect(0, 0, pixelWidth, pixelWidth);

    }*/

    public enum Direction {
        NORTH (new BoardPosVec(-1, 0)),
        EAST  (new BoardPosVec( 0, 1)),
        SOUTH (new BoardPosVec( 1, 0)),
        WEST  (new BoardPosVec( 0, -1));

        public final BoardPosVec vector;

        private Direction(BoardPosVec vec) {
            vector = vec;
        }
    }
}
