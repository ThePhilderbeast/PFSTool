/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-26T17:45:03+10:00
*/
package com.philderbeast.pfstool.dialog;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class AboutDialog extends JDialog  implements ActionListener {

	public AboutDialog(JFrame parent) {
		super(parent, "About", true);
		setLocation(300,300);
		setResizable(false);
		setMinimumSize(new Dimension(400,500));

		JTextArea directions = new JTextArea("The Pathfinder Society Organised Play Tool has been developed to provide Venture Officers and Event Organisers with the ability to view statistics and information about their regions.\n\nThis tool can provide the following statitical information:\n\tTotal number of players who have attended a public event in the region.\n\tThe total number of players, Game Masters and sessions recorded at the public events held in the region for the last 12 months.\n\tThe total number of players, Game Masters and sessions recorded for each Convention held in the region.\n\n\nDeveloped by Ben Jordan, and Phillip Ledger\n\n\n\nCommunity Use Policy\nThis program uses trademarks and/or copyrights owned by Paizo Publishing, LLC, which are used under Paizo's Community Use Policy. We are expressly prohibited from charging you to use or access this content. This program is not published, endorsed, or specifically approved by Paizo Publishing. For more information about Paizo's Community Use Policy, please visit Paizo.com For more information about Paizo Publishing and Paizo products, please visit paizo.com.");

		directions.setEditable(false);
		directions.setLineWrap(true);
		directions.setWrapStyleWord(true);
		JScrollPane scAbout = new JScrollPane(directions);
		getContentPane().add(scAbout);

		JButton ok = new JButton("Ok");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		getContentPane().add(ok, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent evt){
		String command = evt.getActionCommand();
		if (command.equals("ok")) {
			setVisible(false);
		}
	}
}
