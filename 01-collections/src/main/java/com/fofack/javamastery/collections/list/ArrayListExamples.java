package com.fofack.javamastery.collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListExamples {

    /*
     * ============================================================
     * 01 - CREATION AND CAPACITY
     * ============================================================
     */

    /**
     * Creates an empty ArrayList.
     */
    public <T> ArrayList<T> createEmpty() {
        return new ArrayList<>();
    }

    /**
     * Creates an empty ArrayList with the specified initial capacity.
     *
     * @param initialCapacity initial capacity of the list
     * @throws IllegalArgumentException if the capacity is negative
     */
    public <T> ArrayList<T> createWithInitialCapacity(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * Creates a new ArrayList containing all elements
     * from the provided collection.
     *
     * @param source source collection
     * @throws NullPointerException if source is null
     */
    public <T> ArrayList<T> createFromCollection(
            Collection<? extends T> source) {
        return new ArrayList<>(source);
    }

    /**
     * Creates an ArrayList and ensures a minimum capacity.
     *
     * ensureCapacity does not change the size of the list.
     */
    public <T> ArrayList<T> createWithEnsuredCapacity(int minCapacity) {
        ArrayList<T> list = new ArrayList<>();

        list.ensureCapacity(minCapacity);

        return list;
    }

    /**
     * Trims the capacity of the ArrayList to its current size.
     */
    public <T> void trimToSize(ArrayList<T> list) {
        list.trimToSize();
    }

    /*
     * ============================================================
     * 02 - ADD AND UPDATE
     * ============================================================
     */

    /**
     * Adds an element at the end of the list.
     *
     * @return true when the list is modified
     */
    public <T> boolean add(
            ArrayList<T> list,
            T element) {
        return list.add(element);
    }

    /**
     * Inserts an element at the specified position.
     *
     * Existing elements from this position are shifted to the right.
     *
     * Valid index:
     * 0 <= index <= size()
     *
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public <T> void addAtIndex(
            ArrayList<T> list,
            int index,
            T element) {
        list.add(index, element);
    }

    /**
     * Adds an element at the beginning of the list.
     *
     * Available through the Java 21 sequenced collection API.
     */
    public <T> void addFirst(
            ArrayList<T> list,
            T element) {
        list.addFirst(element);
    }

    /**
     * Adds an element at the end of the list using
     * the Java 21 sequenced collection API.
     */
    public <T> void addLast(
            ArrayList<T> list,
            T element) {
        list.addLast(element);
    }

    /**
     * Adds all elements from another collection
     * at the end of the list.
     *
     * @return true if the list changed
     * @throws NullPointerException if source is null
     */
    public <T> boolean addAll(
            ArrayList<T> list,
            Collection<? extends T> source) {
        return list.addAll(source);
    }

    /**
     * Inserts all elements from another collection
     * starting at the specified position.
     *
     * Valid index:
     * 0 <= index <= size()
     *
     * @return true if the list changed
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws NullPointerException      if source is null
     */
    public <T> boolean addAllAtIndex(
            ArrayList<T> list,
            int index,
            Collection<? extends T> source) {
        return list.addAll(index, source);
    }

    /**
     * Replaces the element at the specified position.
     *
     * Valid index:
     * 0 <= index < size()
     *
     * @return the element that was previously stored at this position
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public <T> T set(
            ArrayList<T> list,
            int index,
            T element) {
        return list.set(index, element);
    }

    /*
     * ============================================================
     * 03 - READ AND SEARCH
     * ============================================================
     */

    /**
     * Returns the element at the specified index.
     *
     * Valid index:
     * 0 <= index < size()
     *
     * @param list  the ArrayList to read from
     * @param index position of the element
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public <T> T getAtIndex(
            ArrayList<T> list,
            int index) {
        return list.get(index);
    }

    /**
     * Returns the first element of the list.
     *
     * Available through the Java 21 sequenced collection API.
     *
     * @param list the ArrayList to read from
     * @return the first element
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public <T> T getFirst(
            ArrayList<T> list) {
        return list.getFirst();
    }

    /**
     * Returns the last element of the list.
     *
     * Available through the Java 21 sequenced collection API.
     *
     * @param list the ArrayList to read from
     * @return the last element
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public <T> T getLast(
            ArrayList<T> list) {
        return list.getLast();
    }

    /**
     * Checks whether the list contains the specified element.
     *
     * The comparison is based on logical equality.
     *
     * @param list    the ArrayList to search
     * @param element element to search for
     * @return true if at least one matching element exists
     */
    public <T> boolean contains(
            ArrayList<T> list,
            Object element) {
        return list.contains(element);
    }

    /**
     * Checks whether the list contains all elements
     * from the provided collection.
     *
     * @param list     the ArrayList to search
     * @param elements elements that must all be present
     * @return true if every requested element is present
     * @throws NullPointerException if elements is null
     */
    public <T> boolean containsAll(
            ArrayList<T> list,
            Collection<?> elements) {
        return list.containsAll(elements);
    }

    /**
     * Returns the index of the first occurrence
     * of the specified element.
     *
     * @param list    the ArrayList to search
     * @param element element to search for
     * @return the first matching index, or -1 if not found
     */
    public <T> int indexOf(
            ArrayList<T> list,
            Object element) {
        return list.indexOf(element);
    }

    /**
     * Returns the index of the last occurrence
     * of the specified element.
     *
     * @param list    the ArrayList to search
     * @param element element to search for
     * @return the last matching index, or -1 if not found
     */
    public <T> int lastIndexOf(
            ArrayList<T> list,
            Object element) {
        return list.lastIndexOf(element);
    }
}