package com.tw;

import java.util.Scanner;

public class Console {

    private static Scanner scanner = new Scanner(System.in);

    public static void println(String message) {
        System.out.println(message);
    }

    public static String getInput() {
        return scanner.nextLine();
    }
}
