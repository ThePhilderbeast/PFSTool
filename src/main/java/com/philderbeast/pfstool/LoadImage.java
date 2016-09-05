/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-28T15:53:37+10:00
*/



package com.philderbeast.pfstool;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class LoadImage extends Component {
	BufferedImage img;

	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public LoadImage(String fileName) {
		try {
			img = ImageIO.read(new FileInputStream(fileName));
		} catch (IOException e) {
			//in space no one can hear you scream
			e.printStackTrace();
		}
	}

	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100,100);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}
}
