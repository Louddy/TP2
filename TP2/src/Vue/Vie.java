/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

/**
 *
 * @author Olivier Hurtubise
 */
public class Vie extends JComponent {

    private final Image img = Toolkit.getDefaultToolkit().createImage("Images/coeur.gif");
    private int vieRestante = 3;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vieRestante >= 1) {
            g.drawImage(img, 0, 0, this);
        }
        if (vieRestante >= 2) {
            g.drawImage(img, 32, 0, this);
        }
        if (vieRestante >= 3) {
            g.drawImage(img, 64, 0, this);
        }
    }
    
    public void setVieRestante(int vie) {
        vieRestante = vie;
        invalidate();
        repaint();
    }
}
