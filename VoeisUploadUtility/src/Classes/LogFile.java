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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    private String logDirectory;   
    private final File dataFile;
    private final String pushTime;
    private final String serverResponse;
    private File logFile;
    private final String user;
    
    public LogFile( File dataFile, String pushTime, String serverResponse, String user) {       //Some of these parameters might need to be refactored out of the 
        this.dataFile = dataFile;                                                               //class constructor.
        this.pushTime = pushTime;
        this.serverResponse = serverResponse;
        this.user = user;
    }    
    
    public void writeLog() {  
        
        
        
        logDirectory = getCurrentDirectory();
        logFile = new File(logDirectory + "pushLog.txt");
        if (!logFile.exists()) {
            createNewLog();
        }
        else {
            addToLog();
        }
    }

    private void createNewLog() {
        //FileWriter fileWriter = null;
        BufferedWriter buffWriter = null;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date timeStamp = new Date();
        
        try {
            buffWriter = writeNewLog(buffWriter, dateFormat, timeStamp);
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                //fileWriter.close();
                buffWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    private void addToLog() {
        BufferedWriter buffWriter = null;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date timeStamp = new Date();
        
        try {
            buffWriter = writeToLog(buffWriter, dateFormat, timeStamp);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                buffWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openLog() {
        try {
            Desktop desktop = Desktop.getDesktop();
            logDirectory = getCurrentDirectory();
            logFile = new File(logDirectory + "pushLog.txt");            
            if (logFile.exists()) 
                desktop.open(logFile);
            else
                JOptionPane.showMessageDialog(null, "No log file exists.");
        } catch (IOException ex) {
            Logger.getLogger(LogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }
    
    private BufferedWriter writeToLog(BufferedWriter buffWriter, DateFormat dateFormat, Date timeStamp) throws IOException {
        buffWriter = new BufferedWriter(new FileWriter(logFile, true));
        buffWriter.write("===========================================================================");
        buffWriter.newLine();
        buffWriter.write(dateFormat.format(timeStamp));
        buffWriter.newLine();
        buffWriter.newLine();
        buffWriter.write("User: " + user);
        buffWriter.newLine();
        buffWriter.write("Data File: " + dataFile.toString());
        buffWriter.newLine();
        buffWriter.write("Push Time: " + pushTime);
        buffWriter.newLine();
        buffWriter.write("Server Response: " + serverResponse);
        buffWriter.newLine();
        buffWriter.write("===========================================================================");
        buffWriter.newLine();
        return buffWriter;
    }

    private BufferedWriter writeNewLog(BufferedWriter buffWriter, DateFormat dateFormat, Date timeStamp) throws IOException {
        //fileWriter = new FileWriter(logDirectory + "pushLog.txt");  //put some identifier here to signify the user
        buffWriter = new BufferedWriter(new FileWriter(logFile));
        buffWriter.write("===========================================================================");
        buffWriter.newLine();
        buffWriter.write(dateFormat.format(timeStamp));
        buffWriter.newLine();
        buffWriter.newLine();
        buffWriter.write("User: " + user);
        buffWriter.newLine();
        buffWriter.write("Data File: " + dataFile.toString());
        buffWriter.newLine();
        buffWriter.write("Push Time: " + pushTime);
        buffWriter.newLine();
        buffWriter.write("Server Response: " + serverResponse);
        buffWriter.newLine();
        buffWriter.write("===========================================================================");
        buffWriter.newLine();
        return buffWriter;
    }    
}
