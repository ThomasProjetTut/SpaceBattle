package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class Tools {
    private Font font,police;
    public Tools(){
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
    public static Font loadFont(String string) throws FontFormatException, IOException {
        File fileFont = new File(string);
        Font fontLoaded = Font.createFont(Font.TRUETYPE_FONT, fileFont);
        return fontLoaded;
    }

    public void changerFontButton(JButton bouton, int size){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        bouton.setOpaque(false);
        bouton.setBorderPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.setForeground(Color.white);
        bouton.setFont(police);
        bouton.setFocusPainted(false);
    }
    public void changerFontJLabel(JLabel label,int size){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        label.setForeground(Color.white);
        label.setOpaque(false);
        label.setFont(police);
    }
    public void changerFontJRadioButton(JRadioButton bouton, int size){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        bouton.setForeground(Color.white);
        bouton.setOpaque(false);
        bouton.setFont(police);
    }
    public void changerFontJCheckBox(JCheckBox checkbox, int size){
        police = font.deriveFont(Font.TRUETYPE_FONT, size);
        checkbox.setForeground(Color.white);
        checkbox.setOpaque(false);
        checkbox.setFont(police);
    }
}
