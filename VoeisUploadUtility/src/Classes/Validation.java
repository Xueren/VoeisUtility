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
    
    public boolean stringTextField(String valString) {
       if (valString.length() > 0)
           return true;
       else
           return false;
    }   
    
    public boolean dateTime(String dateString) {
        Pattern pattern = Pattern.compile("^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$");
        Matcher matcher = pattern.matcher(dateString);
        
        return matcher.find();
    }

    public boolean positiveInt(int interval) {
        if (interval > 0)
            return true;
        else
            return false;
    }
}
