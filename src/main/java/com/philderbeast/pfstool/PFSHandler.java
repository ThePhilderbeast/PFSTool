/**
* @Author: Phillip Ledger
* @Date:   2016-09-01T10:52:30+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-05T20:07:31+10:00
*/
package com.philderbeast.pfstool;

import com.philderbeast.paizolib.*;
import com.philderbeast.paizoscraper.*;
import com.philderbeast.pfstool.dialog.*;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;

public class PFSHandler implements ActionListener
{
    PFSOrganiserTool pfstool;
    PFSRegion region;

    public PFSHandler(PFSOrganiserTool pfstool)
    {
        this.pfstool = pfstool;
    }

    public void actionPerformed(ActionEvent evt){
        String command = evt.getActionCommand();

        switch (command){
            case Constants.NEW:
            {
                pfstool.cards.show(pfstool.getContentPane(), "New Panel");
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

                pfstool.setTitle(region.getName() + " - Pathfinder Society Organised Play Tool");
                String[] venueList = region.getVenueList();
                String sVenues ="Venues:";
                for(int i = 0; i < venueList.length; i++)
                    sVenues = sVenues + "\n\t" + venueList[i];
                String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(),"Totals Scenarios Known: " + region.scenarioList.size(), "\nTotal number of Venues: " + region.getNumVenues(),sVenues};
                pfstool.mainInt.setStats(stats);
                pfstool.setEnable(0);
                pfstool.cards.show(pfstool.getContentPane(), "Region Panel");
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
                AboutDialog about = new AboutDialog(pfstool);
                about.setVisible(true);
            }
            break;
            case Constants.ESC:{
                //TODO: fix this
                //AboutDialog.dispose();
                } break;
            case Constants.CLOSE:{
                region = null;
                pfstool.cards.show(pfstool.getContentPane(), "Initial Panel");
                pfstool.setTitle("Pathfinder Society Organised Play Tool");
                pfstool.setEnable(1);
                } break;
            case Constants.FIN:{
                String rName = pfstool.newPanel.tfName.getText();
                String rLoc = pfstool.newPanel.tfName.getText();
                if (! rName.equals("") & ! rLoc.equals("")){
                    pfstool.setTitle(rName + " - Pathfinder Society Organised Play Tool");
                    region = new PFSRegion(rName,rLoc);
                    String[] stats = {"Region Name: " + rName,"Region Location: " + rLoc};
                    pfstool.mainInt.setStats(stats);
                    pfstool.setEnable(2);
                    pfstool.cards.show(pfstool.getContentPane(), "Region Panel");
                    AddVenue tD = new AddVenue(pfstool);
                    region.addVenue(tD.getVenue());
                }
                } break;
            case Constants.ADDVEN:{
                AddVenue tD = new AddVenue(pfstool);
                region.addVenue(tD.getVenue());
                } break;
            case Constants.RENAME:{
                Object[] venueList = region.getVenueList();
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to rename:","Rename Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
                if ((s != null) &&  (s.length() > 0)){
                    int vIndex = region.getVenueIndex(s);
                    s = (String) JOptionPane.showInputDialog(pfstool,"Enter new name for Venue:","Rename Venue",JOptionPane.PLAIN_MESSAGE);
                    region.renameVenue(vIndex,s);
                    venueList = region.getVenueList();
                    String sVenues ="Venues:";
                    for(int i = 0; i < venueList.length; i++)
                        sVenues = sVenues + "\n\t" + venueList[i];
                    String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(), "Total number of Venues: " + region.getNumVenues(),sVenues};
                    pfstool.mainInt.setStats(stats);
                }
                } break;
            case Constants.MOVE:{
                Object[] venueList = region.getVenueList();
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to change address:","Move Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
                if ((s != null) &&  (s.length() > 0)){
                    int vIndex = region.getVenueIndex(s);
                    s = (String) JOptionPane.showInputDialog(pfstool,"Enter new address for Venue:","Move Venue",JOptionPane.PLAIN_MESSAGE);
                    region.moveVenue(vIndex,s);
                    venueList = region.getVenueList();
                    String sVenues ="Venues:";
                    for(int i = 0; i < venueList.length; i++)
                        sVenues = sVenues + "\n\t" + venueList[i];
                    String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(), "Total number of Venues: " + region.getNumVenues(),sVenues};
                    pfstool.mainInt.setStats(stats);
                }
                } break;
            case Constants.ADDEVT:{
                Object[] venueList = region.getVenueList();
                Object[] optionList = new Object[venueList.length+1];
                for (int i = 0; i<venueList.length;i++)
                    optionList[i] = venueList[i];
                optionList[optionList.length-1] = "Conventions";
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to add Event to:","Add Event", JOptionPane.PLAIN_MESSAGE,null,optionList,optionList[0]);
                if ((s != null) && (s.length() > 0)){
                    int vIndex = region.getVenueIndex(s);
                    String eventCode = JOptionPane.showInputDialog(pfstool,"Please enter the event code for the event:","Add Event",JOptionPane.PLAIN_MESSAGE);
                    region.addEvent(vIndex, eventCode);
                }
                } break;
            case Constants.UPDATEPLYR:{
                Task task = new Task();
                task.execute();
            }
            break;
            case Constants.ADDPLYR:{
                AddPlayer nP = new AddPlayer(pfstool);
                region.addPlayer(nP.getPlayer());
                if(!pfstool.miDisPlayer.isEnabled())
                {
                    pfstool.miDisPlayer.setEnabled(true);
                }
            }
            break;
            case Constants.DISPLYR:{
                Object[] playerList = region.getPlayerList().getPlayerList();
                String s = (String) JOptionPane.showInputDialog(pfstool,"Player to display:","Display Player", JOptionPane.PLAIN_MESSAGE,null,playerList,playerList[0]);
                pfstool.playerPanel.setValues( region.getPlayer( Integer.parseInt( s.split(" - ")[0] ) ) );
                pfstool.playerPanel.setRegion(region);
                pfstool.cards.show(pfstool.getContentPane(), "Display Player");
            }
            break;
            case Constants.FIND:
                {
                    ArrayList playableScenarios = new ArrayList();
                    boolean playable = true;

                    TableFinder tableFinder = new TableFinder(pfstool, region);
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

                    ScenarioDialog sd = new ScenarioDialog(pfstool,"Tables Found",playableScenarios);
                }
                break;
            case Constants.DISCON:{
                StatDis tD = new StatDis(pfstool, "Display Conventions",region.getConvetionTotals(),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
                } break;
            case Constants.DISVEN:{
                Object[] venueList = region.getVenueList();
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to display:","Display Venue", JOptionPane.PLAIN_MESSAGE,null,venueList,venueList[0]);
                if ((s != null) && (s.length() > 0)){
                    int vIndex = region.getVenueIndex(s);
                    StatDis tD = new StatDis(pfstool, "Display " + venueList[vIndex],region.getVenueLast12Totals(vIndex),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
                }
                } break;
            case Constants.DISYR:{
                StatDis tD = new StatDis(pfstool, "Statistics for the 12 months", region.getLast12MonthsTotals(), "Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
                } break;
            default:
                break;
        }

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
