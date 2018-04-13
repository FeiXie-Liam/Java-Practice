package com.tw;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LibraryTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup(){
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut(){
        return outContent.toString();
    }

//    @Test
//    public void testSomeLibraryMethod() {
//        Library classUnderTest = new Library();
////        classUnderTest.mainEntry();
////
////        String expectOutput = "";
////        assertEquals(systemOut(), expectOutput);
//        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
//    }

    @Test
    public void testMockClass() throws Exception {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        String value = "first";
        when(mockedList.get(0)).thenReturn(value);

        assertEquals(mockedList.get(0), value);

    }

}
