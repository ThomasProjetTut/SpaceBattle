package View;

import Bateaux.Bateaux;
import Model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueJeu extends JFrame {

    private JMenuBar barreMenu;
    private JMenu menu;
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
    private JButton valider;

    private static JTextArea chatTexte;
    private JTextField chatLigne;
    private JTextField chatNomJoueur;

    private JLabel chatNomJoueurInfo;
    private JLabel tour;
    private Tools tools;
    private Model model;

    private JPanel panPrincipal,panJeu,panOption,panPteGrille,bateauxAffiche
            ,panCroiseur,panTorpilleur,panContreTorpilleur, fond1, panFond, panFond1
            ,panSousMarin,panPorteAvion,chatPanel,panGrille,nomPanel,fond, panValider;

    private GridBagLayout gbl_panel_1;

    public VueJeu(Model model){

        this.model = model;
        initAttribut();
        creerFenetreJeu();
        pack();
        setResizable(false);
        setTitle("Space Battle");
        setIconImage(new ImageIcon("images/Autres/icone.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initGrilleTexte() {
    	//updateTextCellsJoueur1();
    	//updateTextCellsJoueur2();
    }
    
    public void updateTourLabel() {
    	
    	if (model.tourJoueurIsFini()) {
    		tour.setText("Tour de : "+Model.getJoueur(2).getNomJoueur());
    	}
    	else {
    		tour.setText("Tour de : "+Model.getJoueur(1).getNomJoueur());
    	}
    	
    }

    public void resetTextChat() {
		
		chatTexte.setText("");
	}
    
    public static void appendToChatBox(String msg)
    {
    	chatTexte.append(msg + "\n");
    }
    
    public void updateTextCellsJoueur1() {
		
		for (int i = 0 ; i < Model.getTaillePlateau() ; i++)
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++)
 	    		pteGrilleJeu[i][j].setText(""+Model.getJoueur(1).getValeurTabJoueur(i, j));
	}
    
    public void updateTextCellsJoueur2() {
		
		for (int i = 0 ; i < Model.getTaillePlateau() ; i++)
 	    	for (int j = 0 ; j < Model.getTaillePlateau() ; j++)
 	    		grilleJeu[i][j].setText(""+Model.getJoueur(2).getValeurTabJoueur(i, j));
	}


    // Les initialisations
    public void initAttribut(){
        Tools tools = new Tools();
        Bateaux.initTabBateaux();
        Bateaux.initImagesBateaux();

        barreMenu = new JMenuBar();
        menu = new JMenu("Menu");
        tour = new JLabel("Tour du joueur");
        setNouvellePartie(new JMenuItem("Nouvelle Partie"));
        setQuitter(new JMenuItem("Quitter"));
        setAPropos(new JMenuItem("A propos"));

        creerBarreMenu();

        grilleJeu = new JButton[Model.getTaillePlateau()][Model.getTaillePlateau()];
        pteGrilleJeu = new JButton[Model.getTaillePlateau()][Model.getTaillePlateau()];

        initBateaux();
        initChat();
        initGrille(grilleJeu,50);
        initGrille(pteGrilleJeu,50);

        valider = new JButton("Valider");
        valider.setContentAreaFilled(false);

    }

    public void initChat(){
        chatTexte = new JTextArea(10, 20);
        chatTexte.setLineWrap(true);
        chatTexte.setEditable(false);
        chatLigne = new JTextField();
        chatLigne.setEnabled(true);

        chatNomJoueurInfo = new JLabel("Nom du joueur : ");
        chatNomJoueur = new JTextField();
        chatNomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        chatNomJoueur.setEnabled(true);
        chatNomJoueur.setText("Joueur1");
        chatNomJoueur.setColumns(6);

    }
    
    public void reiniPteGrille() {
    	for (int i = 0; i < pteGrilleJeu.length; i++){
            for (int j = 0; j < pteGrilleJeu.length; j++){
            	pteGrilleJeu[i][j].setActionCommand("" + i + "" + j);
            }
        }
    }

    public void initGrille(JButton[][] grilleJeu, int size){

        for (int i = 0; i < grilleJeu.length; i++){
            for (int j = 0; j < grilleJeu.length; j++){
                grilleJeu[i][j] = new JButton();
                grilleJeu[i][j].setVerticalTextPosition(SwingConstants.CENTER);
                grilleJeu[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                grilleJeu[i][j].setBackground(Color.lightGray);
                grilleJeu[i][j].setPreferredSize(new Dimension(size, size));
                grilleJeu[i][j].setActionCommand("" + i + "" + j);
                grilleJeu[i][j].setBorder(null);
            }
        }
    }
    
    public boolean tousLesBateauxSontPlace() {

        return !contreTorpilleurs.isEnabled() &&
                !torpilleur.isEnabled() &&
                !croiseur.isEnabled() &&
                !sousMarin.isEnabled() &&
                !porteAvion.isEnabled();
    	
    }
    
    public void reiniBtnBateaux() {
    	
    	contreTorpilleurs.setEnabled(true);
		torpilleur.setEnabled(true);
		croiseur.setEnabled(true);
		sousMarin.setEnabled(true);
		porteAvion.setEnabled(true);
    }
    
    // ne pas réutiliser
    public void repaintFantomeBateau() {
    	for (int i = 0; i < Model.getTaillePlateau(); i++){
            for (int j = 0; j < Model.getTaillePlateau(); j++){
            	if (Model.getJoueur(1).getTabJoueur()[i][j] == 0)
            		VueJeu.getPteGrilleJeu(i, j).setIcon(null);
            }
        }
    }
    
    public void repaintGrilleAdverseBateau() {
    	for (int i = 0; i < Model.getTaillePlateau(); i++)
            for (int j = 0; j < Model.getTaillePlateau(); j++)
            		VueJeu.getGrilleJeu(i, j).setIcon(null);
    }

    public void initBateaux(){
        contreTorpilleurs = new JButton();
        contreTorpilleurs.setIcon(Bateaux.imageBateau(Bateaux.CONTRETORPILLEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        contreTorpilleurs.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.CONTRETORPILLEUR).getNombreCases())+Bateaux.CONTRETORPILLEUR);
        contreTorpilleurs.setContentAreaFilled(false);

        torpilleur = new JButton();
        torpilleur.setIcon(Bateaux.imageBateau(Bateaux.TORPILLEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        torpilleur.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.TORPILLEUR).getNombreCases())+Bateaux.TORPILLEUR);
        torpilleur.setContentAreaFilled(false);

        croiseur = new JButton();
        croiseur.setIcon(Bateaux.imageBateau(Bateaux.CROISEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        croiseur.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.CROISEUR).getNombreCases())+Bateaux.CROISEUR);
        croiseur.setContentAreaFilled(false);

        sousMarin = new JButton();
        sousMarin.setIcon(Bateaux.imageBateau(Bateaux.SOUSMARIN, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        sousMarin.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.SOUSMARIN).getNombreCases())+Bateaux.SOUSMARIN);
        sousMarin.setContentAreaFilled(false);

        porteAvion = new JButton();
        porteAvion.setIcon(Bateaux.imageBateau(Bateaux.PORTEAVIONS, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        porteAvion.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.PORTEAVIONS).getNombreCases())+Bateaux.PORTEAVIONS);
        porteAvion.setContentAreaFilled(false);
    }

    public JPanel afficheGrille (JPanel panel, int version, JButton[][] grille) {
        switch (version) {
            case 1:
                for (int i = 0; i < grille.length; i++) {
                    for (int j = 0; j < grille.length; j++) {

                        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
                        gbc_btnNewButton.gridx = i;
                        gbc_btnNewButton.gridy = j;
                        panel.add(grille[i][j], gbc_btnNewButton);
                        grille[i][j].setOpaque(false);
                    }
                }
                panel.setOpaque(false);

                return panel;
            case 2:
                for (int i = 0; i < grille.length; i++) {
                    for (int j = 0; j < grille.length; j++) {

                        GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
                        gbc_btnNewButton2.gridx = i;
                        gbc_btnNewButton2.gridy = j;
                        panel.add(grille[i][j], gbc_btnNewButton2);
                    }
                }

                return panel;
        }
        return panel;
    }

    public void creerPanelGauche(){
        tools = new Tools();

        //COTE GAUCHE DE LA FENETRE
        nomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chatPanel = new JPanel(new BorderLayout());
        JScrollPane chatTextPane = new JScrollPane(chatTexte,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        chatPanel.add(chatLigne, BorderLayout.SOUTH);
        chatPanel.add(chatTextPane, BorderLayout.CENTER);
        chatPanel.setPreferredSize(new Dimension(200, 200));
        tools.changerFontJTextField(chatLigne, 15, Color.black, tools.getFontTexte());
        tools.changerFontJTextField(chatNomJoueur, 15, Color.black, tools.getFontTexte());

        nomPanel.add(chatNomJoueurInfo);
        nomPanel.add(chatNomJoueur);
        tools.changerFontJLabel(chatNomJoueurInfo, 13, Color.black, tools.getFontTexte());
        tools.changerFontJTextArea(chatTexte, 15, Color.black, tools.getFontTexte());
        chatPanel.add(nomPanel, BorderLayout.NORTH);


    }
    
    public void creerPanelDroite(){
        tools = new Tools();
        panValider = new JPanel();



        panContreTorpilleur.add(contreTorpilleurs);
        panTorpilleur.add(torpilleur);
        panSousMarin.add(sousMarin);
        panCroiseur.add(croiseur);
        panPorteAvion.add(porteAvion);


        bateauxAffiche.add(panContreTorpilleur);
        bateauxAffiche.add(panTorpilleur);
        bateauxAffiche.add(panSousMarin);
        bateauxAffiche.add(panCroiseur);
        bateauxAffiche.add(panPorteAvion);
        bateauxAffiche.setLayout(new BoxLayout(bateauxAffiche, BoxLayout.Y_AXIS));

    }
    public void changerPanelGrille(){

        panPrincipal = new JPanel();
        panJeu = new JPanel();
        panPteGrille = new JPanel();
        panGrille = new JPanel();
        panFond1 = new JPanel();
        panFond = new JPanel();
        gbl_panel_1 = new GridBagLayout();

        creerPanelGauche();

        panPrincipal.setLayout(new BorderLayout(0, 0));
        panPrincipal.add(chatPanel, BorderLayout.WEST);

        //TABLEAU DE JEU CENTRE DU PANEL

        fond = null;
        panPteGrille.setLayout(gbl_panel_1);
        panPteGrille.setOpaque(false);

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

        fond1 = null;
        panGrille.setLayout(gbl_panel_1);
        panGrille.setOpaque(false);

        try {
            fond1 = new JPanel() {
                BufferedImage image = ImageIO.read(new File("images/Autres/imageGrille.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        panPteGrille = afficheGrille(panPteGrille, 1, pteGrilleJeu);
        panGrille = afficheGrille(panGrille, 1, grilleJeu);
        fond.add(panPteGrille);
        fond1.add(panGrille);
        panFond.add(fond);
        panFond1.add(fond1);
        panJeu.add(panFond);
        panJeu.add(panFond1);
        panJeu.setLayout(new GridLayout(1, 2, 10, 10));
        panPrincipal.add(panJeu, BorderLayout.CENTER);

        setContentPane(panPrincipal);
        pack();
        setVisible(false);
        setVisible(true);
    }
    public void creerFenetreJeu() {

        barreMenu.setVisible(true);

        panValider = new JPanel();
        panPrincipal = new JPanel();
        panJeu = new JPanel();
        panOption = new JPanel();
        panPteGrille = new JPanel();
        panGrille = new JPanel();

        bateauxAffiche = new JPanel();
        panCroiseur = new JPanel();
        panTorpilleur = new JPanel();
        panContreTorpilleur = new JPanel();
        panSousMarin = new JPanel();
        panPorteAvion = new JPanel();
        gbl_panel_1 = new GridBagLayout();

        creerPanelGauche();

        panPrincipal.setLayout(new BorderLayout(0, 0));
        panPrincipal.add(chatPanel, BorderLayout.WEST);

        //TABLEAU DE JEU CENTRE DU PANEL

        fond = null;
        panPteGrille.setLayout(gbl_panel_1);
        panPteGrille.setOpaque(false);

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
        panPteGrille = afficheGrille(panPteGrille, 1, pteGrilleJeu);
        fond.add(panPteGrille);
        panJeu.add(fond);
        panPrincipal.add(panJeu, BorderLayout.CENTER);

        // affichage bateaux sur le cote et du terrain (COTE DROITE)
        creerPanelDroite();

        panContreTorpilleur.setLayout(gbl_panel_1);
        panTorpilleur.setLayout(gbl_panel_1);
        panSousMarin.setLayout(gbl_panel_1);
        panCroiseur.setLayout(gbl_panel_1);
        panPorteAvion.setLayout(gbl_panel_1);

        panOption.add(bateauxAffiche);
        tools.changerFontButton(valider, 30, Color.black, tools.getFontTexte());
        panValider.add(valider);
        panOption.add(panValider);
        panValider.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panOption.setLayout(new BoxLayout(panOption, BoxLayout.Y_AXIS));

        panPrincipal.add(panOption, BorderLayout.EAST);

        setContentPane(panPrincipal);

    }

    public void creerBarreMenu() {
        tools = new Tools();
        menu.add(getNouvellePartie());
        menu.addSeparator();
        menu.add(getaPropos());
        menu.addSeparator();
        menu.add(getQuitter());
        tools.changerFontJMenuItem(getNouvellePartie(), 15, Color.black, tools.getFontTexte());
        tools.changerFontJMenuItem(getaPropos(), 15, Color.black, tools.getFontTexte());
        tools.changerFontJMenuItem(getQuitter(), 15, Color.black, tools.getFontTexte());
        tools.changerFontJMenu(menu, 15, Color.black, tools.getFontTexte());
        barreMenu.add(menu);
        barreMenu.add(Box.createRigidArea(new Dimension(400,10)));
        barreMenu.add(tour);
        tools.changerFontJLabel(tour,15,Color.black,tools.getFontTexte());
        tools.changerFontJMenuBar(barreMenu, 15, Color.black, tools.getFontTexte());
        setJMenuBar(barreMenu);

    }

    // Les controlleurs
    public void setMenuControler(ActionListener listener){
        getNouvellePartie().addActionListener(listener);
        getQuitter().addActionListener(listener);
        getaPropos().addActionListener(listener);
    }

    public void setButtonControler(ActionListener listener, MouseListener listener2){

        for	(int i = 0 ; i < Model.getTaillePlateau() ; i++) {
            for	(int j = 0 ; j < Model.getTaillePlateau() ; j++) {
                grilleJeu[i][j].addActionListener(listener);
                pteGrilleJeu[i][j].addMouseListener(listener2);
            }
        }
            torpilleur.addActionListener(listener);
            contreTorpilleurs.addActionListener(listener);
            croiseur.addActionListener(listener);
            sousMarin.addActionListener(listener);
        porteAvion.addActionListener(listener);
        valider.addActionListener(listener);
    }

    public void setChatControler(KeyListener listener) {
        getChatLigne().addKeyListener(listener);
    }


    // Les set

    public void setNouvellePartie(JMenuItem nouvellePartie) {
        this.nouvellePartie = nouvellePartie;
    }

    public void setQuitter(JMenuItem quitter) {
        this.quitter = quitter;
    }

    public void setAPropos(JMenuItem aPropos){
        this.aPropos = aPropos;
    }


    // Les get
    public static JButton getGrilleJeu(int x, int y) {
        return grilleJeu[x][y];
    }
    
    public static JButton getPteGrilleJeu(int x, int y) {
        return pteGrilleJeu[x][y];
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

    public JButton getBtnBateau(int idBateau) {

        switch (idBateau)
        {
            case Bateaux.CONTRETORPILLEUR:
                return contreTorpilleurs;
            case Bateaux.TORPILLEUR:
                return torpilleur;
            case Bateaux.CROISEUR:
                return croiseur;
            case Bateaux.SOUSMARIN:
                return sousMarin;
            case Bateaux.PORTEAVIONS:
                return porteAvion;
        }

        return null;

    }

	public JMenuItem getNouvellePartie() {
		return nouvellePartie;
	}

	public JMenuItem getQuitter() {
		return quitter;
	}

    public JMenuItem getaPropos() {
        return aPropos;
    }

    public JButton getValider(){
        return valider;
    }

	public JButton getContreTorpilleurs() {
		return contreTorpilleurs;
	}

	public JButton getTorpilleur() {
		return torpilleur;
	}

	public JButton getPorteAvion() {
		return porteAvion;
	}

	public JButton getCroiseur() {
		return croiseur;
	}

	public JButton getSousMarin() {
		return sousMarin;
	}
}
