package Assignments;

import java.util.Arrays;
import java.util.List;

public class A1 {

    public static void main(String[] args) {


        //Print names having more than 5 characters in uppercase
        List<String> employees= Arrays.asList("Jon","Ben","David","Alexandra","Christiana","Ana","Chandu");

        employees.stream()
                .filter(e->e.length()>5)
                .map(e->e.toUpperCase())
                .forEach(e->System.out.println(e));

    }




}
