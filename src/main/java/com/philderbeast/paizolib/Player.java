/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-19T08:48:36+10:00
*/
package com.philderbeast.paizolib;

import com.philderbeast.paizolib.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Player implements Comparable<Player>, Serializable{

	public static final long serialVersionUID = 8604567562985130477L;

	protected int pfsNumber;
	protected String givenName;
	protected String Surname;
	protected String eMailAddress;
	protected String playerSessionURL;
	protected int numSessionsRun;
	protected ArrayList<PlayerSession> sessionsPlayed;
	protected ArrayList<PlayerSession> sessionsRun;

	public Player(String inPFSNumber, String inURL, String inGiven, String inSur, String inEMail, int numPlayed, int numRun){
		pfsNumber = Integer.parseInt(inPFSNumber);
		playerSessionURL = inURL;
		givenName = inGiven;
		Surname = inSur;
		eMailAddress = inEMail;
		sessionsPlayed = new ArrayList<PlayerSession>();
		sessionsRun = new ArrayList<PlayerSession>();
	}

	public Player(String inPFSNumber, String inURL, String inGiven, String inSur, String inEMail){
		pfsNumber = Integer.parseInt(inPFSNumber);
		playerSessionURL = inURL;
		givenName = inGiven;
		Surname = inSur;
		eMailAddress = inEMail;
		sessionsPlayed = new ArrayList<PlayerSession>();
		sessionsRun = new ArrayList<PlayerSession>();
	}

	public void addDetails(short detailToBeAdded, String[] inDetail){
		switch (detailToBeAdded) {
			case 1:
				givenName = inDetail[0];
				Surname = inDetail[1];
				break;
			case 2:
				eMailAddress = inDetail[0];
				break;
			case 3:
				playerSessionURL = inDetail[0];
				break;
			case 4:
				givenName = inDetail[0];
				Surname = inDetail[1];
				eMailAddress = inDetail[2];
				break;
			case 5:
				givenName = inDetail[0];
				Surname = inDetail[1];
				eMailAddress = inDetail[2];
				playerSessionURL = inDetail[3];
				break;
			default:
		}
	}

	public void addSession(String details[], boolean GM){
		//dont add a session without a scenario name, should probably say something about this
		if (details[3] != null)
		{
			if (GM) {
				sessionsRun.add(new PlayerSession(details[0],details[1],details[2],details[3],details[4],details[5]));
				Collections.sort(sessionsRun);
			} else {
				sessionsPlayed.add(new PlayerSession(details[0],details[1],details[2],details[3],details[4],details[5]));
				Collections.sort(sessionsPlayed);
			}
		}
	}

	public void addSession(PlayerSession p, boolean GM){
		//dont add a session without a scenario name, should probably say something about this
		if (GM) {
			sessionsRun.add(p);
			Collections.sort(sessionsRun);
		} else {
			sessionsPlayed.add(p);
			Collections.sort(sessionsPlayed);
		}
	}

	public boolean isGM(){
		return !sessionsRun.isEmpty();
	}

	public boolean playedScenario(String inScenario)
	{
		return playedScenario(inScenario, false);
	}

	public boolean playedScenario(String inScenario, boolean gm)
	{

		if (gm)
		{
			for (PlayerSession currentSession: sessionsRun)
			{
				{
					if (currentSession.getScenario().contains(inScenario)){
						return true;
					}
				}
			}
		}else
		{
			for (PlayerSession currentSession: sessionsPlayed)
			{
				{
					if (currentSession.getScenario().contains(inScenario)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public int getNumber(){
		return pfsNumber;
	}

	public String getName(){
		return givenName + " " + Surname;
	}

	public String getEMail(){
		return eMailAddress;
	}

	public int getGMCredit(){
		return sessionsRun.size();
	}

	public int getSessRun(){
		return numSessionsRun;
	}

	public String getSessURL(){
		return playerSessionURL;
	}

	public ArrayList getSessionsPlayed(){
		Collections.sort(sessionsPlayed);
		return sessionsPlayed;
	}

	public ArrayList getSessionsRun(){
		Collections.sort(sessionsRun);
		return sessionsRun;
	}

	public String toString(){
		String sessPlayOut = "";
		String sessRunOut = "";

		if (sessionsPlayed.size() > 0)
			sessPlayOut = sessionsPlayed.size() + "";

		if (sessionsRun.size() > 0)
			sessRunOut = sessionsRun.size() + "";

		String rtnStr = pfsNumber + "$" + sessPlayOut + "$" + sessRunOut + "\n" + givenName + "$" + Surname + "$" + playerSessionURL + "\n" + eMailAddress + "\n";

		for ( PlayerSession played: sessionsPlayed)
		{
			rtnStr = rtnStr + played + "\n";
		}

		for (PlayerSession run: sessionsRun)
		{
				rtnStr = rtnStr + run + "\n";
		}
		return rtnStr;
	}

	public int compareTo(Player p)
	{
		return Integer.compare(pfsNumber, p.pfsNumber) * -1;
	}
}
