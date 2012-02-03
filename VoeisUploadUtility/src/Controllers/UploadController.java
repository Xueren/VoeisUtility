/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.UploadModel;
import Views.MainView;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author afannin1
 */
public class UploadController extends AbstractController{

    public static final String UPLOAD = "upload";
        
    UploadModel model;
    MainView view;
   
    @SuppressWarnings("LeakingThisInConstructor")
    public UploadController(UploadModel model, MainView view) {
        this.model = model;
        this.view = view;
        view.setActionListeners(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        final File file = view.getFile();
        final String apiKey = view.getApiKey();
        final String projectKey = view.getProjectKey();
        final String templateId = view.getTemplateId();
        final String startLine = view.getStartLine();
        int template;
        int start;
        if (UPLOAD.equals(action))
        {
            
            if (model.validateFile(file) && model.validateText(apiKey) && model.validateText(projectKey) && model.validateInt(templateId) && model.validateInt(startLine))  
            {
                template = Integer.parseInt(templateId);
                start = Integer.parseInt(startLine);
                
                model.uploadData(file, apiKey, projectKey, template, start);
            }
                else 
                    JOptionPane.showMessageDialog(view, "Some required fields were not filled out correctly.\nPlease try again.");
        }
    }
}
