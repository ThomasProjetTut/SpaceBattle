package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Tools {
    private Font fontMenu,police,fontTexte;
    public Tools(){
        try {
            fontMenu = loadFont("font/Sailor-Stitch.ttf");
            fontTexte = loadFont("font/homespun.ttf");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    public static Font loadFont(String string) throws FontFormatException, IOException {
        File fileFont = new File(string);
        return Font.createFont(Font.TRUETYPE_FONT, fileFont);
    }

    public void changerFontButton(JButton bouton, int size, Color color ,Font font){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        bouton.setOpaque(false);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setForeground(color);
        bouton.setFont(police);
        bouton.setFocusPainted(false);
    }
    public void changerFontJLabel(JLabel label,int size, Color color ,Font font){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        label.setForeground(color);
        label.setOpaque(false);
        label.setFont(police);
    }
    public void changerFontJRadioButton(JRadioButton bouton, int size, Color color ,Font font){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        bouton.setForeground(color);
        bouton.setOpaque(false);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setFont(police);
    }
    public void changerFontJTextField(JTextField textField, int size, Color color, Font font){
        police = font.deriveFont(Font.TRUETYPE_FONT,size);
        textField.setForeground(color);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(color));
        textField.setFont(police);
    }
    public void changerFontJTextArea(JTextArea textArea,int size, Color color, Font font){
        police = font.deriveFont(Font.TRUETYPE_FONT,size);
        textArea.setForeground(color);
        textArea.setFont(police);
    }
    public Font getFontMenu() {
        return fontMenu;
    }

    public Font getFontTexte() {
        return fontTexte;
    }
}
