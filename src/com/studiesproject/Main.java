package com.studiesproject;

public class Main {

    protected static final String REGEX_1 = "[0-9]*:(0+[1-9]):(([0-2]+[0-9])|30|31)";

    public static void main(String[] args) {
	// write your code here

        String test = "1232:09:30";
        if (test.matches(REGEX_1)) {
            System.out.println("spelniam regex");
        }
    }
}
