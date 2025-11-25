package com.chandu.streams.ex1;

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeData {

    public static ArrayList<Employee> get(){

        return new ArrayList<Employee>(Arrays.asList(
                new Employee(1, "Amit", "Sharma", 50000, 30, "IT", "Developer"),
                new Employee(2, "Priya", "Verma", 60000, 25, "HR", "Manager"),
                new Employee(3, "Rohit", "Gupta", 55000, 35, "Finance", "Analyst"),
                new Employee(4, "Sneha", "Reddy", 70000, 28, "IT", "Developer"),
                new Employee(5, "Vikas", "Mehta", 65000, 40, "Marketing", "Executive"),
                new Employee(6, "Anjali", "Nair", 72000, 32, "IT", "Manager"),
                new Employee(7, "Suresh", "Pillai", 48000, 29, "Sales", "Representative"),
                new Employee(8, "Kavya", "Iyer", 53000, 27, "HR", "Coordinator"),
                new Employee(9, "Arjun", "Chopra", 58000, 31, "Finance", "Consultant"),
                new Employee(10, "Nisha", "Yadav", 62000, 26, "Marketing", "Specialist"),

                new Employee(11, "Rahul", "Sharma", 51000, 31, "IT", "Developer"),
                new Employee(12, "Divya", "Mishra", 59000, 24, "HR", "Executive"),
                new Employee(13, "Sanjay", "Kumar", 61000, 33, "Finance", "Analyst"),
                new Employee(14, "Isha", "Rao", 68000, 29, "IT", "Tester"),
                new Employee(15, "Prakash", "Naidu", 55000, 38, "Operations", "Supervisor"),
                new Employee(16, "Meera", "Shah", 72000, 34, "Marketing", "Manager"),
                new Employee(17, "Karan", "Patel", 49000, 26, "Sales", "Salesman"),
                new Employee(18, "Riya", "Desai", 54000, 27, "HR", "Assistant"),
                new Employee(19, "Abhishek", "Saxena", 60000, 30, "Finance", "Auditor"),
                new Employee(20, "Sunita", "Joshi", 62000, 32, "Admin", "Administrator"),

                // Duplicates + Indian Names
                new Employee(21, "Amit", "Sharma", 50000, 30, "IT", "Developer"), // duplicate
                new Employee(22, "Sneha", "Reddy", 70000, 28, "IT", "Developer"), // duplicate
                new Employee(23, "Manish", "Kulkarni", 56000, 36, "Logistics", "Officer"),
                new Employee(24, "Kavya", "Iyer", 53000, 27, "HR", "Coordinator"), // duplicate
                new Employee(25, "Suresh", "Pillai", 48000, 29, "Sales", "Representative"), // duplicate
                new Employee(26, "Deepak", "Shetty", 65000, 30, "Finance", "Consultant"),
                new Employee(27, "Harsh", "Mohanty", 59000, 29, "IT", "Support"),
                new Employee(28, "Aishwarya", "Naik", 74000, 33, "Marketing", "Lead"),
                new Employee(29, "Pooja", "Tripathi", 52000, 25, "HR", "Recruiter"),
                new Employee(30, "Tushar", "Bansal", 61000, 31, "Finance", "Analyst"),

                // 31–40
                new Employee(31, "Amit", "Sharma", 50000, 30, "IT", "Developer"),
                new Employee(32, "Priya", "Verma", 60000, 25, "HR", "Manager"),
                new Employee(33, "Rohit", "Gupta", 55000, 35, "Finance", "Analyst"),
                new Employee(34, "Sneha", "Reddy", 70000, 28, "IT", "Developer"),
                new Employee(35, "Vikas", "Mehta", 65000, 40, "Marketing", "Executive"),
                new Employee(36, "Nisha", "Yadav", 63000, 27, "Marketing", "Specialist"),
                new Employee(37, "Varun", "Sethi", 58000, 35, "Finance", "Officer"),
                new Employee(38, "Meera", "Shah", 72000, 34, "Marketing", "Manager"),
                new Employee(39, "Karan", "Patel", 50000, 30, "IT", "Tester"),
                new Employee(40, "Riya", "Desai", 54000, 26, "HR", "HRBP"),

                // 41–60
                new Employee(41, "Tanvi", "Chatterjee", 65000, 31, "Finance", "Advisor"),
                new Employee(42, "Sagar", "Rana", 48000, 28, "Sales", "Executive"),
                new Employee(43, "Naveen", "Kohli", 61000, 33, "Finance", "Analyst"),
                new Employee(44, "Geeta", "Sharma", 62000, 30, "Admin", "Admin Officer"),
                new Employee(45, "Anjali", "Nair", 72000, 32, "IT", "Manager"), // duplicate
                new Employee(46, "Arjun", "Chopra", 58000, 31, "Finance", "Consultant"), // duplicate
                new Employee(47, "Kavya", "Iyer", 53500, 27, "HR", "Coordinator"),
                new Employee(48, "Suresh", "Pillai", 48000, 29, "Sales", "Representative"),
                new Employee(49, "Rohit", "Gupta", 56000, 36, "Finance", "Analyst"),
                new Employee(50, "Sneha", "Reddy", 71000, 29, "IT", "Developer"),

                // 51–200 (shortened but consistent)
                new Employee(51, "Amit", "Sharma", 50500, 31, "IT", "Developer"),
                new Employee(52, "Priya", "Verma", 60500, 26, "HR", "Manager"),
                new Employee(53, "Rohit", "Gupta", 55200, 35, "Finance", "Analyst"),
                new Employee(54, "Sneha", "Reddy", 70200, 28, "IT", "Developer"),
                new Employee(55, "Vikas", "Mehta", 65200, 40, "Marketing", "Executive"),
                new Employee(56, "Anjali", "Nair", 72500, 32, "IT", "Manager"),
                new Employee(57, "Suresh", "Pillai", 48100, 29, "Sales", "Representative"),
                new Employee(58, "Kavya", "Iyer", 53200, 27, "HR", "Coordinator"),
                new Employee(59, "Arjun", "Chopra", 58200, 31, "Finance", "Consultant"),
                new Employee(60, "Nisha", "Yadav", 62100, 26, "Marketing", "Specialist"),

                new Employee(61, "Abhishek", "Saxena", 64000, 32, "Finance", "Auditor"),
                new Employee(62, "Sunita", "Joshi", 59000, 34, "Operations", "Officer"),
                new Employee(63, "Harsh", "Mohanty", 55000, 30, "IT", "Tester"),
                new Employee(64, "Divya", "Mishra", 65000, 28, "HR", "Recruiter"),
                new Employee(65, "Karan", "Patel", 49500, 26, "Sales", "Salesman"),
                new Employee(66, "Riya", "Desai", 54500, 27, "HR", "Assistant"),
                new Employee(67, "Sanjay", "Kumar", 61500, 31, "Finance", "Analyst"),
                new Employee(68, "Meera", "Shah", 72500, 34, "Marketing", "Manager"),
                new Employee(69, "Pooja", "Tripathi", 52500, 25, "HR", "Recruiter"),
                new Employee(70, "Tushar", "Bansal", 70500, 41, "IT", "Architect"),

                // 71–200 condensed but valid
                new Employee(71, "Sneha", "Reddy", 70000, 28, "IT", "Developer"),
                new Employee(72, "Amit", "Sharma", 50000, 30, "IT", "Developer"),
                new Employee(73, "Priya", "Verma", 60000, 25, "HR", "Manager"),
                new Employee(74, "Rohit", "Gupta", 55000, 35, "Finance", "Analyst"),
                new Employee(75, "Suresh", "Pillai", 48000, 29, "Sales", "Representative"),
                new Employee(76, "Anjali", "Nair", 72000, 32, "IT", "Manager"),
                new Employee(77, "Arjun", "Chopra", 58000, 31, "Finance", "Consultant"),
                new Employee(78, "Kavya", "Iyer", 53000, 27, "HR", "Coordinator"),
                new Employee(79, "Nisha", "Yadav", 62000, 26, "Marketing", "Specialist"),
                new Employee(80, "Vikas", "Mehta", 65000, 40, "Marketing", "Executive")

                // (Remaining 120 entries follow same structure—Indian names, roles, duplicates)
                // To keep message within limits, I will send 81–200 if you say "continue"


        ));
    }
}
