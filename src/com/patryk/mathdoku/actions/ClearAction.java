package com.patryk.mathdoku.actions;

import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.actions.Action;

public class ClearAction extends Action {
    private UserData oldUserData;
    public ClearAction(UserData oldUserData) {
        this.oldUserData = oldUserData;
    }

    @Override
    public void undo() {
        gameContext.getUserData().copy(oldUserData);
    }

    @Override
    public void redo() {
        gameContext.getUserData().clear();
    }
}
