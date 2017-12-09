/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Vue.Fenetre;
import Vue.Monde;

/**
 *
 * @author Olivier Hurtubise
 */
public class Controleur {
    private Modele modele;
    private Fenetre fenetre;
    
    public Controleur() {
        modele = new Modele();
        fenetre = new Fenetre(this, modele);
    }
    
    public void ajouterPoints(int points) {
        modele.ajouterPoints(points);
    }
    
    public void ouch() {
        modele.ouch();
    }
    
    public void reset() {
        modele = new Modele();
        fenetre.setMonde();
    }
    
    public void resetVie() {
        modele.resetVie();
    }
}
