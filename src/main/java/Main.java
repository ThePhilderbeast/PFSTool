/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-08-12T18:48:59+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-08-28T09:15:27+10:00
*/

import com.philderbeast.paizoscraper.*;
import com.philderbeast.pfstool.PFSOrganiserTool;

import javax.swing.*;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class Main
{

    public static void main(String[] args){

        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
           // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }

        PFSOrganiserTool tool = new PFSOrganiserTool();
    }

}
