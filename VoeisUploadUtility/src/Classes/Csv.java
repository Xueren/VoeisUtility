/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author afannin1
 */
public class Csv {

    public File writer(File updaterFile, char del, boolean quotes) {
        try {
            FileWriter writer = new FileWriter(updaterFile);
            
            
        }
        catch (Exception ex) {
            //Do something
        }
        finally {
            return updaterFile;
        }
    }

    public File reader(File originalFile) {
        File newFile;
        newFile = originalFile;
        return newFile;
    }
    
}
