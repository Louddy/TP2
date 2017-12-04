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
public class Tentacule extends JComponent implements Runnable {

    public enum Mechants {
        VANILLE,
        VITE,
        GROS;
    }
    
    private final Image imgHaut;
    private final Image imgBas;
    private final Mechants type;
    private final int DEPLACEMENT = 1;
    private Image img;
    private Heros heros;
    
    public Tentacule(String imgHaut, String imgBas, Heros heros, Mechants type) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.imgHaut = tk.getImage(imgHaut);
        this.imgBas = tk.getImage(imgBas);
        this.heros = heros;
        this.type = type;
        img = this.imgHaut;
    }
    
    @Override
    public void run() {
        int x = this.getX() - heros.getX();
        int y = this.getY() - heros.getY();
        if(x < 0 && y < 0) {
            setLocation(getX() + DEPLACEMENT, getY() + DEPLACEMENT);
        } else if (x < 0 && y > 0) {
            setLocation(getX() + DEPLACEMENT, getY() - DEPLACEMENT);
        } else if (x > 0 && y < 0) {
            setLocation(getX() - DEPLACEMENT, getY() + DEPLACEMENT);
        } else if (x > 0 && y > 0) {
            setLocation(getX() - DEPLACEMENT, getY() - DEPLACEMENT);
        } else if (x == 0 && y > 0) {
            setLocation(getX(), getY() - DEPLACEMENT);
        } else if (x == 0 && y < 0) {
            setLocation(getX(), getY() - DEPLACEMENT);
        } else if (x > 0 && y == 0) {
            setLocation(getX() - DEPLACEMENT, getY());
        } else if (x < 0 && y == 0) {
            setLocation(getX() - DEPLACEMENT, getY());
        } 
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
