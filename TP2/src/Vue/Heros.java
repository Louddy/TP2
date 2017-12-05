/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Olivier Hurtubise
 */
public class Heros extends JComponent {

    /*@Override
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
    }*/
    public enum Directions {
        HAUT,
        BAS,
        DROITE,
        GAUCHE,
        AUCUNE;
    }
    private Image img;
    private Directions blocage = null;
    private ArrayList<Directions> listeBlocages = new ArrayList<Directions>();
    private Directions directionCourante;
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

    public void bouger(int largeur, int hauteur, Directions direction) {
        if (direction != Directions.AUCUNE) {
            directionCourante = direction;
            Monde monde = (Monde) getParent();
            System.out.println(!listeBlocages.contains(direction)) ;
                    // && !monde.verifierBlocage()) && !monde.verifierContact());
            if ((!listeBlocages.contains(direction) 
                    && !monde.verifierBlocage() && monde.verifierContact())
                    || !monde.verifierContact()) {
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
            } else if(monde.verifierBlocage()) {
                listeBlocages.add(direction);
            } else if (listeBlocages.isEmpty() && monde.verifierContact()) {
                listeBlocages.add(direction);
            }
            if (!listeBlocages.isEmpty() && !monde.verifierContact()) {
                listeBlocages.clear();
            }
        } else {
            arreter();
        }
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
