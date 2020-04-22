package com.patryk.mathdoku;

public class StackActionRecorder<T> implements ActionRecorder<T>{

    private final int maxSize;
    LimitedStack<T> undoStack;
    LimitedStack<T> redoStack;

    public StackActionRecorder(int maxSize) {
        this.maxSize = maxSize;
        undoStack = new LimitedStack(maxSize);
        redoStack = new LimitedStack(maxSize);
    }

/*    @Override

    public void record(Action action) {
        //top to bottom redo to appending, mirror, flip and place new
        if (!redoStack.isEmpty()) {
            LimitedStack redoClone = new LimitedStack((LimitedStack) redoStack); // use to mirror later
            while (!redoStack.isEmpty()) {
                Action a = redoStack.pop();
                undoStack.push(a);
            }
Action
            while (!redoClone.isEmpty()) {
                Action a = redoClone.popHead();
                a.reverse();
                undoStack.push(a);
            }
        }

        undoStack.push(action);
    }
*/


    @Override
    public void record(T action) {
        redoStack.clear();
        undoStack.push(action);
        UndoRedoButtonManager.me().onRecordButtonPressed();
    }
    @Override
    public T undo() {
        assert (canUndo());
        T a = undoStack.pop();
        redoStack.push(a);
        UndoRedoButtonManager.me().onUndoButtonPressed();
        return a;
    }

    @Override
    public T redo() {
        assert (canRedo());
        T a = redoStack.pop();
        undoStack.push(a);
        UndoRedoButtonManager.me().onRedoButtonPressed();
        return a;
    }

    @Override
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    @Override
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    @Override
    public String toString() {
        return "Undo stack: " + undoStack.toString() + "\n" + "Redo stack: " + redoStack.toString();
    }
}
