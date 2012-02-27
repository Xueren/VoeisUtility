/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ClientModel;
import Models.VOEISAPI;
import Views.ClientView;
import Views.MainView;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author afannin1
 */
public class ClientController extends AbstractController{

    public static final String UPLOAD = "upload";
    public static final String SAVE_KEYS = "saveKeys";
    public static final String RESET_KEYS = "resetKeys";
        
    ClientModel model;
    ClientView view;
   
    @SuppressWarnings("LeakingThisInConstructor")
    public ClientController(ClientModel model, ClientView view) {
        this.model = model;
        this.view = view;
        view.setActionListeners(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        
        if (action.equals(SAVE_KEYS)) {
            populateSites();
        }
    }
    
    //NOTE: Refactor this to the ClientModel and find a place to store
    //      the maps that hold the names and PKs for the sites data.
    private void populateSites() {
       Map<Object, String> sites = new HashMap<Object, String>();
       VOEISAPI api = new VOEISAPI(view.getProjectKey());
       sites = api.get_project_sites();
       
       Collection<String> values = sites.values();
       Iterator<String> it = values.iterator();
       while(it.hasNext()) {
           String value = it.next();
           view.setSitesComboBox(value);
       }   
    }
}
