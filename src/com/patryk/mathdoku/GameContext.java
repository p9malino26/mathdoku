package com.patryk.mathdoku;

import com.patryk.mathdoku.actions.Action;
import com.patryk.mathdoku.actions.CellValueChangeAction;
import com.patryk.mathdoku.actions.ClearAction;
//todo
//import com.patryk.mathdoku.errorChecking.GridErrorChecker;
import com.patryk.mathdoku.errorChecking.GridErrorChecker;
import com.patryk.mathdoku.global.BoardPosVec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents one status of the playing of the game e.g. the cage data, what the user has entered
 */
public class GameContext {

    private static GameContext activeContext;

    CageData cageData;
    UserData userData;
    GridErrorChecker gridErrorChecker;

    StackActionRecorder<Action> actionRecorder;

    public StackActionRecorder<Action> getActionRecorder() {
        return actionRecorder;
    }

    private int boardWidth;

    public static GameContext me() {
        return activeContext;
    }

    //todo

    UserData.ChangeListener updateErrorState = (UserData.ChangeListener.ChangeData changeData) -> {
        gridErrorChecker.onGridChange(changeData);
        if (userData.isFull() && gridErrorChecker.noErrors()) {
            System.out.println("Game won");
            userData.saveToFile("winning.txt");
        }
    };



    //TODO improve this exception choice
    public GameContext(String cageDataFilePath, int boardWidth) throws IOException {
        this.boardWidth = boardWidth;
        BoardPosVec.setBoardWidth(boardWidth);
        cageData = new CageData(cageDataFilePath, boardWidth);
        userData = new UserData(boardWidth);
        actionRecorder = new StackActionRecorder(5);
        //userData.fill();

        gridErrorChecker = new GridErrorChecker(boardWidth, userData, cageData);

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

    public void setActive() {
        GameContext.activeContext = this;
    }

    public static int getBoardWidth() {
        return GameContext.me().boardWidth;
    }

    public void undo() {
        actionRecorder.undo().undo();
    }

    public void redo() {
        actionRecorder.redo().redo();
        UndoRedoButtonManager.me().onRedoButtonPressed();
    }

    public GridErrorChecker getErrorChecker() {
        return gridErrorChecker;
    }
}