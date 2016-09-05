/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-28T21:23:34+10:00
*/
package com.philderbeast.paizolib;

import com.philderbeast.paizoscraper.*;

import java.text.DateFormat;
import java.util.*;
import java.io.*;

public class PFSRegion implements Serializable{

	private static final long serialVersionUID = -6148896503023018223L;

	protected Vector venues = new Vector(1,1);
	protected Venue conventions;
	protected PlayerList players = new PlayerList();
	protected String regionName;
	protected String location;
	public ScenarioList scenarioList = new ScenarioList();
	public transient PaizoSite paizoSite;

	public PFSRegion(String inName, String inLoc){
		regionName = inName;
		location = inLoc;
		conventions = new Venue("Conventions","Various");
	}

	public void addVenue(String inVenueName){
		venues.addElement(new Venue(inVenueName));
	}

	public void addVenue(Venue inVenue){
		venues.addElement(new Venue(inVenue));
	}

	public void addVenue(String inVenueName, String inVenueAdd){
		venues.addElement(new Venue(inVenueName, inVenueAdd));
	}

	public void addPlayer(String inPFSNumber, String inURL, String inGiven, String inSur, String inEMail){
		if (! players.contains(inPFSNumber))
			players.add(new Player(inPFSNumber, inURL, inGiven, inSur, inEMail));
	}

	public void addPlayer(Player inPlayer){
		if (! players.contains(inPlayer.getNumber()))
			players.add(inPlayer);
	}

	public void addEvent(int venueIndex, Event inEvent){
		if (venueIndex <= venues.size()){
			Venue currentVenue = (Venue) venues.get(venueIndex);
			currentVenue.addEvent(inEvent);
		} else {
			conventions.addEvent(inEvent);
		}
	}

	public void addEvent(int venueIndex, String eventCode) {
		if (venueIndex <= venues.size()){
			Venue currentVenue = (Venue) venues.get(venueIndex);
			currentVenue.addEvent(eventCode);
		} else {
			conventions.addEvent(eventCode);
		}
	}

	public void addEvent(int venueIndex, String eventCode, String eventName, String eventURL) {
		if (venueIndex <= venues.size()){
			Venue currentVenue = (Venue) venues.get(venueIndex);
			currentVenue.addEvent(eventCode, eventName, eventURL);
		} else {
			conventions.addEvent(eventCode, eventName, eventURL);
		}
	}

	public void renameVenue(int venueIndex, String newName){
		Venue currentVenue = (Venue) venues.get(venueIndex);
		currentVenue.changeName(newName);
	}

	public void moveVenue(int venueIndex, String newAddress){
		if (venueIndex <= venues.size()){
			Venue currentVenue = (Venue) venues.get(venueIndex);
			currentVenue.setAddress(newAddress);
		} else {
			conventions.setAddress(newAddress);
		}
	}

	public String printEvent(int venueIndex, String eventNumber){
		Venue currentVenue = (Venue) venues.get(venueIndex);
		Enumeration vEnum = currentVenue.eventList.elements();
		while (vEnum.hasMoreElements()){
			Event currentEvent = (Event) vEnum.nextElement();
			if (currentEvent.getEventNumber().equals(eventNumber)) {
				return currentEvent.toString();
			}
		}
		return "No event (" + eventNumber + ") as this venue (" + currentVenue.venueName + ")";
	}

	public String getName(){
		return regionName;
	}

	public String getLoc(){
		return location;
	}

	public PlayerList getPlayerList(){
		return players;
	}

	public String[] getVenueList(){
		Iterator itVenues = venues.iterator();
		Venue currentVenue;
		currentVenue = (Venue) itVenues.next();
		String vList = currentVenue.getName();

		while (itVenues.hasNext()) {
			currentVenue = (Venue) itVenues.next();
			vList += "$" + currentVenue.getName();
		}
		return vList.split("\\$");
	}

	public int getVenueIndex(String venueName){
		Iterator itVenues = venues.iterator();
		Venue currentVenue;
		int i = 0;
		while (itVenues.hasNext()) {
			currentVenue = (Venue) itVenues.next();
			if (venueName.equals(currentVenue.getName())) {
				return i;
			} else {
				i++;
			}
		}

		return i+1;
	}

	public int getNumPlayers(){
		return players.size();
	}

	public int getNumGMs(){
		return players.getNumGMs();
	}

	public int getNumVenues(){
		return (venues.size());
	}

	public int getNumScenarios(){
		return scenarioList.size();
	}

	public int[][] getVenueLast12Totals(int vIndex){
		Venue venue = (Venue) venues.get(vIndex);
		Vector eventList = venue.getEvents();
		int[][] stats = new int[13][3];
		Vector[] playerLists = new Vector[13];
		Vector[] gmLists = new Vector[13];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		PFSDate[] comparitors = new PFSDate[13];
		comparitors[12] = new PFSDate(1 + "/" + month + "/" + year);

		for (int i = 0; i < 13; i++)
			playerLists[i] = new Vector(5,5);
		for (int i = 0; i < 13; i++)
			gmLists[i] = new Vector(5,5);

		for (int i = 11; i > 11-month+1; i--)
			comparitors[i] = new PFSDate(1 + "/" + (month - (11-i)-1) + "/" + year);
		for (int i = 11-month+1; i >= 0; i--)
			comparitors[i] = new PFSDate(1 + "/" + (i+month) + "/" + (year-1));

		Enumeration eEnum = eventList.elements();
		while (eEnum.hasMoreElements()){
			Event currentEvent = (Event) eEnum.nextElement();
			Vector sessionList = currentEvent.getSessionList();
			Enumeration sEnum = sessionList.elements();
			while (sEnum.hasMoreElements()){
				EventSession currentSession = (EventSession) sEnum.nextElement();
				PFSDate sessDate = currentSession.getSessionDate();
				Vector sessPlayerList = currentSession.getPlayerList();
				for (int i = 0; i < 13; i++){
					if (comparitors[i].sameMonth(sessDate)) {
						for (int j = 0; j < sessPlayerList.size(); j++){
							if (!playerLists[i].contains(sessPlayerList.get(j))){
								playerLists[i].add(sessPlayerList.get(j));
							}
						}
						if (!gmLists[i].contains(currentSession.getGM())){
							gmLists[i].add(currentSession.getGM());
						}
						stats[i][1]++;
					}
				}
			}
		}

		for (int i = 0; i < 13; i ++){
			stats[i][0] = playerLists[i].size();
			stats[i][2] = gmLists[i].size();
		}

		return stats;
	}

	public int[][] getConvetionTotals(){
		Vector eventList = conventions.getEvents();
		int numEvents = eventList.size();
		int eventIndex = 0;
		int[][] stats = new int[numEvents][3];
		Vector[] playerLists = new Vector[numEvents];
		Vector[] gmLists = new Vector[numEvents];
		for (int i = 0; i < numEvents; i++)
			playerLists[i] = new Vector(5,5);
		for (int i = 0; i < numEvents; i++)
			gmLists[i] = new Vector(5,5);


		Enumeration eEnum = eventList.elements();
		while (eEnum.hasMoreElements()){
			Event currentEvent = (Event) eEnum.nextElement();
			Vector sessionList = currentEvent.getSessionList();
			Enumeration sEnum = sessionList.elements();
			while (sEnum.hasMoreElements()){
				EventSession currentSession = (EventSession) sEnum.nextElement();
				Vector sessPlayerList = currentSession.getPlayerList();
				for (int j = 0; j < sessPlayerList.size(); j++){
					if (!playerLists[eventIndex].contains(sessPlayerList.get(j))){
						playerLists[eventIndex].add(sessPlayerList.get(j));
					}
				}
				if (!gmLists[eventIndex].contains(currentSession.getGM())){
					gmLists[eventIndex].add(currentSession.getGM());
				}
				stats[eventIndex][1]++;
			}
			eventIndex++;
		}

		for (int i = 0; i < numEvents; i ++){
			stats[i][0] = playerLists[i].size();
			stats[i][2] = gmLists[i].size();
		}

		return stats;
	}

	//TODO: make this work
	public int[][] getLast12MonthsTotals(){
		int[][] stats = new int[13][3];
		Vector[] playerLists = new Vector[13];
		Vector[] gmLists = new Vector[13];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		PFSDate[] comparitors = new PFSDate[13];
		comparitors[12] = new PFSDate(1 + "/" + month + "/" + year);

		for (int i = 0; i < 13; i++)
			playerLists[i] = new Vector(5,5);
		for (int i = 0; i < 13; i++)
			gmLists[i] = new Vector(5,5);

		for (int i = 11; i > 11-month+1; i--)
			comparitors[i] = new PFSDate(1 + "/" + (month - (11-i)-1) + "/" + year);
		for (int i = 11-month+1; i >= 0; i--)
			comparitors[i] = new PFSDate(1 + "/" + (i+month) + "/" + (year-1));

		Enumeration vEnum = venues.elements();
		while (vEnum.hasMoreElements()){
			Venue currentVenue = (Venue) vEnum.nextElement();
			Vector eventList = currentVenue.getEvents();
			Enumeration eEnum = eventList.elements();

			while (eEnum.hasMoreElements()){
				Event currentEvent = (Event) eEnum.nextElement();
				Vector sessionList = currentEvent.getSessionList();
				Enumeration sEnum = sessionList.elements();

				while (sEnum.hasMoreElements()){
					EventSession currentSession = (EventSession) sEnum.nextElement();
					PFSDate sessDate = currentSession.getSessionDate();
					Vector sessPlayerList = currentSession.getPlayerList();

					for (int i = 0; i < 13; i++){
						if (comparitors[i].sameMonth(sessDate)) {
							for (int j = 0; j < sessPlayerList.size(); j++){
								if (!playerLists[i].contains(sessPlayerList.get(j))){
									playerLists[i].add(sessPlayerList.get(j));
								}
							}
							if (!gmLists[i].contains(currentSession.getGM())){
								gmLists[i].add(currentSession.getGM());
							}
							stats[i][1]++;
						}
					}
				}
			}
		}

		for (int i = 0; i < 13; i ++){
			stats[i][0] = playerLists[i].size();
			stats[i][2] = gmLists[i].size();
		}

		return stats;
	}

	public Player getPlayer(int PFSNumber){
		return players.getPlayer(PFSNumber);
	}

	public String toString(){
		String rtnStr;
		Venue currentVenue;

		rtnStr = regionName + "\n" + location + "\n" + venues.size() + "$" + players.size() + "\n";
		for (short i = 0; i < venues.size(); i ++) {
			currentVenue = (Venue) venues.get(i);
			rtnStr += currentVenue;
		}

		rtnStr += conventions;

		rtnStr += "\n" + players;

		return rtnStr.trim();
	}

	public void writeToFile(String fileName){

		try {
			FileOutputStream fw;
			if(fileName.matches("(.+?)[.rgn]"))
			{
				fw = new FileOutputStream(fileName);
			} else
			{
				//we need to add the extension
				fw = new FileOutputStream(fileName + ".rgn");
			}

			ObjectOutputStream out = new ObjectOutputStream(fw);
			out.writeObject(this);
			out.close();
			fw.close();
		} catch(IOException e){
			System.out.println("\n" + e.toString());
		}
	}
}
