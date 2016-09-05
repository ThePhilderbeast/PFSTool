package com.philderbeast.pfstool.dialog;

import javax.swing.*;
import java.awt.event.*;

public class Progress extends JDialog {
    private JPanel contentPane;
    private JProgressBar progressBar;


    public Progress() {
        setContentPane(contentPane);
        setModal(true);



    }
}
