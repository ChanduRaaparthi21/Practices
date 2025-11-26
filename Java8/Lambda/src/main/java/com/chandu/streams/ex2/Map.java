package com.chandu.streams.ex2;

import java.util.Arrays;
import java.util.List;

public class Map {

    public static void main(String[] args) {

        List<String> names= Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        names.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

    }
}
