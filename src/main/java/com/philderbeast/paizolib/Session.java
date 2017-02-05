/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T19:35:01+10:00
*/
package com.philderbeast.paizolib;

import javax.persistence.*;

import java.util.*;

@Entity
@Table
public class Session {

	@Id
	@GeneratedValue
	private int SessionId;

	@ManyToOne(cascade = CascadeType.ALL)
	private Scenario scenario;

	@ManyToOne(cascade = CascadeType.ALL)
	private Player player;
	private String eventNumber;
	private Date date;
	private boolean gm;

	public Session(Scenario scenario, Player player, Date date, String eventnumber, boolean gm)
	{
		this.scenario = scenario;
		this.player = player;
		this.date = date;
		this.eventNumber = eventnumber;
		this.gm = gm;
	}

	public Date getSessionDate(){
		return date;
	}

	public Player getPlayer(){
		return player;
	}
	
	public Scenario getScenario(){
		return scenario;
	}

	public Date getDate(){
		return date;
	}

	public String getEventNumber(){
		return eventNumber;
	}

	public boolean getGM(){
		return gm;
	}


}
