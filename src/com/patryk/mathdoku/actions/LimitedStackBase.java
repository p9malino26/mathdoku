package com.patryk.mathdoku.actions;

public interface LimitedStackBase<E> {
    /**
     * Pushes item at the end
     */
    void push(E item);
    E pop();

    boolean isEmpty();
    boolean isFull();
    void clear();

}
