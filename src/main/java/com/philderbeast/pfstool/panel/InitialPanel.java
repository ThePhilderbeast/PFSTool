/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T08:18:51+10:00
*/



package com.philderbeast.pfstool.panel;

import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel {
	GridBagLayout gridBag = new GridBagLayout();

	public InitialPanel() {
		super();
		GridBagConstraints constraints;
		setLayout(gridBag);
		//setBackground(new Color(0,128,128));

		JTextArea taWelcome = new JTextArea("Welcome to the Pathfinder Society Organiser's Tool. This program has been developed to provide Venture Officers with the ability to view a snap shot of their regions and assist in planning public events.\n\n\n");
		taWelcome.setLineWrap(true);
		taWelcome.setWrapStyleWord(true);
		taWelcome.setEditable(false);

		//addComponent(new LoadImage("PathfinderSociety.jpeg"),0,0,5,3,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		addComponent(taWelcome,0,4,5,2,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
	}

	public void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill, int anchor){

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
}
