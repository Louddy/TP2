/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Olivier Hurtubise
 */
public class Monde extends JPanel {
    
    private BufferedImage floor;
    private Heros heros;
    
    public Monde() {
        this.setLayout(new BorderLayout());
        try {
            floor = ImageIO.read(new File("Images\\floor2.gif"));
        } catch (IOException e) {
            System.out.println("Erreur IO");
        }
        placerElements();
        placerHeros();
        this.setLayout(null);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                g.drawImage(floor, i * 32, j * 32, this);
            }
        }
    }

    private void placerElements() {
        Random rand = new Random();
        Entite entite;
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                if ((j == 0 || j == 16) && (i < 9 || i > 15)) {
                    entite = new Roche();
                    entite.setBounds(i * 32, j * 32, 32, 32);
                    this.add(entite);
                } else if ((j < 5 || j > 11) && (i == 0 || i == 24)) {
                    entite = new Roche();
                    entite.setBounds(i * 32, j * 32, 32, 32);
                    this.add(entite);
                } else if (j > 0 && j < 17 && i > 0 && i < 25
                        && rand.nextInt(100) == 3 && i != 16 && j != 8) {
                    entite = new Buisson();
                    entite.setBounds(i * 32, j * 32, 32, 32);
                    this.add(entite);
                } 
            }
        }
    }
    
    private void placerHeros() {
        heros = new Heros();
        heros.setBounds(16*32, 8*32, 22, 51);
        this.add(heros);
    }
    
    public void monterHeros() {
        heros.bouger(this.getHeight(), this.getWidth(), Heros.Directions.HAUT);
    }
    
    private void majJeu() {
        
    }
}
