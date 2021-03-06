package Joueurs.IA;

import java.util.ArrayList;
import java.util.Random;

import Joueurs.Joueurs;
import Model.Model;
import View.VueJeu;

public class IALevel3 extends IA {
	
	private int[][] tabJoueurAdverse;
	
	private static ArrayList<int[] > listeCiblesPotentielles = new ArrayList<>();
	private static ArrayList<int[] > listePositionCible = new ArrayList<>();
	private static ArrayList<int[] > listeCiblesFutures = new ArrayList<>();
	
	private int idBateauCible;
	
	public IALevel3() {
        super();
		nomJoueur = "Loup de mer";
		typeId = IA_LEVEL_3;
		tabJoueurAdverse = modificationTab(Model.getJoueur(1).getTabJoueur());
        tabJoueurAdverse = correctionTab(Model.getJoueur(1).getTabJoueur());
	}
	
    @Override
    public void jouerCoup(Joueurs joueurAdverse, int x, int y) {

        nombreCoups = Model.getNombresCoupsDepart();

        int[] cible;

        cible = aquisitionCoordonnees();
        
        if (joueurAdverse.estTouche(cible[0], cible[1])) {
            joueurAdverse.updateTabJoueurTouche(cible[0], cible[1]);
            updateIconGrilleJoueurTouche(cible[0], cible[1], joueurAdverse, true);
            VueJeu.getChatTexte().append(nomJoueur+" : Coup réussi\n");
        } else {
            nombreCoups--;
            updateIconGrilleJoueurTouche(cible[0], cible[1], joueurAdverse, false);
            VueJeu.getChatTexte().append(nomJoueur+" : Coup raté\n");
        }

    }
    
    public int[] aquisitionCoordonnees(){

        if(listePositionCible.size() > 0){
            return aquisitionCible();
        }else{
            if(listeCiblesPotentielles.size() > 0){
                return aquisitionCoordonneesPotentielles();
            }else{
                if(listeCiblesFutures.size() > 0){
                    return aquisitionProchaineCibles();
                }else{
                    return aquisitionCoordonneesAleatoire();
                }
            }
        }


    }

    public int[] aquisitionCible(){

        int[] cible;

        cible = listePositionCible.get(0);

        int x = cible[0];
        int y = cible[1];

        listePositionCible.remove(0);

        if(tabJoueurAdverse[x][y] != idBateauCible){
            listePositionCible.clear();
            creationListeCiblesIdentifiee();

            if(tabJoueurAdverse[x][y] > 0){
                creationListeCiblesFutures(cible);
            }
        }

        return cible;
    }

    public void creationListeCiblesIdentifiee(){
        int[] coordonneeCible;

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(tabJoueurAdverse[i][j] == idBateauCible){
                    coordonneeCible = new int[2];

                    coordonneeCible[0] = i;
                    coordonneeCible[1] = j;

                    listePositionCible.add(coordonneeCible);
                }
            }
        }


    }


    public int[] aquisitionProchaineCibles(){
        int[] prochaineCible;

        prochaineCible = listeCiblesFutures.get(0);

        listeCiblesFutures.remove(0);

        idBateauCible = tabJoueurAdverse[prochaineCible[0]][prochaineCible[1]];

        creationListeCiblesPotentielles(prochaineCible);

        return aquisitionCoordonneesPotentielles();

    }

    public int[] aquisitionCoordonneesPotentielles(){
        int[] cible;

        cible = listeCiblesPotentielles.get(0);
        listeCiblesPotentielles.remove(0);

        if(tabJoueurAdverse[cible[0]][cible[1]] == idBateauCible){
            listeCiblesPotentielles.clear();
            creationListePositionCible(cible);
        }else {
            if (tabJoueurAdverse[cible[0]][cible[1]] > 0) {
                creationListeCiblesFutures(cible);
            }
        }

        return cible;

    }

    public void creationListeCiblesFutures(int[] cible){
        listeCiblesFutures.add(cible);
    }

    public void creationListePositionCible(int[] cible){
        int[] coordonneePossible;
        int x = cible[0];
        int y = cible[1];

        if(y < 9) {
            if (tabJoueurAdverse[x][y] == tabJoueurAdverse[x][y + 1] || tabJoueurAdverse[x][y] == -tabJoueurAdverse[x][y + 1]) {
                for (int i = 0; i < getTaille(idBateauCible); i++) {
                    if (y + i < 10) {
                        if (tabJoueurAdverse[x][y + i] >= 0) {
                            coordonneePossible = new int[2];

                            coordonneePossible[0] = x;
                            coordonneePossible[1] = y + i;

                            if (!listePositionCible.contains(coordonneePossible) && coordonneePossible[1] != cible[1]) {
                                listePositionCible.add(coordonneePossible);
                            }
                        }
                    }
                }
            }
        }


        if(y >= 1) {
            if (tabJoueurAdverse[x][y] == tabJoueurAdverse[x][y - 1] || tabJoueurAdverse[x][y] == -tabJoueurAdverse[x][y - 1] ) {
                for (int i = 0; i < getTaille(idBateauCible); i++) {
                    if (y - i >= 0) {
                        if (tabJoueurAdverse[x][y - i] >= 0) {
                            coordonneePossible = new int[2];

                            coordonneePossible[0] = x;
                            coordonneePossible[1] = y - i;

                            if (!listePositionCible.contains(coordonneePossible) && coordonneePossible[1] != cible[1]) {
                                listePositionCible.add(coordonneePossible);
                            }
                        }
                    }
                }
            }
        }


        if(x < 9) {
            if (tabJoueurAdverse[x][y] == tabJoueurAdverse[x + 1][y] || tabJoueurAdverse[x][y] == -tabJoueurAdverse[x + 1][y]) {
                for (int i = 0; i < getTaille(idBateauCible); i++) {
                    if (x + i < 10) {
                        if (tabJoueurAdverse[x + i][y] >= 0) {
                            coordonneePossible = new int[2];

                            coordonneePossible[0] = x + i;
                            coordonneePossible[1] = y;

                            if (!listePositionCible.contains(coordonneePossible) && coordonneePossible[0] != cible[0]) {
                                listePositionCible.add(coordonneePossible);
                            }
                        }
                    }
                }
            }
        }


        if(x >= 1) {
            if (tabJoueurAdverse[x][y] == tabJoueurAdverse[x - 1][y] || tabJoueurAdverse[x][y] == -tabJoueurAdverse[x - 1][y]) {
                for (int i = 0; i < getTaille(idBateauCible); i++) {
                    if (x - i >= 0) {
                        if (tabJoueurAdverse[x - i][y] >= 0) {
                            coordonneePossible = new int[2];

                            coordonneePossible[0] = x - i;
                            coordonneePossible[1] = y;

                            if (!listePositionCible.contains(coordonneePossible) && coordonneePossible[0] != cible[0]) {
                                listePositionCible.add(coordonneePossible);
                            }
                        }
                    }
                }
            }
        }




    }

    public void creationListeCiblesPotentielles(int[] caseCiblee){

        idBateauCible = tabJoueurAdverse[caseCiblee[0]][caseCiblee[1]];

        int[] ciblePotentielle = new int[2];

        ciblePotentielle[0] = caseCiblee[0] + 1;
        ciblePotentielle[1] = caseCiblee[1];

        if(ciblePotentielle[0] < 10 && ciblePotentielle[0] >= 0 && ciblePotentielle[1] < 10 && ciblePotentielle[1] >= 0){
            if(tabJoueurAdverse[ciblePotentielle[0]][ciblePotentielle[1]] >= 0){
                listeCiblesPotentielles.add(ciblePotentielle);
            }
        }

        ciblePotentielle = new int[2];

        ciblePotentielle[0] = caseCiblee[0] - 1;
        ciblePotentielle[1] = caseCiblee[1];

        if(ciblePotentielle[0] < 10 && ciblePotentielle[0] >= 0 && ciblePotentielle[1] < 10 && ciblePotentielle[1] >= 0){
            if(tabJoueurAdverse[ciblePotentielle[0]][ciblePotentielle[1]] >= 0){
                listeCiblesPotentielles.add(ciblePotentielle);
            }
        }

        ciblePotentielle = new int[2];

        ciblePotentielle[0] = caseCiblee[0];
        ciblePotentielle[1] = caseCiblee[1] + 1;

        if(ciblePotentielle[0] < 10 && ciblePotentielle[0] >= 0 && ciblePotentielle[1] < 10 && ciblePotentielle[1] >= 0){
            if(tabJoueurAdverse[ciblePotentielle[0]][ciblePotentielle[1]] >= 0){
                listeCiblesPotentielles.add(ciblePotentielle);
            }
        }

        ciblePotentielle = new int[2];

        ciblePotentielle[0] = caseCiblee[0];
        ciblePotentielle[1] = caseCiblee[1] - 1;

        if(ciblePotentielle[0] < 10 && ciblePotentielle[0] >= 0 && ciblePotentielle[1] < 10 && ciblePotentielle[1] >= 0){
            if(tabJoueurAdverse[ciblePotentielle[0]][ciblePotentielle[1]] >= 0){
                listeCiblesPotentielles.add(ciblePotentielle);
            }
        }


    }

    public int[] aquisitionCoordonneesAleatoire(){
        Random random = new Random();

        int[] retour = new int[2];

        int cibleX;
        int cibleY;

        do{

            cibleX = random.nextInt(Model.getTaillePlateau());
            cibleY = random.nextInt(Model.getTaillePlateau());

        }while (tabJoueurAdverse[cibleX][cibleY] < 0);


        retour[0] = cibleX;
        retour[1] = cibleY;

        if(tabJoueurAdverse[cibleX][cibleY] > 0){
            creationListeCiblesPotentielles(retour);
        }


        return retour;
    }

    public int[][] modificationTab(int[][] tab){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(tab[i][j] == 0){
                    tab[i][j] = -15;
                }
            }
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(tab[i][j] > 0) {
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (k < 10 && k >= 0 && l < 10 && l >= 0) {
                                if (tab[k][l] == -15) {
                                    tab[k][l] = 0;
                                }
                            }

                        }
                    }

                }

            }
        }

        return tab;
    }

    public int[][] correctionTab(int[][] tab){

        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if(tab[i][j] == -15){
                    tab[i][j] = 0;
                }
            }
        }

        return tab;

    }


}
