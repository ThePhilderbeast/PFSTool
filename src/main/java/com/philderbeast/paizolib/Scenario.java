/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-23T21:18:13+10:00
*/
package com.philderbeast.paizolib;

import java.io.*;


public class Scenario implements Serializable, Comparable<Scenario>{

	private static final long serialVersionUID = 297213800984691820L;

	public String name;
	public byte lower;
	public byte upper;
	public String flipMats;
	public String mapPacks;
	public String custom;
	public int season;
	public int scenario;
	public Campaign campaign;

	public static enum Campaign
	{

		PFS,
		CORE
	}

	public Scenario(String name, byte lower, byte upper, String flipMats, String mapPacks, String custom, Campaign c)
	{
		this.name = name;
		this.lower = lower;
		this.upper = upper;
		this.flipMats = flipMats;
		this.mapPacks = mapPacks;
		this.custom = custom;
		this.campaign = c;
	}

	public String toString()
	{
		return name + "$" + lower + "-" + upper + "$" + flipMats + "$" + mapPacks + "$" + custom;
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
