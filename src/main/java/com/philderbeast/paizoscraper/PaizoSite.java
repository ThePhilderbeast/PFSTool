/**
* @Author: Phillip Ledger
* @Date:   2016-08-17T14:09:02+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T19:01:06+10:00
*/

package com.philderbeast.paizoscraper;

import com.gargoylesoftware.htmlunit.*;
import com.philderbeast.paizolib.*;
import com.philderbeast.pfstool.dialog.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.openqa.selenium.*;
import org.openqa.selenium.By.*;
import org.openqa.selenium.htmlunit.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.remote.*;

import java.awt.Cursor;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import javax.swing.*;

public class PaizoSite
{
	private ProgressDialog progress;
	private String username;
	private String password;

	public PaizoSite()
	{
		//turn off html unit logger
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

		username = (String) JOptionPane.showInputDialog("input Paizo username (email):");
		password = (String) JOptionPane.showInputDialog("input Paizo Password:");
		progress = new ProgressDialog();
	}

	//need to think about this more
	public void parseList(ArrayList playerList, PFSRegion r)
	{

		progress.progressStart(100, "logging in to Paizo");
		progress.setVisible(true);

		System.out.println("calling login");
		WebDriver browser = login(username, password);

		progress.progressStep(20);

		progress.progressText("Loading Players");
		int i = 1;
		int iprogress = 20;
		int steps = playerList.size();

		System.out.println("steps " + steps);

		for(Object o: playerList)
		{

			Player p = (Player) o;

			if(p.getSessURL() != null)
			{
				browser.navigate().to("https://secure.paizo.com/people/" + p.getSessURL() + "/sessions");
				Document doc = Jsoup.parse(browser.getPageSource());
				parseScenario(doc, p, r, false);
				iprogress = (int) Math.round(((float)i / steps) * 80 + 20);

				progress.progressStep( iprogress );
				i++;
			}
		}
		progress.setVisible(false);
		browser.quit();
		System.out.println("Done");
	}

	public void parseSingle(Player p, PFSRegion r)
	{

		progress.progressStart(100, "logging in to Paizo");
		progress.setVisible(true);

		WebDriver browser = login(username, password);

		progress.progressStep(20);

		if(p.getSessURL() != null)
		{
			progress.progressText("Loading Scenarios for " + p.getName());
			browser.navigate().to("https://secure.paizo.com/people/" + p.getSessURL() + "/sessions");
			Document doc = Jsoup.parse(browser.getPageSource());
			parseScenario(doc, p, r, true);
		}
		progress.setVisible(false);
		browser.quit();
		System.out.println("Done");
	}

	private void parseScenario(Document doc, Player p, PFSRegion r, boolean updateProgress)
	{
		String character;
		String eventCode;
		String eventName;
		String sessNum;
		String scenarioName;
		String date;
		Boolean gm;
		Scenario.Campaign c;


		//find the tabs div
		Element tabs = doc.getElementById("tabs");
		//get the rows of the table

		int steps = tabs.getElementsByTag("tr").size();
		int i = 1;

		for(Element row: tabs.getElementsByTag("tr"))
		{
			Elements cells = row.getElementsByTag("td");
			if (cells.size() > 8)
			{

				character = cells.get(7).text();
				eventCode = cells.get(1).text();
				eventName = cells.get(2).text();
				sessNum = cells.get(3).text();
				scenarioName = cells.get(5).text();
				date = cells.get(0).text();
				gm = cells.get(9).text().contains("GM");

				if (scenarioName.contains("(PFC)"))
				{
					c = Scenario.Campaign.CORE;
				} else
				{
					c = Scenario.Campaign.PFS;
				}

				Scenario s = new Scenario(scenarioName, 0, 0);
				r.addScenario(s);


				//TODO: fix this with the new layout
				//if ( !p.playedScenario(scenarioName, gm ) )
				//{
				//	PlayerSession ps = new PlayerSession(eventCode, eventName, sessNum, scenarioName, character, date);
				//	p.addSession(ps, gm);
				//}

			}

			if (updateProgress)
			{
				progress.progressStep( (int) Math.round(((float)i / steps) * 80 + 20) );
				i++;
			}
		}
	}

	private WebDriver login(String username, String pass)
	{

		System.out.println("opening the browser");

		HtmlUnitDriver browser = new HtmlUnitDriver(true);

	    browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		System.out.println("loading paizo site");
		//load the login page
		browser.get("http://www.paizo.com");

		progress.progressStep(5);

		//click the login link
		browser.findElements(By.linkText("Sign In")).get(0).click();
		progress.progressStep(10);
		System.out.println("clicked login link");
		//username
		WebElement input = browser.findElement(By.name("0.1.29.3.1.3.2.3.3.7.3.2.1.3.1.1.1"));
		input.sendKeys(username);

		//password
		input = browser.findElement(By.name("0.1.29.3.1.3.2.3.3.7.3.2.1.3.1.1.3"));
		input.sendKeys(pass);

		//login button
		WebElement submit = browser.findElement(By.name("0.1.29.3.1.3.2.3.3.7.3.2.1.3.1.1.5"));
		submit.click();
		progress.progressStep(15);
		try{
			Thread.sleep(5000);
		} catch (Exception e)
		{

		}
		return browser;
	}

}
