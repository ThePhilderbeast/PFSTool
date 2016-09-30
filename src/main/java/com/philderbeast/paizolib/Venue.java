/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T20:02:32+10:00
*/
package com.philderbeast.paizolib;

import java.io.*;
import java.util.*;
import javax.persistence.*;

@Entity
@Table
public class Venue{

	@Id
	private String venueName;
	private String venueAddress;

	@OneToMany(mappedBy="eventNumber")
	private List<Event> eventList;

	public Venue(String inName){
		venueName = inName;
		eventList = new ArrayList<Event>();
	}

	public Venue(String inName, String inAddress){
		venueName = inName;
		venueAddress = inAddress;
		eventList = new ArrayList<Event>();
	}

	protected Venue(Venue inVenue){
		venueName = inVenue.getName();
		venueAddress = inVenue.getAddress();
		eventList = inVenue.getEvents();
	}

	public void addEvent(Event inEvent){
		eventList.add(new Event(inEvent));
	}

	public void addEvent(String inNumber){
		eventList.add(new Event(inNumber));
	}

	protected void addEvent(String inNumber, String inEventName, String inEventSessionURL){
		eventList.add(new Event(inNumber, inEventName, inEventSessionURL));
	}

	protected void changeName(String newName){
		venueName = newName;
	}

	protected void setAddress(String inAdd){
		venueAddress = inAdd;
	}

	public String getName(){
		return venueName;
	}

	public String getAddress(){
		return venueAddress;
	}

	public ArrayList<Event> getEvents(){
		return (ArrayList<Event>)eventList;
	}

	protected String listEvents(){
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
