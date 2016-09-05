/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-29T17:39:55+10:00
*/
package com.philderbeast.paizolib;

import java.io.*;

public class PlayerSession extends Session implements Comparable<PlayerSession>, Serializable{
	protected String characterNumber;
	public String eventNumber;
	public String eventName;
	public String sessNum;
	public String scenarioName;
	public String sessDate;
	public int season;
	public int scenario;

	public PlayerSession(String inEventNumber, String inEventName, String inSessionNumber, String inScenarioName, String inCharacterNumber, String inEventDate){
		eventNumber = inEventNumber;
		eventName = inEventName;
		sessNum = inSessionNumber;
		scenarioName = inScenarioName;
		sessDate = inEventDate;
		characterNumber = inCharacterNumber;
	}

	public String getScenario()
	{
		return scenarioName;
	}

	public String toString(){
		return eventNumber + "$" + eventName + "$" + sessNum + "$" + scenarioName + "$" + characterNumber  + "$" + sessDate;
	}

	@Override
	public int compareTo(PlayerSession ps)
	{
		return scenarioName.compareTo(ps.scenarioName);
	}
}
