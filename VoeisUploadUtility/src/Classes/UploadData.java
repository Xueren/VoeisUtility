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
    private String apiUrl;
    private String projectUrl;
    String result = null;
    
    public UploadData(File dataFile, int templateId, int startLine, String apiKey, String project){
        this.dataFile = dataFile;
        this.templateId = templateId;
        this.startLine = startLine;        
        this.apiKey = apiKey;
        this.project = project;
    }
    
    //check last record for changes
    public String uploadLoggerData() throws Exception {
        URL url;
        HttpURLConnection connection = null;
        String target = "https://voeis.msu.montana.edu/projects/" + project + "/apvis/upload_logger_data.json?api_key=" + apiKey;
        String params =  URLEncoder.encode("datafile", "UTF-8") + "=" + URLEncoder.encode(dataFile.toString(), "UTF-8");
            params += "&" + URLEncoder.encode("data_template_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(templateId), "UTF-8");
            params += "&" + URLEncoder.encode("start_line", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(startLine), "UTF-8");
        
        try {
            url = new URL(target);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application");
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            
            //Request
            DataOutputStream out = new DataOutputStream (connection.getOutputStream());
            out.writeBytes(params);
            out.flush();
            out.close();
            
            //Response
            InputStream in = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (connection != null) 
                connection.disconnect();
        }
        
    }
    
    public void makeUpdater() {
        if (!dataFile.toString().contains("\\Updater.csv")){
            //create csv update file
        }
    }

    private String getApiUrl() throws UnsupportedEncodingException {
        return apiUrl = URLEncoder.encode("api_key=", "UTF-8") + URLEncoder.encode(apiKey, "UTF-8");
    }
    
    private String getProjectUrl() throws UnsupportedEncodingException {
        return projectUrl = URLEncoder.encode("projects/", "UTF-8") + URLEncoder.encode(project, "UTF-8");
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

    private void pipe(Reader reader, Writer writer) throws IOException{
        char[] buf = new char[1024];
        int read = 0;
    while ((read = reader.read(buf)) >= 0)
    {
    writer.write(buf, 0, read); 
    }
    writer.flush();
    }
}
