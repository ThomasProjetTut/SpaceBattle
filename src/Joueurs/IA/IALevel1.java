package Joueurs.IA;

import java.awt.Point;
import java.util.Random;

import Joueurs.Joueurs;
import Model.Model;
import View.VueJeu;

public class IALevel1 extends IA {
	
	public IALevel1() {
		nomJoueur = "IA_Level_1";
		typeId = IA_LEVEL_1;
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
        Random random = new Random();
 
        Point retour = new Point();

        retour.x = random.nextInt(Model.getTaillePlateau());
        retour.y = random.nextInt(Model.getTaillePlateau());
 
        return retour;
    }
}
