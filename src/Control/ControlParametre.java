package Control;

import Sounds.MusicPlayer;
import View.VueJeu;
import View.VueParametre;
import View.VueMenu;
import Model.Model;
import Joueurs.Joueurs;

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
            model.setIsGameActive(true);
            vueJeu.setVisible(true);
        }
        else if (source == vueParametre.getAnnuler()){
            vueParametre.setVisible(false);
        }
        else if (source == vueParametre.getIA1()){
        	model.setAILevel(Joueurs.IA_LEVEL_1);
        }
        else if (source == vueParametre.getIA2()){
        	model.setAILevel(Joueurs.IA_LEVEL_2);
        }
        else if (source == vueParametre.getIA3()){
        	model.setAILevel(Joueurs.IA_LEVEL_3);
        }
        else if (source == vueParametre.getIA4()){
        	model.setAILevel(Joueurs.IA_LEVEL_4);
        }
        else if (source == vueParametre.getCheckBoxBonus()){
            model.setIsBonusActive(true);
        }
        if(vueJeu.isVisible()){
        	musicPlayer.start();
        }

    }

}
