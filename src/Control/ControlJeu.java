package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Joueurs.Joueurs;
import Model.Model;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;

public class ControlJeu implements ActionListener {

	private Model model;
	private VueJeu vueJeu;
	private VueMenu vueMenu;
	private VueParametre vueParametre;

	public ControlJeu(Model model, VueJeu vueJeu, VueMenu vueMenu, VueParametre vueParametre) {
		this.model = model;
		this.vueJeu = vueJeu;
		this.vueMenu = vueMenu;
		this.vueParametre = vueParametre;
		vueJeu.setButtonControler(this);
		vueJeu.setMenuControler(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent source) {

		Object sources = source.getSource();
		
		for (int i = 0; i < Model.getTaillePlateau(); i++){
            for (int j = 0; j < Model.getTaillePlateau(); j++){
            	
            	if (sources == VueJeu.getGrilleJeu(i, j)){
            		
        			JButton btn = (JButton) source.getSource();
        			
        			if (btn.getActionCommand().length() <= 2) {
        				
        			
        				Joueurs verif = model.partieEstFini();
        	    		
        	    		if (verif == null)
							model.getJoueur1().setNomJoueur(vueJeu.getChatNomJoueur().getText());
        	    			model.jouer(source);
        				
        				vueJeu.initGrilleTexte();
        				
        				return;
        			}
        			
        	    }
            }
        }
		
		if (sources == vueJeu.getNouvellePartie()) {
				vueParametre.setVisible(true);
				model.setIsGameActive(false);
	    }
	    else if (sources == vueJeu.getAide()){
	    	System.out.println("aide");
	    }

		else if (sources == vueJeu.getaPropos()){
			vueJeu.aProposBox();
		}

	    else if (sources == vueJeu.getQuitter()) {
	    	vueJeu.setVisible(false);
	    	vueMenu.setVisible(true);
	    }	
		
	}
}
    
