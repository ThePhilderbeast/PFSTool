/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T08:19:02+10:00
*/
package com.philderbeast.pfstool.panel;

import javax.swing.*;
import java.awt.*;

import com.philderbeast.pfstool.LoadImage;

public class NewPanel extends JPanel {
	GridBagLayout gridBag = new GridBagLayout();
	public JTextField tfName = new JTextField();
	public JTextField tfLoc = new JTextField();

	public NewPanel()
	{
		super();
		setLayout(gridBag);

		JTextArea taWelcome = new JTextArea("Welcome to the Pathfinder Society Organiser's Tool. This program has been developed to provide Venture Officers with the ability to view a snap shot of their regions and assist in planning public events.\n\n\nPlease enter a name and location for your Region, for example:\nRegion Name: Canberra\nRegion Location: ACT, Australia");
		taWelcome.setLineWrap(true);
		taWelcome.setWrapStyleWord(true);
		taWelcome.setEditable(false);

		JLabel lblName = new JLabel("Region Name:");
		JLabel lblLoc = new JLabel("Region Location:");

		LoadImage imgLogo = new LoadImage("PathfinderSociety.jpeg");

		addComponent(imgLogo,0,0,5,3,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		addComponent(taWelcome,0,4,5,2,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);
		addComponent(lblName,0,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.EAST);
		addComponent(tfName,1,7,4,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
		addComponent(lblLoc,0,9,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.EAST);
		addComponent(tfLoc,1,9,4,1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
	}

	public void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill, int anchor)
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
}
