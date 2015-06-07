package Control;

import Model.Model;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;


public class ControlGroup {

    private VueJeu vueJeu;
    private VueMenu vueMenu;
	private VueParametre vueParametre;
    
    @SuppressWarnings("unused")
	private ControlChat controlChat;
    
    @SuppressWarnings("unused")
	private ControlJeu controlJeu;
    
    @SuppressWarnings("unused")
	private ControlMenu controlMenu;

	@SuppressWarnings("unused")
	private ControlParametre controlParametre;
	
	public ControlGroup(Model model) {

		vueMenu = new VueMenu(model);
		vueJeu = new VueJeu(model);
		vueParametre = new VueParametre(model);

		controlJeu = new ControlJeu(model, vueJeu, vueMenu, vueParametre);
		controlMenu = new ControlMenu(model, vueJeu, vueMenu, vueParametre);
		controlChat = new ControlChat(model, vueJeu);
		controlParametre  = new ControlParametre(model, vueJeu, vueMenu, vueParametre);
		
		vueMenu.setVisible(true);
	}
}
