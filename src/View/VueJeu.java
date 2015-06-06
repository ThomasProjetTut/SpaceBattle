package View;

import Bateaux.Bateaux;
import Model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Skygi_000 on 05/06/2015.
 */
public class VueJeu extends JFrame {

    private JMenuBar barreMenu;
    private JMenu menu;
    private JMenuItem aide;
    private JMenuItem nouvellePartie;
    private JMenuItem quitter;
    private JMenuItem aPropos;

    private static JButton [][] grilleJeu;
    private static JButton [][] pteGrilleJeu;
    private JButton contreTorpilleurs;
    private JButton torpilleur;
    private JButton porteAvion;
    private JButton croiseur;
    private JButton sousMarin;

    private static JTextArea chatTexte;
    private JTextField chatLigne;
    private JTextField chatNomJoueur;
    private JTable tableJoueur1;

    private JLabel chatNomJoueurInfo;

    private Model model;

    public VueJeu(Model model){
        this.model = model;

        initAttribut();
        creerFenetreJeu();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Bataille Navale");
        setIconImage(new ImageIcon("icone.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void initGrilleTexte() {
    	updateTextCellsJoueur1();
    	updateTextCellsJoueur2();
    }

    public void resetTextChat() {
		
		chatTexte.setText("");
	}
    
    public void updateTextCellsJoueur1() {
		
		for (int i = 0 ; i < Model.getTaillePlateau() ; i++)
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++)
 	    		pteGrilleJeu[i][j].setText(""+Model.getJoueur(1).getValeurTabJoueur1(i, j));
	}
    
    public void updateTextCellsJoueur2() {
		
		for (int i = 0 ; i < Model.getTaillePlateau() ; i++)
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++)
 	    		grilleJeu[i][j].setText(""+Model.getJoueur(2).getValeurTabJoueur1(i, j));
	}

    // Les initialisations
    public void initAttribut(){
        Bateaux.initTabBateaux();
        Bateaux.initImagesBateaux();

        barreMenu = new JMenuBar();
        menu = new JMenu("Menu");
        setAide(new JMenuItem("Aide"));
        setNouvellePartie(new JMenuItem("Nouvelle Partie"));
        setQuitter(new JMenuItem("Quitter"));
        setAPropos(new JMenuItem("A propos"));

        creerBarreMenu();

        grilleJeu = new JButton[Model.getTaillePlateau()][Model.getTaillePlateau()];
        pteGrilleJeu = new JButton[Model.getTaillePlateau()][Model.getTaillePlateau()];

        initBateaux();

    }

    public void initChat(){

        chatTexte = new JTextArea(10, 20);
        chatTexte.setLineWrap(true);
        chatTexte.setEditable(false);
        chatTexte.setForeground(Color.blue);
        chatLigne = new JTextField();
        chatLigne.setEnabled(true);

        chatNomJoueurInfo = new JLabel("Nom du joueur : ");
        chatNomJoueur = new JTextField();
        chatNomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        chatNomJoueur.setEnabled(true);
        chatNomJoueur.setText("Joueur 1");
        chatNomJoueur.setColumns(8);
    }

    public void initGrille(){

        for (int i = 0; i < grilleJeu.length; i++){
            for (int j = 0; j < grilleJeu.length; j++){
                grilleJeu[i][j] = new JButton("0");
                grilleJeu[i][j].setBackground(Color.lightGray);
                grilleJeu[i][j].setPreferredSize(new Dimension(50, 50));
                grilleJeu[i][j].setActionCommand("" + i + "" + j);
                grilleJeu[i][j].setBorder(null);
            }
        }
    }

    public void initBateaux(){
        contreTorpilleurs = new JButton();
        contreTorpilleurs.setIcon(Bateaux.imageBateau(Bateaux.CONTRETORPILLEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));

        torpilleur = new JButton();
        torpilleur.setIcon(Bateaux.imageBateau(Bateaux.TORPILLEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));

        croiseur = new JButton();
        croiseur.setIcon(Bateaux.imageBateau(Bateaux.CROISEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));

        sousMarin = new JButton();
        sousMarin.setIcon(Bateaux.imageBateau(Bateaux.SOUSMARIN, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));

        porteAvion = new JButton();
        porteAvion.setIcon(Bateaux.imageBateau(Bateaux.PORTEAVIONS, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
    }

    public void initPteGrille(){

        for (int i = 0; i < pteGrilleJeu.length; i++){
            for (int j = 0; j < pteGrilleJeu.length; j++){
                pteGrilleJeu[i][j] = new JButton("0");
                pteGrilleJeu[i][j].setBackground(Color.lightGray);
                pteGrilleJeu[i][j].setPreferredSize(new Dimension(20, 20));
                pteGrilleJeu[i][j].setActionCommand("" + i + "" + j);
                pteGrilleJeu[i][j].setBorder(null);
            }
        }
    }


    public void creerFenetreJeu(){

        barreMenu.setVisible(true);
        initChat();
        initGrille();
        initPteGrille();

        JPanel panPrincipal = new JPanel();
        JPanel panJeu = new JPanel();
        JPanel panOption = new JPanel();
        JPanel panPteGrille = new JPanel();

        JPanel bateauxAffiche = new JPanel();
        JPanel panCroiseur = new JPanel();
        JPanel panTorpilleur = new JPanel();
        JPanel panContreTorpilleur = new JPanel();
        JPanel panSousMarin = new JPanel();
        JPanel panPorteAvion = new JPanel();

        JPanel nomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel chatPanel = new JPanel(new BorderLayout());
        JScrollPane chatTextPane = new JScrollPane(chatTexte,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        GridBagLayout gbl_panel_1 = new GridBagLayout();

        chatPanel.add(chatLigne, BorderLayout.SOUTH);
        chatPanel.add(chatTextPane, BorderLayout.CENTER);
        chatPanel.setPreferredSize(new Dimension(200, 200));

        nomPanel.add(chatNomJoueurInfo);
        nomPanel.add(chatNomJoueur);
        chatPanel.add(nomPanel, BorderLayout.NORTH);
        panPrincipal.setLayout(new BorderLayout(0, 0));
        panPrincipal.add(chatPanel, BorderLayout.WEST);


        panPrincipal.add(panJeu, BorderLayout.CENTER);

        gbl_panel_1.columnWidths = new int[]{0};
        gbl_panel_1.rowHeights = new int[]{0};
        gbl_panel_1.columnWeights = new double[]{0.0};
        gbl_panel_1.rowWeights = new double[]{0.0};

        JPanel fond = null;
        JPanel panGrille = new JPanel();
        panGrille.setLayout(gbl_panel_1);
        panGrille.setOpaque(false);

        try {
            fond = new JPanel() {
                BufferedImage image = ImageIO.read(new File("images/Autres/imageGrille.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < grilleJeu.length; i++){
            for (int j = 0; j < grilleJeu.length; j++){

                GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
                gbc_btnNewButton.gridx = i;
                gbc_btnNewButton.gridy = j;
                panGrille.add(grilleJeu[i][j], gbc_btnNewButton);
                grilleJeu[i][j].setOpaque(false);
                panGrille.setOpaque(false);
            }
        }
        fond.add(panGrille);
        panJeu.add(fond);

        // affichage bateaux sur le cote
        panContreTorpilleur.add(contreTorpilleurs);
        panTorpilleur.add(torpilleur);
        panSousMarin.add(sousMarin);
        panCroiseur.add(croiseur);
        panPorteAvion.add(porteAvion);


        panContreTorpilleur.setLayout(gbl_panel_1);
        panTorpilleur.setLayout(gbl_panel_1);
        panSousMarin.setLayout(gbl_panel_1);
        panCroiseur.setLayout(gbl_panel_1);
        panPorteAvion.setLayout(gbl_panel_1);
        panPteGrille.setLayout(gbl_panel_1);

        for (int i = 0; i < pteGrilleJeu.length; i++){
            for (int j = 0; j < pteGrilleJeu.length; j++){

                GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
                gbc_btnNewButton2.gridx = i;
                gbc_btnNewButton2.gridy = j;
                panPteGrille.add(pteGrilleJeu[i][j], gbc_btnNewButton2);
            }
        }

        bateauxAffiche.add(panContreTorpilleur);
        bateauxAffiche.add(panTorpilleur);
        bateauxAffiche.add(panSousMarin);
        bateauxAffiche.add(panCroiseur);
        bateauxAffiche.add(panPorteAvion);
        bateauxAffiche.setLayout(new BoxLayout(bateauxAffiche, BoxLayout.Y_AXIS));

        panOption.add(bateauxAffiche);
        panOption.add(panPteGrille);
        panOption.setLayout(new BoxLayout(panOption, BoxLayout.Y_AXIS));

        panPrincipal.add(panOption, BorderLayout.EAST);

        setContentPane(panPrincipal);

    }

    public void creerBarreMenu(){

        menu.add(getNouvellePartie());
        menu.addSeparator();
        menu.add(getAide());
        menu.add(getaPropos());
        menu.addSeparator();
        menu.add(getQuitter());

        barreMenu.add(menu);

        setJMenuBar(barreMenu);

    }

    public void creerFenetreScore(){

        barreMenu.setVisible(false);

        JPanel panPrincipal = new JPanel();
        JPanel panInstructions = new JPanel();
        JPanel panBouton = new JPanel();

        panPrincipal.add(panBouton, BorderLayout.SOUTH);
        panPrincipal.add(panInstructions, BorderLayout.CENTER);

        setContentPane(panPrincipal);
        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));
    }

    //TODO
    public void aProposBox(){
        JOptionPane.showMessageDialog(this, " Hellow ! ");
    }

    // Les controlleurs
    public void setMenuControler(ActionListener listener){
        getNouvellePartie().addActionListener(listener);
        getQuitter().addActionListener(listener);
        getAide().addActionListener(listener);
        getaPropos().addActionListener(listener);
    }

    public void setButtonControler(ActionListener listener){

        for	(int i = 0 ; i < Model.getTaillePlateau() ; i++) {
            for	(int j = 0 ; j < Model.getTaillePlateau() ; j++) {
                grilleJeu[i][j].addActionListener(listener);
            }
        }
            torpilleur.addActionListener(listener);
            contreTorpilleurs.addActionListener(listener);
            croiseur.addActionListener(listener);
            sousMarin.addActionListener(listener);
            porteAvion.addActionListener(listener);
    }

    public void setChatControler(KeyListener listener) {
        getChatLigne().addKeyListener(listener);
    }


    // Les set

    public void setChatLigne(JTextField chatLigne) {
        this.chatLigne = chatLigne;
    }

    public void setChatNomJoueur(JTextField chatNomJoueur) {
        this.chatNomJoueur = chatNomJoueur;
    }

    public void setNouvellePartie(JMenuItem nouvellePartie) {
        this.nouvellePartie = nouvellePartie;
    }

    public void setQuitter(JMenuItem quitter) {
        this.quitter = quitter;
    }

    public void setAide(JMenuItem aide) {
        this.aide = aide;
    }

    public void setAPropos(JMenuItem aPropos){
        this.aPropos = aPropos;
    }


    // Les get
    public static JButton getGrilleJeu(int x, int y) {
        return grilleJeu[x][y];
    }

    public JTextField getChatNomJoueur() {
        return chatNomJoueur;
    }

    public static JTextArea getChatTexte() {
        return chatTexte;
    }

    public JTextField getChatLigne() {
        return chatLigne;
    }

	public JMenuItem getNouvellePartie() {
		return nouvellePartie;
	}

	public JMenuItem getQuitter() {
		return quitter;
	}

	public JMenuItem getAide() {
		return aide;
	}

    public JMenuItem getaPropos() {
        return aPropos;
    }

	public static JButton[][] getGrilleJeu() {
		return grilleJeu;
	}
}
