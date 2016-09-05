/**
* @Author: Phillip Ledger
* @Date:   2016-09-01T10:52:21+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-05T18:56:15+10:00
*/
package com.philderbeast.pfstool;

import com.philderbeast.pfstool.panel.*;

import java.awt.*;
import javax.swing.*;

public class PFSView extends JFrame
{

    CardLayout cards;

    public PFSView()
    {
        //set the default window title
        super("Pathfinder Society Organised Play Tool");

        cards = new CardLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setLocation(150,150);
		setLayout(cards);

        //create the panels
        InitialPanel initPanel = new InitialPanel();
        NewPanel newPanel = new NewPanel();
        RegionPanel mainInt = new RegionPanel();
        PlayerPanel playerPanel = new PlayerPanel();

        //set up my view

    }

}
