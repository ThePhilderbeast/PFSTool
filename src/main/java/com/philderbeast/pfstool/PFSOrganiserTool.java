/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-05T19:13:26+10:00
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

public class PFSOrganiserTool extends JFrame implements ActionListener {

	PFSRegion region = null;
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

	public static void main(String[] args){
		PFSOrganiserTool tool = new PFSOrganiserTool();
	}

	public PFSOrganiserTool(){
		super("Pathfinder Society Organised Play Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setLocation(150,150);
		setLayout(cards);

		//Setup the menu bar
		fileMenu();
		functionMenu();
		vcMenu();
		helpMenu();
		setJMenuBar(mb);

		//Add the action listner to the panels
		initPanel.setActionListener(this);
		newPanel.setActionListener(this);

		//Main Interface Panel
		add(initPanel, "Initial Panel");
		add(newPanel, "New Panel");
		add(mainInt, "Region Panel");
		add(playerPanel, "Display Player");

		//File Chooser default directory
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent evt){
		String command = evt.getActionCommand();

		switch (command){
			case Constants.NEW:
			{
				cards.show(this.getContentPane(), "New Panel");
			}
			break;
			case Constants.LOAD:{

				String s;

				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
        			"Region Files", "rgn");
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				if(returnVal== JFileChooser.APPROVE_OPTION)
				{
					s = fc.getSelectedFile().getAbsolutePath();
				}else
				{
					return;
				}

				try {

					FileInputStream fileIn = new FileInputStream(s);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					region = (PFSRegion) in.readObject();
					in.close();
					fileIn.close();
					for(Scenario sce : region.scenarioList)
					{
						sce.setCampaign();
					}

				} catch (IOException IOE) {
					System.out.println("Error opening file");
					IOE.printStackTrace();
					return;
				} catch (ClassNotFoundException CNFE) {
					System.out.println("Error - not a region file");
					CNFE.printStackTrace();
					return;
				}

				setTitle(region.getName() + " - Pathfinder Society Organised Play Tool");
				String[] venueList = region.getVenueList();
				String sVenues ="Venues:";
				for(int i = 0; i < venueList.length; i++)
					sVenues = sVenues + "\n\t" + venueList[i];
				String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(),"Totals Scenarios Known: " + region.scenarioList.size(), "\nTotal number of Venues: " + region.getNumVenues(),sVenues};
				mainInt.setStats(stats);
				setEnable(0);
				cards.show(this.getContentPane(), "Region Panel");
			}
			break;
			case Constants.SAVE:{
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
        			"Region Files", "rgn");
				fc.setFileFilter(filter);
				int returnVal = fc.showSaveDialog(null);
				if(returnVal== JFileChooser.APPROVE_OPTION)
				{
					region.writeToFile(fc.getSelectedFile().getAbsolutePath());
				}
			} break;
			case Constants.ABOUT:
			{
				about = new AboutDialog(this);
				about.setVisible(true);
			}
			break;
			case Constants.ESC:{
				super.dispose();
				} break;
			case Constants.CLOSE:{
				region = null;
				cards.show(this.getContentPane(), "Initial Panel");
				setTitle("Pathfinder Society Organised Play Tool");
				setEnable(1);
				} break;
			case Constants.FIN:{
				String rName = newPanel.tfName.getText();
				String rLoc = newPanel.tfName.getText();
				if (! rName.equals("") & ! rLoc.equals("")){
					setTitle(rName + " - Pathfinder Society Organised Play Tool");
					region = new PFSRegion(rName,rLoc);
					String[] stats = {"Region Name: " + rName,"Region Location: " + rLoc};
					mainInt.setStats(stats);
					setEnable(2);
					cards.show(this.getContentPane(), "Region Panel");
					AddVenue tD = new AddVenue(this);
					region.addVenue(tD.getVenue());
				}
				} break;
			case Constants.ADDVEN:{
				AddVenue tD = new AddVenue(this);
				region.addVenue(tD.getVenue());
				} break;
			case Constants.RENAME:{
				Object[] venueList = region.getVenueList();
				String s = (String) JOptionPane.showInputDialog(this,"Select Venue to rename:","Rename Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
				if ((s != null) &&  (s.length() > 0)){
					int vIndex = region.getVenueIndex(s);
					s = (String) JOptionPane.showInputDialog(this,"Enter new name for Venue:","Rename Venue",JOptionPane.PLAIN_MESSAGE);
					region.renameVenue(vIndex,s);
					venueList = region.getVenueList();
					String sVenues ="Venues:";
					for(int i = 0; i < venueList.length; i++)
						sVenues = sVenues + "\n\t" + venueList[i];
					String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(), "Total number of Venues: " + region.getNumVenues(),sVenues};
					mainInt.setStats(stats);
				}
				} break;
			case Constants.MOVE:{
				Object[] venueList = region.getVenueList();
				String s = (String) JOptionPane.showInputDialog(this,"Select Venue to change address:","Move Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
				if ((s != null) &&  (s.length() > 0)){
					int vIndex = region.getVenueIndex(s);
					s = (String) JOptionPane.showInputDialog(this,"Enter new address for Venue:","Move Venue",JOptionPane.PLAIN_MESSAGE);
					region.moveVenue(vIndex,s);
					venueList = region.getVenueList();
					String sVenues ="Venues:";
					for(int i = 0; i < venueList.length; i++)
						sVenues = sVenues + "\n\t" + venueList[i];
					String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(), "Total number of Venues: " + region.getNumVenues(),sVenues};
					mainInt.setStats(stats);
				}
				} break;
			case Constants.ADDEVT:{
				Object[] venueList = region.getVenueList();
				Object[] optionList = new Object[venueList.length+1];
				for (int i = 0; i<venueList.length;i++)
					optionList[i] = venueList[i];
				optionList[optionList.length-1] = "Conventions";
				String s = (String) JOptionPane.showInputDialog(this,"Select Venue to add Event to:","Add Event", JOptionPane.PLAIN_MESSAGE,null,optionList,optionList[0]);
				if ((s != null) && (s.length() > 0)){
					int vIndex = region.getVenueIndex(s);
					String eventCode = JOptionPane.showInputDialog(this,"Please enter the event code for the event:","Add Event",JOptionPane.PLAIN_MESSAGE);
					region.addEvent(vIndex, eventCode);
				}
				} break;
			case Constants.UPDATEPLYR:{
				Task task = new Task();
		 	   	task.execute();
			}
			break;
			case Constants.ADDPLYR:{
				AddPlayer nP = new AddPlayer(this);
				region.addPlayer(nP.getPlayer());
				if(!miDisPlayer.isEnabled())
				{
					miDisPlayer.setEnabled(true);
				}
			}
			break;
			case Constants.DISPLYR:{
				Object[] playerList = region.getPlayerList().getPlayerList();
				String s = (String) JOptionPane.showInputDialog(this,"Player to display:","Display Player", JOptionPane.PLAIN_MESSAGE,null,playerList,playerList[0]);
				playerPanel.setValues( region.getPlayer( Integer.parseInt( s.split(" - ")[0] ) ) );
				playerPanel.setRegion(region);
				cards.show(this.getContentPane(), "Display Player");
			}
			break;
			case Constants.FIND:
				{
					ArrayList playableScenarios = new ArrayList();
					boolean playable = true;

					TableFinder tableFinder = new TableFinder(this, region);
					String[] playerNumbers = tableFinder.showDialog();
					Player[] currentPlayers = new Player[playerNumbers.length];

					for (int i = 0; i < playerNumbers.length; i++)
					{
						if (playerNumbers[i] != "")
						{
							currentPlayers[i] = region.getPlayer( Integer.parseInt( playerNumbers[i].split(" - ")[0] ) );
						}
					}

					for (Scenario currentScenario : region.scenarioList)
					{
						playable = true;

						System.out.println(currentScenario.name);

						for (int j = 0; j < currentPlayers.length; j++){
							if (currentPlayers[j] != null)
							{
								playable = playable && !currentPlayers[j].playedScenario(currentScenario.name);
							}
						}

						if (playable) {
								playableScenarios.add(currentScenario.name);
						}
					}

					ScenarioDialog sd = new ScenarioDialog(this,"Tables Found",playableScenarios);
				}
				break;
			case Constants.DISCON:{
				StatDis tD = new StatDis(this, "Display Conventions",region.getConvetionTotals(),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
				} break;
			case Constants.DISVEN:{
				Object[] venueList = region.getVenueList();
				String s = (String) JOptionPane.showInputDialog(this,"Select Venue to display:","Display Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
				if ((s != null) && (s.length() > 0)){
					int vIndex = region.getVenueIndex(s);
					StatDis tD = new StatDis(this, "Display " + venueList[vIndex],region.getVenueLast12Totals(vIndex),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
				}
				} break;
			case Constants.DISYR:{
				StatDis tD = new StatDis(this, "Statistics for the 12 months", region.getLast12MonthsTotals(), "Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
				} break;
			default:
				break;
		}

	}

	private void setEnable(int config){

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
		miNew.addActionListener(this);

		miLoad = new JMenuItem("Load Region");
		miLoad.setActionCommand(Constants.LOAD);
		miLoad.addActionListener(this);

		miSave = new JMenuItem("Save Region");
		miSave.setActionCommand(Constants.SAVE);
		miSave.setEnabled(false);
		miSave.addActionListener(this);

		miClose = new JMenuItem("Close Region");
		miClose.setActionCommand(Constants.CLOSE);
		miClose.setEnabled(false);
		miClose.addActionListener(this);

		miExit = new JMenuItem("Exit");
		miExit.setActionCommand(Constants.ESC);
		miExit.addActionListener(this);

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
		miAddVenue.addActionListener(this);

		miRename = new JMenuItem("Rename Venue");
		miRename.setActionCommand(Constants.RENAME);
		miRename.setEnabled(false);
		miRename.addActionListener(this);

		miMove = new JMenuItem("Move Venue");
		miMove.setActionCommand(Constants.MOVE);
		miMove.setEnabled(false);
		miMove.addActionListener(this);

		miAddEvent = new JMenuItem("Add Event to Venue");
		miAddEvent.setActionCommand(Constants.ADDEVT);
		miAddEvent.setEnabled(false);
		miAddEvent.addActionListener(this);

		miAddPlayer = new JMenuItem("Add Player");
		miAddPlayer.setActionCommand(Constants.ADDPLYR);
		miAddPlayer.setEnabled(false);
		miAddPlayer.addActionListener(this);

		miDisPlayer = new JMenuItem("Display Player");
		miDisPlayer.setActionCommand(Constants.DISPLYR);
		miDisPlayer.setEnabled(false);
		miDisPlayer.addActionListener(this);

		miFindScenario = new JMenuItem("Find Scenario");
		miFindScenario.setActionCommand(Constants.FIND);
		miFindScenario.setEnabled(false);
		miFindScenario.addActionListener(this);

		miDisPrevYear = new JMenuItem("Display Previous 12 months");
		miDisPrevYear.setActionCommand(Constants.DISYR);
		miDisPrevYear.setEnabled(false);
		miDisPrevYear.addActionListener(this);

		miDisConventions = new JMenuItem("Display Conventions");
		miDisConventions.setActionCommand(Constants.DISCON);
		miDisConventions.setEnabled(false);
		miDisConventions.addActionListener(this);

		miDisVenue = new JMenuItem("Display Venue");
		miDisVenue.setActionCommand(Constants.DISVEN);
		miDisVenue.setEnabled(false);
		miDisVenue.addActionListener(this);

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
		miUpdateAll.addActionListener(this);
		mVC.add(miUpdateAll);

		mb.add(mVC);

	}

	private void helpMenu()
	{

		mHelp = new JMenu("Help");
		//Add the Help menu items
		miAbout = new JMenuItem("About");
		miAbout.setActionCommand(Constants.ABOUT);
		miAbout.addActionListener(this);

		mHelp.add(miAbout);

		mb.add(mHelp);
	}

	class Task extends SwingWorker<Void, Void> {
		@Override
		public Void doInBackground() {
		   if(region.paizoSite == null)
			{
				region.paizoSite = new PaizoSite();
			}
			region.paizoSite.parseList(region.getPlayerList(), region);
			return null;
		}

   }

}
