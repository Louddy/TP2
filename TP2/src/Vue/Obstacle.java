/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Olivier Hurtubise
 */
public abstract class Obstacle extends JComponent {

    private BufferedImage obstacle;

    public Obstacle(File fichier) {
        try {
            obstacle = ImageIO.read(fichier);
        } catch (IOException e) {
            System.out.println("Erreur IO");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(obstacle, 0, 0, this);
    }
}
