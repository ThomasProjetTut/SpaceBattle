package multijoueur;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Model.Model;
import View.VueJeu;
import View.VueMenu;
import View.VueParametre;

public class VueConnexion extends JFrame {

   private JCheckBox checkBoxBonus;
   
   private String hostIP = "localhost";
   private int port = ServeurTCP.portEcoute;
   private static boolean isHost = true;
   
   private JTextField ipField = null;
   private JTextField portField = null;
   private JRadioButton hostOption = null;
   private JRadioButton guestOption = null;
   private JButton connectButton = null;

   private VueJeu vueJeu;
   private VueMenu vueMenu;
   private Model model;
   
   private ServeurTCP serveur;
   private ClientTCP client;
   
   public VueConnexion(Model model, VueJeu vueJeu, VueMenu vueMenu) {
	   this.vueMenu = vueMenu;
       this.vueJeu = vueJeu;
       this.model = model;
	   
	   serveur = new ServeurTCP(model, vueJeu, vueMenu, this);
	   client = new ClientTCP(model, vueJeu, vueMenu, this);
	   
	   JPanel optionsPane = initOptionsPane();
	   
      JPanel mainPane = new JPanel(new BorderLayout());
      mainPane.add(optionsPane, BorderLayout.WEST);
      
      setTitle("TCP");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setContentPane(mainPane);
      setSize(getPreferredSize());
      setLocation(200, 200);
      setResizable(false);
      pack();
      setVisible(false);
   }
   /////////////////////////////////////////////////////////////////

   public JPanel initOptionsPane() {
      JPanel pane = null;
      ActionAdapter buttonListener = null;

      // Create an options pane
      JPanel optionsPane = new JPanel(new GridLayout(4, 1));

      // IP address input
      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Host IP:"));
      ipField = new JTextField(10); 
      ipField.setText(hostIP);
      ipField.setEnabled(false);
      ipField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {
	         ipField.selectAll();
	
	         hostIP = ipField.getText();
         }
       });
      
      pane.add(ipField);
      optionsPane.add(pane);

      // Port input
      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Port:"));
      portField = new JTextField(10); portField.setEditable(true);
      portField.setText((new Integer(port)).toString());
      portField.addFocusListener(new FocusAdapter() {
          public void focusLost(FocusEvent e) {

            int temp;
            try {
               temp = Integer.parseInt(portField.getText());
               port = temp;
            }
            catch (NumberFormatException nfe) {
               portField.setText((new Integer(port)).toString());
               repaint();
            }
          }
       });
      
      pane.add(portField);
      optionsPane.add(pane);

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
      bg.add(hostOption);
      bg.add(guestOption);
      pane = new JPanel(new GridLayout(2, 2));
      pane.add(hostOption);
      pane.add(guestOption);
      optionsPane.add(pane);
      checkBoxBonus = new JCheckBox("Activer les bonus");
      pane.add(checkBoxBonus);

      // Connect/disconnect buttons
      JPanel buttonPane = new JPanel(new GridLayout(1, 2));
      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (isHost) {
            		serveur.start();
            	}
            	else {
            		client.start();
            	}
            }
         };
         
      connectButton = new JButton("Connect");
      connectButton.setMnemonic(KeyEvent.VK_C);
      connectButton.setActionCommand("connect");
      connectButton.addActionListener(buttonListener);
      connectButton.setEnabled(true);
      
      ipField.setText(hostIP);
      portField.setText((new Integer(port)).toString());
      hostOption.setSelected(isHost);
      guestOption.setSelected(!isHost);

      buttonPane.add(connectButton);
      optionsPane.add(buttonPane);

      return optionsPane;
   }

   public static boolean isHost() {
	return isHost;
	}

	class ActionAdapter implements ActionListener {
	   public void actionPerformed(ActionEvent e) {}
	}

}