/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.io.*;
import java.util.prefs.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author wzhu1
 */
public class SaveSettings {
    private Preferences pref;
    Preferences setting = Preferences.userNodeForPackage(this.getClass());
    String apiKey; 
    String projKey;
    int days;
    int hours;
    int minutes;
    int timeOut;
    
    public void setPreference(String apiKey, String projectKey, int days, int hours, int minutes, int timeOut){
        try {
            pref = Preferences.userNodeForPackage(this.getClass());
            pref.clear();  
            pref.put("API_KEY", apiKey);
            pref.put("PROJECT_KEY", projectKey);
            pref.put("HOURS", String.valueOf(hours));
            pref.put("DAYS", String.valueOf(days));
            pref.put("MINUTES", String.valueOf(minutes));
            pref.put("TIME_OUT", String.valueOf(timeOut));            
            
            FileOutputStream outStream;
            try {
                outStream = new FileOutputStream("VoeisUtilitySettings.xml");
                pref.exportNode(outStream);
                outStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveSettings.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (BackingStoreException ex) {
            Logger.getLogger(SaveSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getApiKey(){
        apiKey = setting.get("API_KEY", null);
        return apiKey;
    }
    public String getProjectKey(){
        projKey = setting.get("PROJECT_KEY", null);
        return projKey;
    }
    public int getDays(){
        days = setting.getInt("DAYS", 0);
        return days;
    }
    public int getHours(){
        hours = setting.getInt("HOURS", 0);
        return hours;
    }
    public int getMinutes(){
        minutes = setting.getInt("MINUTES", 0);
        return minutes;
    }
    public int getTimeOut(){
        timeOut = setting.getInt("TIME_OUT", 0);
        return timeOut;
    }
}
