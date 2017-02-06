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
import javax.persistence.*;
import javax.persistence.criteria.*;

@Entity
@Table
public class PFSRegion {

	@Transient
	private static PFSRegion instance = new PFSRegion();

	@Id
	@Column(nullable = false, unique = true)
	private String regionName;
	private String location;
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Scenario> scenarioList = new TreeSet<Scenario>();
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Venue> venues = new TreeSet<Venue>();
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Player> players = new TreeSet<Player>();
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Session> sessions = new TreeSet<Session>();

	//hibernate vars
	@Transient
	private Configuration config;
	@Transient
	private SessionFactory sessionFactory;

	private PFSRegion()
	{
		//singleton constructor
	}

	public static PFSRegion getInstance()
	{
		return instance;
	}

	public void setName(String inName, String inLoc){
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
			System.out.println("adding scenario " + s.name);
			scenarioList.add(s);
		}
	}

	public void addSession(Session s)
	{
		if (!sessions.contains(s))
		{
			System.out.println("adding session scenario: " + s.getScenario() + " player: " + s.getPlayer());
			sessions.add(s);
		}
	}

	public String getName(){
		return regionName;
	}

	public String getLoc(){
		return location;
	}

	public Set<Player> getPlayerList(){
		return (Set<Player>) players;
	}

	public Set<Scenario> getScenarios()
	{
		return (Set<Scenario>) scenarioList;
	}

	public Set<Venue> getVenueList()
	{
		return (Set<Venue>) venues; 
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
		int i = 0;
		for (Player p : players) {
			if (p.isGM())
			{
				i++;
			}
		}
		return i;
	}

	public int getNumVenues(){
		return (venues.size());
	}

	public int getNumScenarios(){
		return scenarioList.size();
	}

	public Player getPlayer(int PFSNumber){
		for (Player p: players) {
			if (p.getNumber() == PFSNumber)
			{
				return p;
			}
		}
		return null;
	}

	public void renameVenue(int venueIndex, String newName){
		Venue currentVenue = (Venue) venues.toArray()[venueIndex];
		currentVenue.changeName(newName);
	}

	public void moveVenue(int venueIndex, String newAddress){
		Venue currentVenue = (Venue) venues.toArray()[venueIndex];
		currentVenue.setAddress(newAddress);
	}

	public String toString(){
		String rtnStr;
		Venue currentVenue;

		rtnStr = regionName + "\n" + location + "\n" + venues.size() + "$" + players.size() + "\n";
		for (short i = 0; i < venues.size(); i ++) {
			currentVenue = (Venue) venues.toArray()[i];
			rtnStr += currentVenue;
		}

		rtnStr += "\n" + players;

		return rtnStr.trim();
	}

	private void configHibernate(String fileName)
	{

		if(!(fileName.endsWith(".rgn")))
		{
			fileName += ".rgn";
		}

		//set up hibernate
		config = new Configuration()
			.addAnnotatedClass(com.philderbeast.paizolib.PFSRegion.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Event.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Player.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Scenario.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Session.class)
			.addAnnotatedClass(com.philderbeast.paizolib.Venue.class)
			.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect")
			.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
			.setProperty("hibernate.connection.url", "jdbc:sqlite:" + fileName)
			.setProperty("hibernate.hbm2ddl.auto", "update");

		sessionFactory = config.buildSessionFactory();
	}

	public void writeToFile(String fileName){

		if (sessionFactory == null)
		{
			configHibernate(fileName);
		}

		org.hibernate.Session session = sessionFactory.openSession();
			
			try
			{
				session.beginTransaction();
				session.saveOrUpdate(this);
				session.getTransaction().commit();
			}catch (HibernateException e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			}

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

	public synchronized void load(String fileName)
	{
		configHibernate(fileName);
		org.hibernate.Session session = sessionFactory.openSession();
		session.beginTransaction();

		//load the PFSRegion
		CriteriaBuilder cb =  session.getCriteriaBuilder();
		CriteriaQuery<PFSRegion> cq = cb.createQuery(PFSRegion.class);
		cq.select(cq.from(PFSRegion.class));
		TypedQuery<PFSRegion> q = session.createQuery(cq);

		List<PFSRegion> results = q.getResultList();

		if (results.size() > 0)
		{
			//replace the singleton with the loaded value
			instance = q.getResultList().get(0);
		}
		session.close();
	}

}
