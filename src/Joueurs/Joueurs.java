package Joueurs;

import javax.swing.JButton;

import Bateaux.Bateaux;
import Model.Model;
import View.VueJeu;

public abstract class Joueurs {
	
	public static final int HUMAIN = 0;
	public static final int IA_LEVEL_1 = 1;
	public static final int IA_LEVEL_2 = 2;
	public static final int IA_LEVEL_3 = 3;
	public static final int IA_LEVEL_4 = 4;

	//Nom du joueur
	protected String nomJoueur;

	// Nombres de coups max par joueurs par tours
	protected int nombreCoups;

	// Vie total = nombre de cases de tous les bâteaux cumulés
	protected int vieJoueur;

	//ID du joueur | 0 = humain | 1 = IA
	protected int typeId;

	protected int[][] tabJoueur;

	public Joueurs(){
		nombreCoups = Model.getNombresCoupsDepart();
		vieJoueur = Model.getVieJoueurDepart();
		tabJoueur= new int[Model.getTaillePlateau()][Model.getTaillePlateau()];
		nomJoueur = "";
	}


	public abstract void initTableauxZero();

	public int getTypeIdJoueurs() {
		return typeId;
	}

	public int getVieJoueur() {
		return vieJoueur;
	}

	public int setVieJoueur(int valeur) {
		vieJoueur = valeur;
		return vieJoueur ;
	}

	public void reiniNombresCoups() {
		nombreCoups = Model.getNombresCoupsDepart();
	}

	public abstract boolean coupEstDisponible();

	// Fonction abstraite pour placer les bateaux dans les tableaux selon le joueur (IA ou Humain)
	public abstract void mettreLesBateaux();

	// Met à jour le tableau si le joueur est touché
	public void updateTabJoueurTouche(int x, int y) {
		this.setVieJoueur(this.getVieJoueur() - 1);
		this.setValeurTabJoueur(x, y, -tabJoueur[x][y]);
	}		
	
	// Met à jour les icons si le joueur est touché
	public void updateIconGrilleJoueurTouche(int x, int y, Joueurs joueurAdverse, boolean estTouche) {
		
		if (joueurAdverse.typeId == HUMAIN && joueurAdverse == Model.getJoueur1()) {
			
			JButton btn = VueJeu.getPteGrilleJeu(x, y);	
			
			if (!estTouche) {
				btn.setIcon(Bateaux.getMapStringIconTouche().get("Plouf"));
				return;
			}
			
			int idBateau = -joueurAdverse.getValeurTabJoueur(x, y);
			int partie = Character.getNumericValue(btn.getActionCommand().charAt(2));
			int sens = Character.getNumericValue(btn.getActionCommand().charAt(3));
			
			int count = 0;
			
			btn.setIcon(Bateaux.imageBateau(idBateau, sens, partie, Bateaux.TOUCHE));
			
			for(int i = 0; i < Model.getTaillePlateau(); i++) {
	            for(int j = 0; j < Model.getTaillePlateau(); j++) {
	            	if (joueurAdverse.getValeurTabJoueur(i, j) == -idBateau) {
	            		count++;
	            	}
	            }
			}
			
			if (count != getTaille(idBateau))
				return;
			
			count = 1;
			
			for(int i = 0; i < Model.getTaillePlateau(); i++) {
	            for(int j = 0; j < Model.getTaillePlateau(); j++) {
	            	if (joueurAdverse.getValeurTabJoueur(i, j) == -idBateau) {
	            		VueJeu.getPteGrilleJeu(i, j).setIcon(Bateaux.imageBateau(idBateau, sens, count, Bateaux.TOUCHE));
	            		count++;
	            	}
	            }
			}
		}
		else {
			JButton btn = VueJeu.getGrilleJeu(x, y);
			
			if (!estTouche) {
				btn.setIcon(Bateaux.getMapStringIconTouche().get("Plouf"));
				return;
			}
			
			btn.setIcon(Bateaux.getMapStringIconTouche().get("Touche"));
			
			int count = 0;
			
			int idBateau = -joueurAdverse.getValeurTabJoueur(x, y);
			
			int x0 = -100;
			int x1 = -100;
			
			int sens;
			
			for(int i = 0; i < Model.getTaillePlateau(); i++) {
	            for(int j = 0; j < Model.getTaillePlateau(); j++) {
	            	if (joueurAdverse.getValeurTabJoueur(i, j) == -idBateau) {
	            		if (x0 == -100) 
	            			x0 = i;
	            		else if (x1 == -100)
	            			x1 = i;
	            		count++;
	            	}
	            }
			}
			
			if (x0 == x1) 
				sens = Bateaux.VERTICAL;
			else
				sens = Bateaux.HORIZONTAL;
			
			if (count != getTaille(idBateau))
				return;
			
			count = 1;
			
			for(int i = 0; i < Model.getTaillePlateau(); i++) {
	            for(int j = 0; j < Model.getTaillePlateau(); j++) {
	            	if (joueurAdverse.getValeurTabJoueur(i, j) == -idBateau) {
	            		VueJeu.getGrilleJeu(i, j).setIcon(Bateaux.imageBateau(idBateau, sens, count, Bateaux.TOUCHE));
	            		count++;
	            	}
	            }
			}
		}
			
	}
	
	public static void paintTousLesBateaux() {

		for(int i = 0; i < Model.getTaillePlateau(); i++) {
            for(int j = 0; j < Model.getTaillePlateau(); j++) {
            	paintBateaux(i, j);
            }
		}

	}
	
	public static void paintBateaux(int x, int y) {
		
		int count = 0;
		
		int idBateau = -Model.getJoueur(2).getValeurTabJoueur(x, y);
		
		if (idBateau == 0)
			return;
		
		int x0 = -100;
		int x1 = -100;
		
		int sens;
		
		for(int i = 0; i < Model.getTaillePlateau(); i++) {
            for(int j = 0; j < Model.getTaillePlateau(); j++) {
            	if (Model.getJoueur(2).getValeurTabJoueur(i, j) == -idBateau || Model.getJoueur(2).getValeurTabJoueur(i, j) == idBateau) {
            		if (x0 == -100) 
            			x0 = i;
            		else if (x1 == -100)
            			x1 = i;
            		count++;
            	}
            }
		}
		
		if (x0 == x1) 
			sens = Bateaux.VERTICAL;
		else
			sens = Bateaux.HORIZONTAL;
		
		int taille = getTaille(idBateau);
		
		if (taille == 0)
			taille = getTaille(-idBateau);
		
		System.out.println(count +" "+taille+" "+sens+" "+idBateau);
		
		if (count != taille)
			return;
		
		count = 1;
		
		for(int i = 0; i < Model.getTaillePlateau(); i++) {
            for(int j = 0; j < Model.getTaillePlateau(); j++) {
            	if (Model.getJoueur(2).getValeurTabJoueur(i, j) == -idBateau || Model.getJoueur(2).getValeurTabJoueur(i, j) == idBateau) {
            		         		
            		if (idBateau < 0)
            			VueJeu.getGrilleJeu(i, j).setIcon(Bateaux.imageBateau(-idBateau, sens, count, Bateaux.SANSETAT));
            		else
            			VueJeu.getGrilleJeu(i, j).setIcon(Bateaux.imageBateau(idBateau, sens, count, Bateaux.TOUCHE));
            			
            		
            		
            		count++;
            	}
            }
		}
		
	}

	// Return true si un bâteau est touché par le tir
	public boolean estTouche(int x, int y) {

		return tabJoueur[x][y] > 0;

	}

	// Met la valeur dans une case de tableau du joueur
	public void setValeurTabJoueur(int x, int y, int valeur) {
		tabJoueur[x][y] = valeur;
	}

	// Obtient la valeur d'une case de tableau du joueur
	public int getValeurTabJoueur(int x, int y) {
		return tabJoueur[x][y];
	}

	// Tour du joueur
	public abstract void jouerCoup(Joueurs joueurAdverse, int x, int y);

	public int[][] getTabJoueur() {
		return tabJoueur;
	}

	public void setTabJoueur(int[][] tab) {
		this.tabJoueur = tab;
	}
	
	public static int getTaille(int idBateau){

		switch (idBateau) {

			case Bateaux.CONTRETORPILLEUR:
				return Bateaux.getTabBateaux().get(Bateaux.CONTRETORPILLEUR).getNombreCases();
			case Bateaux.SOUSMARIN:
				return Bateaux.getTabBateaux().get(Bateaux.SOUSMARIN).getNombreCases();
			case Bateaux.TORPILLEUR:
				return Bateaux.getTabBateaux().get(Bateaux.TORPILLEUR).getNombreCases();
			case Bateaux.CROISEUR:
				return Bateaux.getTabBateaux().get(Bateaux.CROISEUR).getNombreCases();
			case Bateaux.PORTEAVIONS:
				return Bateaux.getTabBateaux().get(Bateaux.PORTEAVIONS).getNombreCases();
		}

		return 0;

	}

	public static boolean validationCoup(int[] cible, int[][] tabJoueur){

		int x = cible[0];
		int y = cible[1];
		int taille = getTaille(cible[2]);
		int direction = cible[3];

		switch (direction) {
			case Bateaux.HORIZONTAL :
				for (int i = x; i < x + taille; i++) {
					if (x + taille > Model.getTaillePlateau())
						return false;
					
					if (tabJoueur[i][y] > 0)
						return false;
				}
				break;

			case Bateaux.VERTICAL :
				for (int j = y; j < y + taille; j++) {
					if (y + taille > Model.getTaillePlateau())
						return false;
					
					if (tabJoueur[x][j] > 0)
						return false;
				}
		}

		return true;
	}

	public String getNomJoueur() {
		return nomJoueur;
	}

	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}



}
