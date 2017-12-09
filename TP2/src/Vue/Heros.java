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
public class Heros extends JComponent {
    
    private Image img;
    private Directions directionCourante;
    private final Image imgHaut;
    private final Image imgBas;
    private final Image imgDroite;
    private final Image imgGauche;
    private final Image imgHautImmobile;
    private final Image imgBasImmobile;
    private final Image imgDroiteImmobile;
    private final Image imgGaucheImmobile;
    private final int DEPLACEMENT = 4;

    /**
     * Instancie les images représentant les différentes apparences possibles
     * du héros.
     */
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
    
    /**
     * Bouge le héros d'une valeur fixe vers la droite
     */
    public void bougerDroite() {
        setLocation(getX() + DEPLACEMENT, getY());
        img = imgDroite;
        directionCourante = Directions.DROITE;
    }

    /**
     * Bouge le héros d'une valeur fixe vers la gauche
     */
    public void bougerGauche() {
        setLocation(getX() - DEPLACEMENT, getY());
        img = imgGauche;
        directionCourante = Directions.GAUCHE;
    }

    /**
     * Bouge le héros d'une valeur fixe vers le haut
     */
    public void bougerHaut() {
        setLocation(getX(), getY() - DEPLACEMENT);
        img = imgHaut;
        directionCourante = Directions.HAUT;

    }

    /**
     * Bouge le héros d'une valeur fixe vers le bas
     */
    public void bougerBas() {
        setLocation(getX(), getY() + DEPLACEMENT);
        img = imgBas;
        directionCourante = Directions.BAS;
    }

    /**
     * Remplace le gif représentant le héros par une image fixe selon sa 
     * direction
     */
    public void arreter() {
        if (directionCourante != null) {
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
        invalidate();
        repaint();
    }

    /**
     * 
     * @return Direction actuelle du héros
     */
    public Directions getDirectionCourante() {
        return directionCourante;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
