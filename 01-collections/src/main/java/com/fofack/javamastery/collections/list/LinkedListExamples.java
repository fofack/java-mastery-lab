package com.fofack.javamastery.collections.list;

import java.util.LinkedList;
import java.util.List;

public class LinkedListExamples {

    public List<String> createDevelopers() {
        List<String> developers = new LinkedList<>();

        developers.add("Alice");
        developers.add("Bob");
        developers.add("Charlie");

        return developers;
    }

    public String findByIndex(List<String> developers, int index) {
        return developers.get(index);
    }

    public void insertAtBeginning(List<String> developers, String developer) {
        developers.add(0, developer);
    }

    public boolean removeDeveloper(List<String> developers, String developer) {
        return developers.remove(developer);
    }
}