/**
* @Author: Phillip Ledger
* @Date:   2016-09-01T10:52:30+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-04T19:28:34+11:00
*/
package com.philderbeast.pfstool;

import com.philderbeast.paizolib.*;
import com.philderbeast.paizoscraper.*;
import com.philderbeast.pfstool.dialog.*;

import java.awt.event.*;

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

    public void setRegion(PFSRegion r)
    {
        this.region = r;
    }

    public void actionPerformed(ActionEvent evt){
        String command = evt.getActionCommand();

        switch (command){
            case Constants.NEW:
            {
                pfstool.cards.show(pfstool.getContentPane(), "New Panel");
            } break;
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

                region = PFSRegion.getInstance();
                region.load(s);
                region = PFSRegion.getInstance();
                
                pfstool.setTitle(region.getName() + " - Pathfinder Society Organised Play Tool");
                Set<Venue> venueList = region.getVenueList();
                String sVenues ="Venues:";
                for (Venue v : venueList) {
                    sVenues = sVenues + "\n\t" + v.getName();
                }

                String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(),"Totals Scenarios Known: " + region.getScenarios().size(), "\nTotal number of Venues: " + region.getNumVenues(),sVenues};
                pfstool.mainInt.setStats(stats);
                pfstool.setEnable(0);
                pfstool.cards.show(pfstool.getContentPane(), "Region Panel");
            } break;
            case Constants.SAVE:
            {
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
            } break;
            case Constants.ESC:
            {
                pfstool.dispose();
            } break;
            case Constants.CLOSE:
            {
                region = null;
                pfstool.cards.show(pfstool.getContentPane(), "Initial Panel");
                pfstool.setTitle("Pathfinder Society Organised Play Tool");
                pfstool.setEnable(1);
            } break;
            case Constants.FIN:
            {
                String rName = pfstool.newPanel.tfName.getText();
                String rLoc = pfstool.newPanel.tfName.getText();
                if (! rName.equals("") & ! rLoc.equals("")){
                    pfstool.setTitle(rName + " - Pathfinder Society Organised Play Tool");
                    region = PFSRegion.getInstance();
                    region.setName(rName, rLoc);
                    String[] stats = {"Region Name: " + rName,"Region Location: " + rLoc};
                    pfstool.mainInt.setStats(stats);
                    pfstool.setEnable(2);
                    pfstool.cards.show(pfstool.getContentPane(), "Region Panel");
                    //AddVenue tD = new AddVenue(pfstool);
                    //region.addVenue(tD.getVenue());
                }
            } break;
            case Constants.ADDVEN:
            {
                AddVenue tD = new AddVenue(pfstool);
                region.addVenue(tD.getVenue());
            } break;
            case Constants.RENAME:
            {
                Set<Venue> venueList = region.getVenueList();
                Venue first = (Venue) venueList.toArray()[0];
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to rename:","Rename Venue", JOptionPane.PLAIN_MESSAGE,null, venueList.toArray(), first.getName());
                if ((s != null) &&  (s.length() > 0)){
                    int vIndex = region.getVenueIndex(s);
                    s = (String) JOptionPane.showInputDialog(pfstool,"Enter new name for Venue:","Rename Venue",JOptionPane.PLAIN_MESSAGE);
                    region.renameVenue(vIndex,s);
                    venueList = region.getVenueList();
                    String sVenues ="Venues:";
                    for(int i = 0; i < venueList.size(); i++)
                        sVenues = sVenues + "\n\t" + venueList.toArray()[i];
                    String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(), "Total number of Venues: " + region.getNumVenues(),sVenues};
                    pfstool.mainInt.setStats(stats);
                }
            } break;
            case Constants.MOVE:
            {
                Set<Venue> venueList = region.getVenueList();
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to change address:","Move Venue", JOptionPane.PLAIN_MESSAGE,null,venueList.toArray(),venueList.toArray()[0]);
                if ((s != null) &&  (s.length() > 0)){
                    int vIndex = region.getVenueIndex(s);
                    s = (String) JOptionPane.showInputDialog(pfstool,"Enter new address for Venue:","Move Venue",JOptionPane.PLAIN_MESSAGE);
                    region.moveVenue(vIndex,s);
                    venueList = region.getVenueList();
                    String sVenues ="Venues:";
                    for(int i = 0; i < venueList.size(); i++)
                    {
                        sVenues = sVenues + "\n\t" + venueList.toArray()[i];
                    }
                    String[] stats = {"Region Name: " + region.getName(), "Region Location: " + region.getLoc(), "Total number of Players: " + region.getNumPlayers(), "Total number of Game Masters: " + region.getNumGMs(), "Total number of Venues: " + region.getNumVenues(),sVenues};
                    pfstool.mainInt.setStats(stats);
                }
            } break;
            case Constants.ADDEVT:
                //TODO: add an event
                break;
            case Constants.UPDATEPLYR:
            {
                Task task = new Task();
                task.execute();
            } break;
            case Constants.ADDPLYR:
            {
                AddPlayer nP = new AddPlayer(pfstool);
                region.addPlayer(nP.getPlayer());
                if(!pfstool.miDisPlayer.isEnabled())
                {
                    pfstool.miDisPlayer.setEnabled(true);
                }
            } break;
            case Constants.DISPLYR:
            {
                Set<Player> playerList = region.getPlayerList();
                Player p = (Player) JOptionPane.showInputDialog( pfstool, 
                                                "Player to display:", 
                                                "Display Player",
                                                JOptionPane.PLAIN_MESSAGE,
                                                null,
                                                playerList.toArray(),
                                                playerList.toArray()[0] );
                pfstool.playerPanel.setValues( p );
                pfstool.playerPanel.setRegion(region);
                pfstool.cards.show(pfstool.getContentPane(), "Display Player");
            }
            break;
            case Constants.FIND:
            {
                ArrayList<Scenario> playableScenarios = new ArrayList<Scenario>();
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

                for (Scenario currentScenario : region.getScenarios())
                {
                    playable = true;

                    for (int j = 0; j < currentPlayers.length; j++){
                        if (currentPlayers[j] != null)
                        {        
                            //TODO: check for playable scenarios                    
                            //playable = playable && !currentPlayers[j].playedScenario(currentScenario);
                        }
                    }

                    if (playable) {
                        playableScenarios.add(currentScenario);
                    }
                }
                @SuppressWarnings("unused")
                ScenarioDialog sd = new ScenarioDialog(pfstool,"Tables Found",playableScenarios);
            } break;
            case Constants.DISCON:
            {
                //TODO: fix stats display
                //StatDis tD = new StatDis(pfstool, "Display Conventions",region.getConvetionTotals(),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
            } break;
            case Constants.DISVEN:
            {
                Set<Venue> venueList = region.getVenueList();
                String s = (String) JOptionPane.showInputDialog(pfstool,"Select Venue to display:","Display Venue", JOptionPane.PLAIN_MESSAGE,null,venueList.toArray(),venueList.toArray()[0]);
                if ((s != null) && (s.length() > 0)){
                    //TODO: fix venue stats
                    //int vIndex = region.getVenueIndex(s);
                    //StatDis tD = new StatDis(pfstool, "Display " + venueList[vIndex],region.getVenueLast12Totals(vIndex),"Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
                }
            } break;
            case Constants.DISYR:
            {
                //TODO: fix yearly stats
                //StatDis tD = new StatDis(pfstool, "Statistics for the 12 months", region.getLast12MonthsTotals(), "Red - Total Players\nBlue - Total Game Masters\nGreen - Total Sessions");
            } break;
            default:
                //TODO: show some kind of error?
                break;
        }

    }
    class Task extends SwingWorker<Void, Void> {
		@Override
		public Void doInBackground() {
			PaizoSite paizoSite = new PaizoSite();
			paizoSite.parseList(region.getPlayerList(), region);
			return null;
		}

   }

}
