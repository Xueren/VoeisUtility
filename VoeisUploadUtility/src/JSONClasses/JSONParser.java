/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONClasses;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author afannin1
 */
public class JSONParser {
   
    public static Map<Object, String> getSites(JSONArray jsonArr) {
        Map<Object, String> siteMap = new HashMap<Object, String>();
        
        try {    
            siteMap = parseObjects(jsonArr);
        }
        catch(Exception ex){   
            ex.printStackTrace();
        }
        finally {
            return siteMap;
        }     
    }

    public static void printJSONString(JSONArray ar) {
        System.out.println(ar.toString() + "\n");
    }
    
    private static Map<Object, String> parseObjects(JSONArray jsonArr) {
        int id = 0;
        String site = null;
        Map<Object, String> keyMap = new HashMap<Object, String>();
        
        try {
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject row = jsonArr.getJSONObject(i);
                id = row.getInt("id");
                site = row.getString("name");
                keyMap.put(id, site);
            }       
        }
        catch (Exception ex) {
            keyMap.put(1, "No Sites Found.");       //Try this but it may need to be taken out
            ex.printStackTrace();
        }
        finally {
            return keyMap;
        }
    }
}
