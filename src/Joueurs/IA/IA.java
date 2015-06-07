package Joueurs.IA;

import java.awt.Point;
import java.util.Random;

import Bateaux.Bateaux;
import Joueurs.Joueurs;
import Model.Model;

public abstract class IA extends Joueurs {


    //BOOOUUUUUH !
	@Override
	public boolean coupEstDisponible() {
		return nombreCoups > 0;
	}
	
	public abstract Point aquisitionCoordonnees();
	
	@Override
    public void mettreLesBateaux() {
		placementIa(tabJoueur);
    }
    
    @Override
	public void initTableauxZero() {

		for (int i = 0 ; i < Model.getTaillePlateau() ; i++) {
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++) {
 	    		tabJoueur[i][j] = Model.PAS_DE_BATEAU;
 	    	}
    	 }
    
	}
    
    public void placementBateaux(int[] cible, int[][] tabJoueur) {
    	
		switch (cible[3]) {
	        case Bateaux.HORIZONTAL :
	            for(int i = cible[0]; i < cible[0] + getTaille(cible[2]); i++){
	            	tabJoueur[i][cible[1]] = cible[2];
	
	            }
	            break;
	
	        case Bateaux.VERTICAL :
	            for(int j = cible[1]; j < cible[1] + getTaille(cible[2]); j++){
	            	tabJoueur[cible[0]][j] = cible[2];
	            }
	            break;
		}
    }
    
    public void placementIa(int[][] tabJoueur) {
        
        int[] cible;

        for(int i = Bateaux.CONTRETORPILLEUR ; i <= Bateaux.PORTEAVIONS; i++){

            do {
                cible = generationCoordonnee(i);
            } while (!validationCoup(cible, tabJoueur));

            placementBateaux(cible, tabJoueur);

        }
    	
    }
	
	public int[] generationCoordonnee(int idBateau){
		
        Random random = new Random();

        int[] retour = new int[4];
        int taille, x, y, direction;

        x = 0;
        y = 0;
        taille = getTaille(idBateau);

        direction = random.nextInt(2);
        //0 = bas; 1 = droite

        switch (direction){
            case Bateaux.HORIZONTAL :
                x = random.nextInt(Model.getTaillePlateau() - taille);
                y = random.nextInt(Model.getTaillePlateau());
                break;
            case Bateaux.VERTICAL :
                x = random.nextInt(Model.getTaillePlateau());
                y = random.nextInt(Model.getTaillePlateau() - taille);
                break;
        }

        retour[0] = x;
        retour[1] = y;
        retour[2] = idBateau;
        retour[3] = direction;

        return retour;

    }
}
