package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import Joueurs.Joueurs;
import Model.Model;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueMenu extends JFrame{

   private Model model;

    private JButton jouerS;
    private JButton jouerM;
    private JButton instruction;
    private JButton score;
    private JButton accueil;
    private Font font,police,policeTitre;
    private JLabel titre;

    private boolean changerFenetre;

    public VueMenu(Model model){
        this.model = model;

        initAttribut();
        creerFenetreMenu();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Bataille Navale");
        setSize(800, 600);
        setIconImage(new ImageIcon("icone.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static Font loadFont(String string) throws FontFormatException, IOException {
        File fileFont = new File(string);
        Font fontLoaded = Font.createFont(Font.TRUETYPE_FONT, fileFont);
        return fontLoaded;
    }

//Les initialisations
    public void initAttribut(){
        jouerS = new JButton("Jeu Solo");
        jouerM = new JButton("Jeu Multi");
        instruction = new JButton("Instructions");
        score = new JButton("Score");
        accueil = new JButton ("Menu");

        try {
            font = loadFont("font/Sailor-Stitch.ttf");
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void changerFontButton(JButton bouton){
        bouton.setOpaque(false);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setForeground(Color.white);
        bouton.setFont(police);
        bouton.setFocusPainted(false);
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
        police = new Font(font.getName(),Font.TRUETYPE_FONT,14);
        policeTitre = new Font(font.getName(),Font.TRUETYPE_FONT,30);
        titre.setFont(policeTitre);
        titre.setForeground(Color.white);
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

        changerFontButton(instruction);
        changerFontButton(jouerM);
        changerFontButton(jouerS);
        changerFontButton(score);


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
