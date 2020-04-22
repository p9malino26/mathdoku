public class CircularActionRecorder<E> implements ActionRecorderBase<E> {

    private final int maxSize = 3;
    E[] actions = new E[maxSize];

    int head = 0; // the actual index in the internal array reflecting the beginning of the list
    int size = 0; // total number of elements, both undoable and redoable (max as specified)
    int nRedoable = 0;

    @Override
    public void record(E action) {
        if (size == maxSize) {
            head = loopForward(head,1);
            size--;
        }

        size -= nRedoable;

        actions[loopForward(head, size)] = action;

        size++;
    }

    @Override
    public E undo() {
        assert (canUndo());
        E a = actions[loopForward(head, size - 1)];
        nRedoable++;

        return a;

    }

    @Override
    public E redo() {
        assert (canRedo());
        E a = actions[loopForward(head, size - 1)];
        nRedoable++;
        return a;
    }

    @Override
    public boolean canUndo() {
        return (size != nRedoable);
    }

    @Override
    public boolean canRedo() {
        return nRedoable != 0;
    }

}
