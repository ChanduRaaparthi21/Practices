package com.chandu.collectors;

import java.util.*;
import java.util.stream.Collectors;

public class ex1 {

    public static void main(String[] args) {

        List<Employee> employees = EmployeeData.get();


        // Join Employee First Names with comma separator\
       /* String empFirstNameJoined = employees.stream()
                .map(e -> e.getFirstName())
                .collect(Collectors.joining(", "));
        System.out.println("Employee First Names: " + empFirstNameJoined);*/


        // Partition Employees based on salary > 50000
        /*Map<Boolean, List<Employee>> collect = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000.0));
        System.out.println(collect);*/


        // Count Employees based on salary > 50000
        /*Map<Boolean, Long> empCountBySalary50k = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000.0, Collectors.counting()));
        System.out.println(empCountBySalary50k);*/


        // Group Employees by Department
        /*Map<String, List<Employee>> empGroupByDepartment = employees.stream()
                .collect(Collectors.groupingBy(e->e.getDepartment()));
        System.out.println(empGroupByDepartment);*/




        // Count Employees by Department
        /*Map<String, Long> empCountByDepartment = employees.stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment(), Collectors.counting()));
        System.out.println(empCountByDepartment);*/


        // Get Employee First Names by Department
        /*Map<String, List<String>> empFirstNameByDepartment = employees.stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment(),
                        Collectors.mapping(e -> e.getFirstName(),
                                Collectors.toList())));
        System.out.println(empFirstNameByDepartment);*/


        // Get Sum of Employee Salaries by Department
        /*Map<String, Optional<Double>> empSalarySumByDepart = employees.stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment(),
                        Collectors.mapping(e -> e.getSalary(),
                                Collectors.reducing((a, b) -> a + b))));
        System.out.println(empSalarySumByDepart);*/

        //or

        // Get Sum of Employee Salaries by Department - simpler way using summingDouble collector
        /*Map<String, Double> empSalarySumByDepart = employees.stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment(),
                        Collectors.summingDouble(e -> e.getSalary())));
        System.out.println(empSalarySumByDepart);*/



        // Get Summary of Employee Salaries by Department
       /* Map<String, DoubleSummaryStatistics> summaryOfSalaryByDept = employees
                .stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment(),
                        Collectors.summarizingDouble(e -> e.getSalary())));
        System.out.println(summaryOfSalaryByDept);
*/


        // Get Summary of Employee Ages by Department
         /* Map<Object, IntSummaryStatistics> summaryOfSalaryByDept = employees
                .stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment(),
                        Collectors.summarizingInt(e -> e.getAge())));
        System.out.println(summaryOfSalaryByDept);*/


        // Get Summary of Salaries of IT Developers
        /*DoubleSummaryStatistics ItDevSalarySummary = employees.stream()
                .collect(Collectors.filtering(e -> e.getDepartment().equals("IT"),
                        Collectors.summarizingDouble(e -> e.getSalary())));
        System.out.println(ItDevSalarySummary);
*/


        // Get Elder Employee of the Company
        /*Optional<Employee> elderOfThisCompany = employees.stream()
                .collect(Collectors.maxBy((a1, a2) -> a1.getAge() - a2.getAge()));
        System.out.println(elderOfThisCompany);*/

        //OR

        /*Optional<Employee> elderOfThisCompany = employees.stream()
                .collect(Collectors.maxBy((Comparator.comparing(Employee::getAge))));
        System.out.println(elderOfThisCompany);*/


        // Get First Name of the Highest Paid Employee or "No Employee Found" if list is empty
      /*  String noEmployeeFound = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary)),
                        EmpOpt -> EmpOpt.map(e -> e.getFirstName()).orElse("No Employee Found")
                ));
        System.out.println(noEmployeeFound);
*/

    }
}
