package View;

import javax.swing.*;
import Model.Model;

import java.awt.event.ActionListener;

/**
 * Created by Zous on 06/06/2015.
 */
public class VueParametre extends JFrame {

    protected Model model;

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
        pack();

        setLocationRelativeTo(null);                   // Place la fenetre au milieu de l'ecran
        setResizable(false);

        setIconImage(new ImageIcon("images/Autres/icone.png").getImage());//Icone de la fenetre
        setTitle("Space Battle");              // Titre de la fenetre
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void initAttribut(){

        titre = new JLabel("Paramètrer la partie");
        IA = new JLabel("Choisir le niveau de difficulté :");
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

        JPanel panGlobal = new JPanel();
        JPanel panPrincipal = new JPanel();
        JPanel panTitre = new JPanel();
        JPanel panIA = new JPanel();
        JPanel panBut1 = new JPanel();
        JPanel panBut2 = new JPanel();
        JPanel panBut3 = new JPanel();
        JPanel panBut4 = new JPanel();
        JPanel panCheck = new JPanel();
        JPanel panButton = new JPanel();

        panCheck.add(checkBoxBonus);
        panIA.add(IA);
        panBut1.add(IA1);
        panBut2.add(IA2);
        panBut3.add(IA3);
        panBut4.add(IA4);
        panButton.add(valider);
        panButton.add(annuler);
        panButton.setLayout(new BoxLayout(panButton, BoxLayout.X_AXIS));

        panTitre.add(titre);
        panPrincipal.add(panTitre);
        panPrincipal.add(IA);
        panPrincipal.add(IA1);
        panPrincipal.add(IA2);
        panPrincipal.add(IA3);
        panPrincipal.add(IA4);
        panPrincipal.add(checkBoxBonus);
        panPrincipal.add(panButton);
        panPrincipal.setLayout(new BoxLayout(panPrincipal, BoxLayout.Y_AXIS));

        panGlobal.add(panPrincipal);
        panGlobal.setLayout(new BoxLayout(panGlobal, BoxLayout.Y_AXIS));

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
