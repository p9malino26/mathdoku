package com.patryk.mathdoku.actions;

public interface ActionRecorder<T> {
    void record(T action);
    T undo();
    T redo();
    boolean canUndo();
    boolean canRedo();
}
