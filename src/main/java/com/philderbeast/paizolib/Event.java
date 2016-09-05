/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T11:44:07+10:00
*/
package com.philderbeast.paizolib;

import java.util.*;
import java.io.*;

class Event implements Serializable{

	protected String eventSessionURL;
	protected String eventNumber;
	protected String eventName;
	protected Vector sessions;

	Event(String inEventNumber){
		eventNumber = inEventNumber;
		//Magic happens using th event number to get the URL and the Name
		sessions = new Vector(5,5);
		getSessionData();
	}

	Event(String inEventNumber, String inEventName, String inEventSessionURL){
		eventNumber = inEventNumber;
		eventName = inEventName;
		eventSessionURL = inEventSessionURL;
		sessions = new Vector(5,5);
		//getSessionData();
	}

	Event(String inEventNumber, String inEventName, String inEventSessionURL, Vector inSessList){
		eventNumber = inEventNumber;
		eventName = inEventName;
		eventSessionURL = inEventSessionURL;
		sessions = inSessList;
	}

	Event(Event inEvent){
		eventSessionURL = inEvent.getEventURL();
		eventNumber = inEvent.getEventNumber();
		eventName = inEvent.getEventName();
		sessions = inEvent.getSessionList();
	}

	void addSession(String inSessionNumber, String inScenarioName, String inGM, String inEventDate, Vector inPlayerList){
		sessions.addElement(new EventSession(eventNumber, eventName, inSessionNumber, inScenarioName, inGM, inEventDate, inPlayerList));
	}

	String listSessions(){
		String rtnString;
		rtnString = "List of Sessions for " + eventName + "$" + sessions.size() + "$" + eventNumber + "$" + eventSessionURL + "\n";
		Enumeration vEnum = sessions.elements();
		while(vEnum.hasMoreElements())
			rtnString = rtnString + vEnum.nextElement() + "\n";
		return rtnString;
	}

	String getEventURL(){
		return eventSessionURL;
	}

	String getEventNumber(){
		return eventNumber;
	}

	String getEventName(){
		return eventName;
	}

	int getNumSessions(){
		return sessions.size();
	}

	Vector getSessionList(){
		return sessions;
	}

	void getSessionData(){
		//Magic Happens!
		Vector playerList = new Vector(3,0);
		playerList.addElement("11635-1");
		playerList.addElement("13792-1");
		playerList.addElement("12826-1");

		sessions.addElement(new EventSession(eventNumber,eventName,"1","Test Scenario","28013-1","07 Jan 2013",playerList));
	}

	public String toString(){
		return eventName + "\n" + listSessions();
	}
}
