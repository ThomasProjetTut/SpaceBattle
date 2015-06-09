package Control;

import View.VueJeu;
import View.VueParametre;
import View.VueMenu;
import Model.Model;
import Joueurs.Joueurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlParametre implements ActionListener{

    private VueParametre vueParametre;
    private VueJeu vueJeu;
    private VueMenu vueMenu;
    private Model model;

    public ControlParametre(Model model, VueJeu vueJeu, VueMenu vueMenu, VueParametre vueParametre){

        this.vueMenu = vueMenu;
        this.vueParametre = vueParametre;
        this.vueJeu = vueJeu;
        this.model = model;
        vueParametre.setControlParametre(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == vueParametre.getValider()){
        	
        	if (Model.getJoueur(2) != null && Model.getJoueur(2).getTypeIdJoueurs() == Joueurs.HUMAIN)
        		model.setAILevel(Joueurs.IA_LEVEL_1);
        	
        	model.setJeuEstEnMulti(false);
            model.initJeu();
            vueJeu.reiniPteGrille();
            vueJeu.repaintFantomeBateau();
            vueJeu.reiniBtnBateaux();
            model.setPlacementBateauEstLock(false);
            vueJeu.initGrilleTexte();
            vueJeu.repaintGrilleAdverseBateau();
            vueJeu.resetTextChat();
            vueMenu.setVisible(false);
            vueParametre.setVisible(false);
            vueJeu.creerFenetreJeu();
            vueJeu.pack();
            vueJeu.setVisible(true);
            VueJeu.getChatTexte().append("Placez vos bateaux dans la grille \n");

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
    }

}
