import java.awt.EventQueue;

import Control.ControlGroup;
import Model.Model;

public class BatailleNavale {

    public static void main(String[] args) {
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				Model model = new Model();
				
                @SuppressWarnings("unused")
				ControlGroup controlGroup = new ControlGroup(model);
                
                
			}
		});
    }
}
