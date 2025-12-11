package Assignments;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class A3 {
    public static void main(String[] args) {

        String sentence = "By the end of this video, you’ll understand how these operations work and how to combine them effectively in a stream pipeline This is part of a series. Stay tuned for upcoming videos on advanced stream operations like collect, reduce, flatMap, anyMatch, and more!";

        List<String> result = Arrays.stream(sentence.split(" "))
                .filter(word -> word.toLowerCase().startsWith("a"))  // words starting with A or a
                .map(String::toLowerCase)                           // convert to lowercase
                .map(s -> new StringBuilder(s).reverse().toString()) // reverse each word
                .sorted(Comparator.comparingInt(String::length))     // sort from shortest to longest
                .collect(Collectors.toList());                       // collect to list

        // Print each reversed word + length
        result.forEach(word -> System.out.println(word + " - " + word.length()));
    }
}
