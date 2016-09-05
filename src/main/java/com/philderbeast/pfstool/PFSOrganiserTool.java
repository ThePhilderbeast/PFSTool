/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-05T20:08:12+10:00
*/

package com.philderbeast.pfstool;

import com.philderbeast.paizolib.*;
import com.philderbeast.paizoscraper.*;
import com.philderbeast.pfstool.dialog.*;
import com.philderbeast.pfstool.panel.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PFSOrganiserTool extends JFrame{

	CardLayout cards = new CardLayout();
	InitialPanel initPanel = new InitialPanel();
	NewPanel newPanel = new NewPanel();
	RegionPanel mainInt = new RegionPanel();
	PlayerPanel playerPanel = new PlayerPanel();
	JDialog about;

	JMenuBar mb = new JMenuBar();
	JMenu mFile, mFunctions, mVC, mHelp;
	JMenuItem miNew, miLoad, miSave, miClose, miExit;
	JMenuItem miAddVenue, miRename, miMove, miAddEvent, miAddPlayer;
	JMenuItem miUpdateAll, miDisPlayer, miFindScenario, miDisPrevYear, miDisConventions, miDisVenue;
	JMenuItem miAbout;

	PFSHandler handler;

	public PFSOrganiserTool(){
		super("Pathfinder Society Organised Play Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setLocation(150,150);
		setLayout(cards);

		handler = new PFSHandler(this);

		//Setup the menu bar
		fileMenu();
		functionMenu();
		vcMenu();
		helpMenu();
		setJMenuBar(mb);

		//Add the action listner to the panels
		initPanel.setActionListener(handler);
		newPanel.setActionListener(handler);

		//Main Interface Panel
		add(initPanel, "Initial Panel");
		add(newPanel, "New Panel");
		add(mainInt, "Region Panel");
		add(playerPanel, "Display Player");

		//File Chooser default directory
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setEnable(int config){

		switch(config){
			case 0:
				miSave.setEnabled(true);
				miClose.setEnabled(true);

				miAddVenue.setEnabled(true);
				miRename.setEnabled(true);
				miMove.setEnabled(true);
				miAddEvent.setEnabled(true);
				miAddPlayer.setEnabled(true);

				miUpdateAll.setEnabled(true);
				miDisPlayer.setEnabled(true);
				miFindScenario.setEnabled(true);
				miDisPrevYear.setEnabled(true);
				miDisConventions.setEnabled(true);
				miDisVenue.setEnabled(true);

				break;
			case 1:
				miSave.setEnabled(false);
				miClose.setEnabled(false);

				miAddVenue.setEnabled(false);
				miRename.setEnabled(false);
				miMove.setEnabled(false);
				miAddEvent.setEnabled(false);
				miAddPlayer.setEnabled(false);

				miDisPlayer.setEnabled(false);
				miFindScenario.setEnabled(false);
				miDisPrevYear.setEnabled(false);
				miDisConventions.setEnabled(false);
				miDisVenue.setEnabled(false);

				break;
			case 2:
				miSave.setEnabled(true);
				miClose.setEnabled(true);

				miAddVenue.setEnabled(true);
				miRename.setEnabled(true);
				miMove.setEnabled(true);
				miAddEvent.setEnabled(true);
				miAddPlayer.setEnabled(true);

				break;
		}
	}

	private void fileMenu()
	{
		//Add the File menu items
		mFile = new JMenu("File");

		miNew = new JMenuItem("New Region");
		miNew.setActionCommand(Constants.NEW);
		miNew.addActionListener(handler);

		miLoad = new JMenuItem("Load Region");
		miLoad.setActionCommand(Constants.LOAD);
		miLoad.addActionListener(handler);

		miSave = new JMenuItem("Save Region");
		miSave.setActionCommand(Constants.SAVE);
		miSave.setEnabled(false);
		miSave.addActionListener(handler);

		miClose = new JMenuItem("Close Region");
		miClose.setActionCommand(Constants.CLOSE);
		miClose.setEnabled(false);
		miClose.addActionListener(handler);

		miExit = new JMenuItem("Exit");
		miExit.setActionCommand(Constants.ESC);
		miExit.addActionListener(handler);

		mFile.add(miNew);
		mFile.add(miLoad);
		mFile.add(miSave);
		mFile.addSeparator();
		mFile.add(miClose);
		mFile.addSeparator();
		mFile.add(miExit);

		mb.add(mFile);
	}

	private void functionMenu()
	{
		mFunctions = new JMenu("Functions");
		//Add the Function menu items.
		miAddVenue = new JMenuItem("Add Venue");
		miAddVenue.setActionCommand(Constants.ADDVEN);
		miAddVenue.setEnabled(false);
		miAddVenue.addActionListener(handler);

		miRename = new JMenuItem("Rename Venue");
		miRename.setActionCommand(Constants.RENAME);
		miRename.setEnabled(false);
		miRename.addActionListener(handler);

		miMove = new JMenuItem("Move Venue");
		miMove.setActionCommand(Constants.MOVE);
		miMove.setEnabled(false);
		miMove.addActionListener(handler);

		miAddEvent = new JMenuItem("Add Event to Venue");
		miAddEvent.setActionCommand(Constants.ADDEVT);
		miAddEvent.setEnabled(false);
		miAddEvent.addActionListener(handler);

		miAddPlayer = new JMenuItem("Add Player");
		miAddPlayer.setActionCommand(Constants.ADDPLYR);
		miAddPlayer.setEnabled(false);
		miAddPlayer.addActionListener(handler);

		miDisPlayer = new JMenuItem("Display Player");
		miDisPlayer.setActionCommand(Constants.DISPLYR);
		miDisPlayer.setEnabled(false);
		miDisPlayer.addActionListener(handler);

		miFindScenario = new JMenuItem("Find Scenario");
		miFindScenario.setActionCommand(Constants.FIND);
		miFindScenario.setEnabled(false);
		miFindScenario.addActionListener(handler);

		miDisPrevYear = new JMenuItem("Display Previous 12 months");
		miDisPrevYear.setActionCommand(Constants.DISYR);
		miDisPrevYear.setEnabled(false);
		miDisPrevYear.addActionListener(handler);

		miDisConventions = new JMenuItem("Display Conventions");
		miDisConventions.setActionCommand(Constants.DISCON);
		miDisConventions.setEnabled(false);
		miDisConventions.addActionListener(handler);

		miDisVenue = new JMenuItem("Display Venue");
		miDisVenue.setActionCommand(Constants.DISVEN);
		miDisVenue.setEnabled(false);
		miDisVenue.addActionListener(handler);

		mFunctions.add(miAddVenue);
		mFunctions.add(miRename);
		mFunctions.add(miMove);
		mFunctions.add(miAddEvent);
		mFunctions.add(miAddPlayer);
		mFunctions.addSeparator();
		mFunctions.add(miFindScenario);
		mFunctions.add(miDisPlayer);

		mb.add(mFunctions);
	}

	private void vcMenu()
	{
		mVC = new JMenu("VC Functions");
		miUpdateAll = new JMenuItem("Update All Players");
		miUpdateAll.setActionCommand(Constants.UPDATEPLYR);
		miUpdateAll.setEnabled(false);
		miUpdateAll.addActionListener(handler);
		mVC.add(miUpdateAll);

		mb.add(mVC);

	}

	private void helpMenu()
	{

		mHelp = new JMenu("Help");
		//Add the Help menu items
		miAbout = new JMenuItem("About");
		miAbout.setActionCommand(Constants.ABOUT);
		miAbout.addActionListener(handler);

		mHelp.add(miAbout);

		mb.add(mHelp);
	}

}
