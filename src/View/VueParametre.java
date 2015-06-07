package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Model;

import java.awt.BorderLayout;
import java.awt.Graphics;
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

    private JButton valider;
    private JButton annuler;

    public VueParametre(Model model){
        this.model = model;

        initAttribut();
        affichage();
        //pack();
        setSize(400, 300);

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
                BufferedImage image = ImageIO.read(new File("images/Autres/imageMenu.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, 400, 300, this);
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
        tools.changerFontJLabel(titre,15);
        tools.changerFontJLabel(IA,11);
        tools.changerFontButton(valider,10);
        tools.changerFontButton(annuler,10);
        tools.changerFontJRadioButton(IA1, 10);
        tools.changerFontJRadioButton(IA2, 10);
        tools.changerFontJRadioButton(IA3, 10);
        tools.changerFontJRadioButton(IA4, 10);
        tools.changerFontJCheckBox(checkBoxBonus, 10);

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
        panButton.setLayout(new BoxLayout(panButton, BoxLayout.X_AXIS));

        panMenu.add(panGButton);
        panMenu.add(panButton);

        panPrincipal.add(panTitre);
        panPrincipal.add(panIA);
        panPrincipal.add(panMenu);
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
