package com.patryk.mathdoku;

public class LimitedStack<T> implements LimitedStackBase<T> {
    final int maxSize;
    int size = 0;
    int head = 0;
    
    T[] data;

    public LimitedStack(LimitedStack<T> other) {
        this(other.maxSize);
        this.size = other.size;
        this.head = other.head;

        System.arraycopy(other.data, 0, data, 0, maxSize);
    }

    public LimitedStack(int maxSize) {
        this.maxSize = maxSize;
        data = (T[])new Object[maxSize];
    }

    @Override
    public void push(T item) {
        if (isFull()) {
            head = loop(head, 1);
            size--;
        }

        data[loop(head, size)] = item;
        size++;
    }

    public T popHead() {
        assert (!isEmpty());
        T a = data[head];
        size--;
        head = loop(head, 1);
        return a;
    }

    /*@Override
    public void push(Action item, int pos) {

    }*/

    @Override
    public T pop() {
        assert(!isEmpty());
        T item = data[loop(head, size - 1)];
        size--;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        for (int i = 0; i < size; i ++) {
            arr[i] = data[loop(head, i)];
        }
        return arr;
    }

    @Override
    public boolean isFull() {
        return size == maxSize;
    }

    private int loop(int index,int offset) {
        return (index + offset) % maxSize;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(data[loop(head, i)]);
            sb.append(", ");
        }
        sb.append(']');
        return sb.toString();


    }

}
