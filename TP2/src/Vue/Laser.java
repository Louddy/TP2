/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Olivier Hurtubise
 */
public class Laser extends Projectile {

    public Laser(Directions direction) {
        super(direction);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.setColor(Color.red);
        if (direction == Directions.HAUT || direction == Directions.BAS) {
            g.fillOval(0, 0, 5, 50);
        } else {
            g.fillOval(0, 0, 50, 5);
        }
    }
}
