import java.awt.EventQueue;

import Control.ControlGroup;
import Model.Model;

public class BatailleNavale {

    public static void main(String[] args) {
    	
    	EventQueue.invokeLater(() -> {
            Model model = new Model();
            new ControlGroup(model);

        });
    }
}
