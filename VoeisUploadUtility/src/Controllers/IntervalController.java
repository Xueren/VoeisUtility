/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.Validation;
import Models.IntervalModel;
import Models.ToolBarModel;
import Views.IntervalView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author afannin1
 */
public class IntervalController extends AbstractController{
    
    private final String APPLY = "apply";
    private final String CANCEL = "cancel";
    IntervalModel model;
    IntervalView view;
    ToolBarModel tModel;
    
    public IntervalController(IntervalModel model, IntervalView view, ToolBarModel tModel) {
        this.model = model;
        this.view = view;
        this.tModel = tModel;
        view.setActionListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Validation validate = new Validation();
        String action = e.getActionCommand();
        final String dateString = view.getDateString();
        final int interval = view.getInterval();
        final String units = view.getUnits();
        
        if (APPLY.equals(action)) {
            if (validate.dateTime(dateString) && validate.positiveInt(interval)) {
            //Send the interval and date to the main program.
            //Call a class that will upload on the desired interval OR
            //write a script that will do this without the application running.
            }
            else
                JOptionPane.showMessageDialog(view, "Some required fields were not filled out correctly.\nPlease try again");
        }
        else if (CANCEL.equals(action)){
            tModel.disposeFrame();
        }         
    }
}
