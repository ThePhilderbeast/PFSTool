/**
* @Author: Phillip Ledger
* @Date:   2016-12-03T23:49:54+11:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-03T23:55:59+11:00
*/
package com.philderbeast.paizolib;

import junit.framework.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerTest extends TestCase{

	public PlayerTest(String testName)
	{
		super (testName);
	}

	public static Test suite()
	{
		return new TestSuite(PlayerTest.class);
	}

    //
	// Tests after here
	//
    public void test1CreatePlayer()
    {
        Player p = new Player("007", "player", "John", "Smith", "e@mail.com");
        assertTrue(p != null);
    }

    public void testplayedScenario()
    {
        assertTrue(true);
    }

}