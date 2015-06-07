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
    private JButton score;
    private JButton accueil;
    private JLabel titre;

    //USELESS
    private boolean changerFenetre;

    public VueMenu(){

        initAttribut();
        creerFenetreMenu();
        pack();
        setLocationRelativeTo(null);
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
        score = new JButton("Score");
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
        JPanel panScore = new JPanel();

        panButton.setOpaque(false);
        panTitre.setOpaque(false);
        panMenu.setOpaque(false);
        panPrincipal.setOpaque(false);
        panButton.setLayout(new BoxLayout(panButton, BoxLayout.Y_AXIS));
        panMenu.setLayout(new BoxLayout(panMenu, BoxLayout.Y_AXIS));
        panPrincipal.setLayout(new BorderLayout(50,50));


        JPanel fond = null;
        titre = new JLabel("Space Battle");
        tools.changerFontJLabel(titre,30,Color.white);
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
        panScore.add(score);
        panScore.setOpaque(false);

        tools.changerFontButton(instruction,14,Color.white);
        tools.changerFontButton(jouerM,14,Color.white);
        tools.changerFontButton(jouerS,14,Color.white);
        tools.changerFontButton(score,14,Color.white);


        panTitre.add(titre);
        panButton.add(panJouerS);
        panButton.add(panJouerM);
        panButton.add(panInstructions);
        panButton.add(panScore);

        panMenu.add(panButton,BorderLayout.CENTER);
        panPrincipal.add(panTitre,BorderLayout.NORTH);
        panPrincipal.add(panMenu,BorderLayout.WEST);

        fond.add(panPrincipal,BorderLayout.WEST);
        setContentPane(fond);

    }

    public void creerFenetreScore(){


        JPanel panPrincipal = new JPanel();
        panPrincipal.setOpaque(false);
        JPanel panScore = new JPanel();
        panScore.setOpaque(false);
        JPanel panBouton = new JPanel();
        panBouton.setOpaque(false);

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

        fond.add(panPrincipal);
        panBouton.add(accueil);

        panPrincipal.add(panBouton);
        panPrincipal.add(panScore);

        setContentPane(fond);

        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));
    }

    public void creerFenetreInstruction(){


        JPanel panPrincipal = new JPanel();
        panPrincipal.setOpaque(false);
        JPanel panInstructions = new JPanel();
        panInstructions.setOpaque(false);
        JPanel panBouton = new JPanel();
        panBouton.setOpaque(false);

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

        fond.add(panPrincipal);
        panBouton.add(accueil);

        panPrincipal.add(panBouton);
        panPrincipal.add(panInstructions);

        setContentPane(fond);
        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));

    }


    // Les controlleurs
    public void setMenuControler(ActionListener listener){
        jouerS.addActionListener(listener);
        jouerM.addActionListener(listener);
        instruction.addActionListener(listener);
        score.addActionListener(listener);
        accueil.addActionListener(listener);
    }

    public boolean getChangerFenetre() {
        return changerFenetre;}

    public JButton getJouerS() {
        return jouerS;
    }

    public JButton getJouerM() {
        return jouerM;
    }

    public JButton getInstruction() {
        return instruction;
    }

    public JButton getScore() {
        return score;
    }

    public JButton getAccueil() {
        return accueil;
    }


}
