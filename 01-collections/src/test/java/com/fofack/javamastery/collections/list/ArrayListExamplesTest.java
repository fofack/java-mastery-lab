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

    /*
     * ============================================================
     * 02 - ADD AND UPDATE
     * ============================================================
     */

    @Test
    void shouldAddElementAtEnd() {

        ArrayList<String> technologies = new ArrayList<>();

        boolean changed = examples.add(technologies, "Java");

        assertTrue(changed);
        assertEquals(1, technologies.size());
        assertEquals("Java", technologies.get(0));
    }

    @Test
    void shouldPreserveInsertionOrderWhenAddingElements() {

        ArrayList<String> technologies = new ArrayList<>();

        examples.add(technologies, "Java");
        examples.add(technologies, "Spring Boot");
        examples.add(technologies, "React");

        assertEquals(
                List.of("Java", "Spring Boot", "React"),
                technologies);
    }

    @Test
    void shouldAllowDuplicateElements() {

        ArrayList<String> technologies = new ArrayList<>();

        examples.add(technologies, "Java");
        examples.add(technologies, "Java");

        assertEquals(2, technologies.size());

        assertEquals(
                List.of("Java", "Java"),
                technologies);
    }

    @Test
    void shouldAllowNullElements() {

        ArrayList<String> technologies = new ArrayList<>();

        examples.add(technologies, null);

        assertEquals(1, technologies.size());
        assertNull(technologies.get(0));
    }

    @Test
    void shouldInsertElementAtSpecifiedIndex() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java", "React"));

        examples.addAtIndex(
                technologies,
                1,
                "Spring Boot");

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"),
                technologies);
    }

    @Test
    void shouldAllowInsertionAtIndexEqualToSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java", "Spring Boot"));

        examples.addAtIndex(
                technologies,
                technologies.size(),
                "React");

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"),
                technologies);
    }

    @Test
    void shouldThrowExceptionWhenAddIndexIsNegative() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.addAtIndex(
                        technologies,
                        -1,
                        "Spring Boot"));
    }

    @Test
    void shouldThrowExceptionWhenAddIndexIsGreaterThanSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.addAtIndex(
                        technologies,
                        2,
                        "Spring Boot"));
    }

    @Test
    void shouldAddElementAtBeginningUsingAddFirst() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Spring Boot",
                        "React"));

        examples.addFirst(
                technologies,
                "Java");

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"),
                technologies);
    }

    @Test
    void shouldAddElementAtEndUsingAddLast() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        examples.addLast(
                technologies,
                "React");

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"),
                technologies);
    }

    @Test
    void shouldAddAllElementsAtEnd() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        List<String> frameworks = List.of(
                "Spring Boot",
                "React",
                "Angular");

        boolean changed = examples.addAll(
                technologies,
                frameworks);

        assertTrue(changed);

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Angular"),
                technologies);
    }

    @Test
    void shouldReturnFalseWhenAddingEmptyCollection() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        boolean changed = examples.addAll(
                technologies,
                List.of());

        assertFalse(changed);

        assertEquals(
                List.of("Java"),
                technologies);
    }

    @Test
    void shouldThrowExceptionWhenAddAllSourceIsNull() {

        ArrayList<String> technologies = new ArrayList<>();

        assertThrows(
                NullPointerException.class,
                () -> examples.addAll(
                        technologies,
                        null));
    }

    @Test
    void shouldInsertCollectionAtSpecifiedIndex() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Angular"));

        List<String> values = List.of(
                "Spring Boot",
                "React");

        boolean changed = examples.addAllAtIndex(
                technologies,
                1,
                values);

        assertTrue(changed);

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Angular"),
                technologies);
    }

    @Test
    void shouldAllowAddAllAtIndexEqualToSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        examples.addAllAtIndex(
                technologies,
                technologies.size(),
                List.of(
                        "React",
                        "Angular"));

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Angular"),
                technologies);
    }

    @Test
    void shouldThrowExceptionWhenAddAllIndexIsGreaterThanSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.addAllAtIndex(
                        technologies,
                        2,
                        List.of("Spring Boot")));
    }

    @Test
    void shouldThrowExceptionWhenAddAllIndexIsNegative() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.addAllAtIndex(
                        technologies,
                        -1,
                        List.of("Spring Boot")));
    }

    @Test
    void shouldReplaceElementAndReturnPreviousValue() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring",
                        "React"));

        String previous = examples.set(
                technologies,
                1,
                "Spring Boot");

        assertEquals(
                "Spring",
                previous);

        assertEquals(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"),
                technologies);
    }

    @Test
    void setShouldNotChangeListSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring",
                        "React"));

        int sizeBefore = technologies.size();

        examples.set(
                technologies,
                1,
                "Spring Boot");

        assertEquals(
                sizeBefore,
                technologies.size());
    }

    @Test
    void shouldAllowReplacingElementWithNull() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        String previous = examples.set(
                technologies,
                1,
                null);

        assertEquals(
                "Spring Boot",
                previous);

        assertNull(
                technologies.get(1));
    }

    @Test
    void shouldThrowExceptionWhenSetIndexEqualsSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.set(
                        technologies,
                        technologies.size(),
                        "React"));
    }

    @Test
    void shouldThrowExceptionWhenSetIndexIsNegative() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.set(
                        technologies,
                        -1,
                        "Spring Boot"));
    }

    /*
     * ============================================================
     * 03 - READ AND SEARCH
     * ============================================================
     */

    @Test
    void shouldGetElementAtSpecifiedIndex() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"));

        String result = examples.getAtIndex(
                technologies,
                1);

        assertEquals(
                "Spring Boot",
                result);
    }

    @Test
    void shouldGetFirstElement() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"));

        String result = examples.getFirst(technologies);

        assertEquals(
                "Java",
                result);
    }

    @Test
    void shouldGetLastElement() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"));

        String result = examples.getLast(technologies);

        assertEquals(
                "React",
                result);
    }

    @Test
    void shouldThrowExceptionWhenGetIndexIsNegative() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.getAtIndex(
                        technologies,
                        -1));
    }

    @Test
    void shouldThrowExceptionWhenGetIndexEqualsSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.getAtIndex(
                        technologies,
                        technologies.size()));
    }

    @Test
    void shouldThrowExceptionWhenGetIndexIsGreaterThanSize() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> examples.getAtIndex(
                        technologies,
                        10));
    }

    @Test
    void shouldThrowExceptionWhenGettingFirstElementFromEmptyList() {

        ArrayList<String> technologies = new ArrayList<>();

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> examples.getFirst(
                        technologies));
    }

    @Test
    void shouldThrowExceptionWhenGettingLastElementFromEmptyList() {

        ArrayList<String> technologies = new ArrayList<>();

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> examples.getLast(
                        technologies));
    }

    @Test
    void shouldReturnNullWhenFirstElementIsNull() {

        ArrayList<String> technologies = new ArrayList<>();

        technologies.add(null);
        technologies.add("Java");

        String result = examples.getFirst(technologies);

        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenLastElementIsNull() {

        ArrayList<String> technologies = new ArrayList<>();

        technologies.add("Java");
        technologies.add(null);

        String result = examples.getLast(technologies);

        assertNull(result);
    }

    @Test
    void shouldReturnTrueWhenElementExists() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"));

        boolean result = examples.contains(
                technologies,
                "Spring Boot");

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenElementDoesNotExist() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"));

        boolean result = examples.contains(
                technologies,
                "Flutter");

        assertFalse(result);
    }

    @Test
    void shouldFindNullElement() {

        ArrayList<String> technologies = new ArrayList<>();

        technologies.add("Java");
        technologies.add(null);
        technologies.add("React");

        boolean result = examples.contains(
                technologies,
                null);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenNullElementDoesNotExist() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        boolean result = examples.contains(
                technologies,
                null);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenAllElementsExist() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Angular"));

        boolean result = examples.containsAll(
                technologies,
                List.of(
                        "Java",
                        "React"));

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenAtLeastOneElementDoesNotExist() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"));

        boolean result = examples.containsAll(
                technologies,
                List.of(
                        "Java",
                        "Flutter"));

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenCheckingEmptyCollection() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        boolean result = examples.containsAll(
                technologies,
                List.of());

        assertTrue(result);
    }

    @Test
    void shouldThrowExceptionWhenContainsAllArgumentIsNull() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of("Java"));

        assertThrows(
                NullPointerException.class,
                () -> examples.containsAll(
                        technologies,
                        null));
    }

    @Test
    void shouldReturnFirstOccurrenceIndex() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Java"));

        int result = examples.indexOf(
                technologies,
                "Java");

        assertEquals(
                0,
                result);
    }

    @Test
    void shouldReturnMinusOneWhenIndexOfElementDoesNotExist() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        int result = examples.indexOf(
                technologies,
                "Flutter");

        assertEquals(
                -1,
                result);
    }

    @Test
    void shouldReturnFirstNullIndex() {

        ArrayList<String> technologies = new ArrayList<>();

        technologies.add("Java");
        technologies.add(null);
        technologies.add("React");
        technologies.add(null);

        int result = examples.indexOf(
                technologies,
                null);

        assertEquals(
                1,
                result);
    }

    @Test
    void shouldReturnLastOccurrenceIndex() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Java"));

        int result = examples.lastIndexOf(
                technologies,
                "Java");

        assertEquals(
                3,
                result);
    }

    @Test
    void shouldReturnMinusOneWhenLastIndexOfElementDoesNotExist() {

        ArrayList<String> technologies = new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot"));

        int result = examples.lastIndexOf(
                technologies,
                "Flutter");

        assertEquals(
                -1,
                result);
    }

    @Test
    void shouldReturnLastNullIndex() {

        ArrayList<String> technologies = new ArrayList<>();

        technologies.add(null);
        technologies.add("Java");
        technologies.add(null);
        technologies.add("React");

        int result = examples.lastIndexOf(
                technologies,
                null);

        assertEquals(
                2,
                result);
    }

    private record Technology(
            String name) {
    }

    @Test
    void shouldFindDifferentObjectWhenLogicallyEqual() {

        Technology storedTechnology = new Technology("Java");

        Technology searchedTechnology = new Technology("Java");

        ArrayList<Technology> technologies = new ArrayList<>();

        technologies.add(storedTechnology);

        boolean result = examples.contains(
                technologies,
                searchedTechnology);

        assertTrue(result);

        assertNotSame(
                storedTechnology,
                searchedTechnology);

        assertEquals(
                storedTechnology,
                searchedTechnology);
    }
}