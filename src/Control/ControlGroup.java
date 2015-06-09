package Control;

import Model.Model;
import Sounds.MusicPlayer;
import View.VueAPropos;
import View.VueConnexion;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;


public class ControlGroup {

    private VueJeu vueJeu;
    private VueMenu vueMenu;
	private VueParametre vueParametre;
	private MusicPlayer musicPlayer;
	private VueConnexion vueConnexion;
	private VueAPropos vueAPropos;
	
	public ControlGroup(Model model) {

		vueMenu = new VueMenu();
		vueJeu = new VueJeu(model);
		vueParametre = new VueParametre(model);

		musicPlayer = new MusicPlayer();

		vueConnexion = new VueConnexion(model, vueJeu, vueMenu);
		
		new ControlJeu(model, vueJeu, vueMenu, vueParametre, vueAPropos, vueConnexion);
		new ControlMenu(vueMenu, vueParametre, vueConnexion);
		new ControlChat(vueJeu);
		new ControlParametre(model, vueJeu, vueMenu, vueParametre);
		
		vueMenu.setVisible(true);
		musicPlayer.start();
	}
}
