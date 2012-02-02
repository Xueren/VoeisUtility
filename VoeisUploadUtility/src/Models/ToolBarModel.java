/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Classes.LogFile;
import Views.AboutView;
import Views.IntervalView;
import Views.LogView;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author afannin1
 */
public class ToolBarModel implements IModel{
    
    public void exitApplication() {
        System.exit(0);
    }

    public void loadAbout() {
        AboutView aView = new AboutView();
        aView.setVisible(true);
    }

    public void loadUploadInterval() {
        IntervalView dView = new IntervalView();
        JFrame frame = new JFrame();
        frame.setTitle("Set Data Upload Interval");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(dView);
        frame.pack();
        frame.setVisible(true);
    }

    public void openLogs() {
        File testFile = new File("test.txt");
        LogFile log = new LogFile(testFile, "02/22/11 00:00:00", "Everything is going OK", "Succeeded");
        log.writeLog();
        //LogView log = new LogView();
        //log.setVisible(true);
    }
}
