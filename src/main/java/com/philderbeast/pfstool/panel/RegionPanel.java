/**
* @Author: Phillip Ledger
* @Date:   2016-08-17T14:13:45+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-05T19:11:23+10:00
*/
package com.philderbeast.pfstool.panel;

import com.philderbeast.pfstool.*;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class RegionPanel extends JPanel {
	TextArea regionStats = new TextArea();

	public RegionPanel() {
		super();
		setLayout(new BorderLayout());

		JPanel imgPanel = new JPanel();

		imgPanel.setLayout(new FlowLayout());
		LoadImage img = new LoadImage("PathfinderSociety.jpeg");

		imgPanel.add(img);

		add(imgPanel, BorderLayout.NORTH);
		add(regionStats,BorderLayout.CENTER);
	}

	public void setStats(String[] stats){
		regionStats.setText(null);
		for (int i = 0; i < stats.length; i++)
			regionStats.append(stats[i] + "\n");
	}

}
