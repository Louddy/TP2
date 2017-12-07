/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.io.File;

/**
 *
 * @author Olivier Hurtubise
 */
public class Buisson extends Obstacle {

    private final static File fichier = new File("Images\\buisson1.gif");

    public Buisson() {
        super(fichier);
    }
}
