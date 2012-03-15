/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.prefs.Preferences;

/**
 *
 * @author afannin1
 */
public class UserSettings {
    
    private Preferences prefs;
    
    public UserSettings() {
        
    }
    
    /*TODO: Perform exception handling and check for nulls */
    public void saveSettings(String apiKey, String projectId, int days, int hours, int minutes, int timeOut) {
        prefs = Preferences.userNodeForPackage(this.getClass());
        prefs.put("API_KEY", apiKey);
        prefs.put("PROJECT_ID", projectId);
        prefs.put("DAYS", String.valueOf(days));
        prefs.put("HOURS", String.valueOf(hours));
        prefs.put("MINUTES", String.valueOf(minutes));
        prefs.put("TIMEOUT", String.valueOf(timeOut));
    }
    
    public String getApiKey() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        return prefs.get("API_KEY", "NULL");
    }
    
    public String getProjectId() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        return prefs.get("PROJECT_ID", "NULL");
    }
    
    public int setDays() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        return prefs.getInt("DAYS", -999);
    }
    
    public int setHours() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        return prefs.getInt("HOURS", -999);
    }
    
    public int setMinutes() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        return prefs.getInt("MINUTES", -999);
    }
    
    public int setTimeOut() {
        prefs = Preferences.userNodeForPackage(this.getClass());
        return prefs.getInt("TIMEOUT", -999);
    }
}
