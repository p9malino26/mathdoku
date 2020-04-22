package com.patryk.mathdoku;

public interface LimitedStackBase<E> {
    /**
     * Pushes item at the end
     */
    void push(E item);
    //void push(E item, int pos);
    E pop();

    boolean isEmpty();
    boolean isFull();
    E popHead();
    void clear();

}
