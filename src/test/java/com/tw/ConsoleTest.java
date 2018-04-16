package com.tw;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConsoleTest {

//    @Test
//    public void should_printMessageSuccess_whenPrintlnInvoked() {
//        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(systemOut));
//
//        Console.println("this is test message");
//
//        assertThat(systemOut.toString(), is("this is test message\n"));
//    }

    @Test
    public void should_getConsoleInput() {
        System.setIn(new ByteArrayInputStream("this is input message".getBytes()));

        String result = Console.getInput();

        assertThat(result,is("this is input message"));
    }
}