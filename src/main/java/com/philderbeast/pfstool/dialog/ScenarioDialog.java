/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T09:08:05+10:00
*/
package com.philderbeast.pfstool.dialog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ScenarioDialog extends JDialog  implements ActionListener {

	public ScenarioDialog(JFrame parent, String inPlayers, ArrayList scenarioList) {
		super(parent, "Find Scenario for " + inPlayers, true);
		setLocation(300,300);
		setResizable(false);
		setMinimumSize(new Dimension(570,500));

		String s = "";

		for (int i = 0; i < scenarioList.size(); i++)
		{
			s += scenarioList.get(i) + "\n";
		}

		JTextArea jtScenarioList = new JTextArea(s);

		jtScenarioList.setEditable(false);
		jtScenarioList.setLineWrap(false);
		JScrollPane scAbout = new JScrollPane(jtScenarioList);
		getContentPane().add(scAbout);

		JButton ok = new JButton("Ok");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		getContentPane().add(ok, BorderLayout.SOUTH);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent evt){
		String command = evt.getActionCommand();
		if (command.equals("ok"))
		{
			dispose();
		}
	}
}
