/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author afannin1
 */
public class Validation {
    
    public Validation() {
    }
    
    public boolean integerTextField(String valString) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(valString);
        
        return matcher.find();
    }

    public boolean intervalTextField(String valString) {
        Pattern pattern = Pattern.compile("");          //get Regex for the interval format that will be used
        Matcher matcher = pattern.matcher(valString);
        
        return matcher.find();
    }   
    
    public boolean stringTextField(String valString) {
       if (valString.length() > 0)
           return true;
       else
           return false;
    }   
}
