/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author vivierlo
 */
public class IhmFinTour extends JFrame {
    private Color fond = new Color(218,233,212);
    private final int HAUTEUR_BASE = 400;
    private final int LONGUEUR_PAR_JOUEUR = 200;
    private Observateur observateur;
    private ArrayList<Joueur> joueurs;
    private HashMap<Joueur, String> couleurJoueurs = new HashMap<>();
    private IhmJeu ihmJeu;
    String couleur[] ={"#F41C25", "#083052", "#25980E", "#5a2400", "#D101FF", "#FF6800"};
    
    JPanel panelStats;

    public IhmFinTour(int numTour, ArrayList<Joueur> joueurs, HashMap<String, String> couleurJoueurs) {
        super();
        this.joueurs = joueurs;
        this.ihmJeu = ihmJeu;
        for (int i = 0; i< joueurs.size(); i++) {
            //Transfert des couleurs en String en couleurs en hexadecimal
            String couleur = couleurJoueurs.get(joueurs.get(i).getNom()); 
            String couleurJoueur = null;
            switch (couleur) {
                case "Rouge":   couleurJoueur = this.couleur[0];break;
                case "Bleu" :   couleurJoueur = this.couleur[1];break; 
                case "Vert" :   couleurJoueur = this.couleur[2];break;
                case "Marron" : couleurJoueur = this.couleur[3];break;
                case "Violet" : couleurJoueur = this.couleur[4];break;  
                case "Orange" : couleurJoueur = this.couleur[5];break;   
            }
            this.couleurJoueurs.put(joueurs.get(i), couleurJoueur);
        }
        afficher(numTour); 
        initUIComponents();
    }
    
    public void initUIComponents() {
        this.setBackground(fond);
        this.getContentPane().setBackground(fond);

        this.setSize(LONGUEUR_PAR_JOUEUR*joueurs.size(),HAUTEUR_BASE);
        this.setLayout(new BorderLayout());
        
        panelStats = new JPanel();
        panelStats.setLayout(new GridLayout(1,joueurs.size())); //Créé un "tableau" d'affichage pour chaque joueurs
        this.add(panelStats, BorderLayout.CENTER);
        
        for (int i = 0; i< joueurs.size(); i++) { //Création du tableau d'affichage pour chaque joueur
            JPanel panelJoueurCourant = new JPanel();
            panelJoueurCourant.setBackground(fond);
            panelJoueurCourant.setLayout(new GridLayout(4,1));
            if (i == 0) { // Gère les bordures pour qu'il n'y ai pas 2 tracés sur la même ligne
                panelJoueurCourant.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
            } else {
                panelJoueurCourant.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 3, Color.BLACK));
            }
            //Affichage du nom
            JLabel labelNom = new JLabel("<html><font color = " + couleurJoueurs.get(joueurs.get(i)) + " >" + joueurs.get(i).getNom() + "      </font></html>");
            labelNom.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            labelNom.setHorizontalAlignment(JLabel.CENTER);
            labelNom.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelNom);
            
            //Affichage du Cash
            JLabel labelCash = new JLabel("Cash = " + joueurs.get(i).getCash());
            labelCash.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            labelCash.setHorizontalAlignment(JLabel.CENTER);
            labelCash.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelCash);
            
            //Affichage de la position courante
            JLabel labelPosition = new JLabel("<html>Position: <br>" + joueurs.get(i).getPositionCourante().getNomCarreau() +"</html>");
            labelPosition.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            labelPosition.setHorizontalAlignment(JLabel.CENTER);
            labelPosition.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelPosition);
            
            
            JButton voirProp = new JButton("Voir Propriétés");
            panelJoueurCourant.add(voirProp);
            panelStats.add(panelJoueurCourant);
            Joueur joueurCourant = joueurs.get(i);
            voirProp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IhmPropriete ihmProp = new IhmPropriete(joueurCourant);
                }
            });
        }
        
        
        JButton quitter = new JButton("Fermer cette fenêtre");
        this.add(quitter,BorderLayout.SOUTH);
        
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observateur.retourAuJeu();
            }
        });
    }

    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    public void afficher(int numTour) {
       this.setTitle("Statistiques de fin du tour n°" + numTour);
       this.setVisible(true);
       this.setBackground(fond);
       this.setAlwaysOnTop(true);
       this.setLocationRelativeTo(null);
    }
    
}
