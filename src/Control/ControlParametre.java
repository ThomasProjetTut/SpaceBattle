package Control;

import Sounds.MusicPlayer;
import View.VueJeu;
import View.VueParametre;
import View.VueMenu;
import Model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by Zous on 06/06/2015.
 */
public class ControlParametre implements ActionListener{

    private VueParametre vueParametre;
    private VueJeu vueJeu;
    private VueMenu vueMenu;
    private Model model;
    private MusicPlayer musicPlayer;

    public ControlParametre(Model model, VueJeu vueJeu, VueMenu vueMenu, VueParametre vueParametre){

        this.vueMenu = vueMenu;
        this.vueParametre = vueParametre;
        this.vueJeu = vueJeu;
        this.model = model;
        vueParametre.setControlParametre(this);
        musicPlayer = new MusicPlayer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == vueParametre.getValider()){
            model.initJeu();
            vueJeu.initGrilleTexte();
            vueJeu.resetTextChat();
            vueMenu.setVisible(false);
            vueParametre.setVisible(false);
            vueJeu.setVisible(true);
        }
        else if (source == vueParametre.getAnnuler()){
            vueParametre.setVisible(false);
        }
        else if (source == vueParametre.getIA1()){

        }
        else if (source == vueParametre.getIA2()){

        }
        else if (source == vueParametre.getIA3()){

        }
        else if (source == vueParametre.getIA4()){

        }
        else if (source == vueParametre.getCheckBoxBonus()){
            model.setIsBonusActive(true);
        }
        if(vueJeu.isVisible()){
        	musicPlayer.start();
        }

    }

}
