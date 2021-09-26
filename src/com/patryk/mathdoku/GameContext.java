package com.patryk.mathdoku;

import com.patryk.mathdoku.actions.*;
import com.patryk.mathdoku.cageData.CageData;
import com.patryk.mathdoku.cageData.DataFormatException;
import com.patryk.mathdoku.errorChecking.UserErrorChecker;
import com.patryk.mathdoku.util.BoardPosVec;

/**
 * Represents one status of the playing of the game e.g. the cage data, what the user has entered
 */
public class GameContext {

    private int boardWidth;

    private CageData cageData;
    private UserData userData;
    private UserErrorChecker userErrorChecker;

    StackActionRecorder<Action> actionRecorder;

    public StackActionRecorder<Action> getActionRecorder() {
        return actionRecorder;
    }


    UserData.ChangeListener updateErrorState = (UserData.ChangeListener.ChangeData changeData) -> {
        userErrorChecker.onGridChange(changeData);
    };


    public GameContext(String data) throws DataFormatException {
        cageData = new CageData(data);
        this.boardWidth = cageData.getWidth();
        BoardPosVec.setBoardWidth(boardWidth);
        Action.setGameContext(this);
        userData = new UserData(boardWidth);
        actionRecorder = new StackActionRecorder<>(5);
        //userData.fill();

        userErrorChecker = new UserErrorChecker(boardWidth, userData, cageData);

        userData.addChangeListener(updateErrorState);

    }




    public CageData getCageData() {
        return cageData;
    }

    public UserData getUserData() {
        return userData;
    }


    public void setValueAtCell(BoardPosVec cell, int value, boolean record ) {
        int oldValue = userData.getValueAtCell(cell);
        userData.setValueAtCell(cell, value);

        if (record)
            actionRecorder.record(new CellValueChangeAction(cell, oldValue, value));
    }

    public void clear(boolean record) {
        UserData copy = UserData.move(userData);

        if(record) {
            actionRecorder.record(new ClearAction(copy));
        }

    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void undo() {
        actionRecorder.undo().undo();
    }

    public void redo() {
        actionRecorder.redo().redo();
        UndoRedoButtonManager.me().onRedoButtonPressed();
    }


    public UserErrorChecker getErrorChecker() {
        return userErrorChecker;
    }


    public boolean isWon() {
        if (userData.isFull()) {
            if (userErrorChecker.noErrors()) {
                return true;
            }
        }

        return false;
    }
}