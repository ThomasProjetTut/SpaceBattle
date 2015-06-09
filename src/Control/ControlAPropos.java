package Control;

import View.VueAPropos;
import View.VueConnexion;
import View.VueMenu;
import View.VueParametre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ControlAPropos implements ActionListener {

    private VueAPropos vueAPropos;

    public ControlAPropos(VueAPropos vueAPropos) {
        this.vueAPropos = vueAPropos;
        vueAPropos.setAProposController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == vueAPropos.getOkay()) {
            vueAPropos.setVisible(false);
        }

    }
}