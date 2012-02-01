/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ToolBarModel;
import Models.UploadModel;
import Views.Container;
import Views.MainView;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 *
 * @author afannin1
 */
public class ToolBarController extends AbstractController{

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
    MainView view;
    Container container;
    UploadModel uModel;
    
    public ToolBarController(ToolBarModel tModel, MainView view, Container container, UploadModel uModel)
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
        else if (DATA_INTERVAL.equals(action))
            tModel.loadUploadInterval();
        else if (PUSH_LOGS.equals(action))
            tModel.openLogs();
//        else if (COPY.equals(action))
//            tModel.copy(view.getClipboardMember());
//        else if (PASTE.equals(action))
//        {
//            view.setClipboardMember(tModel.paste());
//        }
        else if (ABOUT.equals(action))
            tModel.loadAbout();
    }

    private void resetFields() {
        view.setFile(null);
        view.setApiKey("");
        view.setProjectKey("");
        view.setTemplateId("");
        view.setStartLine("");
        view.setTextFields();
    }
}
