/**
* @Author: Phillip Ledger
* @Date:   2016-12-03T23:49:54+11:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-03T23:55:59+11:00
*/
package com.philderbeast.paizolib;

import junit.framework.*;

import java.util.Date;

public class SessionTest extends TestCase
{

    public SessionTest(String testName)
    {
        super (testName);
    }

    public static Test suite()
	{
		return new TestSuite(SessionTest.class);
	}

    //
	// Tests after here
	//
    public void test1CreateSession()
    {
        Session s = new Session(new Scenario("test Scenario", 0, 0), 
                                new Player("007", "player", "John", "Smith", "e@mail.com"),
                                new Date(), 
                                "123456", 
                                false);
        assertTrue(s != null);
    }

}