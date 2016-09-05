/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T08:18:56+10:00
*/



package com.philderbeast.pfstool.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Last12Display extends JPanel {
	private int[][] stats = {{1,2,3},{4,5,6},{7,8,9},{9,8,7},{6,5,4},{3,2,1},{4,8,6},{8,4,6},{6,8,4},{8,6,6},{1,2,3},{9,8,7},{4,5,6}};
	private int maxValue = 9;
	private int numGroups = 13;

	public void paintComponent(Graphics comp) {
		int height = this.getHeight();
		int width = this.getWidth();
		int increment = height / maxValue;

		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setColor(Color.gray);

		Rectangle2D.Float bg = new Rectangle.Float(0F, 0F, (float)getSize().width, (float)getSize().height);
		comp2D.fill(bg);

		comp2D.setStroke(new BasicStroke(8F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));

		comp2D.setColor(Color.red);
		for (int i = 0; i<numGroups; i++){
			Line2D.Float line = new Line2D.Float(5+i*30,height,5+i*30,height - increment*stats[i][0]);
			comp2D.draw(line);
		}

		comp2D.setColor(Color.blue);
		for (int i = 0; i<numGroups; i++){
			Line2D.Float line = new Line2D.Float(15+i*30,height,15+i*30,height - increment*stats[i][1]);
			comp2D.draw(line);
		}

		comp2D.setColor(Color.green);
		for (int i = 0; i<numGroups; i++){
			Line2D.Float line = new Line2D.Float(25+i*30,height,25+i*30,height - increment*stats[i][2]);
			comp2D.draw(line);
		}
	}

	public void setStatistics(int[][] inStats){
		maxValue = 0;
		for (int i = 0; i < inStats.length; i++){
			for (int j = 0; j < inStats[0].length; j++) {
				if (maxValue < inStats[i][j]) {
					maxValue = inStats[i][j];
				}
			}
		}

		stats = inStats;
	}

}
