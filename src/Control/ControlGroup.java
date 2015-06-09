package Control;

import Model.Model;
import Sounds.MusicPlayer;
import View.*;


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
		vueAPropos = new VueAPropos((model));

		musicPlayer = new MusicPlayer();

		vueConnexion = new VueConnexion(model, vueJeu, vueMenu);
		
		new ControlJeu(model, vueJeu, vueMenu, vueParametre, vueAPropos);
		new ControlMenu(vueMenu, vueParametre, vueConnexion);
		new ControlChat(vueJeu);
		new ControlParametre(model, vueJeu, vueMenu, vueParametre);
		new ControlAPropos(vueAPropos);
		
		vueMenu.setVisible(true);
		musicPlayer.start();
	}
}
