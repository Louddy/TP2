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
public class Roche extends Entite {
    private final static File fichier  = new File("Images\\roche1.gif");
    public Roche () {
        super(fichier);
    }
}
