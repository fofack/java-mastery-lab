package com.fofack.javamastery.collections.list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListExamplesTest {

    private final ArrayListExamples examples = new ArrayListExamples();

    @Test
    void shouldCreateEmptyArrayList() {

        ArrayList<String> result = examples.createEmpty();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void shouldCreateArrayListWithInitialCapacity() {

        ArrayList<String> result = examples.createWithInitialCapacity(100);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void shouldThrowExceptionWhenInitialCapacityIsNegative() {

        assertThrows(
                IllegalArgumentException.class,
                () -> examples.createWithInitialCapacity(-1));
    }

    @Test
    void shouldCreateArrayListFromCollection() {

        List<String> source = List.of("Java", "Spring Boot", "React");

        ArrayList<String> result = examples.createFromCollection(source);

        assertEquals(3, result.size());

        assertEquals(
                List.of("Java", "Spring Boot", "React"),
                result);
    }

    @Test
    void createdArrayListShouldBeIndependentFromSourceCollection() {

        ArrayList<String> source = new ArrayList<>();

        source.add("Java");
        source.add("Spring Boot");

        ArrayList<String> result = examples.createFromCollection(source);

        source.add("React");

        assertEquals(3, source.size());
        assertEquals(2, result.size());

        assertFalse(result.contains("React"));
    }

    @Test
    void shouldThrowExceptionWhenSourceCollectionIsNull() {

        assertThrows(
                NullPointerException.class,
                () -> examples.createFromCollection(null));
    }

    @Test
    void ensureCapacityShouldNotChangeListSize() {

        ArrayList<String> result = examples.createWithEnsuredCapacity(1000);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void ensureCapacityShouldNotPreventAddingElements() {

        ArrayList<String> result = examples.createWithEnsuredCapacity(1000);

        result.add("Java");
        result.add("Spring Boot");

        assertEquals(2, result.size());
        assertEquals(
                List.of("Java", "Spring Boot"),
                result);
    }

    @Test
    void trimToSizeShouldKeepAllElements() {

        ArrayList<String> list = new ArrayList<>(100);

        list.add("Java");
        list.add("Spring Boot");
        list.add("React");

        examples.trimToSize(list);

        assertEquals(3, list.size());

        assertEquals(
                List.of("Java", "Spring Boot", "React"),
                list);
    }
}