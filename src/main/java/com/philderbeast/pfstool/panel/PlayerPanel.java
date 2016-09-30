/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-29T19:04:43+10:00
*/
package com.philderbeast.pfstool.panel;

import com.philderbeast.paizolib.*;
import com.philderbeast.paizoscraper.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.*;
import java.beans.*;

public class PlayerPanel extends JPanel implements ActionListener {
	GridBagLayout gridBag = new GridBagLayout();
	JLabel lblName, lblNumber, lblEmail, lblNumSession, lblCredit, lblNumScenariosRun;
	JButton btnImport;
	TextArea pPlayed, pRun;
	Player p;
	PFSRegion region;

	private ProgressMonitor progressMonitor;
	private Task task;


	public PlayerPanel() {
		super();
		setLayout(gridBag);

		lblName = new JLabel("Player Name");
		lblNumber = new JLabel("Player Number");
		lblEmail = new JLabel("Player eMail");
		lblNumSession = new JLabel("Played");
		lblCredit = new JLabel("GM Credit");
		lblNumScenariosRun = new JLabel("Sessions Run");

		btnImport = new JButton("Import Scenarios");
		btnImport.addActionListener(this);

		pPlayed = new TextArea("");
		pRun = new TextArea("");

		addComponent(lblName,0,0,10,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(lblNumber,11,0,5,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(lblEmail,0,1,10,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(lblNumSession,0,2,6,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(lblCredit,6,2,6,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(lblNumScenariosRun,12,2,6,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(pPlayed,0,3,10,9,1,10,GridBagConstraints.BOTH,GridBagConstraints.CENTER);
		addComponent(pRun,11,3,10,9,1,10,GridBagConstraints.BOTH,GridBagConstraints.CENTER);

		addComponent(btnImport, 12, 0, 5, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

	}

	public void setValues(Player playerToDisplay){

		p = playerToDisplay;

		//TODO: fix all of this
		//ArrayList<Session> sessionsPlayed = playerToDisplay.getSessionsPlayed();
		//ArrayList<PlayerSession> sessionsRun = playerToDisplay.getSessionsRun();

		lblName.setText(playerToDisplay.getName());
		lblNumber.setText("PFS Number: " + playerToDisplay.getNumber());
		lblEmail.setText("eMail Address: " + playerToDisplay.getEMail());
		//lblNumSession.setText("Sessions Played: " + sessionsPlayed.size());
		//lblCredit.setText("Total Credit: " + playerToDisplay.getGMCredit());
		//lblNumScenariosRun.setText("Total Scenarios Run: " + sessionsRun.size());

		//blank out the feilds before adding the new data
		pPlayed.setText("Scenarios Played:-\n\n");
		pRun.setText("Scenarios Run:-\n\n");

		//get the names of all the scernarios that have been played
		//for (PlayerSession ps : sessionsPlayed)
		//{
		//	pPlayed.append(ps.scenarioName + "\n");
		//}
		pPlayed.setCaretPosition(0);

		//get the names of all the scernarios that have been played
		//for (PlayerSession ps : sessionsRun)
		//{
		//	pRun.append(ps.scenarioName + "\n");
		//}
		pRun.setCaretPosition(0);
	}

	private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill, int anchor)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridwidth;
		constraints.gridheight = gridheight;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.fill = fill;
		constraints.anchor = anchor;
		gridBag.setConstraints(component, constraints);
		add(component);
	}

	public void setRegion(PFSRegion r)
	{
		this.region = r;
	}

	public void actionPerformed(ActionEvent evt)
	{

	   task = new Task();
	   task.execute();

	}

	class Task extends SwingWorker<Void, Void> {
		@Override
		public Void doInBackground() {
			PaizoSite paizoSite = new PaizoSite();
			paizoSite.parseSingle(p, region);
			setValues(p);
			return null;
		}

   }

}
