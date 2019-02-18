package edu.northeastern.ccs.im.server;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 

{
    @Test
    public void getHelloTest(){

        App hi = new App("Hello World");
        Assert.assertEquals("Hello world",hi.getHello());
    }
// tests for server
}
