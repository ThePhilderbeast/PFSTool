/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-28T21:22:05+10:00
*/
package com.philderbeast.pfstool.dialog;

import com.philderbeast.pfstool.panel.PFSDisplay;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class StatDis extends JDialog  implements ActionListener {

	public StatDis(JFrame parent, String title, int[][] inValues, String inLegend) {
		super(parent, title, false);
		setLocation(300,300);
		//setResizable(false);
		setMinimumSize(new Dimension(545,300));

		JButton ok = new JButton("OK");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		add(ok,BorderLayout.SOUTH);

		PFSDisplay dis = new PFSDisplay();
		dis.setStatistics(inValues);
		add(dis,BorderLayout.CENTER);

		JTextArea legend = new JTextArea(inLegend);
		add(legend,BorderLayout.EAST);

		setVisible(true);

	}

	public void actionPerformed(ActionEvent evt){
		String command = evt.getActionCommand();
		if (command.equals("ok")) {
			dispose();
		}
	}
}
