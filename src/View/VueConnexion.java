package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.*;

import multijoueur.ClientTCP;
import multijoueur.ServeurTCP;
import Model.Model;

public class VueConnexion extends JFrame {

   private JCheckBox checkBoxBonus;
   
   private String hostIP = "localhost";
   private int port = ServeurTCP.portEcoute;
   private static boolean isHost = true;
   
   private JTextField ipField = null;
   private JLabel label1,label2;
   private JTextField portField = null;
   private JRadioButton hostOption = null;
   private JRadioButton guestOption = null;
   private JButton connectButton = null;

   private VueJeu vueJeu;
   private VueMenu vueMenu;
   private Model model;
   private VueConnexion vueConnexion;

   private ServeurTCP serveur;
   private ClientTCP client;
   
   public VueConnexion(Model model, VueJeu vueJeu, VueMenu vueMenu) {
	   this.vueMenu = vueMenu;
       this.vueJeu = vueJeu;
       this.model = model;
       this.vueConnexion = this;
	   
	   serveur = new ServeurTCP(model, vueJeu, vueMenu, this);
	   client = new ClientTCP(model, vueJeu, vueMenu, this);
	   
	   JPanel optionsPane = initOptionsPane();
	   
      JPanel mainPane = new JPanel(new BorderLayout());
      mainPane.add(optionsPane, BorderLayout.WEST);
      
      String ipv4 = "";
      
      try {
		ipv4 = java.net.InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
      
      setTitle("Multijoueurs - IPv4 : "+ipv4);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setContentPane(mainPane);
      setSize(getPreferredSize());
      setLocation(200, 200);
      setIconImage(new ImageIcon("images/Autres/icone.png").getImage());
      setResizable(false);
      pack();
      setVisible(false);
   }

   public void deconnexion() {
	   client.deconnexion();
	   serveur.deconnexion();
   }
   
   public JPanel initOptionsPane() {
      Tools tools = new Tools();
      JPanel paneNom;
      ActionAdapter buttonListener;
      JPanel fond = null;
      try {
         fond = new JPanel() {
            BufferedImage image = ImageIO.read(new File("images/Autres/fondText.jpg"));

            public void paintComponent(Graphics g) {
               super.paintComponent(g);
               g.drawImage(image, 0, 0, 600, 400, this);
            }
         };
      } catch (IOException e) {
         e.printStackTrace();
      }

      // Create an options pane
      JPanel optionsPane = new JPanel(new GridLayout(4, 1));

      // IP address input
      paneNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
      label1 = new JLabel("Host IP : ");
      paneNom.add(label1);
      ipField = new JTextField(10); 
      ipField.setText(hostIP);
      ipField.setEnabled(false);
      tools.changerFontJTextField(ipField, 30, Color.white, tools.getFontTexte());
      tools.changerFontJLabel(label1, 30, Color.white, tools.getFontTexte());
      ipField.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent e) {
            ipField.selectAll();

            hostIP = ipField.getText();
         }
      });

      paneNom.add(ipField);
      paneNom.setOpaque(false);
      optionsPane.add(paneNom);

      // Port input
      paneNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
      label2 = new JLabel("Port : ");
      paneNom.add(label2);
      portField = new JTextField(10);
      portField.setEditable(true);
      tools.changerFontJTextField(portField, 30, Color.white, tools.getFontTexte());
      tools.changerFontJLabel(label2, 30, Color.white, tools.getFontTexte());
      portField.setText((new Integer(port)).toString());
      portField.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent e) {

            int temp;
            try {
               temp = Integer.parseInt(portField.getText());
               port = temp;
            } catch (NumberFormatException nfe) {
               portField.setText((new Integer(port)).toString());
               repaint();
            }
         }
      });

      paneNom.add(portField);
      paneNom.setOpaque(false);
      optionsPane.add(paneNom);

      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
              isHost = e.getActionCommand().equals("host");

              if (isHost) {
                 ipField.setEnabled(false);
                 ipField.setText("localhost");
                 hostIP = "localhost";
              }
              else {
                 ipField.setEnabled(true);
              }
            }
         };
         
      ButtonGroup bg = new ButtonGroup();
      hostOption = new JRadioButton("Host", true);
      hostOption.setMnemonic(KeyEvent.VK_H);
      hostOption.setActionCommand("host");
      hostOption.addActionListener(buttonListener);
      guestOption = new JRadioButton("Guest", false);
      guestOption.setMnemonic(KeyEvent.VK_G);
      guestOption.setActionCommand("guest");
      guestOption.addActionListener(buttonListener);
      tools.changerFontJRadioButton(hostOption, 30, Color.white, tools.getFontTexte());
      tools.changerFontJRadioButton(guestOption,30,Color.white,tools.getFontTexte());
      bg.add(hostOption);
      bg.add(guestOption);
      paneNom = new JPanel(new GridLayout(2, 2));
      paneNom.add(hostOption);
      paneNom.add(guestOption);
      paneNom.setOpaque(false);
      optionsPane.add(paneNom);
      paneNom.setOpaque(false);

      // Connect/disconnect buttons
      JPanel buttonPane = new JPanel(new GridLayout(1, 2));
      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (isHost) {
            		if (!serveur.isAlive()) {
	            		connectButton.setText("En attente..");
	            		serveur = new ServeurTCP(model, vueJeu, vueMenu, vueConnexion);
	            		serveur.start();
            		}
            		else {
            			connectButton.setText("Connexion");
            			serveur.deconnexion();
            		}
            	}
            	else {
            		if (!client.isAlive()) {
                		connectButton.setText("En attente..");
                		client = new ClientTCP(model, vueJeu, vueMenu, vueConnexion);
                		client.start();
                	}
                	else {
                		connectButton.setText("Connexion");
                		client.deconnexion();
                	}
            	}
            }
         };
         
      connectButton = new JButton("Connexion");
      tools.changerFontButton(connectButton, 30, Color.white, tools.getFontTexte());
      connectButton.setMnemonic(KeyEvent.VK_C);
      connectButton.setActionCommand("connect");
      connectButton.addActionListener(buttonListener);
      connectButton.setEnabled(true);
      
      ipField.setText(hostIP);
      portField.setText((new Integer(port)).toString());
      hostOption.setSelected(isHost);
      guestOption.setSelected(!isHost);

      buttonPane.add(connectButton);
      buttonPane.setBorder(BorderFactory.createLineBorder(Color.white, 3));
      optionsPane.add(buttonPane);
      fond.add(optionsPane);
      paneNom.setOpaque(false);
      buttonPane.setOpaque(false);
      optionsPane.setOpaque(false);
      return fond;
   }

   public static boolean isHost() {
	return isHost;
	}

	class ActionAdapter implements ActionListener {
	   public void actionPerformed(ActionEvent e) {}
	}

	public String getHostIP() {
		return hostIP;
	}

	public JButton getConnectButton() {
		return connectButton;
	}

}
