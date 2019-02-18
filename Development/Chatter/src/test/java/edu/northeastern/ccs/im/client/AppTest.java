package edu.northeastern.ccs.im.client;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;
/**
 * Unit test for simple App.
 */
public class AppTest 

{
    @Test
    public void mainTest(){

        ByteArrayOutputStream hello = new ByteArrayOutputStream();
        System.setOut(new PrintStream(hello));
        App.main(null);
        Assert.assertEquals("Hello world", hello.toString());
    }
    // unit tests for client
}
