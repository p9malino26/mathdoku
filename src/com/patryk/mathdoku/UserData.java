package com.patryk.mathdoku;

import com.patryk.mathdoku.util.BoardPosVec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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

    private UserData() {};

    public UserData (int width) {
        boardWidth = width;
        fullSize = width * width;
        data = new int[fullSize];

        clear();
    }

    /**
     * Creates a new UserData object with the data of other, other will have blank data
     * @return
     */
    public static UserData move(UserData other) {
        UserData newData = new UserData();
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
        this.populationCount = other.populationCount;
        notifyListener(new ChangeListener.MultipleCellChange(false));
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getPopulationCount() {
        return populationCount;
    }

    public boolean isEmpty() {
        return populationCount == 0;
    }

    public void addChangeListener(ChangeListener listener) {
        changeListenerList.add(listener);
    }

    public void notifyListener(ChangeListener.ChangeData changeData) {
        for (ChangeListener changeListener: changeListenerList){
            changeListener.onDataChanged(changeData);
        }
    }

    public boolean isFull() {
        return populationCount == fullSize;
    }


    public void setValueAtCell(BoardPosVec cell, int value) {
        setValueAtCell(cell.toIndex(), value);
    }
    public void setValueAtCell(int index, int value) {
        int oldValue = data[index];

        if (oldValue != value) {
            data[index] = value;
            if (oldValue == 0)
                populationCount++;

            if (value == 0) {
                populationCount--;
            }
            notifyListener(new ChangeListener.SingleCellChange(new BoardPosVec(index), oldValue, value));

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
        populationCount = 0;
        notifyListener(new ChangeListener.MultipleCellChange(true));
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

    public Set<Integer> getAllowedDigits(int index) {
        BoardPosVec posVec = new BoardPosVec(index);
        int r = posVec.r;
        int c = posVec.c;

        Set<Integer> allDigits = new HashSet<>();
        for(int i = 1; i <= boardWidth; i++) {
            allDigits.add(i);
        }


        //remove row digits
        for (int vcol = 0; vcol < c; vcol++) {
            allDigits.remove(Integer.valueOf(getValueAtCell(new BoardPosVec(r, vcol))));
        }
        //remove column digits
        for (int vrow = 0; vrow < r; vrow++) {
            allDigits.remove(Integer.valueOf(getValueAtCell(new BoardPosVec(vrow, c))));
        }
        return allDigits;
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
            //this is very rare
        }

    }

}
