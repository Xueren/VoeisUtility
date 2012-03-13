/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ToolBarModel;
import Models.ClientModel;
import Views.ClientView;
import Views.Container;
import java.awt.event.ActionEvent;

/**
 *
 * @author afannin1
 */
public class ToolBarController extends AbstractController{

    public static final String SAVE = "save";
    public static final String OPEN = "open";
    public static final String RESET_FIELDS = "reset";
    public static final String EXIT = "exit";
    public static final String CUT = "cut";
    public static final String COPY = "copy";
    public static final String PASTE = "paste";
    public static final String PUSH_SETTINGS = "push";
    public static final String PUSH_LOGS = "logs";
    public static final String DATA_INTERVAL = "interval";
    public static final String HELP = "help";
    public static final String ABOUT = "about";
    
    ToolBarModel tModel;
    ClientView view;
    Container container;
    ClientModel uModel;
    
    public ToolBarController(ToolBarModel tModel, ClientView view, Container container, ClientModel uModel)
    {
        this.tModel = tModel;
        this.view = view;
        this.container = container;
        this.uModel = uModel;
        container.setActionListeners(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        
        if (OPEN.equals(action))  
            view.loadFile();
        if (RESET_FIELDS.equals(action))
            resetFields();
        else if (EXIT.equals(action))
            tModel.exitApplication();  
        else if (PUSH_LOGS.equals(action))
            tModel.openLogs();
        else if (ABOUT.equals(action))
            tModel.loadAbout();
        else if (SAVE.equals(action))
            uModel.savePreferences(view.getApiKey(), view.getProjectKey(), view.getDays(), view.getHours(), view.getMinutes(), view.getTimeOut());
    }

    private void resetFields() {
        view.setFile(null);
        view.setApiKey("");
        view.setProjectKey("");
        view.setStartLine("");
        view.setTextFields();
    }
}
