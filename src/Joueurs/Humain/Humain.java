package Joueurs.Humain;

import multijoueur.ClientTCP;
import multijoueur.ServeurTCP;
import Joueurs.Joueurs;
import Model.Model;
import View.VueConnexion;
import View.VueJeu;

public class Humain extends Joueurs {
	
	public Humain() {
		super();
		nomJoueur = "HUMAIN";
		typeId = HUMAIN;
		
	}
	
	@Override
	public boolean coupEstDisponible() {
		return nombreCoups > 0;
	}
	
	
	@Override
    public void mettreLesBateaux() {
    }

    @Override
    public void jouerCoup(Joueurs joueurAdverse, int x, int y) {

        nombreCoups = Model.getNombresCoupsDepart();

        if (joueurAdverse.estTouche(x, y)) {
            joueurAdverse.updateTabJoueurTouche(x, y);
            updateIconGrilleJoueurTouche(x, y, joueurAdverse, true);
            VueJeu.getChatTexte().append(nomJoueur+" : Coup réussi\n");
            
            if (Model.getJoueur(2).getTypeIdJoueurs() == Joueurs.HUMAIN) {
	            if (VueConnexion.isHost())
	        		ServeurTCP.getOutPut().println("I" + x + y + "1"+Model.getJoueur1().getNomJoueur());
	        	else
	        		ClientTCP.getOutPut().println("I" + x + y + "1"+Model.getJoueur1().getNomJoueur());
            }
            
        } else {
        	
        	if (Model.getJoueur(2).getTypeIdJoueurs() == Joueurs.HUMAIN) {
	            if (VueConnexion.isHost())
	        		ServeurTCP.getOutPut().println("I" + x + y + "0"+Model.getJoueur1().getNomJoueur());
	        	else
	        		ClientTCP.getOutPut().println("I" + x + y + "0"+Model.getJoueur1().getNomJoueur());
            }
        	
            nombreCoups--;
            updateIconGrilleJoueurTouche(x, y, joueurAdverse, false);
            VueJeu.getChatTexte().append(nomJoueur+" : Coup raté\n");
        }
        
    }

	@Override
	public void initTableauxZero() {
		
		for (int i = 0 ; i < Model.getTaillePlateau() ; i++) {
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++) {
 	    		tabJoueur[i][j] = Model.PAS_DE_BATEAU;
 	    	}
    	 }
	}

	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}

}
