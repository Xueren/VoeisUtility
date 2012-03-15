/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Classes.UserSettings;
import Classes.Validation;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author afannin1
 */
public class ClientModel {

    private String pushInterval;
    private Map<Object, String> siteMap = new HashMap<Object, String>();
    private Map<Object, String> tempMap = new HashMap<Object, String>();
    Validation validate = new Validation();
    
    public void setPushInterval(String interval) {
        pushInterval = interval;
    }
    
    public String getPushInterval() {
        return pushInterval;
    }
    
    public boolean validateText(String text) {
        return validate.stringTextField(text);
    }
    
    public boolean validateInt(String text) {
        return validate.integerTextField(text);
    }
    
    public boolean validateFile(File file) {
        return validate.file(file);
    }
    
    public Collection<String> populateSites(String projectKey) {       
       VOEISAPI api = new VOEISAPI(projectKey, null);
       siteMap = api.get_project_sites();
       
       Collection<String> values = siteMap.values();
       return values;
       }   

    public Collection<String> populateTemplates(String projectKey, String site) {
        VOEISAPI api = new VOEISAPI(projectKey, null);
        int siteId = 0;
        for (Entry<Object, String> entry : siteMap.entrySet()) {
            if (site.equals(entry.getValue()))
                siteId =  Integer.parseInt(entry.getKey().toString());
        }
        tempMap = api.get_project_data_templates(siteId);
        
        Collection<String> values = tempMap.values();
        return values;        
    }
    
    public boolean pushData(File dataFile, String site, String template, int start_line, String projectKey, String apiKey) throws Exception {
        VOEISAPI api = new VOEISAPI(projectKey, apiKey);
        int data_template_id = getCurrentTemplate(template);
        int site_id = getCurrentSite(site);
        api.upload_data(dataFile, data_template_id, site_id, start_line);
        return true;
    }

    private int getCurrentTemplate(String template) {
        for (Entry<Object, String> entry : tempMap.entrySet()) {
            if (template.equals(entry.getValue()))
                return Integer.parseInt(entry.getKey().toString());
        }
        return -999;
    }

    private int getCurrentSite(String site) {
        for (Entry<Object, String> entry : siteMap.entrySet()) {
            if (site.equals(entry.getValue()))
                return Integer.parseInt(entry.getKey().toString());
        }
        return -999;
    }
    
    public String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date timeStamp = new Date();
        return dateFormat.format(timeStamp);
    }

    public boolean savePreferences(String apiKey, String projectId, int days, int hours, int minutes, int timeOut) {
        UserSettings settings = new UserSettings();
        try {
        settings.saveSettings(apiKey, projectId, days, hours, minutes, timeOut); 
        return true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
