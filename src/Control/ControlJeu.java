package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import Joueurs.Joueurs;
import Model.Model;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;
import Bateaux.Bateaux;

public class ControlJeu extends MouseAdapter implements ActionListener {

	private Model model;
	private VueJeu vueJeu;
	private VueMenu vueMenu;
	private VueParametre vueParametre;
	
	private JButton bateau = null;
	private int sensBateau = Bateaux.HORIZONTAL;

	public ControlJeu(Model model, VueJeu vueJeu, VueMenu vueMenu, VueParametre vueParametre) {
		this.model = model;
		this.vueJeu = vueJeu;
		this.vueMenu = vueMenu;
		this.vueParametre = vueParametre;
		vueJeu.setButtonControler(this, this);
		vueJeu.setMenuControler(this);
	}
	
	@Override
	public void mouseReleased (MouseEvent event) {
		
		JButton btn = (JButton) event.getSource();
		
		int x = Character.getNumericValue(btn.getActionCommand().charAt(0));;
		int y = Character.getNumericValue(btn.getActionCommand().charAt(1));
		
		int idBateau = Model.getJoueur(1).getTabJoueur()[x][y];
		
		if(event.isPopupTrigger()){
            if (idBateau != 0) {
            	for (int i = 0; i < Model.getTaillePlateau(); i++){
                    for (int j = 0; j < Model.getTaillePlateau(); j++){
                    	if (Model.getJoueur(1).getTabJoueur()[i][j] == idBateau){
                    		Model.getJoueur(1).getTabJoueur()[i][j] = 0;
                    		VueJeu.getPteGrilleJeu(i, j).setIcon(null);
                    	}
                    }
            	}
            	
            	vueJeu.getBtnBateau(idBateau).setEnabled(true);
            	
            }
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent source) {

		Object sources = source.getSource();
		
		if (sources == vueJeu.getSousMarin()) {
			bateau = vueJeu.getSousMarin();
			return;
		}
		else if (sources == vueJeu.getContreTorpilleurs()) {
			bateau = vueJeu.getContreTorpilleurs();
			return;
		}
		else if (sources == vueJeu.getPorteAvion()) {
			bateau = vueJeu.getPorteAvion();
			return;
		}
		else if (sources == vueJeu.getTorpilleur()) {
			bateau = vueJeu.getTorpilleur();
			return;
		}
		else if (sources == vueJeu.getCroiseur()) {
			bateau = vueJeu.getCroiseur();
			return;
		}
				
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
            	else if (sources == VueJeu.getPteGrilleJeu(i, j)){
                       		
            		if (bateau == null)
            			return;
            		
            		int[] cible = new int[4];
            		
            		int count = 1;
            		
            		int idBateau = Character.getNumericValue(bateau.getActionCommand().charAt(1));;
            		int nbCase = Character.getNumericValue(bateau.getActionCommand().charAt(0));
            		
            		cible[0] = i;
            		cible[1] = j;
            		cible[2] = nbCase;
            		cible[3] = sensBateau;
            		
            		if (Joueurs.validationCoup(cible, Model.getJoueur(1).getTabJoueur())) {
            			
            			if (sensBateau == Bateaux.HORIZONTAL) {
            			
	            			for (int a = i ; a < i + nbCase ; a++) {
	            				VueJeu.getPteGrilleJeu(a, j).setIcon(Bateaux.imageBateau(idBateau, Bateaux.HORIZONTAL, count, Bateaux.SANSETAT));
	            				Model.getJoueur(1).setValeurTabJoueur(a, j, idBateau);
	            				count++;
	            			}
	            			bateau.setEnabled(false);
            			}
            			else {
            				for (int a = j ; a < j + nbCase ; a++) {
	            				VueJeu.getPteGrilleJeu(i, a).setIcon(Bateaux.imageBateau(idBateau, Bateaux.VERTICAL, count, Bateaux.SANSETAT));
	            				Model.getJoueur(1).setValeurTabJoueur(i, a, idBateau);
	            				count++;
	            			}
            				bateau.setEnabled(false);
            			}
            		}
            		
            		bateau = null;
            		
            		return;
            		
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
    
