/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Controleur;
import Modele.Modele;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 *
 * @author Xavie
 */
public class Fenetre extends JFrame implements Observer {

    final static int HAUTEUR = 646;
    final static int LARGEUR = 806;

    //Menu
    private final JMenuBar mnuPrincipal = new JMenuBar();
    private final JMenu mnuFichier = new JMenu("Fichier");
    private final JMenu mnuQuestionMark = new JMenu("?");
    private final JMenuItem mnuFichierNouvellePartie = new JMenuItem("Nouvelle Partie");
    private final JMenuItem mnuFichierQuitter = new JMenuItem("Quitter");
    private final JMenuItem mnuQuestionMarkAide = new JMenuItem("Aide");
    private final JMenuItem mnuQuestionMarkAPropros = new JMenuItem("À propos");
    private final JPanel pnlPrincipal;
    private final JPanel pnlSecondaire;
    private Monde monde;
    private static ArrayList<Integer> listeKeyCodes = new ArrayList<Integer>();
    private static boolean espaceEnfonce;
    private Vie vie;
    private JLabel lblPoints;
    private Controleur controleur;
    private Modele modele;

    public Fenetre(Controleur controleur, Modele modele) {
        monde = new Monde(controleur);

        setTitle("Attaque des tentacules");
        setSize(LARGEUR, HAUTEUR);

        this.modele = modele;
        modele.addObserver(this);
        this.controleur = controleur;

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
        pnlPrincipal = new JPanel(new BorderLayout());
        pnlSecondaire = new JPanel();
        pnlSecondaire.setLayout(null);
        monde.setPreferredSize(new Dimension(806, 596));
        pnlSecondaire.setPreferredSize(new Dimension(806, 50));
        pnlSecondaire.setBackground(Color.black);
        vie = new Vie();
        vie.setBounds(32, 15, 96, 32);
        lblPoints = new JLabel("0 pts");
        lblPoints.setForeground(Color.red);
        lblPoints.setFont(new Font("Impact", Font.PLAIN, 32));
        lblPoints.setBounds(700, 0, 100, 50);
        pnlSecondaire.add(vie);
        pnlSecondaire.add(lblPoints);
        pnlPrincipal.add(pnlSecondaire, BorderLayout.NORTH);
        pnlPrincipal.add(monde, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (!listeKeyCodes.contains(e.getKeyCode())
                        && (e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_DOWN
                        || e.getKeyCode() == KeyEvent.VK_RIGHT
                        || e.getKeyCode() == KeyEvent.VK_LEFT)) {
                    listeKeyCodes.add(e.getKeyCode());
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espaceEnfonce = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                listeKeyCodes.remove(new Integer(e.getKeyCode()));
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espaceEnfonce = false;
                }
            }
        });

        //Jeu
        //Sauce
        this.add(pnlPrincipal);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static Integer getToucheEnfoncee() {
        if (listeKeyCodes.size() != 0) {
            return listeKeyCodes.get(0);
        } else {
            return 0;
        }
    }

    public static boolean barreEstEnfoncee() {
        return espaceEnfonce;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Allô");
        lblPoints.setText(modele.getPoints() + " pts");
        vie.setVieRestante(modele.getVie());
        if (modele.getVie() <= 0) {
            int reponse = JOptionPane.showConfirmDialog(null, "Votre score est"
                    + " de " + modele.getPoints() + " points.\n Voulez-vous "
                    + "jouer à nouveau?",
                    "Choisissez:", JOptionPane.YES_NO_OPTION);
            if(reponse == JOptionPane.YES_OPTION) {
                controleur.reset();
            } else {
                System.exit(0);
            }
        }
    }
    
    public void setMonde(Monde monde) {
        this.monde = monde;
    }
}
