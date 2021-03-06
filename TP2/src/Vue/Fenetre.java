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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            /**
             * Ajoute le code de la touche si elle est fléchée.
             * Sinon indique que la barre d'espace est enfoncée.
             */
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
            /**
             * Retire le code de la touche si elle a été enregistrée par
             * le programme.
             */
            public void keyReleased(KeyEvent e) {
                listeKeyCodes.remove(new Integer(e.getKeyCode()));
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espaceEnfonce = false;
                }
            }
        });

        mnuFichierNouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int reply = JOptionPane.showConfirmDialog(
                        Fenetre.this,
                        "Voulez-vous commencer une nouvelle partie ?",
                        "Select an Option",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    controleur.reset();

                } else {

                }

            }

        });
        mnuFichierQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                System.exit(0);
            }

        });
        mnuQuestionMarkAPropros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(rootPane, "Olivier Hurtubise et Xavier Généreux \n Date de Création : Vendredi le 8 Décemvbre 2017");
            }

        });
        mnuQuestionMarkAide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(rootPane, "Le combat ultime "
                        + ": vous contre l’armée de Tentacule Mauve (ses "
                        + "clones et ses acolytes). Un champ de bataille. "
                        + "Le sort de l’humanité est entre vos mains. Mais "
                        + "aucune pression, faites votre possible . \n"
                        + "Utilisez les flèches pour bouger \n"
                        + "Espace pour tirez \n");
            }

        });
        //Sauce
        this.add(pnlPrincipal);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * 
     * @return Le code de la dernière touche enfoncée, si aucune touche n'est
     * enfoncée, 0.
     */
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
    /**
     * Met à jour l'interface lorsque le modèle change.
     */
    public void update(Observable o, Object arg) {
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
    
    public void setMonde() {
        this.monde = new Monde(controleur);
    }

    public Monde getMonde() {
        return monde;
    }

   
    
}
