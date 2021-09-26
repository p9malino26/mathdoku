package com.patryk.mathdoku.util;

public class Util {


    public static int clampToRange(int x, int maxWidth) {

        if (x < 0) {
            return 0;
        }

        if(x >= maxWidth) {
            return maxWidth - 1;
        }

        return x;
    }



    public static int charToInt(char c) {
        return c & 15;
    }


    public static int boardToPixel(int n, int boardWidth, int pixelWidth) {
        return Math.floorDiv(n * pixelWidth, boardWidth);
    }

    public static int pixelToBoard(int pixCoord, int boardWidth, int pixelWidth) {
        return Math.floorDiv(pixCoord * boardWidth, pixelWidth);
    }

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

        /*public static int appendDigitToInt(int mainInt, char digit) {
        if (mainInt == -1) {
            return charToInt(digit);
        }
        return Integer.parseInt(Integer.toString(mainInt) + digit);
    }*/
}
