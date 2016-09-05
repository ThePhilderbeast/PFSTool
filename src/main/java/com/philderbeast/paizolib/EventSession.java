/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T11:44:41+10:00
*/
package com.philderbeast.paizolib;

import com.philderbeast.paizolib.*;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

class EventSession extends Session implements Serializable{
	protected String GM;
	protected Vector playerList;

	EventSession(String inEventNumber, String inEventName, String inSessionNumber, String inScenarioName, String inGM, String inEventDate, Vector inPlayerList){
		eventNumber = inEventNumber;
		eventName = inEventName;
		sessNum = inSessionNumber;
		scenarioName = inScenarioName;
		GM = inGM;
		sessDate = new PFSDate(inEventDate);
		playerList = new Vector(7,0);
		playerList = inPlayerList;
		playerList.trimToSize();
	}

	EventSession(String inEventNumber, String inEventName, String inEventSession){
		String inputItems[] = inEventSession.split("\\$");
		eventNumber = inEventNumber;
		eventName = inEventName;
		sessNum = inputItems[0];
		sessDate = new PFSDate(inputItems[1]);
		scenarioName = inputItems[2];
		GM = inputItems[3];
		playerList = new Vector(7,0);
		for (int i = 4; i < inputItems.length;i++){
			playerList.addElement(inputItems[i]);
		}
		playerList.trimToSize();
	}

	public int getNumPlayers(){
		return playerList.size();
	}

	public String getGM(){
		return GM;
	}

	public Vector getPlayerList(){
		return playerList;
	}

	public String toString(){
		String rtnStr;
		rtnStr = sessNum + "$" + sessDate.toString() + "$" + scenarioName + "$" + GM;
		Enumeration vEnum = playerList.elements();
		while (vEnum.hasMoreElements())
			rtnStr = rtnStr + "$" + vEnum.nextElement();
		return rtnStr;
	}
}
