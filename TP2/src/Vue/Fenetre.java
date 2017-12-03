/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;


import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Xavie
 */
public class Fenetre extends JFrame implements Observer{
    /*private class Dispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                System.out.println("2test2");
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
                System.out.println("3test3");
            }
            return false;
        }
    }*/
    
    final static int HAUTEUR = 596;
    final static int LARGEUR = 806;

    //Menu
    private final JMenuBar mnuPrincipal = new JMenuBar();
    private final JMenu mnuFichier = new JMenu("Fichier");
    private final JMenu mnuQuestionMark = new JMenu("?");
    private final JMenuItem mnuFichierNouvellePartie = new JMenuItem("Nouvelle Partie");
    private final JMenuItem mnuFichierQuitter = new JMenuItem("Quitter");
    private final JMenuItem mnuQuestionMarkAide = new JMenuItem("Aide");
    private final JMenuItem mnuQuestionMarkAPropros = new JMenuItem("À propos");
    private Monde monde = new Monde();

    public Fenetre() {
        /*KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new Dispatcher());*/
        
        setTitle("Jeux");
        setSize(LARGEUR, HAUTEUR);

        //Menu
        mnuFichier.add(mnuFichierNouvellePartie);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuFichierQuitter);

        mnuQuestionMark.add(mnuQuestionMarkAPropros);
        mnuQuestionMark.addSeparator();
        mnuQuestionMark.add(mnuQuestionMarkAide);
        mnuPrincipal.add(mnuFichier);
        mnuPrincipal.add(mnuQuestionMark);
        this.setJMenuBar(mnuPrincipal);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    monde.monterHeros();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    
                }
            }
        });
        //Jeu
        //Sauce
        this.add(monde);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }
}
