package Control;

import View.VueJeu;
import Model.Model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ControlChat implements KeyListener {

    private Model model;
    private VueJeu vueJeu;

    public ControlChat(Model model, VueJeu vueJeu) {
        this.model = model;
        this.vueJeu = vueJeu;
        vueJeu.setChatControler(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 10) {
            if (!vueJeu.getChatLigne().getText().isEmpty() && e.getSource() == vueJeu.getChatLigne()) {
            	VueJeu.getChatTexte().append(vueJeu.getChatNomJoueur().getText() + " : " + vueJeu.getChatLigne().getText() + "\n");
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
