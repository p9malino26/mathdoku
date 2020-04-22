package com.patryk.mathdoku.actions;

public interface Action {
    void undo();
    void redo();
    void flip();
}
