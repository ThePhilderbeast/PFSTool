/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T11:43:46+10:00
*/
package com.philderbeast.paizolib;


import java.util.*;
import java.io.*;

public class Venue implements Serializable{
	protected String venueName;
	protected String venueAddress;
	protected Vector eventList;

	public Venue(String inName){
		venueName = inName;
		eventList = new Vector(5,5);
	}

	public Venue(String inName, String inAddress){
		venueName = inName;
		venueAddress = inAddress;
		eventList = new Vector(5,5);
	}

	Venue(Venue inVenue){
		venueName = inVenue.getName();
		venueAddress = inVenue.getAddress();
		eventList = inVenue.getEvents();
	}

	public void addEvent(Event inEvent){
		eventList.addElement(new Event(inEvent));
	}

	public void addEvent(String inNumber){
		eventList.addElement(new Event(inNumber));
	}

	void addEvent(String inNumber, String inEventName, String inEventSessionURL){
		eventList.addElement(new Event(inNumber, inEventName, inEventSessionURL));
	}

	void changeName(String newName){
		venueName = newName;
	}

	void setAddress(String inAdd){
		venueAddress = inAdd;
	}

	public String getName(){
		return venueName;
	}

	public String getAddress(){
		return venueAddress;
	}

	public Vector getEvents(){
		return eventList;
	}

	String listEvents(){
		String rtnString;
		int i;
		Event currentEvent;

		rtnString = "Events held at " + venueName + "$" + eventList.size() + "\n";
		for (i = 0; i < eventList.size(); i ++) {
			currentEvent = (Event) eventList.get(i);
			rtnString = rtnString + currentEvent + "\n";
		}
		return rtnString;
	}

	public String toString(){
		return venueName + "\n" + venueAddress + "\n" + listEvents() + "\n";
	}
}
