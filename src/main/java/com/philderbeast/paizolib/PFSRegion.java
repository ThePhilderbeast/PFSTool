/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T19:48:52+10:00
*/
package com.philderbeast.paizolib;

import com.philderbeast.paizoscraper.*;
import org.hibernate.*;
import org.hibernate.annotations.*;
import org.hibernate.cfg.*;

import java.io.*;
import java.text.DateFormat;
import java.util.*;
import javax.persistence.*;

public class PFSRegion {

	private String regionName;
	private String location;
	private ArrayList<Scenario> scenarioList = new ArrayList<Scenario>();
	private ArrayList<Venue> venues = new ArrayList<Venue>();
	private ArrayList<Player> players = new ArrayList<Player>();

	public PFSRegion(String inName, String inLoc){
		regionName = inName;
		location = inLoc;

	}

	public void addVenue(Venue inVenue){
		venues.add(new Venue(inVenue));
	}

	public void addPlayer(String inPFSNumber, String inURL, String inGiven, String inSur, String inEMail){
		if (! players.contains(inPFSNumber))
			players.add(new Player(inPFSNumber, inURL, inGiven, inSur, inEMail));
	}

	public void addPlayer(Player inPlayer){
		if (! players.contains(inPlayer.getNumber()))
			players.add(inPlayer);
	}

	public void addEvent(Event inEvent){
	//	venues.addEvent(inEvent);
	}

	public void addScenario(Scenario s)
	{
		if (!scenarioList.contains(s))
		{
			scenarioList.add(s);
		}
	}

	public void renameVenue(int venueIndex, String newName){
		Venue currentVenue = (Venue) venues.get(venueIndex);
		currentVenue.changeName(newName);
	}

	public void moveVenue(int venueIndex, String newAddress){
		Venue currentVenue = (Venue) venues.get(venueIndex);
		currentVenue.setAddress(newAddress);
	}

	public String printEvent(int venueIndex, String eventNumber){
		//TODO: fix this
		return "";
	}

	public String getName(){
		return regionName;
	}

	public String getLoc(){
		return location;
	}

	public ArrayList getPlayerList(){
		return players;
	}

	public ArrayList getScenarios()
	{
		return scenarioList;
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
		//TODO: fix this using a query
		return 0;
	}

	public int getNumVenues(){
		return (venues.size());
	}

	public int getNumScenarios(){
		return scenarioList.size();
	}

	public Player getPlayer(int PFSNumber){
		//TODO: fix this
		return null;
	}

	public String toString(){
		String rtnStr;
		Venue currentVenue;

		rtnStr = regionName + "\n" + location + "\n" + venues.size() + "$" + players.size() + "\n";
		for (short i = 0; i < venues.size(); i ++) {
			currentVenue = (Venue) venues.get(i);
			rtnStr += currentVenue;
		}

		rtnStr += "\n" + players;

		return rtnStr.trim();
	}

	public void writeToFile(String fileName){

		if(!(fileName.endsWith(".rgn")))
		{
			fileName += ".rgn";
		}

		//set up hibernate
		Configuration config = new Configuration()
			.addAnnotatedClass(com.philderbeast.paizolib.Event.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Player.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Scenario.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Session.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Venue.class)
			.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect")
			.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
			.setProperty("hibernate.connection.url", "jdbc:sqlite:" + fileName)
			.setProperty("hibernate.hbm2ddl.auto", "update");

		SessionFactory sessionFactory = config.buildSessionFactory();

		org.hibernate.Session session = sessionFactory.openSession();
			//save the players
			for(Player p: players)
			{
				try
				{
					session.beginTransaction();
					session.saveOrUpdate(p);
					session.getTransaction().commit();
				}catch (HibernateException e) {
            		e.printStackTrace();
            		session.getTransaction().rollback();
				}
			}
		session.close();
	}
}
