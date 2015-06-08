package multijoueur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Model.Model;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;

public class TestConnection extends Thread{

	private VueParametre vueParametre;
    private VueJeu vueJeu;
    private VueMenu vueMenu;
    private Model model;
    private Seconnecter connexion;
	
	 public TestConnection(Model model, VueJeu vueJeu, VueMenu vueMenu, VueParametre vueParametre){
		 this.vueMenu = vueMenu;
	     this.vueParametre = vueParametre;
	     this.vueJeu = vueJeu;
	     this.model = model;
	     connexion = new Seconnecter();
	 }
	 
	 public void displayTCP(boolean valeur) {
			 connexion.setVisible(valeur);
	 }
	 
	 public void run(){
	
	      String s;

	      //Seconnecter.initGUI();

	      connexion.initGUI();
	      
	      while (true) {
	         try { // Poll every ~10 ms
	            Thread.sleep(10);
	         }
	         catch (InterruptedException e) {}

	         switch (Seconnecter.connectionStatus) {
	         case Seconnecter.BEGIN_CONNECT:
	            try {
	               // Try to set up a server if host
	               if (Seconnecter.isHost) {
	            	   Seconnecter.hostServer = new ServerSocket(Seconnecter.port);
	            	   Seconnecter.socket = Seconnecter.hostServer.accept();
	               }

	               // If guest, try to connect to the server
	               else {
	            	   Seconnecter.socket = new Socket(Seconnecter.hostIP, Seconnecter.port);
	               }

	               Seconnecter.in = new BufferedReader(new 
	                  InputStreamReader(Seconnecter.socket.getInputStream()));
	               Seconnecter.out = new PrintWriter(Seconnecter.socket.getOutputStream(), true);
	               Seconnecter.changeStatusTS(Seconnecter.CONNECTED, true);
	            }
	            // If error, clean up and output an error message
	            catch (IOException e) {
	            	Seconnecter.cleanUp();
	            	Seconnecter.changeStatusTS(Seconnecter.DISCONNECTED, false);
	            }
	            break;

	         case Seconnecter.CONNECTED:
	            try {
	            	
	            	/*model.initJeu();
	                vueJeu.repaintFantomeBateau();
	                vueJeu.reiniBtnBateaux();
	                model.setPlacementBateauEstLock(false);
	                vueJeu.initGrilleTexte();
	                vueJeu.resetTextChat();
	                vueMenu.setVisible(false);
	                vueParametre.setVisible(false);
	                model.setIsGameActive(true);
	                vueJeu.setVisible(true);*/
	                
	               // Send data
	               if (Seconnecter.toSend.length() != 0) {
	            	   Seconnecter.out.print(Seconnecter.toSend);
	            	   Seconnecter.out.flush();
	            	   Seconnecter.toSend.setLength(0);
	            	   Seconnecter.changeStatusTS(Seconnecter.NULL, true);
	               }

	               // Receive data
	               if (Seconnecter.in.ready()) {
	                  s = Seconnecter.in.readLine();
	                  if ((s != null) &&  (s.length() != 0)) {
	                     // Check if it is the end of a trasmission
	                     if (s.equals(Seconnecter.END_CHAT_SESSION)) {
	                    	 Seconnecter.changeStatusTS(Seconnecter.DISCONNECTING, true);
	                     }

	                     // Otherwise, receive what text
	                     else {
	                    	 Seconnecter.appendToChatBox("INCOMING: " + s + "\n");
	                    	 Seconnecter.changeStatusTS(Seconnecter.NULL, true);
	                     }
	                  }
	               }
	            }
	            catch (IOException e) {
	            	Seconnecter.cleanUp();
	            	Seconnecter.changeStatusTS(Seconnecter.DISCONNECTED, false);
	            }
	            break;

	         case Seconnecter.DISCONNECTING:
	            // Tell other chatter to disconnect as well
	        	 Seconnecter.out.print(Seconnecter.END_CHAT_SESSION); Seconnecter.out.flush();

	            // Clean up (close all streams/sockets)
	        	 Seconnecter.cleanUp();
	        	 Seconnecter.changeStatusTS(Seconnecter.DISCONNECTED, true);
	            break;

	         default: break; // do nothing
	         }
	      }
	 }
}

