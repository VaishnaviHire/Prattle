package edu.northeastern.ccs.im.client;

import org.junit.Assert;
import org.junit.Test;
/**
 * Unit test for simple App.
 */
public class AppTest 

{
    @Test
    public void getHelloTest(){

        App hi = new App("Hello world");
        Assert.assertEquals("Hello world",hi.getHello());
    }
    // unit tests for client
}
