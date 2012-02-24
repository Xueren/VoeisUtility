/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.net.*;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



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
    
    public String uploadLoggerDataSocket() throws Exception {
        String params =  URLEncoder.encode("datafile", "UTF-8") + "=" + URLEncoder.encode(dataFile.toString(), "UTF-8");
            params += "&" + URLEncoder.encode("data_template_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(templateId), "UTF-8");
            params += "&" + URLEncoder.encode("start_line", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(startLine), "UTF-8");
            
        String hostname = "https://voeis-dev.msu.montana.edu";
        int port = 80;
        InetAddress address = InetAddress.getByName(hostname);
        Socket socket = new Socket(address, port);
        
        String path = "/projects/" + project + "/apivs/upload_logger_data.json?api_key=" + apiKey;
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        wr.write("POST " + path + " HTTP/1.0\r\n");
        wr.write("Content-Length:" + params.length()+"\r\n");
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
        wr.write("\r\n");
    
        wr.write(params);
        wr.flush();
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();
        rd.close();
        return line;
    }
    
    //check last record for changes
    public String uploadLoggerData() throws Exception {
        //This was added to trust all certificates so that SSLExceptions would not be thrown.
        //Needs to be taken out eventually, when certificate can be validated.
        try {
          final TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
              @Override
              public void checkClientTrusted(final X509Certificate[] chain, final String authType) {    
              }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
          }
        };
          
          //Install trust manager for all certs
          final SSLContext sslContext = SSLContext.getInstance("SSL");
          sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
          //Create ssl socket factory
          final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();        
       
        URL url;
        HttpURLConnection connection = null;
        String target = "https://voeis-dev.msu.montana.edu/projects/" + project + "/apivs/upload_data.json?api_key=" + apiKey;
        String params =  URLEncoder.encode("datafile", "UTF-8") + "=" + URLEncoder.encode(dataFile.toString(), "UTF-8");
            params += "&" + URLEncoder.encode("data_template_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(templateId), "UTF-8");
            params += "&" + URLEncoder.encode("start_line", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(startLine), "UTF-8");
            
            
        
        try {
            url = new URL(target);
            connection = (HttpURLConnection)url.openConnection();
            ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "" + params.length());
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            
            //Request
            //DataOutputStream out = new DataOutputStream (connection.getOutputStream());
            //out.writeBytes(params);
            //out.flush();
            //out.close();
            
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(params);
            out.flush();
            
            //Response
           
           InputStream in = null;
           try {
            in = connection.getInputStream();
           }
           catch (Exception ex)
           {
               in = connection.getErrorStream();
               System.out.println(connection.getErrorStream());
               System.out.println("\n\n" + ex.toString());
           }
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                //response.append(line);
                //response.append('\r');
                System.out.println(line);
            }
            rd.close();
            out.close();
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
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
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
}
