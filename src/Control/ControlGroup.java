package Control;

import multijoueur.ClientTCP;
import multijoueur.ServeurTCP;
import multijoueur.VueConnexion;
import Model.Model;
import Sounds.MusicPlayer;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;


public class ControlGroup {

    private VueJeu vueJeu;
    private VueMenu vueMenu;
	private VueParametre vueParametre;
	private MusicPlayer musicPlayer;
	private VueConnexion vueConnexion;

	public ControlGroup(Model model) {

		vueMenu = new VueMenu();
		vueJeu = new VueJeu(model);
		vueParametre = new VueParametre(model);

		musicPlayer = new MusicPlayer();

		vueConnexion = new VueConnexion(model, vueJeu, vueMenu);
		
		new ControlJeu(model, vueJeu, vueMenu, vueParametre);
		new ControlMenu(vueMenu, vueParametre, vueConnexion);
		new ControlChat(vueJeu);
		new ControlParametre(model, vueJeu, vueMenu, vueParametre);
		
		vueMenu.setVisible(true);
		musicPlayer.start();
	}
}
