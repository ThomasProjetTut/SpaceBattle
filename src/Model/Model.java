package Model;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import multijoueur.ClientTCP;
import multijoueur.ServeurTCP;
import Bateaux.Bateaux;
import Joueurs.Joueurs;
import Joueurs.Humain.Humain;
import Joueurs.IA.IALevel1;
import Joueurs.IA.IALevel2;
import Joueurs.IA.IALevel3;
import Joueurs.IA.IALevel4;
import View.VueConnexion;
import View.VueJeu;

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

	private boolean tourJoueurEstFini;
	
	private boolean placementMultiEstFini;
	
	private static boolean jeuEstEnMulti;
	
	// 0 = joueur / 1= level 1 / 2 = level 2 etc
	private int levelAI = 1;
	
	private boolean placementBateauEstLock;
	
	public Model() {
		Bateaux.initTabBateaux();
		Bateaux.initImagesBateaux();
		Bateaux.initMapTouche();
		
		placementBateauEstLock = false;
		placementMultiEstFini = false;
		jeuEstEnMulti = false;
	}
	
	public void initJeu() {

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
			case Joueurs.IA_LEVEL_2:
				joueur2 = new IALevel2();
				break;
			case Joueurs.IA_LEVEL_3:
				joueur2 = new IALevel3();
				break;
			case Joueurs.IA_LEVEL_4:
				joueur2 = new IALevel4();
				break;
				
		}
	}
	
	public int[][] convertStringToTab(String msg) {
				
		int[][] tab = new int[taillePlateau][taillePlateau];
		
		int count = 0;
		
		for(int i = 0; i < taillePlateau; i++) {
			for(int j = 0; j < taillePlateau; j++) {
					tab[i][j] = Character.getNumericValue(msg.charAt(count));
					count++;
				}
		}
			          
		return tab;
	}
	
	public String convertTabToString() {
		
		String msg = "";
		
		for(int i = 0; i < taillePlateau; i++)
            for(int j = 0; j < taillePlateau; j++)
                msg = msg + joueur1.getValeurTabJoueur(i, j);
                    
		
		return msg;
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
		
		if (verifGrillesJoueursEstFini(joueur1)) {
			Joueurs.paintTousLesBateaux();
			return joueur2; 
			}
		else if (verifGrillesJoueursEstFini(joueur2)) {
			Joueurs.paintTousLesBateaux();
			return joueur1;
		 }
		
		if (joueur1.getVieJoueur() <= 0){
			Joueurs.paintTousLesBateaux();
			return joueur2;
		}
		else if	(joueur2.getVieJoueur() <= 0) {
			Joueurs.paintTousLesBateaux();
			return joueur1;
		}
		
		return null;
	}

	public void updateTabToucheMulti(int x, int y, int valeur) {
		
		if (valeur == 1) {
			joueur1.updateTabJoueurTouche(x, y);
            joueur1.updateIconGrilleJoueurTouche(x, y, joueur1, true);
            
            
            
            VueJeu.getChatTexte().append(Model.getJoueur(2).getNomJoueur()+" : Coup réussi\n");
            
            Joueurs verif = partieEstFini();

			if (verif != null) {
				javax.swing.JOptionPane.showMessageDialog(null, verif.getNomJoueur()+" a gagné la partie !");
			}
        
		}
		else {
			joueur1.updateIconGrilleJoueurTouche(x, y, joueur1, false);
			VueJeu.getChatTexte().append(Model.getJoueur(2).getNomJoueur()+" : Coup raté\n");
		}
		
	}
	
	public void jouer(ActionEvent source) {
		
		placementBateauEstLock = true;
		
		JButton btn = (JButton) source.getSource();
		
		int x = Character.getNumericValue(btn.getActionCommand().charAt(0));
		int y = Character.getNumericValue(btn.getActionCommand().charAt(1));

		System.out.println("Coordonnées : "+x+" | "+y+"\n");
		
		Joueurs verif;
		
		// Vérifie si le joueur 1 peut jouer
    	if (joueur1.coupEstDisponible()) {
    		
    		joueur1.jouerCoup(joueur2, x, y);
    		
    		verif = partieEstFini();
    		
    		// Vérifie si le joueur a encore des coups à jouer et si la partie est finis
    		if (joueur1.coupEstDisponible() && verif == null) {
    			return; 
    		}
    		else {
    			if (verif != null) {
    				javax.swing.JOptionPane.showMessageDialog(null,verif.getNomJoueur()+" a gagné la partie !");
    				return;
    			}
    		}
    	}
    	
    	joueur1.reiniNombresCoups();
    	
    	if (joueur2.getTypeIdJoueurs() == Joueurs.HUMAIN) {
			
			tourJoueurEstFini = true;
			
			if (VueConnexion.isHost())
        		ServeurTCP.getOutPut().println("S");
        	else
        		ClientTCP.getOutPut().println("S");
			
			return;
		}

    	// Si le bot est level 4, il ne faut pas qu'il est plusieurs coups à jouer
    	if (joueur2.getTypeIdJoueurs() != 4) {
    		
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

	public boolean placementBateauIsLock() {
		return placementBateauEstLock;
	}

	public void setPlacementBateauEstLock(boolean placementBateauEstLock) {
		this.placementBateauEstLock = placementBateauEstLock;
	}

	public boolean placementMultiIsFini() {
		return placementMultiEstFini;
	}

	public void setPlacementMultiEstFini(boolean placementMultiEstFini) {
		this.placementMultiEstFini = placementMultiEstFini;
	}

	public boolean tourJoueurIsFini() {
		return tourJoueurEstFini;
	}

	public void setTourJoueurEstFini(boolean tourJoueur1EstFini) {
		this.tourJoueurEstFini = tourJoueur1EstFini;
	}

	public static boolean jeuIsEnMulti() {
		return jeuEstEnMulti;
	}

	public void setJeuEstEnMulti(boolean jeuEstEnMulti) {
		Model.jeuEstEnMulti = jeuEstEnMulti;
	}
}
