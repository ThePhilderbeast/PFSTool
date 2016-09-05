/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-29T20:59:12+10:00
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

	static final String NEW = "new";
	static final String LOAD = "load";
	static final String SAVE = "save";
	static final String ABOUT = "about";
	static final String ESC = "ESC";
	static final String CLOSE = "close";
	static final String FIN = "finish";
	static final String ADDVEN = "AddVenue";
	static final String RENAME = "Rename";
	static final String MOVE = "Move";
	static final String ADDEVT = "AddEvent";
	static final String UPDATEPLYR = "UpdatePlayer";
	static final String ADDPLYR = "AddPlayer";
	static final String DISPLYR = "DisPlyr";
	static final String FIND = "FindScenario";
	static final String DISYR = "DisYear";
	static final String DISCON = "DisCon";
	static final String DISVEN = "DisVenue";

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

		//Setup the panels
		//Initial Panel
		JButton jbNew = new JButton("New Region");
		jbNew.setActionCommand(NEW);
		jbNew.addActionListener(this);

		JButton jbLoad = new JButton("Load Region");
		jbLoad.setActionCommand(LOAD);
		jbLoad.addActionListener(this);

		JButton jbClose = new JButton("Close");
		jbClose.setActionCommand(ESC);
		jbClose.addActionListener(this);

		LoadImage imgLogo = new LoadImage("PathfinderSociety.jpeg");
		initPanel.addComponent(imgLogo,0,0,5,3,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		initPanel.addComponent(jbNew,0,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		initPanel.addComponent(jbLoad,2,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		initPanel.addComponent(jbClose,4,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);

		//New Region Panel
		JButton jbNext = new JButton("Finish");
		jbNext.setActionCommand(FIN);
		jbNext.addActionListener(this);

		JButton jbCancel = new JButton("Close");
		jbCancel.setActionCommand(ESC);
		jbCancel.addActionListener(this);

		JButton jbBack = new JButton("Back");
		jbBack.setActionCommand(CLOSE);
		jbBack.addActionListener(this);

		newPanel.addComponent(jbBack,0,11,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		newPanel.addComponent(jbNext,2,11,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		newPanel.addComponent(jbCancel,4,11,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);

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
			case NEW:
			{
				cards.show(this.getContentPane(), "New Panel");
			}
			break;
			case LOAD:{

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
			case SAVE:{
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
			case ABOUT:
			{
				about = new AboutDialog(this);
				about.setVisible(true);
			}
			break;
			case ESC:{
				super.dispose();
				} break;
			case CLOSE:{
				region = null;
				cards.show(this.getContentPane(), "Initial Panel");
				setTitle("Pathfinder Society Organised Play Tool");
				setEnable(1);
				} break;
			case FIN:{
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
			case ADDVEN:{
				AddVenue tD = new AddVenue(this);
				region.addVenue(tD.getVenue());
				} break;
			case RENAME:{
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
			case MOVE:{
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
			case ADDEVT:{
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
			case UPDATEPLYR:{
				Task task = new Task();
		 	   	task.execute();
			}
			break;
			case ADDPLYR:{
				AddPlayer nP = new AddPlayer(this);
				region.addPlayer(nP.getPlayer());
				if(!miDisPlayer.isEnabled())
				{
					miDisPlayer.setEnabled(true);
				}
			}
			break;
			case DISPLYR:{
				Object[] playerList = region.getPlayerList().getPlayerList();
				String s = (String) JOptionPane.showInputDialog(this,"Player to display:","Display Player", JOptionPane.PLAIN_MESSAGE,null,playerList,playerList[0]);
				playerPanel.setValues( region.getPlayer( Integer.parseInt( s.split(" - ")[0] ) ) );
				playerPanel.setRegion(region);
				cards.show(this.getContentPane(), "Display Player");
			}
			break;
			case FIND:
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
			case DISCON:{
				StatDis tD = new StatDis(this, "Display Conventions",region.getConvetionTotals(),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
				} break;
			case DISVEN:{
				Object[] venueList = region.getVenueList();
				String s = (String) JOptionPane.showInputDialog(this,"Select Venue to display:","Display Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
				if ((s != null) && (s.length() > 0)){
					int vIndex = region.getVenueIndex(s);
					StatDis tD = new StatDis(this, "Display " + venueList[vIndex],region.getVenueLast12Totals(vIndex),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
				}
				} break;
			case DISYR:{
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
		miNew.setActionCommand(NEW);
		miNew.addActionListener(this);

		miLoad = new JMenuItem("Load Region");
		miLoad.setActionCommand(LOAD);
		miLoad.addActionListener(this);

		miSave = new JMenuItem("Save Region");
		miSave.setActionCommand(SAVE);
		miSave.setEnabled(false);
		miSave.addActionListener(this);

		miClose = new JMenuItem("Close Region");
		miClose.setActionCommand(CLOSE);
		miClose.setEnabled(false);
		miClose.addActionListener(this);

		miExit = new JMenuItem("Exit");
		miExit.setActionCommand(ESC);
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
		miAddVenue.setActionCommand(ADDVEN);
		miAddVenue.setEnabled(false);
		miAddVenue.addActionListener(this);

		miRename = new JMenuItem("Rename Venue");
		miRename.setActionCommand(RENAME);
		miRename.setEnabled(false);
		miRename.addActionListener(this);

		miMove = new JMenuItem("Move Venue");
		miMove.setActionCommand(MOVE);
		miMove.setEnabled(false);
		miMove.addActionListener(this);

		miAddEvent = new JMenuItem("Add Event to Venue");
		miAddEvent.setActionCommand(ADDEVT);
		miAddEvent.setEnabled(false);
		miAddEvent.addActionListener(this);

		miAddPlayer = new JMenuItem("Add Player");
		miAddPlayer.setActionCommand(ADDPLYR);
		miAddPlayer.setEnabled(false);
		miAddPlayer.addActionListener(this);

		miDisPlayer = new JMenuItem("Display Player");
		miDisPlayer.setActionCommand(DISPLYR);
		miDisPlayer.setEnabled(false);
		miDisPlayer.addActionListener(this);

		miFindScenario = new JMenuItem("Find Scenario");
		miFindScenario.setActionCommand(FIND);
		miFindScenario.setEnabled(false);
		miFindScenario.addActionListener(this);

		miDisPrevYear = new JMenuItem("Display Previous 12 months");
		miDisPrevYear.setActionCommand(DISYR);
		miDisPrevYear.setEnabled(false);
		miDisPrevYear.addActionListener(this);

		miDisConventions = new JMenuItem("Display Conventions");
		miDisConventions.setActionCommand(DISCON);
		miDisConventions.setEnabled(false);
		miDisConventions.addActionListener(this);

		miDisVenue = new JMenuItem("Display Venue");
		miDisVenue.setActionCommand(DISVEN);
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
		//mFunctions.addSeparator();
		//mFunctions.add(miDisPrevYear);
		//mFunctions.add(miDisConventions);
		//mFunctions.add(miDisVenue);

		mb.add(mFunctions);
	}

	private void vcMenu()
	{
		mVC = new JMenu("VC Functions");
		miUpdateAll = new JMenuItem("Update All Players");
		miUpdateAll.setActionCommand(UPDATEPLYR);
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
		miAbout.setActionCommand(ABOUT);
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
