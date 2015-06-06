package Joueurs.IA;

import java.awt.Point;

import Joueurs.Joueurs;
import Model.Model;
import View.VueJeu;

public class IALevel4 extends IA {
	
	private int[][] tabJoueurAdverse;
	
	public IALevel4(int[][] tabJoueurAdverse) {
		super();
		this.tabJoueurAdverse = tabJoueurAdverse;
		levelIA = 4;
	}
	
    @Override
    public void jouerCoup(Joueurs joueurAdverse, int x, int y) {

        nombreCoups = Model.getNombresCoupsDepart();

        Point point = aquisitionCoordonnees();
        
        if (joueurAdverse.estTouche(point.x, point.y)) {
            joueurAdverse.updateTabJoueurTouche(point.x, point.y);
            VueJeu.getChatTexte().append("Système IA: Coup réussi\n");
        } else {
            nombreCoups--;
            VueJeu.getChatTexte().append("Système IA: Coup raté\n");
        }

    }
 
    @Override
    public Point aquisitionCoordonnees(){
 
        Point retour = new Point(0, 0);
 
        
        for(int i = 0; i < Model.getTaillePlateau(); i++){
            for(int j = 0; j < Model.getTaillePlateau(); j++){
                if(tabJoueurAdverse[i][j] > 0){
                    retour.x = i;
                    retour.y = j;
                }
            }
        }
 
        return retour;
    }
}
