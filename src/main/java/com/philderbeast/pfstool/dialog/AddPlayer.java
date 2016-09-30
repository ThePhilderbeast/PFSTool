/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-28T20:29:32+10:00
*/
package com.philderbeast.pfstool.dialog;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import com.philderbeast.paizolib.Player;

public class AddPlayer extends JDialog  implements ActionListener {

	private Player newPlayer;
	private JButton finish;
	private JButton cancel;

	private JTextField tfFirstName = new JTextField();
	private JTextField tfSurname = new JTextField();
	private JTextField tfPaizoName = new JTextField();
	private JTextField tfPFSNumber = new JTextField();
	private JTextField tfeMail = new JTextField();

	public AddPlayer(JFrame parent) {
		super(parent, "Add Player to Region", true);
		setLocation(300,300);
		setResizable(false);
		setMinimumSize(new Dimension(400,400));

		GridBagLayout gridBag = new GridBagLayout();
		getContentPane().setLayout(gridBag);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		JTextArea directions = new JTextArea("Please enter the following information for the player you are adding to your region;");
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

		JLabel lblFirstName = new JLabel("First name:");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(lblFirstName, constraints);
		getContentPane().add(lblFirstName);

		JLabel lblSurname = new JLabel("Surname:");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(lblSurname, constraints);
		getContentPane().add(lblSurname);

		//TODO: explain this better so it makes more sence if the url name
		JLabel lblPaizoName= new JLabel("Paizo Alias:");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridBag.setConstraints(lblPaizoName, constraints);
		getContentPane().add(lblPaizoName);

		JLabel lblNumber = new JLabel("PFS Number:");
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(lblNumber, constraints);
		getContentPane().add(lblNumber);

		JLabel lblEMail = new JLabel("eMail Address:");
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(lblEMail, constraints);
		getContentPane().add(lblEMail);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfFirstName, constraints);
		getContentPane().add(tfFirstName);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfSurname, constraints);
		getContentPane().add(tfSurname);

		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfPaizoName, constraints);
		getContentPane().add(tfPaizoName);

		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfPFSNumber, constraints);
		getContentPane().add(tfPFSNumber);

		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(tfeMail, constraints);
		getContentPane().add(tfeMail);

		JButton add = new JButton("Add Player");
		add.setActionCommand("add");
		add.addActionListener(this);
		constraints.gridx = 1;
		constraints.gridy = 7;
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
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		gridBag.setConstraints(cancel, constraints);
		getContentPane().add(cancel);

		setVisible(true);
	}

	public Player getPlayer(){
		newPlayer = new Player(tfPFSNumber.getText(), tfPaizoName.getText(), tfFirstName.getText(), tfSurname.getText(), tfeMail.getText());
		return newPlayer;
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
