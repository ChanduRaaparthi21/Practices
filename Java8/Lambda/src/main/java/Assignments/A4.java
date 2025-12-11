package Assignments;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class A4 {

    public static void main(String[] args) {
        //Find common elements in two lists, reverse them and collect to a new list

        List<String> fruits1 = List.of("apple", "banana", "cherry", "date", "elderberry", "fig", "grape");
        List<String> fruits2 = List.of("avocado", "blueberry", "fig", "dragonfruit", "kiwi", "banana", "elderberry");

        List<String> result=fruits1.stream()
                .filter(fruits2::contains)
                .map(s->new StringBuilder(s).reverse().toString())
                .collect(Collectors.toList());

        result.forEach(System.out::println);

    }
}
