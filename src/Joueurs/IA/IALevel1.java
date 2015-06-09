package Joueurs.IA;

import java.awt.Point;
import java.util.Random;

import Joueurs.Joueurs;
import Model.Model;
import View.VueJeu;

public class IALevel1 extends IA {
	
	public IALevel1() {
        super();
		nomJoueur = "Novice";
		typeId = IA_LEVEL_1;
	}

	int[][] tabCaseDejaTouche = new int[Model.getTaillePlateau()][Model.getTaillePlateau()];
	
	public void initTableauxZero() {

		for (int i = 0 ; i < Model.getTaillePlateau() ; i++) {
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++) {
 	    		tabJoueur[i][j] = Model.PAS_DE_BATEAU;
 	    	}
    	 }
		
		for (int i = 0 ; i < Model.getTaillePlateau() ; i++) {
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++) {
 	    		tabCaseDejaTouche[i][j] = Model.PAS_DE_BATEAU;
 	    	}
    	 }
    
	}
	
    @Override
    public void jouerCoup(Joueurs joueurAdverse, int x, int y) {

        nombreCoups = Model.getNombresCoupsDepart();

        Point point = aquisitionCoordonnees();
        
        if (joueurAdverse.estTouche(point.x, point.y)) {
            joueurAdverse.updateTabJoueurTouche(point.x, point.y);
            updateIconGrilleJoueurTouche(point.x, point.y, joueurAdverse, true);
            VueJeu.getChatTexte().append(nomJoueur+" : Coup réussi\n");
        } else {
            nombreCoups--;
            updateIconGrilleJoueurTouche(point.x, point.y, joueurAdverse, false);
            VueJeu.getChatTexte().append(nomJoueur+" : Coup raté\n");
        }

    }

    public Point aquisitionCoordonnees(){
        Random random = new Random();

        Point retour = new Point();

        retour.x = random.nextInt(Model.getTaillePlateau());
        retour.y = random.nextInt(Model.getTaillePlateau());

        while (tabCaseDejaTouche[retour.x][retour.y] != 0) {
        	retour.x = random.nextInt(Model.getTaillePlateau());
            retour.y = random.nextInt(Model.getTaillePlateau());
        }

        tabCaseDejaTouche[retour.x][retour.y] = 1;

        return retour;
    }

}
