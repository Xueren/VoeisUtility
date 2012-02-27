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
import java.io.IOException;
import java.io.InputStreamReader;
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

/**
 *
 * @author afannin1
 */
public class VOEISAPI implements IModel{

    final private String devHost = "https://voeis-dev.msu.montana.edu/projects/";
    final private String projectID;
    
    public VOEISAPI(final String projectID) {
        this.projectID = projectID;
    }
    
    public Map<Object, String> get_project_sites() {
        Map<Object, String> siteMap = new HashMap<Object, String>();
        try {
            siteMap = JSONParser.getSites(httpGetRequest("/apivs/get_project_sites.json?", null));
        } catch (Exception ex) {
            Logger.getLogger(VOEISAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return siteMap;
    }
    
    public Map<Object, String> get_project_data_templates(final String siteID) {
        Map<Object, String> dataTemplateMap = new HashMap<Object, String>();
      
        String[][] params = new String[1][2];   //Maybe change to a hash table
        params[0][0] = "id";
        params[0][1] = siteID.toString();
        try {
            httpGetRequest("/apivs/get_project_data_templates.json?", null);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return dataTemplateMap;
    }

    private JSONArray httpGetRequest(String methodCall, String[][] params) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        
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
