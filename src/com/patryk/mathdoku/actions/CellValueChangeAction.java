package com.patryk.mathdoku.actions;

import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.actions.Action;
import com.patryk.mathdoku.global.BoardPosVec;

public class CellValueChangeAction implements Action {
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
        GameContext.me().setValueAtCell(cell, oldValue, false);
    }

    @Override
    public void redo() {
        GameContext.me().setValueAtCell(cell, newValue, false);
    }

    @Override
    public void flip() {

    }


/*    public int getOldValue() {
        return oldValue;
    }*/

/*    public Util.BoardPosVec getCell() {
        return cell;
    }

    public int getNewValue() {
        return newValue;
    }*/
}
