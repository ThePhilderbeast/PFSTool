/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T20:04:03+10:00
*/
package com.philderbeast.paizolib;

//import java.util.*;
import javax.persistence.*;

@Entity
@Table
class Event {

	@Id
	private String eventNumber;
	private String eventName;
	private String eventSessionURL;

	Event(String inEventNumber){
		eventNumber = inEventNumber;
		//TODO: Magic happens using the event number to get the URL and the Name
	}

	Event(String inEventNumber, String inEventName, String inEventSessionURL){
		eventNumber = inEventNumber;
		eventName = inEventName;
		eventSessionURL = inEventSessionURL;
	}

	Event(Event inEvent){
		eventSessionURL = inEvent.getEventURL();
		eventNumber = inEvent.getEventNumber();
		eventName = inEvent.getEventName();
	}

	String getEventURL(){
		return eventSessionURL;
	}

	String getEventNumber(){
		return eventNumber;
	}

	String getEventName(){
		return eventName;
	}

	public String toString(){
		return eventName;
	}
}
