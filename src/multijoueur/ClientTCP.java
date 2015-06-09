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
 
public class ClientTCP extends Thread {
 
	private static Socket socket;
	
	private static BufferedReader input;
	private static PrintWriter output;
	
    private VueJeu vueJeu;
    private VueMenu vueMenu;
    private Model model;
	private VueConnexion vueConnexion;
    
    public ClientTCP(Model model, VueJeu vueJeu, VueMenu vueMenu, VueConnexion vueConnexion) {
    	this.vueMenu = vueMenu;
        this.vueJeu = vueJeu;
        this.model = model;
    	this.vueConnexion = vueConnexion;
    }
	
	public static PrintWriter getOutPut() {
		return output;
	}
	
    public void run(){
		// Creation de la socket
		socket = null;
		
		while (socket == null)
		{
			try {
			    socket = new Socket(vueConnexion.getHostIP(), ServeurTCP.portEcoute);
			} catch(UnknownHostException e) {
			    System.err.println("Erreur sur l'h�te : " + e);
			} catch(IOException e) {
			    System.err.println("Creation de la socket impossible : " + e);
			}
		}
		
		model.initJeu();
        vueJeu.repaintFantomeBateau();
        vueJeu.reiniBtnBateaux();
        model.setPlacementBateauEstLock(false);
        vueJeu.initGrilleTexte();
        vueJeu.repaintGrilleAdverseBateau();
        vueJeu.resetTextChat();
        vueMenu.setVisible(false);
        model.setIsGameActive(true);
        vueJeu.setVisible(true);
		vueConnexion.setVisible(false);
		
		// Association d'un flux d'entree et de sortie
		input = null;
		output = null;
		try {
		    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch(IOException e) {
		    System.err.println("Association des flux impossible : " + e);
		}
	 
		while (true)
		{
			String message = "";
			
			// Lecture 
			try {
			    message = input.readLine();
			    
			    if (message.isEmpty())
			    	continue;
			    
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
	    }
    }
    
    public void deconnexion() {
    	// Fermeture des flux et de la socket
		try {
			if (input != null)
 				input.close();
 			if (output != null)
 				output.close();
 			if (socket != null)
 				socket.close();
		} catch(IOException e) {
		    System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
		}
    }
 
}