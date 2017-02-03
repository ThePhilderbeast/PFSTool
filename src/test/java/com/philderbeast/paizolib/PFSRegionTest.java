/**
* @Author: Phillip Ledger
* @Date:   2016-12-03T23:49:54+11:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-03T23:55:59+11:00
*/
package com.philderbeast.paizolib;

import junit.framework.*;

public class PFSRegionTest extends TestCase{

	public PFSRegionTest( String testName)
	{
		super (testName);
	}

	public static Test suite()
	{
		return new TestSuite(PFSRegionTest.class);
	}

	//
	// Tests after here
	//
	public void testCreateNew()
	{
		PFSRegion pfr = new PFSRegion("testRegion", "test location");
		assertTrue(pfr != null); 
	}

}