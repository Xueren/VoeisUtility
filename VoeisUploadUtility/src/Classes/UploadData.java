/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author afannin1
 */
public class UploadData {
    
    private final File dataFile;
    private final int templateId;
    private final int startLine;
    private final String apiKey;
    private final String project;
    
    public UploadData(File dataFile, int templateId, int startLine, String apiKey, String project){
        this.dataFile = dataFile;
        this.templateId = templateId;
        this.startLine = startLine;        
        this.apiKey = apiKey;
        this.project = project;
    }
    
    //check last record for changes
    public void uploadLoggerData() {
        String dataString = constructData();
        
        OutputStreamWriter outWriter = null;
        BufferedReader reader = null;
        try {
        URL url = new URL("https://voeis.msu.montana.edu/projects/" + project + "/apvis/upload_logger_data.json?"); //I'm don't like building the URL string here
                                                                                                                    //change to a method parameter called from the
                                                                                                                    //UploadModel's method to call this.
        URLConnection connection = url.openConnection();
        outWriter = new OutputStreamWriter(connection.getOutputStream());
        connection.setDoInput(true);
        
        outWriter.write(dataString);
        outWriter.flush();
        
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while((line = reader.readLine()) != null){
            //do processing
        }
       }
       catch (Exception ex) {
           
           //do something
       }
       finally {
            try {
                outWriter.close();
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
    
    public void makeUpdater() {
        if (!dataFile.toString().contains("\\Updater.csv")){
            //create csv update file
        }
    }

    private String constructData() {
        try {
            String data = URLEncoder.encode("datafile", "UTF-8") + "=" + URLEncoder.encode(dataFile.toString(), "UTF-8");
            data += "&" + URLEncoder.encode("data_template_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(templateId), "UTF-8");
            data += "&" + URLEncoder.encode("start_line", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(startLine), "UTF-8");
            data += "&" + URLEncoder.encode("api_key", "UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8");
            
            return data;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
