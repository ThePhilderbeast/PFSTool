/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-08-13T15:27:59+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-26T17:39:59+10:00
*/
package com.philderbeast.pfstool.dialog;

import java.awt.*;
import java.awt.event.*;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.philderbeast.paizolib.PFSRegion;

@SuppressWarnings("serial")
public class TableFinder extends JDialog implements ActionListener {

    ArrayList<JComboBox<String>> players;
    JComboBox<String> gm;

    PFSRegion r;

    String[] result;

    static final int NUMPLAYERS = 6;

    public TableFinder(JFrame parent, PFSRegion region) {
		super(parent, "Table Finder", true);

        setLocation(300,300);
		setResizable(false);
		setMinimumSize(new Dimension(400,240));

        r = region;
        SpringLayout layout = new SpringLayout();
        JPanel playersPanel = new JPanel(layout);
    
        players = new ArrayList<JComboBox<String>>(NUMPLAYERS);

//        players = new JComboBox[NUMPLAYERS];

        JLabel l;
        Container contentPane = getContentPane();
        for (int i = 0; i < 6; i++)
        {
            players.add(new JComboBox<String>());
            int playerNum = i+1;
            players.get(i).addItem("");
            //TODO: update for JPA
            //for (String player : region.getPlayerList())
            //{
            //    players[i].addItem(player);
            //}

            l = new JLabel("Player " + playerNum);

            playersPanel.add(l);
            playersPanel.add(players.get(i));

        }

        SpringUtilities.makeCompactGrid(playersPanel, //parent
                                   NUMPLAYERS, 2,
                                   3, 3,  //initX, initY
                                   3, 3); //xPad, yPad

        JButton ok = new JButton("Ok");
        ok.setActionCommand("ok");
        ok.addActionListener(this);
        contentPane.add(playersPanel, BorderLayout.CENTER);
        contentPane.add(ok, BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent evt){
        String command = evt.getActionCommand();

        result = new String[NUMPLAYERS];

        if (command.equals("ok")) {
            for (int i = 0; i < NUMPLAYERS; i++)
            {
                result[i] = (String) players.get(i).getSelectedItem();
            }

            setVisible(false);
            dispose();
        }
    }

    public String[] showDialog()
    {
        setVisible(true);
        return result;
    }

}
