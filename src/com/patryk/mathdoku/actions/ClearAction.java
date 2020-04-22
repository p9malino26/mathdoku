package com.patryk.mathdoku.actions;

import com.patryk.mathdoku.GameContext;
import com.patryk.mathdoku.UserData;
import com.patryk.mathdoku.actions.Action;

public class ClearAction implements Action {
    private UserData oldUserData;
    public ClearAction(UserData oldUserData) {
        this.oldUserData = oldUserData;
    }

    @Override
    public void undo() {
        GameContext.me().getUserData().copy(oldUserData);
    }

    @Override
    public void redo() {
        GameContext.me().getUserData().clear();
    }

    @Override
    public void flip() {

    }
}
