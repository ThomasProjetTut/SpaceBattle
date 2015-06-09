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
    private static JButton [][] tableauPasserelle;
    private JButton contreTorpilleurs;
    private JButton torpilleur;
    private JButton porteAvion;
    private JButton croiseur;
    private JButton sousMarin;
    private JButton valider;

    private static JTextArea chatTexte;
    private JTextField chatLigne;
    private JTextField chatNomJoueur;
    private JTable tableJoueur1;

    private JLabel chatNomJoueurInfo;

    private Model model;
    private String nomJoueur;

    private JPanel panPrincipal,panJeu,panOption,panPteGrille,bateauxAffiche
            ,panCroiseur,panTorpilleur,panContreTorpilleur
            ,panSousMarin,panPorteAvion,chatPanel,panGrille,nomPanel,fond,panBouton, panValider;

    private GridBagLayout gbl_panel_1;

    public VueJeu(Model model){

        this.model = model;
        initAttribut();
        creerFenetreJeu();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Space Battle");
        setIconImage(new ImageIcon("images/Autres/icone.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void initGrilleTexte() {
    	updateTextCellsJoueur1();
    	updateTextCellsJoueur2();
    }

    public void resetTextChat() {
		
		chatTexte.setText("");
	}
    
    public static void appendToChatBox(String msg)
    {
    	chatTexte.append(msg+"\n");
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
        Bateaux.initTabBateaux();
        Bateaux.initImagesBateaux();

        barreMenu = new JMenuBar();
        menu = new JMenu("Menu");
        setNouvellePartie(new JMenuItem("Nouvelle Partie"));
        setQuitter(new JMenuItem("Quitter"));
        setAPropos(new JMenuItem("A propos"));

        creerBarreMenu();

        grilleJeu = new JButton[Model.getTaillePlateau()][Model.getTaillePlateau()];
        pteGrilleJeu = new JButton[Model.getTaillePlateau()][Model.getTaillePlateau()];

        initBateaux();

        valider = new JButton("Valider");

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
        chatNomJoueur.setText("Joueur1");
        chatNomJoueur.setColumns(8);

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
		
    	if (!contreTorpilleurs.isEnabled() &&
    			!torpilleur.isEnabled() &&
    			!croiseur.isEnabled() &&
    			!sousMarin.isEnabled() &&
    			!porteAvion.isEnabled())
    		return true;
    	else
    		return false;
    	
    }
    
    public void reiniBtnBateaux() {
    	
    	contreTorpilleurs.setEnabled(true);
		torpilleur.setEnabled(true);
		croiseur.setEnabled(true);
		sousMarin.setEnabled(true);
		porteAvion.setEnabled(true);
    }

    public void echangerGrille(){

        tableauPasserelle = new JButton[10][10];

        for (int i = 0; i < tableauPasserelle.length; i++){
            for (int j = 0; j < tableauPasserelle.length; j++){
                tableauPasserelle[i][j] = new JButton();
                tableauPasserelle[i][j] = grilleJeu[i][j];
                grilleJeu[i][j] = pteGrilleJeu[i][j];
                pteGrilleJeu[i][j] = tableauPasserelle[i][j];
            }
        }
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

        torpilleur = new JButton();
        torpilleur.setIcon(Bateaux.imageBateau(Bateaux.TORPILLEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        torpilleur.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.TORPILLEUR).getNombreCases())+Bateaux.TORPILLEUR);

        croiseur = new JButton();
        croiseur.setIcon(Bateaux.imageBateau(Bateaux.CROISEUR, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        croiseur.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.CROISEUR).getNombreCases())+Bateaux.CROISEUR);

        sousMarin = new JButton();
        sousMarin.setIcon(Bateaux.imageBateau(Bateaux.SOUSMARIN, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        sousMarin.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.SOUSMARIN).getNombreCases())+Bateaux.SOUSMARIN);

        porteAvion = new JButton();
        porteAvion.setIcon(Bateaux.imageBateau(Bateaux.PORTEAVIONS, Bateaux.HORIZONTAL,Bateaux.COMPLET, Bateaux.SANSETAT));
        porteAvion.setActionCommand(Integer.toString(Bateaux.getTabBateaux().get(Bateaux.PORTEAVIONS).getNombreCases())+Bateaux.PORTEAVIONS);
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

        //COTE GAUCHE DE LA FENETRE
        nomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chatPanel = new JPanel(new BorderLayout());
        JScrollPane chatTextPane = new JScrollPane(chatTexte,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        chatPanel.add(chatLigne, BorderLayout.SOUTH);
        chatPanel.add(chatTextPane, BorderLayout.CENTER);
        chatPanel.setPreferredSize(new Dimension(200, 200));

        nomPanel.add(chatNomJoueurInfo);
        nomPanel.add(chatNomJoueur);
        chatPanel.add(nomPanel, BorderLayout.NORTH);


    }
    
    public void creerPanelDroite(){

        panValider = new JPanel();
        panValider.add(valider);
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

        panOption.add(bateauxAffiche);

        // PANEL DE LA GRILLE A AFFICHER AU MILIEU
        if (!tousLesBateauxSontPlace()){
            panOption.add(panValider);
        }
        panOption.add(panGrille);
        panOption.setLayout(new BoxLayout(panOption, BoxLayout.Y_AXIS));

        panPrincipal.add(panOption, BorderLayout.EAST);

    }

    public void creerFenetreJeu(){

        barreMenu.setVisible(true);
        initChat();
        //La grille de placement de plateau est situé au milieu
        initGrille(pteGrilleJeu, 50);
        //La grille de jeu pdt la game est placé en bas à droite
        initGrille(grilleJeu,20);

        panPrincipal = new JPanel();
        panJeu = new JPanel();
        panOption = new JPanel();
        panPteGrille = new JPanel();
        panGrille = new JPanel();
        panBouton = new JPanel();

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
        //panGrille = afficheGrille(panGrille);
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
        panGrille.setLayout(gbl_panel_1);
        panGrille = afficheGrille(panGrille,2,grilleJeu);

        panOption.add(bateauxAffiche);
        panOption.add(panGrille);
        panOption.setLayout(new BoxLayout(panOption, BoxLayout.Y_AXIS));

        panPrincipal.add(panOption, BorderLayout.EAST);

        setContentPane(panPrincipal);

    }

    public void creerBarreMenu() {

        menu.add(getNouvellePartie());
        menu.addSeparator();
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

	public static JButton[][] getGrilleJeu() {
		return grilleJeu;
	}

    public static JButton[][] getTableauPasserelle(){
        return tableauPasserelle;
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

	public static JButton[][] getPteGrilleJeu() {
		return pteGrilleJeu;
	}
}
