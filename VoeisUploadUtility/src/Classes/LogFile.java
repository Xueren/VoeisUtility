/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * LogFile Class: Creates a local log file that contains information about the
 *                user's uploads to VOEIS.
 *                The "View Log Files" option on the menu will open this file.
 *                  
 * @author afannin1
 */
public class LogFile {
    
    //TODO: Create a folder as a default directory to store the logs wherever the application is
    //      installed.
    private final String logDirectory = "C:\\Users\\afannin1\\Desktop\\";   //JUST FOR TESTING
    private final File dataFile;
    private final String pushTime;
    private final String serverResponse;
    
    public LogFile( File dataFile, String pushTime, String serverResponse) {
        this.dataFile = dataFile;
        this.pushTime = pushTime;
        this.serverResponse = serverResponse;
    }
    
    public void writeLog(File logFile) {
        if (!logFile.exists()) {
            createNewLog();
        }
        else {
            addToLog(logFile);
        }
    }

    private boolean createNewLog() {
        FileWriter fileWriter = null;
        BufferedWriter buffWriter = null;
        
        try {
            fileWriter = new FileWriter(logDirectory + "pushLog.txt");  //put some identifier here to signify the user
            buffWriter = new BufferedWriter(fileWriter);
            
            buffWriter.write("Data File: " + dataFile.toString()
                             + "\nPush Time: " + pushTime
                             +"\nServer Response: " + serverResponse);
            
            return true;
        }
        catch (Exception ex) {
            Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                fileWriter.close();
                buffWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean addToLog(File logFile) {
        FileWriter fileWriter = null;
        BufferedWriter buffWriter = null;
        
        try {
            fileWriter = new FileWriter(logFile);
            buffWriter = new BufferedWriter(fileWriter);
            
            buffWriter.write("Data File: " + dataFile.toString()
                             + "\nPush Time: " + pushTime
                             +"\nServer Response: " + serverResponse
                             +"\n\n");
            return true;
        }
        catch (Exception ex) {
            Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                fileWriter.close();
                buffWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void openLog(File log) {
        try {
            Desktop desktop = Desktop.getDesktop();
            
            if (log.exists()) 
                desktop.open(log);
            else
                JOptionPane.showMessageDialog(null, "No log file exists.");
        } catch (IOException ex) {
            Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
