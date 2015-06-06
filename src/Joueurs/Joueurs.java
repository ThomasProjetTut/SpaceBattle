package Joueurs;

import java.util.HashMap;

import Bateaux.Bateaux;
import Model.Model;

public abstract class Joueurs {

	protected String nomJoueur;

	// Nombres de coups max par joueurs par tours
	protected int nombreCoups = Model.getNombresCoupsDepart();

	// Vie total = nombre de cases de tous les bâteaux cumulés
	protected int vieJoueur = Model.getVieJoueurDepart();

	//ID du joueur | 0 = humain | 1 = IA
	protected int typeId;

	protected int tabJoueur[][] = new int[Model.getTaillePlateau()][Model.getTaillePlateau()];

	public abstract int getLevelIA();

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

		if (tabJoueur[x][y] > 0)
			return true;
		else
			return false;

	}

	// Met la valeur dans une case de tableau du joueur
	public void setValeurTabJoueur(int x, int y, int valeur) {
		tabJoueur[x][y] = valeur;
	}

	// Obtient la valeur d'une case de tableau du joueur
	public int getValeurTabJoueur1(int x, int y) {
		return tabJoueur[x][y];
	}

	// Tour du joueur
	public abstract void jouerCoup(Joueurs joueurAdverse, int x, int y);

	public int[][] getTabJoueur() {
		return tabJoueur;
	}

	public int getTaille(int idBateau){

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

	public boolean validationCoup(int[] cible, int[][] tabJoueur){

		int x = cible[0];
		int y = cible[1];
		int taille = getTaille(cible[2]);
		int direction = cible[3];

		switch (direction) {
			case 0 :
				for (int i = x; i < x + taille; i++)
					if (tabJoueur[i][y] > 0)
						return false;
				break;

			case 1 :
				for (int j = y; j < y + taille; j++)
					if (tabJoueur[x][j] > 0)
						return false;
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
