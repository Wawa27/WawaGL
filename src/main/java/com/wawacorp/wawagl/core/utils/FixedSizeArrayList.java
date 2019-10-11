package com.wawacorp.wawagl.core.utils;

import java.util.*;

public class FixedSizeArrayList<E> implements List<E> {
    private int count;
    private final E[] array;

    public FixedSizeArrayList(int size) {
        this.count = 0;
        this.array = (E[]) new Object[size];
    }

    /**
     * @return The maximum number of element possible
     */
    public int length() {
        return array.length;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object o) {
        for(E e : array) {
            if (o == e) return true;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        E[] safeArray = (E[]) new Object[count];
        System.arraycopy(array, 0, safeArray, 0, count);
        return safeArray;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(E o) {
        array[count++] = o;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == o) {
                array[i] = array[count--];
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public void clear() {
        this.count = 0;
    }

    @Override
    public E get(int index) {
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        E previousElement = array[index];
        array[index] = element;
        return previousElement;
    }

    @Override
    public void add(int index, Object element) throws UnsupportedOperationException {

    }

    @Override
    public E remove(int index) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == o) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == o) index = i;
        }
        return index;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }
}
