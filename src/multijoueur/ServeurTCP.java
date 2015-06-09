package multijoueur;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
 
import java.net.ServerSocket;
import java.net.Socket;

import Model.Model;
import View.VueConnexion;
import View.VueJeu;
import View.VueMenu;

public class ServeurTCP extends Thread {
 
	private static BufferedReader input;
	private static PrintWriter output;
	
	private static ServerSocket socketServeur;
	private static Socket socketClient;
	
    public static final int portEcoute = 5001;
 
    private VueJeu vueJeu;
    private VueMenu vueMenu;
    private Model model;
	private VueConnexion vueConnexion;
    
    public ServeurTCP(Model model, VueJeu vueJeu, VueMenu vueMenu, VueConnexion vueConnexion) {
    	this.vueMenu = vueMenu;
        this.vueJeu = vueJeu;
        this.model = model;
    	this.vueConnexion = vueConnexion;
    }
    
    public static PrintWriter getOutPut() {
		return output;
	}
    
    public void run(){
		// Creation de la socket serveur
		socketServeur = null;
		try {	
		    socketServeur = new ServerSocket(portEcoute);
		} catch(IOException e) {
		    System.err.println("Creation de la socket impossible : " + e);
		}
	 
		// Attente d'une connexion d'un client
		socketClient = null;
		try {
		    socketClient = socketServeur.accept();
		} catch(IOException e) {
		    System.err.println("Erreur lors de l'attente d'une connexion : " + e);
		}
		
		if (socketClient == null)
			return;
	 
		if (VueConnexion.isHost())
    		model.setTourJoueurEstFini(true);
    	else
    		model.setTourJoueurEstFini(false);
		
		model.setJeuEstEnMulti(true);
		
		model.setAILevel(0);
		model.initJeu();
        vueJeu.repaintFantomeBateau();
        vueJeu.reiniBtnBateaux();
        model.setPlacementBateauEstLock(false);
        vueJeu.initGrilleTexte();
        vueJeu.repaintGrilleAdverseBateau();
        vueJeu.resetTextChat();
        vueMenu.setVisible(false);
        vueJeu.setVisible(true);
		vueConnexion.setVisible(false);
		
		// Association d'un flux d'entree et de sortie
		input = null;
		output = null;
		try {
		    input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
		} catch(IOException e) {
		    System.err.println("Association des flux impossible : " + e);
		}
	 
		while (true) 
		{
			// Lecture 
			String message;
			
			try {
			    message = input.readLine();
			    
			    if (message.isEmpty())
			    	continue;
			    
			    // 'C' = Chat | 'I' = Index coordonnées | 'T' = TabBateau
			    if (message.charAt(0) == 'P') {
			    	message.substring(1,  message.length());
			    	
			    	vueJeu.creerFenetreJeu();
					vueJeu.pack();
					model.setJeuEstEnMulti(true);
					model.setAILevel(0);
					model.initJeu();
			        vueJeu.repaintFantomeBateau();
			        vueJeu.reiniBtnBateaux();
			        model.setPlacementBateauEstLock(false);
			        vueJeu.initGrilleTexte();
			        vueJeu.repaintGrilleAdverseBateau();
			        vueJeu.resetTextChat();
			        vueMenu.setVisible(false);
			        vueJeu.setVisible(true);
			    }
			    else if (message.charAt(0) == 'N') {
			    	message = message.substring(1,  message.length());
			    	Model.getJoueur(2).setNomJoueur(message);
			    }
			    else if (message.charAt(0) == 'C') {
			    	message = message.substring(1,  message.length());
			    	VueJeu.appendToChatBox(message);
			    }
			    else if (message.charAt(0) == 'I') {
			    	message = message.substring(1,  message.length());
			    	model.updateTabToucheMulti(Character.getNumericValue(message.charAt(0)), Character.getNumericValue(message.charAt(1)), Character.getNumericValue(message.charAt(2)));
			    }
			    else if (message.charAt(0) == 'S') {
			    	model.setTourJoueurEstFini(false);
			    }
			    else if (message.charAt(0) == 'T') {
			    	message = message.substring(1,  message.length());
			    	Model.getJoueur(2).setTabJoueur(model.convertStringToTab(message));
			    	vueJeu.initGrilleTexte();
			    	
			    	if (model.placementBateauIsLock()) {
			    		
			    		output.println("V");
			    		
			    		VueJeu.appendToChatBox("V - Serveur Lock");
			    		
			    		model.setPlacementMultiEstFini(true);
			    		
			    		System.out.println("Serveur - Valider jeu");
			    	}
			    	else
			    		VueJeu.appendToChatBox("V - Serveur !Lock");
			    }
			    else if (message.charAt(0) == 'V') {
			    	
			    	model.setPlacementMultiEstFini(true);
			    	
			    	System.out.println("Serveur - Debuter jeu");
			    	
			    	VueJeu.appendToChatBox("D - Serveur");
			    }
			    
			} catch(IOException e) {
			    System.err.println("Erreur lors de la lecture : " + e);
			}
		}
    }
    
    public void deconnexion() {
    	// Fermeture des flux et des sockets
 		try {
 			if (input != null)
 				input.close();
 			if (output != null)
 				output.close();
 			if (socketClient != null)
 				socketClient.close();
 			if (socketServeur != null)
 				socketServeur.close();
 		} catch(IOException e) {
 		    System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
 		}
    }
 
}
