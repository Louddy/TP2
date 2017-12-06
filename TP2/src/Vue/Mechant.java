/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Xavie
 */
public class Mechant extends Ennemi {

    private Image img;
    private Image Front = Toolkit.getDefaultToolkit().getImage("Images/greenfront.gif");
    private Image Back = Toolkit.getDefaultToolkit().getImage("Images/greenback.gif");

    public Mechant() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);

    }

    
    public void setFront() {
        img = Front;
    }

    public void setBack() {
        img = Back;
    }

}
