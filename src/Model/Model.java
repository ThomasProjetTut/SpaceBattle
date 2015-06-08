package Model;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import Bateaux.Bateaux;
import Joueurs.Joueurs;
import Joueurs.Humain.Humain;
import Joueurs.IA.IALevel1;
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
	
	// 0 = joueur / 1= level 1 / 2 = level 2 etc
	private int levelAI = 1;
	
	public Model() {
		Bateaux.initTabBateaux();
		Bateaux.initImagesBateaux();
	}
	
	public void initJeu() {
		
		isGameActive = true;
		
		joueur1 = new Humain();
        
        System.out.println("Type joueur 1 : "+joueur1.getTypeIdJoueurs());

        joueur1.initTableauxZero();
        
        initJoueur2();
        
        joueur2.initTableauxZero();
        
        joueur2.mettreLesBateaux();

        System.out.println("Type joueur 2 : "+joueur2.getTypeIdJoueurs());
      
	}
	
	public void initJoueur2() {
		
		switch(levelAI)
		{
			case Joueurs.HUMAIN:
				joueur2 = new Humain();
				break;
			case Joueurs.IA_LEVEL_1:
				joueur2 = new IALevel1();
				break;
			/*case Joueurs.IA_LEVEL_2:
				joueur2 = new IALevel2();
				break;
			case Joueurs.IA_LEVEL_3:
				joueur2 = new IALevel3();
				break;*/
			case Joueurs.IA_LEVEL_4:
				joueur2 = new IALevel4(joueur1.getTabJoueur());
				break;
				
		}
	}
	
	public void setAILevel(int level) {
		levelAI = level;
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

		System.out.println("Coordonnées : "+x+" | "+y+"\n");
		
		Joueurs verif;
		
		// Vérifie si le joueur un peut jouer
    	if (joueur1.coupEstDisponible()) {
    		
    		joueur1.jouerCoup(joueur2, x, y);
    		
    		verif = partieEstFini();
    		
    		// Vérifie si le joueur a encore des coups à jouer et si la partie est finis
    		if (joueur1.coupEstDisponible() && verif == null)
    			return; 
    		else {
    			if (verif != null) {
    				javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagné la partie !");
    				return;
    			}
    		}
    	}

    	// si le joueur 2 est humain
    	if (joueur2.getTypeIdJoueurs() == 0) {
    		
    		// Mettre la fonction multijoueurs d'attente de coups
    		
    		
    		
    	}
    	// Si le bot est level 4, il ne faut pas qu'il est plusieurs coups à jouer
    	else if (joueur2.getTypeIdJoueurs() != 4) {
    		
    		while (joueur2.coupEstDisponible()) {
    			
    			joueur2.jouerCoup(joueur1, x, y);
    			
    			verif = partieEstFini();
	    		
	    		if (verif != null) {
	    			javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagné la partie !");
	    			break;
	    		}
	    		
    		}
    	}
    	else {
    		joueur2.jouerCoup(joueur1, x, y);
    		
    		verif = partieEstFini();
    		
    		if (verif != null)
    			javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagné la partie !");
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

	public static Joueurs getJoueur1() {
		return joueur1;
	}

	public static Joueurs getJoueur2() {
		return joueur2;
	}
}
