package com.chandu.debugging;

public class NullPointerException {

    public static void main(String[] args) {
        String name = "null";
        if(name!=null){
            System.out.println(name.toUpperCase());
        }else
        {
            System.out.println("Name is null");
        }
    }

}
