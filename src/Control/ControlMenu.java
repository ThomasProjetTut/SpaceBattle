package Control;

import multijoueur.Seconnecter;
import multijoueur.TestConnection;
import View.VueJeu;
import View.VueMenu;
import Model.Model;
import View.VueParametre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import multijoueur.Seconnecter;

public class ControlMenu implements ActionListener {

	private VueMenu vueMenu;
	private VueParametre vueParametre;
	private TestConnection testconnexion;
	
	public ControlMenu(VueMenu vueMenu, VueParametre vueParametre, TestConnection testconnexion) {
		this.vueMenu = vueMenu;
		this.vueParametre = vueParametre;
		vueMenu.setMenuControler(this);
		this.testconnexion = testconnexion;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//test
		Object source = e.getSource();

		if (source == vueMenu.getJouerS()) {
			vueParametre.setVisible(true);
		}

		else if (source == vueMenu.getJouerM()) {

			vueParametre.setVisible(false);
			testconnexion.displayTCP(true);
		}
		else if (source == vueMenu.getInstruction()){
			vueParametre.setVisible(false);
			vueMenu.setVisible(false);
			vueMenu.creerFenetreInstruction();
			vueMenu.setVisible(true);
		}

		else if (source == vueMenu.getScore()){
			vueParametre.setVisible(false);
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

