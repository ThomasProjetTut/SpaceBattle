package Joueurs;

import Bateaux.Bateaux;
import Model.Model;

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

	public static int getTaille(int idBateau){

		switch (idBateau) {

			case 1:
				return Bateaux.getTabBateaux().get(Bateaux.CONTRETORPILLEUR).getNombreCases();
			case 2:
				return Bateaux.getTabBateaux().get(Bateaux.SOUSMARIN).getNombreCases();
			case 3:
				return Bateaux.getTabBateaux().get(Bateaux.TORPILLEUR).getNombreCases();
			case 4:
				return Bateaux.getTabBateaux().get(Bateaux.CROISEUR).getNombreCases();
			case 5:
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
