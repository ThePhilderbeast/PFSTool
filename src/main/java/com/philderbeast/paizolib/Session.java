/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T08:12:53+10:00
*/
package com.philderbeast.paizolib;

public abstract class Session {
	protected String eventNumber;
	protected String eventName;
	protected String sessNum;
	protected String scenarioName;
	protected PFSDate sessDate;

	public PFSDate getSessionDate(){
		return sessDate;
	}

	public String[] getSessionDetails(){
		String[] rtnStr = {sessNum, eventName, eventNumber};
		return rtnStr;
	}

	public String getScenario(){
		return scenarioName;
	}

}
