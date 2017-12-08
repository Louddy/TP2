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
public class Balle extends Projectile {
    
    public Balle(Directions direction) {
        super(direction);
    }
   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.fillRect(0, 0, 10, 10);
    }
    
}
