/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Classes.LogFile;
import Views.AboutView;
import Views.IntervalView;
import javax.swing.JFrame;

/**
 *
 * @author afannin1
 */
public class ToolBarModel implements IModel{
    
    private JFrame frame = new JFrame();
    
    public void exitApplication() {
        System.exit(0);
    }

    public void loadAbout() {
        AboutView aView = new AboutView();
        aView.setVisible(true);
    }

    public void loadUploadInterval() {
        IntervalView dView = new IntervalView(this);

        frame.setTitle("Set Data Upload Interval");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(dView);
        frame.pack();
        frame.setVisible(true);
    }

    public void openLogs() {
        /* Move call for LogFile to
         * where the user uploads their file.
         * Create logs dynamically when tthey
         * are uploading data on an interval.
         */
        LogFile log = new LogFile(null,null,null,null);     //Bad way of doing this
        log.openLog();
    }
    
    public void disposeFrame() {
        frame.dispose();
    }
}
