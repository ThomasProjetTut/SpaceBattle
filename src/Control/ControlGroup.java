package Control;

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

	public ControlGroup(Model model) {

		vueMenu = new VueMenu();
		vueJeu = new VueJeu(model);
		vueParametre = new VueParametre(model);

		musicPlayer = new MusicPlayer();

		new ControlJeu(model, vueJeu, vueMenu, vueParametre);
		new ControlMenu(vueMenu, vueParametre);
		new ControlChat(vueJeu);
		new ControlParametre(model, vueJeu, vueMenu, vueParametre);
		
		vueMenu.setVisible(true);
		musicPlayer.start();
	}
}
