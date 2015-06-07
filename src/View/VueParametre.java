package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Model;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Zous on 06/06/2015.
 */
public class VueParametre extends JFrame {

    protected Model model;
    private Tools tools;
    private JLabel titre;
    private JLabel IA;

    private JCheckBox checkBoxBonus;

    private JRadioButton IA1;
    private JRadioButton IA2;
    private JRadioButton IA3;
    private JRadioButton IA4;

    private ButtonGroup buttonGroup;

    private JButton valider,annuler,vide;

    public VueParametre(Model model){
        this.model = model;

        initAttribut();
        affichage();
        setSize(600, 400);

        setLocationRelativeTo(null);                   // Place la fenetre au milieu de l'ecran
        setResizable(false);

        setIconImage(new ImageIcon("images/Autres/icone.png").getImage());//Icone de la fenetre
        setTitle("Space Battle");              // Titre de la fenetre
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void initAttribut(){
        tools = new Tools();
        titre = new JLabel("Paramétrer la partie");
        IA = new JLabel("Choisir le niveau de difficulté");
        IA1 = new JRadioButton("Ballade de plaisance", true);
        IA2 = new JRadioButton("Survie dans l'espace");
        IA3 = new JRadioButton("Guerre interstellaire");
        IA4 = new JRadioButton("Annihilation extraterrestre");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(IA1);
        buttonGroup.add(IA2);
        buttonGroup.add(IA3);
        buttonGroup.add(IA4);

        checkBoxBonus = new JCheckBox("Activer les bonus");

        valider = new JButton("Valider");
        annuler = new JButton("Annuler");

    }

    public void affichage(){

        JPanel panGlobal = null;
        JPanel panPrincipal = new JPanel();
        JPanel panTitre = new JPanel();
        JPanel panMenu = new JPanel();
        JPanel panIA = new JPanel();
        JPanel panGButton = new JPanel();
        JPanel panBut1 = new JPanel();
        JPanel panBut2 = new JPanel();
        JPanel panBut3 = new JPanel();
        JPanel panBut4 = new JPanel();
        JPanel panCheck = new JPanel();
        JPanel panButton = new JPanel();
        // image de fond
        try {
            panGlobal = new JPanel() {
                BufferedImage image = ImageIO.read(new File("images/Autres/fondText.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, 600, 400, this);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        // faire ressortir l'image de fond
        panGlobal.setOpaque(false);
        panPrincipal.setOpaque(false);
        panMenu.setOpaque(false);
        panTitre.setOpaque(false);
        panIA.setOpaque(false);
        panBut1.setOpaque(false);
        panBut2.setOpaque(false);
        panBut3.setOpaque(false);
        panBut4.setOpaque(false);
        panGButton.setOpaque(false);
        panCheck.setOpaque(false);
        panButton.setOpaque(false);

        //changement de la police
        tools.changerFontJLabel(titre, 50, Color.white, tools.getFontTexte());
        tools.changerFontJLabel(IA, 25, Color.white, tools.getFontTexte());
        tools.changerFontButton(valider, 25, Color.white, tools.getFontTexte());
        tools.changerFontButton(annuler, 25, Color.white, tools.getFontTexte());
        tools.changerFontJRadioButton(IA1, 25, Color.white, tools.getFontTexte());
        tools.changerFontJRadioButton(IA2, 25, Color.white, tools.getFontTexte());
        tools.changerFontJRadioButton(IA3, 25, Color.white, tools.getFontTexte());
        tools.changerFontJRadioButton(IA4, 25, Color.white, tools.getFontTexte());
        tools.changerFontJCheckBox(checkBoxBonus, 25, Color.white, tools.getFontTexte());

        valider.setBorder(BorderFactory.createLineBorder(Color.white));
        valider.setBorderPainted(true);
        annuler.setBorder(BorderFactory.createLineBorder(Color.white));
        annuler.setBorderPainted(true);
        panTitre.add(titre);
        panCheck.add(checkBoxBonus);
        panIA.add(IA);
        panBut1.add(IA1);
        panBut2.add(IA2);
        panBut3.add(IA3);
        panBut4.add(IA4);
        panGButton.add(panBut1);
        panGButton.add(panBut2);
        panGButton.add(panBut3);
        panGButton.add(panBut4);
        panGButton.add(panCheck);
        panGButton.setLayout(new BoxLayout(panGButton, BoxLayout.Y_AXIS));
        panButton.add(valider);
        panButton.add(annuler);
        panButton.setLayout(new GridLayout(1,2));

        panMenu.add(panGButton);

        panPrincipal.add(panTitre);
        panPrincipal.add(panIA);
        panPrincipal.add(panMenu);
        panPrincipal.add(panButton);
        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));

        panGlobal.setLayout(new BoxLayout(panGlobal, BoxLayout.Y_AXIS));

        panGlobal.add(panPrincipal);



        setContentPane(panGlobal);
    }

    public void setControlParametre(ActionListener listener){
        checkBoxBonus.addActionListener(listener);
        IA1.addActionListener(listener);
        IA2.addActionListener(listener);
        IA3.addActionListener(listener);
        IA4.addActionListener(listener);
        valider.addActionListener(listener);
        annuler.addActionListener(listener);
    }


    public JButton getValider(){
        return valider;
    }

    public JButton getAnnuler(){
        return annuler;
    }

    public JRadioButton getIA1() {
        return IA1;
    }

    public JRadioButton getIA2() {
        return IA2;
    }

    public JRadioButton getIA3() {
        return IA3;
    }

    public JRadioButton getIA4() {
        return IA4;
    }

    public JCheckBox getCheckBoxBonus(){
        return checkBoxBonus;
    }
}
