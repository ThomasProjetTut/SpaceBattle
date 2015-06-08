package multijoueur;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
 
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Model.Model;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;
 
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
	 
		model.initJeu();
        vueJeu.repaintFantomeBateau();
        vueJeu.reiniBtnBateaux();
        model.setPlacementBateauEstLock(false);
        vueJeu.initGrilleTexte();
        vueJeu.resetTextChat();
        vueMenu.setVisible(false);
        model.setIsGameActive(true);
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
			String message = "";
			
			try {
			    message = input.readLine();
			    
			    // 'C' = Chat | 'I' = Index coordonnées | 'T' = TabBateau
			    if (message.charAt(0) == 'C') {
			    	message = message.substring(1,  message.length());
			    	VueJeu.appendToChatBox(message);
			    }
			    else if (message.charAt(0) == 'I') {
			    	
			    }
			    else if (message.charAt(0) == 'T') {
			    	
			    }
			    
			} catch(IOException e) {
			    System.err.println("Erreur lors de la lecture : " + e);
			}
			System.out.println("Lu: " + message);
		}
    }
    
    public void deconnexion() {
    	// Fermeture des flux et des sockets
 		try {
 		    input.close();
 		    output.close();
 		    socketClient.close();
 		    socketServeur.close();
 		} catch(IOException e) {
 		    System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
 		}
    }
 
}