package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
		if (model.placementBateauIsLock())
			return;
		
		vueJeu.repaintFantomeBateau();
		
		JButton btn = (JButton) event.getSource();
		
		int x = Character.getNumericValue(btn.getActionCommand().charAt(0));
		int y = Character.getNumericValue(btn.getActionCommand().charAt(1));
		
		int idBateau = Model.getJoueur(1).getTabJoueur()[x][y];
		
		// Clique droit pour enlever les bateaux
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
            else { // Changer sens bateau
            	if (sensBateau == Bateaux.HORIZONTAL)
            		sensBateau = Bateaux.VERTICAL;
            	else
            		sensBateau = Bateaux.HORIZONTAL;
            }
        }
		else { // Clic gauche pour mettre les bateaux
           		
    		if (bateau == null)
    			return;
    		
    		int[] cible = new int[4];
    		
    		int count = 1;
    		
    		idBateau = Character.getNumericValue(bateau.getActionCommand().charAt(1));
    		int nbCase = Character.getNumericValue(bateau.getActionCommand().charAt(0));
    		
    		cible[0] = x;
    		cible[1] = y;
    		cible[2] = idBateau;
    		cible[3] = sensBateau;
    		
    		if (Joueurs.validationCoup(cible, Model.getJoueur(1).getTabJoueur())) {

    			if (sensBateau == Bateaux.HORIZONTAL) {
    			
        			for (int a = x ; a < x + nbCase ; a++) {
        				VueJeu.getPteGrilleJeu(a, y).setIcon(Bateaux.imageBateau(idBateau, Bateaux.HORIZONTAL, count, Bateaux.SANSETAT));
        				VueJeu.getPteGrilleJeu(a, y).setActionCommand(VueJeu.getPteGrilleJeu(a, y).getActionCommand() + count + sensBateau);
        				Model.getJoueur(1).setValeurTabJoueur(a, y, idBateau);
        				count++;
        			}
        			bateau.setEnabled(false);
    			}
    			else {
    				for (int a = y ; a < y + nbCase ; a++) {
        				VueJeu.getPteGrilleJeu(x, a).setIcon(Bateaux.imageBateau(idBateau, Bateaux.VERTICAL, count, Bateaux.SANSETAT));
        				VueJeu.getPteGrilleJeu(x, a).setActionCommand(VueJeu.getPteGrilleJeu(x, a).getActionCommand() + count + sensBateau);
        				Model.getJoueur(1).setValeurTabJoueur(x, a, idBateau);
        				count++;
        			}
    				bateau.setEnabled(false);
    			}
    		}
    		
    		bateau = null;
    		
    		return;
    		
    	}
	}
	
	// Placement fantome bateaux
	@Override
	public void mouseEntered(MouseEvent event){
		
		if (model.placementBateauIsLock())
			return;
		
		if (bateau == null)
			return;
		
		vueJeu.repaintFantomeBateau();

		JButton btn = (JButton) event.getSource();
		
		int x = Character.getNumericValue(btn.getActionCommand().charAt(0));;
		int y = Character.getNumericValue(btn.getActionCommand().charAt(1));
		
		int nbCase = Character.getNumericValue(bateau.getActionCommand().charAt(0));
		int idBateau = Character.getNumericValue(bateau.getActionCommand().charAt(1));
		
		int count = 1;
		
		if (sensBateau == Bateaux.HORIZONTAL) {
			
			for (int a = x ; a < x + nbCase ; a++) {
				if (a < Model.getTaillePlateau())
					if (Model.getJoueur(1).getTabJoueur()[a][y] == 0)
						VueJeu.getPteGrilleJeu(a, y).setIcon(Bateaux.imageBateau(idBateau, Bateaux.HORIZONTAL, count, Bateaux.FANTOME));
				count++;
			}
		}
		else {
			for (int a = y ; a < y + nbCase ; a++) {
				if (a < Model.getTaillePlateau())
					if (Model.getJoueur(1).getTabJoueur()[x][a] == 0)
						VueJeu.getPteGrilleJeu(x, a).setIcon(Bateaux.imageBateau(idBateau, Bateaux.VERTICAL, count, Bateaux.FANTOME));
				count++;
			}
		}
    }
	
	@Override
	public void actionPerformed(ActionEvent source) {

		Object sources = source.getSource();
		
		// Initialise le bateau cliqué
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
		
		// Fonction pour jouer quand n clique sur la grille adverse
		for (int i = 0; i < Model.getTaillePlateau(); i++){
            for (int j = 0; j < Model.getTaillePlateau(); j++){
            	
            	// Regarde si le JButton est un bouton de la grille adverse
            	if (sources == VueJeu.getGrilleJeu(i, j)){
            		
            		if (!vueJeu.tousLesBateauxSontPlace()) {
            			VueJeu.getChatTexte().append("Vous devez placer tous vos bateaux avant de pouvoir jouer !\n");
            			return;
            		}
            		
    				Joueurs verif = model.partieEstFini();
    				
    	    		if (verif == null) {
    	    			Model.getJoueur1().setNomJoueur(vueJeu.getChatNomJoueur().getText());
    	    			model.jouer(source); 
    	    		}
    				
    				vueJeu.initGrilleTexte();
    				
    				return;
        	    }
            }
        }
		
		
		// Menu
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
    
