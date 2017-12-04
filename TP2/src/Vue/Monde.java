/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Vue.Heros.Directions;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Olivier Hurtubise
 */
public class Monde extends JPanel {

    private Thread threadBouger;
    private BufferedImage floor;
    private Heros heros;
    private ArrayList<Obstacle> listeObstacles;

    public Monde() {
        try {
            floor = ImageIO.read(new File("Images\\floor2.gif"));
        } catch (IOException e) {
            System.out.println("Erreur IO");
        }
        listeObstacles = new ArrayList<Obstacle>();
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
        Obstacle entite;
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 17; j++) {
                if ((j == 0 || j == 16) && (i < 9 || i > 15)) {
                    entite = new Roche();
                    entite.setBounds(i * 32, j * 32, 32, 32);
                    this.add(entite);
                    listeObstacles.add(entite);
                } else if ((j < 5 || j > 11) && (i == 0 || i == 24)) {
                    entite = new Roche();
                    entite.setBounds(i * 32, j * 32, 32, 32);
                    this.add(entite);
                    listeObstacles.add(entite);
                } else if (j > 0 && j < 17 && i > 0 && i < 25
                        && rand.nextInt(100) == 3 && i != 16 && j != 8) {
                    entite = new Buisson();
                    entite.setBounds(i * 32, j * 32, 32, 32);
                    this.add(entite);
                    listeObstacles.add(entite);
                }
            }
        }
    }

    private void placerHeros() {
        heros = new Heros();
        heros.setBounds(16 * 32, 8 * 32, 22, 51);
        this.add(heros);
    }

    public void bougerHeros(Directions direction) {
        heros.setDirectionCourante(direction);
        threadBouger = new Thread(heros);
        threadBouger.start();
    }

    public void arreterHeros() {
        threadBouger.interrupt();
        heros.arreter();
    }
    
    public boolean verifierContact() {
        for(Obstacle obstacle: listeObstacles) {
            if(obstacle.getBounds().intersects(heros.getBounds())) {
                return true;
            }
        }
        return false;
    }
}
