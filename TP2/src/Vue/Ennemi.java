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
    private int vitesseX=0;
    private int vitesseY=0;
    
    private Image img;
    private Image Front;
    private Image Back;
    public Ennemi() {
    }
    
    public void bouger() {
        setLocation(getX() + vitesseX, getY() + vitesseY);
        vitesseX=0;
        vitesseY=0;
    }
    

    public void bougerDroite() {
        vitesseX = 1;
    }

    public void bougerGauche() {
        vitesseX = -1;
    }

    public void bougerHaut() {
        vitesseY = -1;
        
    }

    public void bougerBas() {
        vitesseY = 1;
    }
    
    
    public void setFront() {
        img = Front;
    }

    public void setBack() {
        img = Back;
    }

//    public int getVitesseY() {
//        return vitesseY;
//    }
    


   
    
}
