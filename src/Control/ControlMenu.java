package Control;

import View.VueJeu;
import View.VueMenu;
import Model.Model;
import View.VueParametre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {

    private VueMenu vueMenu;
    private VueParametre vueParametre;
    public ControlMenu(VueMenu vueMenu, VueParametre vueParametre){
        this.vueMenu = vueMenu;
        this.vueParametre = vueParametre;
        vueMenu.setMenuControler(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == vueMenu.getJouerS()){
            vueParametre.setVisible(true);
        }

        if (source == vueMenu.getJouerM()){
            vueParametre.setVisible(true);
        }
        else if (source == vueMenu.getInstruction()){
            vueMenu.setVisible(false);
            vueMenu.creerFenetreInstruction();
            vueMenu.setVisible(true);
        }
       
        else if (source == vueMenu.getScore()){
            vueMenu.setVisible(false);
            vueMenu.creerFenetreScore();
            vueMenu.setVisible(true);
        }

        else if (source == vueMenu.getAccueil()){
            vueMenu.setVisible(false);
            vueMenu.creerFenetreMenu();
            vueMenu.setVisible(true);
        }
    }
}