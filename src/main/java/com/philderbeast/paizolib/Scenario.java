/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T19:15:21+10:00
*/
package com.philderbeast.paizolib;

import java.io.*;
import javax.persistence.*;

@Entity
@Table
public class Scenario implements Comparable<Scenario>{

	@Id
	@GeneratedValue
	private int scenarioId;
	public String name;
	public int lower;
	public int upper;
	public int season;
	public int scenario;
	public Campaign campaign;

	public static enum Campaign
	{
		PFS,
		CORE
	}

	public Scenario(String name, int lower, int upper)
	{
		this.name = name;
		this.lower = lower;
		this.upper = upper;
	}

	public String toString()
	{
		return name + "$" + lower + "-" + upper;
	}

	public int compareTo(Scenario s)
	{
		return name.compareTo(s.name);
	}

	public void setCampaign()
	{
		if (name.contains("(PFC)"))
		{
			campaign = Scenario.Campaign.CORE;
		} else
		{
			campaign = Scenario.Campaign.PFS;
		}
	}
}
