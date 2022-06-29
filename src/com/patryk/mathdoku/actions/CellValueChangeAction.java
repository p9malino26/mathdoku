package com.patryk.mathdoku.actions;

import com.patryk.mathdoku.util.BoardPosVec;

public class CellValueChangeAction extends Action {
    BoardPosVec cell;
    int                 oldValue;
    int                 newValue;

    public CellValueChangeAction(BoardPosVec cell, int oldValue, int newValue) {
        this.cell = cell;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void undo() {
        gameContext.setValueAtCell(cell, oldValue, false);
    }

    @Override
    public void redo() {
        gameContext.setValueAtCell(cell, newValue, false);
    }


}
