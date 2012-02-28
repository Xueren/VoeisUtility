/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import JSONClasses.JSONArray;
import JSONClasses.JSONObject;
import JSONClasses.JSONParser;
import JSONClasses.JSONTokener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author afannin1
 */
public class VOEISAPI implements IModel{

    final private String devHost = "https://voeis-dev.msu.montana.edu/projects/";
    final private String projectID;
    final private String apiKey;
    
    public VOEISAPI(final String projectID, final String apiKey) {
        this.projectID = projectID;
        this.apiKey = apiKey;
    }
    
    public Map<Object, String> get_project_sites() {
        Map<Object, String> siteMap = new HashMap<Object, String>();
        try {
            siteMap = JSONParser.getNameInformation(httpGetRequest("/apivs/get_project_sites.json?", 0));
        } catch (Exception ex) {
            Logger.getLogger(VOEISAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return siteMap;
    }
    
    public Map<Object, String> get_project_data_templates(final int siteID) {
        Map<Object, String> dataTemplateMap = new HashMap<Object, String>();
        try {
            dataTemplateMap = JSONParser.getNameInformation(httpGetRequest("/apivs/get_project_data_templates.json?", siteID));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return dataTemplateMap;
    }
    
    //This part does not work
    public void upload_data(File datafile, int data_template_id, int site_id, int start_line) throws Exception {
        SSLSocketFactory sslSocketFactory = bypassCerts(); //DEV!!!
        
        URL url;
        HttpURLConnection connection = null;
        String target = devHost + projectID + "/apivs/upload_data.json?" + apiKey;
        BufferedReader reader = null;
        StringBuilder builder = null;
        String line = null;
        
        try {
              url = new URL(target);
              connection = (HttpURLConnection)url.openConnection();
              ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
              connection.setRequestMethod("POST");
              connection.setDoOutput(true);
              connection.setRequestProperty("datafile", "this");
              connection.setRequestProperty("data_template_id", String.valueOf(data_template_id));
              connection.setRequestProperty("site_id", String.valueOf(site_id));
              connection.setRequestProperty("start_line", String.valueOf(start_line));
              
              OutputStream os = connection.getOutputStream();
              
              TransformerFactory tf = TransformerFactory.newInstance();
              Transformer transformer = tf.newTransformer();
              FileReader fileReader = new FileReader(datafile);
              StreamSource source = new StreamSource(fileReader);
              StreamResult result = new StreamResult(os);
              
              os.flush();
              System.out.println(connection.getResponseCode());
              connection.disconnect();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JSONArray httpGetRequest(String methodCall, final int ID) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        
        SSLSocketFactory sslSocketFactory = bypassCerts();  //DEV!!!
        
        JSONTokener tokener;
        JSONArray parsedArray = null;

          URL url;
          HttpURLConnection connection = null;
          String target = devHost + projectID + methodCall;
          BufferedReader reader = null;
          StringBuilder builder = null;
          String line = null;
          
          try {
              url = new URL(target);
              connection = (HttpURLConnection)url.openConnection();
              ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
              connection.setRequestMethod("GET");
              if (ID > 0) {
                  connection.setRequestProperty("id", String.valueOf(ID));
              }
              connection.setDoOutput(true);
              connection.setReadTimeout(10000);
              
              connection.connect();
              
              reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              builder = new StringBuilder();
              
              while((line = reader.readLine()) != null) {
                  builder.append(line).append('\n');
              }
              
              tokener = new JSONTokener(builder.toString());
              parsedArray = new JSONArray(tokener);
              //parsedString = new JSONObject(tokener);
              JSONParser.printJSONString(parsedArray);
              //System.out.println(builder.toString());
          }
                  catch (Exception ex){
                      ex.printStackTrace();
                  }
          finally {
              connection.disconnect();
              reader = null;
              builder = null;
              connection = null;
              return parsedArray;
          }
    }

    /***********************************************************************************************
     * The Following methods are used so that the application will not fail when a self-signed
     * certificate is used.  This is for DEV purposes ONLY and should be removed.
     ***********************************************************************************************/
    
    private SSLSocketFactory bypassCerts() throws NoSuchAlgorithmException, KeyManagementException {
        //Install trust manager for all certs
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        //Create ssl socket factory
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return sslSocketFactory;
    }
    
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
}
