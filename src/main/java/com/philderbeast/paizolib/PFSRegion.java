/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-04T00:04:36+11:00
*/
package com.philderbeast.paizolib;

import org.hibernate.*;
import org.hibernate.cfg.*;

import java.util.*;

public class PFSRegion {

	private String regionName;
	private String location;
	private ArrayList<Scenario> scenarioList = new ArrayList<Scenario>();
	private ArrayList<Venue> venues = new ArrayList<Venue>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Session> sessions = new ArrayList<Session>();

	public PFSRegion(String inName, String inLoc){
		regionName = inName;
		location = inLoc;
	}

	public void addVenue(Venue inVenue){
		venues.add(new Venue(inVenue));
	}

	public void addPlayer(Player inPlayer){
		if (! players.contains(inPlayer.getNumber())){
			players.add(inPlayer);
		}
	}

	public void addScenario(Scenario s) {
		if (!scenarioList.contains(s))
		{
			scenarioList.add(s);
		}
	}

	public void addSession(Session s)
	{
		if (!sessions.contains(s))
		{
			sessions.add(s);
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

	//public String printEvent(int venueIndex, String eventNumber){
	//	//TODO: fix this
	//	return "";
	//}

	public String getName(){
		return regionName;
	}

	public String getLoc(){
		return location;
	}

	public ArrayList<Player> getPlayerList(){
		return players;
	}

	public ArrayList<Scenario> getScenarios()
	{
		return scenarioList;
	}

	public ArrayList<Venue> getVenueList()
	{
		return venues;
	}

	public int getVenueIndex(String venueName) {
		int i = 0;
		for (Venue v : venues) {
			if (venueName.equals(v.getName())) {
				return i;
			} else {
				i++;
			}
		}
		return -1;
	}

	public int getNumPlayers(){
		return players.size();
	}

	public int getNumGMs(){
		//TODO: get the acctual number of GM's
		return 0;
	}

	public int getNumVenues(){
		return (venues.size());
	}

	public int getNumScenarios(){
		return scenarioList.size();
	}

	public Player getPlayer(int PFSNumber){
		//TODO: get the acctual PFS number
		
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
			.setProperty("hibernate.dialect", 	"org.hibernate.dialect.SQLiteDialect")
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

			//save the scenario list
			for (Scenario s: scenarioList)
			{
				try
				{
					session.beginTransaction();
					session.saveOrUpdate(s);
					session.getTransaction().commit();
				}catch (HibernateException e) {
            		e.printStackTrace();
            		session.getTransaction().rollback();
				}
			}

			//save the venue list
			for (Venue v: venues)
			{
				try
				{
					session.beginTransaction();
					session.saveOrUpdate(v);
					session.getTransaction().commit();
				}catch (HibernateException e) {
            		e.printStackTrace();
            		session.getTransaction().rollback();
				}
			}

		session.close();
	}
}
