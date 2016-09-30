/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-28T21:30:36+10:00
*/
package com.philderbeast.paizolib;

import com.philderbeast.paizolib.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.persistence.*;

@Entity
@Table
public class Player implements Comparable<Player>, Serializable{

	@Id
	private int pfsNumber;

	private String givenName;
	private String surname;
	private String eMailAddress;
	private String playerSessionURL;


	public Player(){
		pfsNumber = 0;
		playerSessionURL = "url";
		//remove all whitespace from the url
		playerSessionURL = playerSessionURL.replaceAll("\\s","");
		givenName = "given";
		surname = "surname";
		eMailAddress ="e@mail.com";
	}

	public Player(String inPFSNumber, String inURL, String inGiven, String inSur, String inEMail){
		pfsNumber = Integer.parseInt(inPFSNumber);
		playerSessionURL = inURL;
		//remove all whitespace from the url
		playerSessionURL = playerSessionURL.replaceAll("\\s","");
		givenName = inGiven;
		surname = inSur;
		eMailAddress = inEMail;
	}

	public int getNumber(){
		return pfsNumber;
	}

	public String getName(){
		return givenName + " " + surname;
	}

	public String getEMail(){
		return eMailAddress;
	}

	public String getSessURL(){
		return playerSessionURL;
	}

	public String toString(){
		String sessPlayOut = "";
		String sessRunOut = "";

		String rtnStr = pfsNumber + "$" + givenName + "$" + surname + "$" + playerSessionURL + "\n" + eMailAddress + "\n";

		return rtnStr;
	}

	public int compareTo(Player p)
	{
		return Integer.compare(pfsNumber, p.pfsNumber) * -1;
	}
}
