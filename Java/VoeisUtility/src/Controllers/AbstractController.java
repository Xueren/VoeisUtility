package Controllers;

import Models.IModel;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author afannin1
 */
public abstract class AbstractController implements ActionListener{

    
//    private ArrayList<JPanel> registeredViews;
//    private ArrayList<IModel> registeredModels;
//    
//    public AbstractController() {
//        this.registeredViews = new ArrayList<JPanel>();
//        this.registeredModels = new ArrayList<IModel>();
//    }
//    
//    public void addModel(IModel model){
//        registeredModels.add(model);
//        model.addPropertyChangeListener(this);
//    }
//    
//    public void removeModel(IModel model) {
//        registeredModels.remove(model);
//        model.removePropertyChangeListener(this);
//    }
//    
//    public void addView (JPanel view) {
//        registeredViews.add(view);
//    }
//    
//    public void removeView (JPanel view) {
//        registeredViews.remove(view);
//    }
//    
//    public void propertyChange(PropertyChangeEvent evt) {
//        for (JPanel view: registeredViews)
//            view.action(evt)
//    }
}
