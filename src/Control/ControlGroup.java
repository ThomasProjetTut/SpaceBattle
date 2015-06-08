package Control;

import multijoueur.TestConnection;
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
	private TestConnection testconnection;

	public ControlGroup(Model model) {

		vueMenu = new VueMenu();
		vueJeu = new VueJeu(model);
		vueParametre = new VueParametre(model);

		musicPlayer = new MusicPlayer();

		testconnection = new TestConnection(model, vueJeu, vueMenu, vueParametre);
		
		new ControlJeu(model, vueJeu, vueMenu, vueParametre);
		new ControlMenu(vueMenu, vueParametre, testconnection);
		new ControlChat(vueJeu);
		new ControlParametre(model, vueJeu, vueMenu, vueParametre);
		
		vueMenu.setVisible(true);
		musicPlayer.start();
		testconnection.start();
	}
}
