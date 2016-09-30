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
public abstract class Session {

	@Id
	@GeneratedValue
	private int SessionId;

	@ManyToOne(cascade = CascadeType.ALL)
	private Scenario scenario;
	private String eventNumber;
	private Date date;


	public Date getSessionDate(){
		return date;
	}


	public Scenario getScenario(){
		return scenario;
	}

}
