/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author fallm
 */
public class IhmInscription extends JFrame  {
    private Observateur observateur;
    
    private int nbjoueurs = 2;
    private JPanel inscriptions;
    private JPanel gestionJoueurs;
    private JPanel choix;
    private HashSet<String> nomJoueurs = new HashSet<>();
    private ArrayList<JLabel> labelJoueurs = new ArrayList<>();
    private ArrayList<JTextField> champNomJoueurs = new ArrayList<>();

    private IhmJeu ihmJeu;
    
    
    
    
    public IhmInscription() {
        super();

        initUIComponents();
    }
    
     public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }
    
    private void initUIComponents() {
        this.setLayout((new BorderLayout()));
        gestionJoueurs = new JPanel(); 
        inscriptions = new JPanel();
        choix = new JPanel();
        ///**NORD**///
        this.add(gestionJoueurs, BorderLayout.NORTH);
        gestionJoueurs.setLayout((new BorderLayout()));
        JButton ajouter = new JButton("Ajouter un Joueur");
        JButton retirer = new JButton("Retirer un Joueur");
        gestionJoueurs.add(ajouter, BorderLayout.EAST);
        gestionJoueurs.add(retirer, BorderLayout.WEST);

        
        ///**CENTRE**///
        this.add(inscriptions, BorderLayout.CENTER);
        inscriptions.setLayout(new GridLayout(7,2));
        inscriptions.add(new JLabel("De 2 à 6 joueurs"));
        inscriptions.add(new JLabel("Entrez le nom des joueurs:"));
        //Ajout des widgets au panel central
        for (int i=0; i<6; i++) {
            labelJoueurs.add(new JLabel("Joueur " + (i+1) + ": "));
            champNomJoueurs.add(new JTextField());
            inscriptions.add(labelJoueurs.get(i));
            inscriptions.add(champNomJoueurs.get(i));
        }
        traceInscriptions();
        
        ///**SUD**///
        this.add(choix, BorderLayout.SOUTH);
        choix.setLayout((new BorderLayout()));
        JButton jouer = new JButton("Jouer");
        JButton quitter = new JButton("Quitter");
        choix.add(jouer, BorderLayout.EAST);
        choix.add(quitter, BorderLayout.WEST);
        
        ///**GESTION DES BOUTONS**///
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addJoueur();
            }
        });
        retirer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeJoueur();
            }
        });
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res = IhmBoiteMessage.afficherBoiteDialogue("Etes vous sûr de vouloir quitter?", "ouinon");
                if (res) {
                    quitter();
                }
            }
        });
        jouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean nomJoueursCorrects = vérifierNomJoueurs();
                if (nomJoueursCorrects) {
                    boolean res = IhmBoiteMessage.afficherBoiteDialogue("Souhaitez vous lancer la partie avec " + nbjoueurs + " joueurs ?", "ouinon");
                    if (res) {
                        setVisible(false);
                        try {
                            //On lance la fenêtre de jeu
                            ihmJeu = new IhmJeu(); // A ENLEVER D'ICI
                            //observateur.jouer(joueurs);
                        } catch (IOException ex) {
                            Logger.getLogger(IhmInscription.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }   
    
    public void addJoueur() {
        if (nbjoueurs<6) {nbjoueurs++;}
        traceInscriptions();  
    }
    
    public void removeJoueur() {
        if (nbjoueurs>2) {nbjoueurs--;}        
        traceInscriptions();
    }
    
    public void traceInscriptions() { //Algorithme d'ajout ou de suppression de champ de création de joueur
        int i =0;
        while (i<6) {
            if (i<nbjoueurs) {
                labelJoueurs.get(i).setVisible(true);
                champNomJoueurs.get(i).setVisible(true);
            } else {
                labelJoueurs.get(i).setVisible(false);
                champNomJoueurs.get(i).setVisible(false);
            }
            i++;
        }
    }
    
    /**
     * Rend visible la fenetre
     */
    public void afficher() {
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setVisible(true);                        
    }

    public void quitter() {
        boolean res = IhmBoiteMessage.afficherBoiteDialogue("A bientôt", "info");
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));//Permet de fermer la fenêtre
    }
    
    public boolean vérifierNomJoueurs() {
        nomJoueurs.clear();
        boolean correct = true;
        int i=0;
        while (correct && i<nbjoueurs) {
            if (champNomJoueurs.get(i).getText().equals("")) {
                IhmBoiteMessage.afficherBoiteDialogue("Saisie Incorrecte: \nUn ou plusieurs joueurs n'ont pas de nom.", "info");
                correct = false;
                
            } else if (nomJoueurs.contains(champNomJoueurs.get(i).getText())) {
                IhmBoiteMessage.afficherBoiteDialogue("Saisie Incorrecte: \nPlusieurs joueurs ont le même nom.", "info");
                correct = false;
            } else {
                nomJoueurs.add(champNomJoueurs.get(i).getText());
            }
            i++;
        }
        return correct;
    }
    
    public HashSet<String> getNomJoueurs() {  
        return nomJoueurs;
    }
    
    
}
