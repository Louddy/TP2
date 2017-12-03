/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Olivier Hurtubise
 */
public class Heros extends Entite {

    public enum Directions {
        HAUT,
        BAS,
        DROITE,
        GAUCHE;
    }
    private Image img;
    private final Image imgHaut;
    private final Image imgBas;
    private final Image imgDroite;
    private final Image imgGauche;
    private static final File fichier = new File("Images\\herofront.gif");

    public Heros() {
        super(fichier);
        final Toolkit tk = Toolkit.getDefaultToolkit();
        imgHaut = tk.getImage("Images\\heroback.gif");
        imgBas = tk.getImage("Images\\herofront.gif");
        imgDroite = tk.getImage("Image\\herodroite.gif");
        imgGauche = tk.getImage("Image\\herogauche.gif");
        /*tk.prepareImage(imgHaut, 22, 51, this);
        tk.prepareImage(imgBas, 22, 51, this);
        tk.prepareImage(imgDroite, 22, 51, this);
        tk.prepareImage(imgGauche, 22, 51, this);*/
    }

    public void bouger(int hauteur, int largeur, Directions direction) {
        switch (direction) {
            case HAUT:
                if (getY() > 0) {
                    setLocation(getX(), getY() - 5);
                }
                img = imgHaut;
                break;
            case BAS:
                if (getY() + getHeight() < hauteur) {
                    setLocation(getX(), getY() + 1);
                }
                img = imgBas;
                break;
            case DROITE:
                if (getX() + getWidth() < largeur) {
                    setLocation(getX() + 1, getY());
                }
                img = imgDroite;
                break;
            case GAUCHE:
                if (getX() > 0) {
                    setLocation(getX() - 1, getY());
                }
                img = imgGauche;
        }
        invalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 22, 51, this);
    }
    public Image getImage() {
        return img;
    }
}
