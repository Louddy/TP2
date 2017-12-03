/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

/**
 *
 * @author Olivier Hurtubise
 */
public class Heros extends JComponent implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2);
                bouger(this.getParent().getHeight(), this.getParent().getWidth(),
                        directionCourante);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public enum Directions {
        HAUT,
        BAS,
        DROITE,
        GAUCHE;
    }
    private Image img;
    private Directions directionCourante;
    private Directions blocage = null;
    private final Image imgHaut;
    private final Image imgBas;
    private final Image imgDroite;
    private final Image imgGauche;
    private final Image imgHautImmobile;
    private final Image imgBasImmobile;
    private final Image imgDroiteImmobile;
    private final Image imgGaucheImmobile;
    private final int DEPLACEMENT = 1;

    public Heros() {
        final Toolkit tk = Toolkit.getDefaultToolkit();
        imgHaut = tk.getImage("Images\\heroback.gif");
        imgBas = tk.getImage("Images\\herofront.gif");
        imgDroite = tk.getImage("Images\\herodroite.gif");
        imgGauche = tk.getImage("Images\\herogauche.gif");
        imgHautImmobile = tk.getImage("Images\\backimmobile.gif");
        imgBasImmobile = tk.getImage("Images\\frontimmobile.gif");
        imgDroiteImmobile = tk.getImage("Images\\droiteimmobile.gif");
        imgGaucheImmobile = tk.getImage("Images\\gaucheimmobile.gif");
        img = imgBasImmobile;
    }

    public void bouger(int hauteur, int largeur, Directions direction) {
        Monde monde = (Monde) getParent();
        boolean passe = ((direction == Directions.HAUT && blocage == Directions.BAS) ||
                (direction == Directions.BAS && blocage == Directions.HAUT) ||
                (direction == Directions.DROITE && blocage == Directions.GAUCHE) ||
                (direction == Directions.GAUCHE && blocage == Directions.DROITE));
        if ((passe) || !monde.verifierContact()) {
            switch (direction) {
                case HAUT:
                    if (getY() > 0) {
                        setLocation(getX(), getY() - DEPLACEMENT);
                    } 
                    img = imgHaut;
                    break;
                case BAS:
                    if (getY() + getHeight() < hauteur) {
                        setLocation(getX(), getY() + DEPLACEMENT);
                    }
                    img = imgBas;
                    break;
                case DROITE:
                    if (getX() + getWidth() < largeur) {
                        setLocation(getX() + DEPLACEMENT, getY());
                    }
                    img = imgDroite;
                    break;
                case GAUCHE:
                    if (getX() > 0) {
                        setLocation(getX() - DEPLACEMENT, getY());
                    }
                    img = imgGauche;
            }
        } else if (blocage == null && monde.verifierContact()) {
            blocage = direction;
        }
        if (blocage != null && !monde.verifierContact()) {
            blocage = null;
        }
    }

    public void arreter() {
        switch (directionCourante) {
            case HAUT:
                img = imgHautImmobile;
                break;
            case BAS:
                img = imgBasImmobile;
                break;
            case DROITE:
                img = imgDroiteImmobile;
                break;
            case GAUCHE:
                img = imgGaucheImmobile;
        }
    }

    public void setDirectionCourante(Directions direction) {
        directionCourante = direction;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
