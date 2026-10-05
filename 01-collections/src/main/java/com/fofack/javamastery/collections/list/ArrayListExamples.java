package com.fofack.javamastery.collections.list;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListExamples {

    /**
     * Creates an empty ArrayList.
     */
    public <T> ArrayList<T> createEmpty() {
        return new ArrayList<>();
    }

    /**
     * Creates an empty ArrayList with a specified initial capacity.
     */
    public <T> ArrayList<T> createWithInitialCapacity(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * Creates a new ArrayList containing all elements
     * from the provided collection.
     */
    public <T> ArrayList<T> createFromCollection(
            Collection<? extends T> source) {
        return new ArrayList<>(source);
    }

    /**
     * Ensures that the ArrayList can hold at least
     * the requested number of elements without requiring
     * another capacity increase.
     */
    public <T> ArrayList<T> createWithEnsuredCapacity(int minCapacity) {
        ArrayList<T> list = new ArrayList<>();

        list.ensureCapacity(minCapacity);

        return list;
    }

    /**
     * Reduces the capacity of the ArrayList
     * to its current size.
     */
    public <T> void trimToSize(ArrayList<T> list) {
        list.trimToSize();
    }
}