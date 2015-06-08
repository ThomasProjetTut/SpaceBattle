package Control;

import Model.Model;
import View.VueJeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ControlChat implements KeyListener {

    private VueJeu vueJeu;

    public ControlChat(VueJeu vueJeu) {
        this.vueJeu = vueJeu;
        vueJeu.setChatControler(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 10) {
            if (!vueJeu.getChatLigne().getText().isEmpty() && e.getSource() == vueJeu.getChatLigne()) {
            	Model.getJoueur1().setNomJoueur(vueJeu.getChatNomJoueur().getText());
            	VueJeu.getChatTexte().append(Model.getJoueur1().getNomJoueur() + " : " + vueJeu.getChatLigne().getText() + "\n");
            	vueJeu.getChatLigne().setText("");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
