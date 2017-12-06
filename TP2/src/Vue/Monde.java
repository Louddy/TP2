/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Vue.Heros.Directions;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Olivier Hurtubise
 */
public class Monde extends JPanel {

    private List<Ennemi> ennemis = new ArrayList<>();
    private BufferedImage floor;
    private Heros heros;

    private int skip = 0;
    private int skip2 = 0;
    private int timer = 0;
    private int level = 1;

    private ArrayList<Obstacle> listeObstacles;
    private ArrayList<Rectangle> listeIntersections = new ArrayList<Rectangle>();

    private Thread threadMAJ = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    majJeu();
                    Thread.sleep(3);
                    repaint();
                    revalidate();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    ex.printStackTrace();
                }
            }
        }
    };

    public Monde() {
        this.setLayout(null);
        try {
            floor = ImageIO.read(new File("Images\\floor2.gif"));
        } catch (IOException e) {
            System.out.println("Erreur IO");
        }
        listeObstacles = new ArrayList<Obstacle>();
        placerElements();
        placerHeros();

        threadMAJ.start();
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

//    public void arreterHeros() {
//        threadMAJ.interrupt();
//        heros.arreter();
//    }
//    private void placerMechant() {
//        mechant = new Mechant();
//        mechant.setBounds(8 * 32, 8 * 32, 35, 42);
//        this.add(mechant);
//    }
    public boolean verifierContact() {
        listeIntersections.clear();
        for (Obstacle obstacle : listeObstacles) {
            if (obstacle.getBounds().intersects(heros.getBounds())) {
                listeIntersections.add(obstacle.getBounds().intersection(heros.getBounds()));
            }
        }
        return !listeIntersections.isEmpty();
    }

    public boolean verifierBlocage() {
        boolean xGrand = false;
        boolean yGrand = false;
        /*for (int i = 0; i < listeIntersections.size(); i++) {
            System.out.println(listeIntersections.get(i));
        }*/
        if (!listeIntersections.isEmpty()) {
            for (Rectangle r : listeIntersections) {
                if (r.getHeight() > r.getWidth()) {
                    yGrand = true;
                } else if (r.getHeight() < r.getWidth()) {
                    xGrand = true;
                }
            }
            return yGrand && xGrand;
        }
        return false;
    }

    public void majJeu() {
        bougerhero();
        monsterTracking();
        timer();

    }

    private void monsterTracking() {
        //Si le monstre atteint le joueur, il disparait
        //reste a gerer la perte de coeur
        for (Ennemi ennemi : ennemis) {
            if (ennemi.getBounds().intersects(heros.getBounds())) {
                remove(ennemi);
            }
            //Logique de la poursuite des monstres
            if (heros.getX() > ennemi.getX()) {
                ennemi.bougerDroite();
                ennemi.setFront();

            }
            if (heros.getX() < ennemi.getX()) {
                ennemi.bougerGauche();
                ennemi.setFront();

            }
            if (heros.getY() > ennemi.getY()) {
                ennemi.bougerBas();
                ennemi.setFront();

            }
            if (heros.getY() < ennemi.getY()) {
                ennemi.bougerHaut();
                ennemi.setBack();

            }

            bougerEnnemi(ennemi);
        }

    }

    private void bougerEnnemi(Ennemi ennemi) {

        if (skip == 3) {
            ennemi.bouger();
            skip = 0;
        } else {

            skip++;
        }

    }

    private void bougerhero() {
        Directions direction = Heros.Directions.AUCUNE;
        switch (Fenetre.getToucheEnfoncee()) {
            case KeyEvent.VK_UP:
                direction = Heros.Directions.HAUT;
                break;
            case KeyEvent.VK_DOWN:
                direction = Heros.Directions.BAS;
                break;
            case KeyEvent.VK_RIGHT:
                direction = Heros.Directions.DROITE;
                break;
            case KeyEvent.VK_LEFT:
                direction = Heros.Directions.GAUCHE;
                break;
            case 0:
                direction = Heros.Directions.AUCUNE;
        }
        heros.bouger(getWidth(), getHeight(), direction);
    }

    private void spawnEnnemis() {
        System.out.println("debut");
        //ici on souhaite cree un systeme pour choisir un mur au hasard 
        //dependament du niveau on en choisira un certain nombre
        // on insteau la valeur -1 puisque 0 sera utilise comme mur
        boolean wall[] = new boolean[4];
        wall[0] = false;
        wall[1] = false;
        wall[2] = false;
        wall[3] = false;
        level = (timer / 5) + 1;
        System.out.println(level);
        Random r = new Random();
        wall[r.nextInt(4)] = true;
        System.out.println("premier wall");
        if (level >= 3) {
            boolean bool = false;
            do {
                int temp = r.nextInt(4);
                if (!wall[temp]) {
                    wall[temp] = true;
                    bool = true;
                    System.out.println("deuxieme wall");
                }
            } while (!bool);
        }
        if (level >= 6) {
            boolean bool = false;
            do {
                int temp = r.nextInt(4);
                if (!wall[temp]) {
                    wall[temp] = true;
                    bool = true;
                    System.out.println("troisieme wall");
                }
            } while (!bool);
        }

        if (level >= 10) {
            for (int i = 0; i < 4; i++) {
                wall[i] = true;
            }
            System.out.println("dernier");
        }

        //On spawn les monstres au(x) mur(s) choisi
        System.out.println("xd");
        for (int i = 0; i < 4; i++) {
            
            if (wall[i]) {
                switch (i) {

                    case 0:
                        addEnemis(1, 596 / 2 + 1, 35, 42);

                        break;
                    case 1:
                        addEnemis(806 / 2 + 1, 1, 35, 42);

                        break;
                    case 2:
                        addEnemis(806 + 1, 596 / 2 + 1, 35, 42);

                        break;
                    case 3:
                        addEnemis(806 / 2 + 1, 596 + 1, 35, 42);

                }

            }
        }

    }

    private void timer() {
        if (skip2 == 1000) {
            timer++;
            spawnEnnemis();
            skip2 = 0;
        } else {
            skip2++;
        }
    }

    private void addEnemis(int x, int y, int w, int h) {
        ennemis.add(new Mechant());
        this.add(ennemis.get(ennemis.size() - 1));
        ennemis.get(ennemis.size() - 1).setBounds(x, y, w, h);
        if (level >= 2) {
            ennemis.add(new PasFin());
            this.add(ennemis.get(ennemis.size() - 1));
            ennemis.get(ennemis.size() - 1).setBounds(x+35, y, w, h);
        }
        if (level >= 5) {
            ennemis.add(new TroubleFete());
            this.add(ennemis.get(ennemis.size() - 1));
            ennemis.get(ennemis.size() - 1).setBounds(x-35, y, w, h);

        }
    }
}
