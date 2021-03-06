package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Model;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueMenu extends JFrame{

    private Tools tools;
    private JButton jouerS;
    private JButton jouerM;
    private JButton instruction;
    private JButton accueil;
    private JLabel titre;
    private JLabel texte;

    public VueMenu(){

        initAttribut();
        creerFenetreMenu();
        pack();
        setResizable(false);
        setTitle("Space Battle");
        setSize(800, 600);
        setIconImage(new ImageIcon("images/Autres/icone.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //Les initialisations
    public void initAttribut(){
        tools = new Tools();
        jouerS = new JButton("Jeu Solo");
        jouerM = new JButton("Jeu Multi");
        instruction = new JButton("Instructions");
        accueil = new JButton ("Menu");
    }
    // Creer les différents aspects de la fenetre
    public void creerFenetreMenu(){

        JPanel panPrincipal = new JPanel();
        JPanel panMenu = new JPanel();
        JPanel panButton =  new JPanel();
        JPanel panTitre = new JPanel();
        JPanel panJouerS = new JPanel();
        JPanel panJouerM = new JPanel();
        JPanel panInstructions = new JPanel();

        panButton.setOpaque(false);
        panTitre.setOpaque(false);
        panMenu.setOpaque(false);
        panPrincipal.setOpaque(false);
        panButton.setLayout(new BoxLayout(panButton, BoxLayout.Y_AXIS));
        panMenu.setLayout(new BoxLayout(panMenu, BoxLayout.Y_AXIS));
        panPrincipal.setLayout(new BorderLayout(50,50));


        JPanel fond = null;
        titre = new JLabel("Space Battle");
        tools.changerFontJLabel(titre,33,Color.white,tools.getFontMenu());
        try {
            fond = new JPanel() {
                BufferedImage image = ImageIO.read(new File("images/Autres/imageMenu.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, 800, 600, this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        fond.setLayout(new BorderLayout());
        panJouerS.add(jouerS);
        panJouerS.setOpaque(false);
        panJouerM.add(jouerM);
        panJouerM.setOpaque(false);
        panInstructions.add(instruction);
        panInstructions.setOpaque(false);

        tools.changerFontButton(instruction,30,Color.white,tools.getFontTexte());
        tools.changerFontButton(jouerM,30,Color.white,tools.getFontTexte());
        tools.changerFontButton(jouerS,30,Color.white,tools.getFontTexte());


        panTitre.add(titre);
        panButton.add(panJouerS);
        panButton.add(panJouerM);
        panButton.add(panInstructions);

        panMenu.add(panButton,BorderLayout.CENTER);
        panPrincipal.add(panTitre,BorderLayout.NORTH);
        panPrincipal.add(panMenu,BorderLayout.WEST);

        fond.add(panPrincipal,BorderLayout.WEST);
        setContentPane(fond);

    }

    public void creerFenetreInstruction(){

        titre = new JLabel("Comment jouer : ");
        texte = new JLabel();
        texte.setText("<html><br><br>Pour placer vos bateaux, cliquez une fois sur le bateau<br><br> que vous voulez selectionner." +
                " Puis déplacez le sur la grille<br><br> à l'endroit où vous voulez le placer.<br><br>" +
                "Vous pouvez changer l'orientation de votre bateau avec le clic droit.<br><br>" +
                "Pour gagner, détruisez les cinq vaisseaux adverses en premier.<br><br></html>");

        JPanel panPrincipal = new JPanel();
        panPrincipal.setOpaque(false);
        JPanel panBouton = new JPanel();
        panBouton.setOpaque(false);
        JPanel panTexte = new JPanel();
        panTexte.setOpaque(false);
        JPanel panTitre = new JPanel();
        panTitre.setOpaque(false);

        JPanel fond = null;

        try {
            fond = new JPanel() {
                BufferedImage image = ImageIO.read(new File("images/Autres/imageMenu.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, 800, 600, this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        tools.changerFontJLabel(titre, 30, Color.white, tools.getFontTexte());
        tools.changerFontJLabel(texte,20,Color.white,tools.getFontTexte());
        tools.changerFontButton(accueil, 30, Color.white, tools.getFontTexte());

        panTexte.add(texte);
        panTitre.add(titre);

        fond.add(panPrincipal);
        panBouton.add(accueil);

        panPrincipal.add(panTitre);
        panPrincipal.add(panTexte);
        panPrincipal.add(panBouton);

        setContentPane(fond);
        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));
        pack();

    }


    // Les controlleurs
    public void setMenuControler(ActionListener listener){
        jouerS.addActionListener(listener);
        jouerM.addActionListener(listener);
        instruction.addActionListener(listener);
        accueil.addActionListener(listener);
    }

    public JButton getJouerS() {
        return jouerS;
    }

    public JButton getJouerM() {
        return jouerM;
    }

    public JButton getInstruction() {
        return instruction;
    }

    public JButton getAccueil() {
        return accueil;
    }


}
