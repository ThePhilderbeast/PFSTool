/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T09:01:51+10:00
*/
package com.philderbeast.pfstool.dialog;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import com.philderbeast.paizolib.Venue;

public class AddVenue extends JDialog  implements ActionListener {

	private Venue newVenue;
	private JButton finish;
	private JButton cancel;

	private JTextField tfName = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextArea tfEvent = new JTextArea();

	public AddVenue(JFrame parent) {
		super(parent, "Add Venue to Region", true);
		setLocation(300,300);
		setResizable(false);
		setMinimumSize(new Dimension(400,400));

		GridBagLayout gridBag = new GridBagLayout();
		getContentPane().setLayout(gridBag);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		JTextArea directions = new JTextArea("Please enter the following information for the public venue you are adding to your region;\n\tThe Venue's name\n\tThe Venue's address\n\tThe event code or codes used to report sessions held at the public venue. If multiple events have been used please separate them with the \";\" symbol.");
		directions.setLineWrap(true);
		directions.setWrapStyleWord(true);
		directions.setEditable(false);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 5;
		constraints.gridheight = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(directions, constraints);
		getContentPane().add(directions);

		JLabel lblName = new JLabel("Venue Name:");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(lblName, constraints);
		getContentPane().add(lblName);

		JLabel lblAddress = new JLabel("Venue Address:");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(lblAddress, constraints);
		getContentPane().add(lblAddress);

		JLabel lblEvent = new JLabel("Event List:");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(lblEvent, constraints);
		getContentPane().add(lblEvent);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfName, constraints);
		getContentPane().add(tfName);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfAddress, constraints);
		getContentPane().add(tfAddress);

		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfEvent, constraints);
		getContentPane().add(tfEvent);

		JButton add = new JButton("Add Venue");
		add.setActionCommand("add");
		add.addActionListener(this);
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(add, constraints);
		getContentPane().add(add);

		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(cancel, constraints);
		getContentPane().add(cancel);

		setVisible(true);
	}

	public Venue getVenue(){
		newVenue = new Venue(tfName.getText(), tfAddress.getText());
		String[] events = tfEvent.getText().split(";");
		for (int i = 0; i < events.length; i++)
			newVenue.addEvent(events[i]);
		return newVenue;
	}

	public void actionPerformed(ActionEvent evt){
		String command = evt.getActionCommand();
		if (command.equals("add")) {
			setVisible(false);
		} else if (command.equals("cancel")) {
			dispose();
		} else {
		}
	}

}
