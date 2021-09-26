package com.patryk.mathdoku.actions;

import com.patryk.mathdoku.GameContext;
import javafx.scene.control.Button;

public class UndoRedoButtonManager {
    static UndoRedoButtonManager instance;
    public static UndoRedoButtonManager me() {
        return instance;
    }

    Button undoButton;
    Button redoButton;
    StackActionRecorder<Action> actionRecorder;


    public UndoRedoButtonManager(Button undoButton, Button redoButton) {
        this.undoButton = undoButton;
        this.redoButton = redoButton;
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        instance = this;
    }

    public void setGameContext(GameContext gameContext) {
        this.actionRecorder = gameContext.getActionRecorder();
        undoButton.setDisable(true);
        redoButton.setDisable(true);
    }

    public void onUndoButtonPressed() {
        redoButton.setDisable(false);
        if (!actionRecorder.canUndo()) {
            undoButton.setDisable(true);
        }
    }

    public void onRedoButtonPressed() {
        undoButton.setDisable(false);
        if(!actionRecorder.canRedo()) {
            redoButton.setDisable(true);
        }
    }
    public void onRecordButtonPressed() {
        undoButton.setDisable(false);
        redoButton.setDisable(true);
    }
    /*
    Both buttons initially disabled
    record (action done) -> undo enabled, redo disabled
    undo -> redo enabled, undo disabled if cannot undo
    redo -> undo enabled, redo disabled if cannot redo
     */
}
