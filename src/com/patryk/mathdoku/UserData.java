package com.patryk.mathdoku;

import com.patryk.mathdoku.global.BoardPosVec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserData {
    public interface ChangeListener {
        interface ChangeData {

        }

        class SingleCellChange implements ChangeData {
            BoardPosVec cellChanged;
            int oldValue;
            int newValue;

            public SingleCellChange(BoardPosVec cellChanged, int oldValue, int newValue) {
                this.cellChanged = cellChanged;
                this.oldValue = oldValue;
                this.newValue = newValue;
            }

            public BoardPosVec getCellChanged() {
                return cellChanged;
            }

            public int getOldValue() {
                return oldValue;
            }

            public int getNewValue() {
                return newValue;
            }
        }

        class MultipleCellChange implements ChangeData{
            boolean cleared;

            public MultipleCellChange(boolean cleared) {
                this.cleared = cleared;
            }

            public boolean isCleared() {
                return cleared;
            }
        }



        void onDataChanged(ChangeData changeData);
    }

    int[] data;
    int boardWidth;
    int fullSize;
    int populationCount = 0;
    List<ChangeListener> changeListenerList = new LinkedList<>();
    static UserData instance; // instance of current, active userdata

    public static UserData me() {return instance; }

    private UserData() {};

    public UserData (int width) {
        boardWidth = width;
        fullSize = width * width;
        data = new int[fullSize];
        instance = this;

        clear();
    }

    /**
     * Creates a new UserData object with the data of other, other will have blank data
     * @return
     */
    public static UserData move(UserData other) {
        UserData newData = new UserData();
        instance = newData;
        newData.boardWidth = other.boardWidth;
        //boardwidths are the same

        newData.data = other.data;
        other.data = new int[other.boardWidth * other.boardWidth];

        newData.populationCount = other.populationCount;
        other.populationCount = 0;

        other.notifyListener(new ChangeListener.MultipleCellChange(true));
        return newData;
    }

    public void copy(UserData other) {
        //this.boardWidth = other.boardWidth;
        this.data = Arrays.copyOf(other.data, boardWidth * boardWidth);
        notifyListener(new ChangeListener.MultipleCellChange(false));
    }

    public void addChangeListener(ChangeListener listener) {
        changeListenerList.add(listener);
    }

    public void notifyListener(ChangeListener.ChangeData changeData) {
        //TODO fix
        for (ChangeListener changeListener: changeListenerList){
            changeListener.onDataChanged(changeData);
        }
    }

    public boolean isFull() {
        return populationCount == fullSize;
    }


    public void setValueAtCell(BoardPosVec cell, int value) {
        int oldValue = data[cell.toIndex()];

        if (oldValue != value) {
            data[cell.toIndex()] = value;
            notifyListener(new ChangeListener.SingleCellChange(cell, oldValue, value));
            if (oldValue == 0)
                populationCount++;
            else if (value == 0) {
                populationCount--;
            }
        }
    }


    public int getValueAtCell(BoardPosVec cell) {
        return data[cell.toIndex()];
    }

    public int getValueAtCell(int cellId) {
        return data[cellId];
    }


    public void clear() {
        Arrays.fill(data, 0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        //for every row
        for (int r = 0; r < boardWidth; r ++) {
            //for every column
            for (int c = 0; c < boardWidth; c ++) {
                builder.append(data[r * boardWidth + c]);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public void fill() {
        for (int i = 0; i < fullSize;i ++) {
            int val = (int)( 5 * Math.random()) + 1;
            System.out.println("generating value " + val);
            data[i] = val;
        }
    }

    public void saveToFile(String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (int r = 0; r < boardWidth; r++) {
                for (int c = 0; c < boardWidth; c++) {
                    BoardPosVec pos = new BoardPosVec(r, c);
                    bw.write(getValueAtCell(pos));
                }

                if (r < boardWidth - 1)
                    bw.newLine();

            }

            bw.close();

        } catch (IOException e) {
            System.out.println("some serious shit has happened!");
        }

    }

}
