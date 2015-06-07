package Model;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import Bateaux.Bateaux;
import Joueurs.Joueurs;
import Joueurs.Humain.Humain;
import Joueurs.IA.IALevel4;

public class Model {
	
	// 10 * 10 -> http://fr.wikipedia.org/wiki/Bataille_navale_%28jeu%29#Grille_de_jeu
    private static final int taillePlateau = 10;
	
    // Nombres de coups max par joueurs par tours
    private static final int NOMBRES_COUPS_DEPART = 1;
	
	// Vie total = nombre de cases de tous les bâteaux cumulés
    private static final int VIE_JOUEUR_DEPART = 17;
    
    public static final int PAS_DE_BATEAU = 0;
    
	private static Joueurs joueur1;
    private static Joueurs joueur2;
    
    private boolean isGameActive;
	private boolean isBonusActive;
	
	public void initJeu() {
		
		isGameActive = true;
		
		Bateaux.initTabBateaux();
		Bateaux.initImagesBateaux();
		
		joueur1 = new Humain();
        
        System.out.println("Type joueur 1 : "+joueur1.getTypeIdJoueurs());

        joueur1.initTableauxZero();
        joueur1.mettreLesBateaux();
        
        
        joueur2 = new IALevel4(joueur1.getTabJoueur());
        
        joueur2.initTableauxZero();
        joueur2.mettreLesBateaux();
        
        System.out.println("Type joueur 2 : "+joueur2.getTypeIdJoueurs());
      
	}
	
	public boolean verifGrillesJoueursEstFini(Joueurs joueurs) {
				
		for(int i = 0; i < taillePlateau; i++)
            for(int j = 0; j < taillePlateau; j++)
                if(joueurs.getTabJoueur()[i][j] > 0)
                    return false;
		
		return true;
	}
	
	public Joueurs partieEstFini() {
		
		if (verifGrillesJoueursEstFini(joueur1))
			return joueur2;
		else if (verifGrillesJoueursEstFini(joueur2))
			return joueur1;
		
		if (joueur1.getVieJoueur() <= 0)
			return joueur2;
		else if	(joueur2.getVieJoueur() <= 0)
			return joueur1;
		
		return null;
	}
	
	public boolean IsGameActive(){
        return isGameActive;
    }
	
	public void setIsGameActive(boolean state){
        this.isGameActive = state;
    }
	
	public void jouer(ActionEvent source) {
		
		JButton btn = (JButton) source.getSource();
		
		int x = Character.getNumericValue(btn.getActionCommand().charAt(0));
		int y = Character.getNumericValue(btn.getActionCommand().charAt(1));

		System.out.println("Coordonn�es : "+x+" | "+y+"\n");
		
		Joueurs verif;
		
    	if (joueur1.coupEstDisponible()) {
    		joueur1.jouerCoup(joueur2, x, y);
    		
    		verif = partieEstFini();
    		
    		if (joueur1.coupEstDisponible() && verif == null)
    			return;
    		else {
    			if (verif != null) {
    				javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagn� la partie !");
    				return;
    			}
    		}
    	}

    	if (joueur2.getTypeIdJoueurs() == 1 && joueur2.getLevelIA() != 4) {
    		while (joueur2.coupEstDisponible()) {
    			joueur2.jouerCoup(joueur1, x, y);
    			
    			verif = partieEstFini();
	    		
	    		if (verif != null) {
	    			javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagn� la partie !");
	    			break;
	    		}
	    		
    		}
    	}
    	else {
    		joueur2.jouerCoup(joueur1, x, y);
    		
    		verif = partieEstFini();
    		
    		if (verif != null)
    			javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagn� la partie !");
    	}
    		
    	joueur1.reiniNombresCoups();
    	joueur2.reiniNombresCoups();
    	
		
    	
    	
		
	}
	
	/*###############################################################
	  #																#
	  #		------------------------------------------------		#
	  #		|												|		#
	  #		|					GETTERS						|		#
	  #		|												|		#
	  #		------------------------------------------------		#
	  #																#
	  ###############################################################*/
	
	public static Joueurs getJoueur(int id) {
		
		if (id == 1)
			return joueur1;

		return joueur2;
	}

	public void setIsBonusActive(boolean state){
		this.isBonusActive = state;
	}

	public boolean getIsBonusActive(){
		return isBonusActive;
	}
	
	public static int getTaillePlateau() {
		return taillePlateau;
	}

	public static int getNombresCoupsDepart() {
		return NOMBRES_COUPS_DEPART;
	}

	public static int getVieJoueurDepart() {
		return VIE_JOUEUR_DEPART;
	}
}
