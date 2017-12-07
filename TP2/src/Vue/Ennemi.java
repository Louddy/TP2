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
public class Ennemi extends JComponent {

    private int DEPLACEMENT = 1;

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

    public void setFront() {
        img = Front;
    }

    public void setBack() {
        img = Back;
    }
}
