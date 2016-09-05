/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-23T20:59:56+10:00
*/
package com.philderbeast.paizolib;


import java.io.*;
import java.util.*;

import com.philderbeast.paizolib.*;

public class ScenarioList extends ArrayList<Scenario> {

	public static final long serialVersionUID = 4311231693320423655L;

	@Override
	public boolean add(Scenario s)
	{
		for (Scenario sen : this)
		{
			if (sen.name.equals(s.name))
				return false;
		}
		return super.add(s);
	}

}
