/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-05T19:09:49+10:00
*/
package com.philderbeast.pfstool.panel;

import com.philderbeast.pfstool.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InitialPanel extends JPanel {
	GridBagLayout gridBag;

	JButton jbNew, jbLoad, jbClose;

	public InitialPanel() {
		super();
		gridBag = new GridBagLayout();
		setLayout(gridBag);

		JTextArea taWelcome = new JTextArea("Welcome to the Pathfinder Society Organiser's Tool. This program has been developed to provide Venture Officers with the ability to view a snap shot of their regions and assist in planning public events.\n\n\n");
		taWelcome.setLineWrap(true);
		taWelcome.setWrapStyleWord(true);
		taWelcome.setEditable(false);

		addComponent(new LoadImage("PathfinderSociety.jpeg"),0,0,5,3,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		addComponent(taWelcome,0,4,5,2,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER);

		jbNew = new JButton("New Region");
		jbNew.setActionCommand(Constants.NEW);

		jbLoad = new JButton("Load Region");
		jbLoad.setActionCommand(Constants.LOAD);

		jbClose = new JButton("Close");
		jbClose.setActionCommand(Constants.ESC);

		addComponent(jbNew,0,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		addComponent(jbLoad,2,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
		addComponent(jbClose,4,7,1,1,1,1,GridBagConstraints.NONE,GridBagConstraints.CENTER);
	}

	public void setActionListener(ActionListener a)
	{
		jbNew.addActionListener(a);
		jbLoad.addActionListener(a);
		jbClose.addActionListener(a);
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
