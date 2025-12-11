package Assignments;

import java.util.Arrays;

public class A2 {
    public static void main(String[] args) {

        int [] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Long  count= Arrays.stream(numbers)
                .filter(e-> e%2==0)
                .map(e->e*e)
                .peek(value -> System.out.println(value))
        .count();
        System.out.println(count);

    }
}
