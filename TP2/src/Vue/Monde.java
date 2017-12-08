/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import com.sun.glass.events.KeyEvent;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
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

    private ArrayList<Ennemi> listeEnnemis = new ArrayList<>();
    private final ArrayList<Obstacle> listeObstacles;
    private ArrayList<Projectile> listeProjectiles = new ArrayList<>();
    private ArrayList<PowerUp> listePowerUps = new ArrayList<>();
    private BufferedImage floor;
    private Heros heros;

    private int skip = 11;
    private int skip2 = 0;
    private int timer = 0;
    private int level = 1;
    private int tempsRestantTripleShot = 0;

    private Thread threadMAJ = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    majJeu();
                    Thread.sleep(15);
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
        //5 de + pour obtacles
        heros.setBounds(16 * 32, 8 * 32, 22, 51);
        this.add(heros);
    }

    private boolean verifierContactObstacleHeros() {

        for (Obstacle obstacle : listeObstacles) {
            if (obstacle.getBounds().intersects(heros.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private boolean verifierContactTentacule(Ennemi tentaculeComparee) {
        for (Ennemi tentacule : listeEnnemis) {
            if (tentacule != tentaculeComparee) {
                if (tentaculeComparee.getBounds().intersects(tentacule.getBounds())) {
                    return true;
                }
            }
        }
        for (Obstacle obstacle : listeObstacles) {
            if (tentaculeComparee.getBounds().intersects(obstacle.getBounds())) {
                tentaculeComparee.setIntersection(tentaculeComparee.getBounds()
                        .intersection(obstacle.getBounds()));
                return true;
            }
        }
        return false;
    }

    private void majJeu() {
        bougerhero();
        monsterTracking();
        if (Fenetre.barreEstEnfoncee() && skip > 30) {
            skip = 0;
            tirer(tempsRestantTripleShot);
        }
        skip++;
        bougerLaser();
        collisionLasers();
        timer();
    }

    private void monsterTracking() {
        //Si le monstre atteint le joueur, il disparait
        //reste a gerer la perte de coeur
        ArrayList<Ennemi> listeEnnemisAEnlever = new ArrayList<>();
        for (Ennemi ennemi : listeEnnemis) {
            Point locationOriginale = new Point(ennemi.getLocation());
            Point locationActuelle = new Point(ennemi.getLocation());
            //Logique de la poursuite des monstres
            if (heros.getX() > ennemi.getX()) {
                ennemi.bougerDroite();
                if (ennemi.getBounds().intersects(heros.getBounds())) {
                    remove(ennemi);
                    listeEnnemisAEnlever.add(ennemi);
                }
                if (verifierContactTentacule(ennemi)) {
                    ennemi.setLocation(locationActuelle);
                }
                ennemi.setFront();
            }
            locationActuelle = new Point(ennemi.getLocation());
            if (heros.getX() < ennemi.getX()) {
                ennemi.bougerGauche();
                ennemi.setFront();
                if (ennemi.getBounds().intersects(heros.getBounds())) {
                    remove(ennemi);
                    listeEnnemisAEnlever.add(ennemi);
                }
                if (verifierContactTentacule(ennemi)) {
                    ennemi.setLocation(locationActuelle);
                }
            }
            locationActuelle = new Point(ennemi.getLocation());
            if (heros.getY() > ennemi.getY()) {
                ennemi.bougerBas();
                ennemi.setFront();
                if (ennemi.getBounds().intersects(heros.getBounds())) {
                    remove(ennemi);
                    listeEnnemisAEnlever.add(ennemi);
                }
                if (verifierContactTentacule(ennemi)) {
                    ennemi.setLocation(locationActuelle);
                }
            }
            locationActuelle = new Point(ennemi.getLocation());
            if (heros.getY() < ennemi.getY()) {
                ennemi.bougerHaut();
                ennemi.setBack();
                if (ennemi.getBounds().intersects(heros.getBounds())) {
                    remove(ennemi);
                    listeEnnemisAEnlever.add(ennemi);
                }
                if (verifierContactTentacule(ennemi)) {
                    ennemi.setLocation(locationActuelle);
                }
            }
        }
        for (Ennemi ennemiAEnlever : listeEnnemisAEnlever) {
            if (ennemiAEnlever != null) {
                Random rand = new Random();
                if (rand.nextInt(10) == 0) {
                    PowerUp powerUp = new PowerUp();
                    powerUp.setBounds(ennemiAEnlever.getX(),
                            ennemiAEnlever.getY(), 32, 32);
                    add(powerUp);
                    listePowerUps.add(powerUp);
                }
                listeEnnemis.remove(ennemiAEnlever);
            }
        }
    }

    private void bougerhero() {
        Point locationOriginale = new Point(heros.getLocation());
        switch (Fenetre.getToucheEnfoncee()) {
            case KeyEvent.VK_UP:
                heros.bougerHaut();
                if (verifierContactObstacleHeros()) {
                    heros.setLocation(locationOriginale);
                }
                break;
            case KeyEvent.VK_DOWN:
                heros.bougerBas();
                if (verifierContactObstacleHeros()) {
                    heros.setLocation(locationOriginale);
                }
                break;
            case KeyEvent.VK_RIGHT:
                heros.bougerDroite();
                if (verifierContactObstacleHeros()) {
                    heros.setLocation(locationOriginale);
                }
                break;
            case KeyEvent.VK_LEFT:
                heros.bougerGauche();
                if (verifierContactObstacleHeros()) {
                    heros.setLocation(locationOriginale);
                }
                break;
            case 0:
                heros.arreter();
        }

        // heros.bouger();
        //heros.bouger(getWidth(), getHeight(), direction);
    }

    private void tirer(int tempsRestant) {
        double x;
        double y;
        int xInt;
        int yInt;
        Laser laser;
        Rectangle r = heros.getBounds();
        switch (heros.getDirectionCourante()) {
            case HAUT:
                if (tempsRestant <= 0) {
                    laser = new Laser(Directions.HAUT);
                    x = r.getX() + r.getWidth() / 2;
                    xInt = (int) x;
                    y = r.getY() - 51;
                    yInt = (int) y;
                    laser.setBounds(xInt, yInt, 5, 50);
                    add(laser);
                    listeProjectiles.add(laser);
                } else if (tempsRestant > 0) {
                    Balle balle1 = new Balle(Directions.HAUT);
                    Balle balle2 = new Balle(Directions.HAUT);
                    Balle balle3 = new Balle(Directions.HAUT);
                    x = r.getX() + r.getWidth() / 2;
                    xInt = (int) x;
                    y = r.getY() + 10;
                    yInt = (int) y;
                    balle1.setBounds(xInt, yInt, 10, 10);
                    balle2.setBounds(xInt - 20, yInt, 10, 10);
                    balle3.setBounds(xInt + 20, yInt, 10, 10);
                    add(balle1);
                    add(balle2);
                    add(balle3);
                    listeProjectiles.add(balle1);
                    listeProjectiles.add(balle2);
                    listeProjectiles.add(balle3);
                }
                break;
            case BAS:
                if (tempsRestant <= 0) {
                    laser = new Laser(Directions.BAS);
                    x = r.getX() + r.getWidth() / 2;
                    xInt = (int) x;
                    y = r.getY() + r.getHeight() + 1;
                    yInt = (int) y;
                    laser.setBounds(xInt, yInt, 5, 50);
                    add(laser);
                    listeProjectiles.add(laser);
                } else if (tempsRestant > 0) {
                    Balle balle1 = new Balle(Directions.BAS);
                    Balle balle2 = new Balle(Directions.BAS);
                    Balle balle3 = new Balle(Directions.BAS);
                    x = r.getX() + r.getWidth() / 2;
                    xInt = (int) x;
                    y = r.getY() + r.getHeight() + 1;
                    yInt = (int) y;
                    balle1.setBounds(xInt, yInt, 10, 10);
                    balle2.setBounds(xInt - 20, yInt, 10, 10);
                    balle3.setBounds(xInt + 20, yInt, 10, 10);
                    add(balle1);
                    add(balle2);
                    add(balle3);
                    listeProjectiles.add(balle1);
                    listeProjectiles.add(balle2);
                    listeProjectiles.add(balle3);
                }
                break;
            case DROITE:
                if (tempsRestant > 0) {
                    laser = new Laser(Directions.DROITE);
                    x = r.getX() + r.getWidth() + 1;
                    xInt = (int) x;
                    y = r.getY() + r.getHeight() / 2;
                    yInt = (int) y;
                    laser.setBounds(xInt, yInt, 50, 5);
                    add(laser);
                    listeProjectiles.add(laser);
                } else if (tempsRestant > 0) {
                    Balle balle1 = new Balle(Directions.DROITE);
                    Balle balle2 = new Balle(Directions.DROITE);
                    Balle balle3 = new Balle(Directions.DROITE);
                    x = r.getX() + r.getWidth() + 1;
                    xInt = (int) x;
                    y = r.getY() + r.getHeight() / 2;
                    yInt = (int) y;
                    balle1.setBounds(xInt, yInt, 10, 10);
                    balle2.setBounds(xInt, yInt - 20, 10, 10);
                    balle3.setBounds(xInt, yInt + 20, 10, 10);
                    add(balle1);
                    add(balle2);
                    add(balle3);
                    listeProjectiles.add(balle1);
                    listeProjectiles.add(balle2);
                    listeProjectiles.add(balle3);
                }
                break;
            case GAUCHE:
                laser = new Laser(Directions.GAUCHE);
                x = r.getX() - 51;
                xInt = (int) x;
                y = r.getY() + r.getHeight() / 2;
                yInt = (int) y;
                laser.setBounds(xInt, yInt, 50, 5);
                add(laser);
                listeProjectiles.add(laser);
        }
    }

    private void spawnEnnemis() {

        //ici on souhaite cree un systeme pour choisir un mur au hasard 
        //dependament du niveau on en choisira un certain nombre
        // on insteau la valeur -1 puisque 0 sera utilise comme mur
        boolean wall[] = new boolean[4];
        wall[0] = false;
        wall[1] = false;
        wall[2] = false;
        wall[3] = false;
        level = (timer / 5) + 1;

        Random r = new Random();
        wall[r.nextInt(4)] = true;

        if (level >= 3) {
            boolean bool = false;
            do {
                int temp = r.nextInt(4);
                if (!wall[temp]) {
                    wall[temp] = true;
                    bool = true;
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
                }
            } while (!bool);
        }

        if (level >= 10) {
            for (int i = 0; i < 4; i++) {
                wall[i] = true;
            }

        }

        //On spawn les monstres au(x) mur(s) choisi
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

    private void bougerLaser() {
        for (Projectile projectile : listeProjectiles) {
            switch (projectile.getDirection()) {
                case HAUT:
                    projectile.setLocation(projectile.getX(), projectile.getY() - 6);
                    break;
                case BAS:
                    projectile.setLocation(projectile.getX(), projectile.getY() + 6);
                    break;
                case DROITE:
                    projectile.setLocation(projectile.getX() + 6, projectile.getY());
                    break;
                case GAUCHE:
                    projectile.setLocation(projectile.getX() - 6, projectile.getY());
            }
        }
    }

    private void collisionLasers() {
        Ennemi ennemiAEnlever = null;
        ArrayList<Projectile> listeProjectilesARetirer = new ArrayList<>();
        for (Projectile projectile : listeProjectiles) {
            for (Obstacle obstacle : listeObstacles) {
                if (projectile.getBounds().intersects(obstacle.getBounds())) {
                    remove(projectile);
                    listeProjectilesARetirer.add(projectile);
                }
            }
            for (Ennemi ennemi : listeEnnemis) {
                if (projectile.getBounds().intersects(ennemi.getBounds())) {
                    remove(projectile);
                    listeProjectilesARetirer.add(projectile);
                    remove(ennemi);
                    ennemiAEnlever = ennemi;
                }
            }
            listeEnnemis.remove(ennemiAEnlever);
        }
        if (ennemiAEnlever != null) {
            Random rand = new Random();
            if (rand.nextInt(10) == 0) {
                PowerUp powerUp = new PowerUp();
                powerUp.setBounds(ennemiAEnlever.getX(),
                        ennemiAEnlever.getY(), 32, 32);
                add(powerUp);
                listePowerUps.add(powerUp);
            }
            listeEnnemis.remove(ennemiAEnlever);
        }
        for (Projectile projectile : listeProjectilesARetirer) {
            listeProjectiles.remove(projectile);
        }
    }

    private void contactPowerUp() {
        Random rand = new Random();
        int resultat = rand.nextInt(3);
        for (PowerUp powerUp : listePowerUps) {
            if (heros.getBounds().intersects(heros.getBounds())) {
                switch (resultat) {
                    case 0:
                        for (Ennemi ennemi : listeEnnemis) {
                            remove(ennemi);
                        }
                        listeEnnemis.clear();
                        break;
                    case 1:
                        // Regénérer les coeurs
                        break;
                    case 2:

                }
            }
        }
    }

    private void timer() {
        if (skip2 == 200) {
            timer++;
            spawnEnnemis();
            skip2 = 0;
        } else {
            skip2++;
        }
    }

    private void addEnemis(int x, int y, int w, int h) {
        listeEnnemis.add(new Mechant());
        this.add(listeEnnemis.get(listeEnnemis.size() - 1));
        listeEnnemis.get(listeEnnemis.size() - 1).setBounds(x, y, w, h);
        if (level >= 2) {
            listeEnnemis.add(new PasFin());
            this.add(listeEnnemis.get(listeEnnemis.size() - 1));
            listeEnnemis.get(listeEnnemis.size() - 1).setBounds(x + 35, y, w, h);
        }
        if (level >= 5) {
            listeEnnemis.add(new TroubleFete());
            this.add(listeEnnemis.get(listeEnnemis.size() - 1));
            listeEnnemis.get(listeEnnemis.size() - 1).setBounds(x - 35, y, w, h);
        }
    }
}
