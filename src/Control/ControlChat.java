package Control;

import Model.Model;
import View.VueJeu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import multijoueur.ClientTCP;
import multijoueur.ServeurTCP;
import multijoueur.VueConnexion;


public class ControlChat extends KeyAdapter {

    private VueJeu vueJeu;

    public ControlChat(VueJeu vueJeu) {
        this.vueJeu = vueJeu;
        vueJeu.setChatControler(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 10) {
        	if (vueJeu.getChatNomJoueur().getText().isEmpty())
        		VueJeu.getChatTexte().append("Vous devez mettre un nom avant de parler.\n");
        	else if (!vueJeu.getChatLigne().getText().isEmpty() && e.getSource() == vueJeu.getChatLigne()) {
            	Model.getJoueur1().setNomJoueur(vueJeu.getChatNomJoueur().getText());
            	
            	if (VueConnexion.isHost())
            		ServeurTCP.getOutPut().println("C"+Model.getJoueur1().getNomJoueur() + " : " + vueJeu.getChatLigne().getText() + "\n");
            	else
            		ClientTCP.getOutPut().println("C"+Model.getJoueur1().getNomJoueur() + " : " + vueJeu.getChatLigne().getText() + "\n");
            	
            	VueJeu.getChatTexte().append(Model.getJoueur1().getNomJoueur() + " : " + vueJeu.getChatLigne().getText() + "\n");
            	vueJeu.getChatLigne().setText("");
            }
        }
    }
}
