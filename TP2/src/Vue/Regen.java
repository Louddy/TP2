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
public class Regen extends PowerUp{
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        g.fillOval(0, 0, 32, 32);
        g.setColor(Color.red);
        g.fillOval(8, 8, 16, 16);
        g.setColor(Color.yellow);
        g.fillOval(12, 12, 8, 8);
    }
}
