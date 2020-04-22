package com.patryk.mathdoku;

import com.patryk.mathdoku.actions.Action;
import javafx.scene.control.Button;

public class UndoRedoButtonManager {
    static UndoRedoButtonManager instance;
    public static UndoRedoButtonManager me() {
        return instance;
    }

    Button undoButton;
    Button redoButton;
    StackActionRecorder<Action> actionRecorder;

    public UndoRedoButtonManager(Button undoButton, Button redoButton, StackActionRecorder<Action> actionRecorder) {
        this.undoButton = undoButton;
        this.redoButton = redoButton;
        this.actionRecorder = actionRecorder;
        undoButton.setDisable(true);
        instance = this;

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
