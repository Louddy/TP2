/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;


import java.util.Observable;

/**
 *
 * @author Olivier Hurtubise
 */
public class Modele extends Observable {
    private int vie;
    private int points;
    
    public Modele() {
        vie = 3;
        points = 0;
    }
    
    public void ouch() {
        vie--;
        majObservers();
    }
    
    public void ajouterPoints(int pointsGagne) {
        points += pointsGagne;
        majObservers();
    }
    
    public void majObservers() {
        setChanged();
        notifyObservers();
    }
    
    public int getPoints() {
        return points;
    }
    
    public int getVie() {
        return vie;
    }
    
    public void resetVie() {
        vie = 3;
        majObservers();
    }
}
