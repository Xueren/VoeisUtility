/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ClientModel;
import Views.ClientView;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afannin1
 */
public class ClientController extends AbstractController{

    public static final String START = "start";
    public static final String SAVE_KEYS = "saveKeys";
    public static final String RESET_KEYS = "resetKeys";
    public static final String SITE_CHANGED = "siteChanged";
    
        
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
        Collection<String> siteValues;
        Collection<String> tempValues = null;
        
        if (action.equals(SAVE_KEYS)) {   
            if (view.getFlag()) {
            view.clearSiteComboBox();
            siteValues = model.populateSites(view.getProjectKey());
            if (!siteValues.isEmpty()) {
                Iterator<String> it = siteValues.iterator();
               
                while(it.hasNext()) {
                    String value = it.next();
                    view.setSitesComboBox(value);
                }   
            }
            getTemplateData(tempValues);
            view.setFlag(false);
            }           
        }
        if (action.equals(SITE_CHANGED)) {          //NOTE: This is always being fired when a save action is performed.
            getTemplateData(tempValues);            //      This needs to be changed in the view so it is only fired when
        }                                           //      the combobox selected item is changed.
        
        if (action.equals(START)) {
            try {
                //Check and make sure all fields are valid, then perform HTTP Post Request
                model.pushData(view.getFile(),view.getSite(), view.getTemplateId(), 1, view.getProjectKey(), view.getApiKey());
            } catch (Exception ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void getTemplateData(Collection<String> tempValues) {
        tempValues = model.populateTemplates(view.getProjectKey(),view.getSite());
        if (!tempValues.isEmpty()) {
            view.clearTemplateComboBox();
            Iterator<String> i = tempValues.iterator();
            
            while(i.hasNext()) {
                String val = i.next();
                view.setTemplateComboBox(val);
            }
        }
    }
    
}
