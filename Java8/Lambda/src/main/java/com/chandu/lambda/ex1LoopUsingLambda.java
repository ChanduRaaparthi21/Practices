package com.chandu.lambda;

import java.util.*;

public class ex1LoopUsingLambda {

    public static void main(String[] args) {

        List<String> names= new ArrayList<>(Arrays.asList("Chandu","Ravi","Kumar","Ajay","Vijay"));

        //for loop
//        for(int i=0; i<names.size(); i++){
//            System.out.println(names.get(i));
//        }

        //for each loop
//        for(String name: names){
//            System.out.println(name);
//        }

        //for each method
//        names.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

//        for each method with lambda
//        names.forEach(e-> System.out.println(e));




        //sorting

//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });
//        System.out.println(names);
//
//
        //sorting with lambda
//        Collections.sort(names,(n1,n2)->n2.compareTo(n1));
//        System.out.println(names);

        //or

//        names.forEach(s ->  System.out.println(s));

    }

}
