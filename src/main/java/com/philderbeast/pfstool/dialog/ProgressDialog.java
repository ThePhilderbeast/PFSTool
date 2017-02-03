/**
* @Author: Phillip Ledger
* @Date:   2016-08-28T11:23:05+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-09-01T10:30:15+10:00
*/
package com.philderbeast.pfstool.dialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ProgressDialog
{
    /** The current info string */
    private String info = "Unset";

    private JFrame frame;
    private JLabel jLabel;
    private JProgressBar jProgressBar;

    public ProgressDialog()
    {
        super();
        initComponents();
    }

    private void initComponents()
    {
        frame = new JFrame();

        jLabel = new JLabel();
        jProgressBar = new JProgressBar();

        //frame.setTitle("Importing Data...");
        jLabel.setText("Loading player data for: ");

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        frame.getRootPane().setBorder(padding);
        frame.add(BorderLayout.NORTH, jLabel);
        frame.add(BorderLayout.SOUTH, jProgressBar);

        frame.setUndecorated(true);

        //probably should make these relative some how
        frame.setSize(250, 50);
        //center the window
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean show)
    {
        frame.setVisible(show);
    }

    /**
     * Called when a operation is about to start to set up ready to display
     * progress info
     * @param steps The total number of steps that there will be in this
     * operation
     * @param newInfo A string describing the operation
     */
    public void progressStart(int steps, String newInfo)
    {
        this.info = newInfo;

        this.jLabel.setText(info + "...");

        System.out.println("info = " + info);
        this.jProgressBar.setValue(0);
        this.jProgressBar.setMinimum(0);
        this.jProgressBar.setMaximum(steps);

    }

    /**
     * Called if progress has halted for some time
     *
     * @param reason A string describing the reason that no progress is
     * occurring
     */
    public void progressText(String msg)
    {
        this.jLabel.setText(msg);
    }

    /**
     * Called when progress has been made to update the display
     *
     * @param step The current step number that we have reached. This will
     * range from 0 to one less than the number of steps that exist for
     * this operation.
     */
    public void progressStep(int step)
    {
        //System.out.println("Setting progress to " + step);
        this.jProgressBar.setValue(step);
    }
}
