public abstract class Action {
    enum Type {CHANGE_CELL_VALUE, CLEAR_BOARD, SAMPLE};

    private Type type;
    private boolean flipped = false;

    public Action(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void reverse() {
        flipped = !flipped;
    }
}
