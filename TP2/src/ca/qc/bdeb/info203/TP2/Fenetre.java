/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Xavie
 */
public class Fenetre extends JFrame implements Observer{
    
    final static int HAUTEUR = 600;
    final static int LARGEUR = 800;
    
    //Textures
    private final Image floor = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Xavie\\Documents\\NetBeansProjects\\TP2\\TP2\\Images\\floor2.gif");
    

   
    //Menu
    private final JMenuBar mnuPrincipal = new JMenuBar();
    private final JMenu mnuFichier = new JMenu("Fichier");
    private final JMenu mnuQuestionMark = new JMenu("?");
    private final JMenuItem mnuFichierNouvellePartie = new JMenuItem("Nouvelle Partie");
    private final JMenuItem mnuFichierQuitter = new JMenuItem("Quitter");
    private final JMenuItem mnuQuestionMarkAide = new JMenuItem("Aide");
    private final JMenuItem mnuQuestionMarkAPropros  = new JMenuItem("À propos");
   
   
    

    
    public Fenetre() {
    
        
        
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

        
        //Jeu
        
       

        //Sauce
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void paintComponent(Graphics g){
        g.drawImage(floor, 0, 0, this);
    }
}

