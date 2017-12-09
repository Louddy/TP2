/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Xavie
 */
public abstract class Ennemi extends JComponent {

    protected int vie = 1;
    protected int DEPLACEMENT = 1;
    protected int points = 0;

    private Image img;
    private Image Front;
    private Image Back;

    public Ennemi() {
    }

    public void bougerDroite() {
        setLocation(getX() + DEPLACEMENT, getY());
    }

    public void bougerGauche() {
        setLocation(getX() - DEPLACEMENT, getY());
    }

    public void bougerHaut() {
        setLocation(getX(), getY() - DEPLACEMENT);
    }

    public void bougerBas() {
        setLocation(getX(), getY() + DEPLACEMENT);
    }

    /**
     * Représente la tentacule par une image de face d'elle-même.
     */
    public void setFront() {
        img = Front;
    }

    /**
     * Représente la tentacule par une image de face d'elle-même.
     */
    public void setBack() {
        img = Back;
    }
    
    /**
     * Enlève un de vie à la tentacule
     */
    public void prendreCoup() {
        vie--;
    }
    
    public int getVie() {
        return vie;
    }
    
    public int getPoints() {
        return points;
    }
}
