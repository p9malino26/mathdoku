package com.patryk.mathdoku.actions;

import com.patryk.mathdoku.GameContext;

public abstract class Action {
    public static GameContext gameContext;

    public static void setGameContext(GameContext gameContext) {
        Action.gameContext = gameContext;
    }

    public abstract void undo();
    public abstract void redo();
}
