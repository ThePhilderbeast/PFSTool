/**
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-08-12T18:48:59+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Phillip Ledger
* @Last modified time: 2016-12-04T00:20:13+11:00
*/
import com.philderbeast.pfstool.PFSOrganiserTool;

import javax.swing.*;

public class Main
{

    public static void main(String[] args){

        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            System.err.println("Unsupported Look and feel");
        }
        catch (ClassNotFoundException e) {
            System.err.println("Looks and Feel Not Found");
        }
        catch (InstantiationException e) {
            System.err.println("Unable in Instatntate Look and feel");
        }
        catch (IllegalAccessException e) {
            System.err.println("Unable in Access Look and feel");
        }

        @SuppressWarnings("unused")
        PFSOrganiserTool tool = new PFSOrganiserTool();

    }

}
