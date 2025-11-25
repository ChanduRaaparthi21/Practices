package com.chandu.streams.ex1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ex1 {

    public static void main(String[] args) {

        List<Employee> employees = EmployeeData.get();


        // Print all employees
       /* employees.stream()
                .forEach(e-> System.out.println(e));*/




        // Print employees with salary greater than 20000 and last name starting with 'D'
        /*employees.stream()
                .filter(e->e.getSalary()>20000.0)
                .filter(e->e.getLastName().startsWith("D"))
                .forEach(e-> System.out.println(e));*/


// Print employees sorted by first name
           /* employees.stream()
                    .sorted((e1,e2)->e1.getFirstName().compareTo(e2.getFirstName()))
                    .forEach(e-> System.out.println(e));*/


        // Print first names of employees sorted alphabetically
        /*employees.stream()
                .map(e->e.getFirstName())
                .sorted()
                .forEach(e-> System.out.println(e));*/



        // Print employees from IT department
        /*employees.stream()
                .filter(e->e.getDepartment().equals("IT"))
                .forEach(e-> System.out.println(e));*/




        // Count number of employees in IT department
        /*System.out.println(
        employees.stream()
                .filter(e->e.getDepartment().equals("IT"))
                .count());*/


        // Calculate total salary of employees in IT department
       /* System.out.println(employees.stream()
                        .filter(e->e.getDepartment().equals("IT"))
                .mapToDouble(e -> e.getSalary())
                .sum());*/



        // Find the maximum salary among all employees
        /*System.out.println(
        employees.stream()
                .mapToDouble(e->e.getSalary())
                .max());
*/


        // Get a list of distinct first names of employees
     /*   List<String> empNames = employees.stream()
                .map(e -> e.getFirstName())
                .distinct()
                .toList();
        System.out.println(empNames);*/


      /*  List<String> empNames = employees.stream()
                .map(e -> e.getFirstName())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(empNames);*/



        // Get an unmodifiable list of distinct first names of employees
       /* List<String> empNames = employees.stream()
                .map(e -> e.getFirstName())
                .distinct()
                .collect(Collectors.toUnmodifiableList());
//        empNames.add("chandu");
        System.out.println(empNames);*/



        // Create a map of employee first names to their salaries

        /*Map<String, String> empToMap = employees.stream()
                .collect(Collectors.toMap(
                        e -> e.getFirstName(),
                        e -> String.valueOf(e.getSalary()),
                        (a,b)-> a+" , "+ b
                ))
                ;
        System.out.println(empToMap);*/



    }
}
