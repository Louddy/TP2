/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import javax.swing.JComponent;

/**
 *
 * @author Olivier Hurtubise
 */
public abstract class Projectile extends JComponent {
    protected final Directions direction;

    public Projectile(Directions direction) {
        this.direction = direction;
    }
    
    public Directions getDirection() {
        return direction;
    }
}
