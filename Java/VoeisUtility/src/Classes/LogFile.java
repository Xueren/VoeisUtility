/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afannin1
 */
public class LogFile {
    
    private final String logDirectory;
    private final File dataFile;
    private final String pushTime;
    private final String serverResponse;
    
    public LogFile(String logDirectory, File dataFile, String pushTime, String serverResponse) {
        this.logDirectory = logDirectory;
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
}
