package Joueurs.Humain;

import Joueurs.Joueurs;
import Model.Model;
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
        } else {
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
