/**
* @Author: Phillip Ledger
* @Date:   2016-12-03T23:49:54+11:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-03T23:55:59+11:00
*/
package com.philderbeast.paizolib;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.*;

//Sadly these need to run in order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public void test1NewRegion()
	{
		PFSRegion pfr = PFSRegion.getInstance();
		pfr.setName("testRegion", "test location");
		assertTrue(pfr != null);
	}

	public void test2SaveRegion()
	{
		PFSRegion pfr = PFSRegion.getInstance();
		pfr.setName("testRegion", "test location");
		pfr.writeToFile("./test.rgn");
	}

	public void test3LoadRegion()
	{

		//make sure there is a file to load
		//testSaveRegion();

		PFSRegion pfr = PFSRegion.getInstance();
		pfr.load("./test.rgn");

		//reload the instance
		pfr = PFSRegion.getInstance();
		System.err.println("instance name = " + pfr.getName());
		assertTrue(pfr.getName().equals("testRegion"));
	}

}