package com.chandu.streams.ex2;

import java.util.Arrays;

public class Reduce {

    public static void main(String[] args) {

        // Given an array of integers, filter out the even numbers, square them, and print the results.

        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        int reduce = Arrays.stream(nums)
                .reduce(0, (a, b) -> a + b);
        System.out.println(reduce);



    }
}
