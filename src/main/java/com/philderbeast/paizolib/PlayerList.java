/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-19T08:48:52+10:00
*/
package com.philderbeast.paizolib;

import java.io.*;
import java.util.*;

public class PlayerList extends ArrayList<Player> implements Serializable {

	public static final long serialVersionUID = 2300210589404218821L;

	PlayerList(){
		<Player> super(5);
	}

	boolean contains(String PFSNumber){
		Iterator itList = this.iterator();
		Player currentPlayer;
		while (itList.hasNext()){
			currentPlayer = (Player) itList.next();
			if (PFSNumber.equals(currentPlayer.getNumber())){
				return true;
			}
		}

		return false;
	}

	public int getNumGMs(){
		Iterator itList = this.iterator();
		Player currentPlayer;
		int rtnInt = 0;
		while (itList.hasNext()){
			currentPlayer = (Player) itList.next();
			if (currentPlayer.isGM())
				rtnInt++;
		}

		return rtnInt;
	}

	public String[] getPlayerList(){
		Collections.sort(this);
		String list = "";
		Iterator itList = this.iterator();
		Player currentPlayer;
		while (itList.hasNext()){
			currentPlayer = (Player) itList.next();
			list = currentPlayer.getNumber() + " - " + currentPlayer.getName() + "$" + list;
		}
		return  list.split("\\$");
	}

	//TODO: update this from a string to an IN
	public Player getPlayer(int PFSNumber){
		Iterator itList = this.iterator();
		Player currentPlayer;
		while (itList.hasNext()){
			currentPlayer = (Player) itList.next();
			if ( PFSNumber == currentPlayer.getNumber() ){
				return currentPlayer;
			}
		}
		return null;
	}

	public String toString(){
		String rtnStr = new String();

		for (int i = 0; i < this.size(); i++){
			rtnStr = rtnStr + this.get(i) + "\n";
		}

		return rtnStr;
	}
}
