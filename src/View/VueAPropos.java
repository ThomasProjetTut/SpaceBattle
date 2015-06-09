package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Model;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueAPropos extends JFrame {

    protected Model model;
    private Tools tools;
    private JLabel titre,texte;

    private JButton okay;

    public VueAPropos(){

        initAttribut();
        affichage();

        setLocationRelativeTo(null);                   // Place la fenetre au milieu de l'ecran
        setResizable(false);
        pack();
        setIconImage(new ImageIcon("images/Autres/icone.png").getImage());//Icone de la fenetre
        setTitle("Space Battle");              // Titre de la fenetre
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void initAttribut(){
        tools = new Tools();
        titre = new JLabel("A propos de nous : ");
        texte = new JLabel();
        texte.setText("<html>Ce projet a été réalisé par l'équipe de thomasprojettut,<br><br>" +
                "Chef de projet : Daloz Thomas,<br> Membres : Bigé Mégane, Crelier Nicolas, Hecht Adam, Janod Lucie,<br>" +
                "Lim Richard, Royer Félix, Van Der Post Johan.<br><br> La musique a été créee par Planetary Confinement <br>et les " +
                "images trouvées sur Google.<br><br> Nous remercions également notre tuteur<br> Mr Hakem Mourad pour son suivi et " +
                "ses conseils.</html>");
        okay = new JButton("OK");

    }

    public void affichage(){

        JPanel panGlobal = null;
        JPanel panPrincipal = new JPanel();
        JPanel panTexte = new JPanel();
        JPanel panTitre = new JPanel();
        // image de fond
        try {
            panGlobal = new JPanel() {
                BufferedImage image = ImageIO.read(new File("images/Autres/fondText.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, 800, 600, this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        // faire ressortir l'image de fond
        panGlobal.setOpaque(false);
        panPrincipal.setOpaque(false);
        panTitre.setOpaque(false);
        panTexte.setOpaque(false);

        //changement de la police
        tools.changerFontJLabel(titre, 30, Color.white, tools.getFontTexte());
        tools.changerFontJLabel(texte,20,Color.white,tools.getFontTexte());
        tools.changerFontButton(okay,30,Color.white,tools.getFontTexte());

        panTexte.add(texte);
        panTitre.add(titre);

        panPrincipal.add(panTitre);
        panPrincipal.add(panTexte);
        panPrincipal.add(okay);

        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));

        panGlobal.setLayout(new BoxLayout(panGlobal, BoxLayout.Y_AXIS));

        panGlobal.add(panPrincipal);

        setContentPane(panGlobal);
    }

    public void setAProposController(ActionListener listener){
        okay.addActionListener(listener);
    }

    public JButton getOkay() {
        return okay;
    }
}
