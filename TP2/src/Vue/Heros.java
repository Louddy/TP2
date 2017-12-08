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

    public void bougerDroite() {
        setLocation(getX() + DEPLACEMENT, getY());
        img = imgDroite;
        directionCourante = Directions.DROITE;
    }

    public void bougerGauche() {
        setLocation(getX() - DEPLACEMENT, getY());
        img = imgGauche;
        directionCourante = Directions.GAUCHE;
    }

    public void bougerHaut() {
        setLocation(getX(), getY() - DEPLACEMENT);
        img = imgHaut;
        directionCourante = Directions.HAUT;

    }

    public void bougerBas() {
        setLocation(getX(), getY() + DEPLACEMENT);
        img = imgBas;
        directionCourante = Directions.BAS;
    }

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

    public Directions getDirectionCourante() {
        return directionCourante;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
