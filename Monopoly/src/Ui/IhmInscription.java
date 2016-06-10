/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
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
    private final ArrayList<JLabel> labelJoueurs = new ArrayList<>();
    private final ArrayList<JTextField> champNomJoueurs = new ArrayList<>();
    
    
    
    
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
        
        //Couleur de fond
        Color fond = new Color(206,239,203);
        this.getContentPane().setBackground(fond);
        gestionJoueurs.setBackground(fond);
        inscriptions.setBackground(fond);
        choix.setBackground(fond);
        
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
        JButton valider = new JButton("Valider les modifications");
        JButton retourMenu = new JButton("Retour au Menu");
        choix.add(valider, BorderLayout.EAST);
        choix.add(retourMenu, BorderLayout.WEST);
        
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
        
        retourMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                observateur.recupererNomJoueurs(nomJoueurs);
            }
        });        
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res = vérifierNomJoueurs();
                if (res) {
                    IhmBoiteMessage.afficherBoiteDialogue(nbjoueurs + " joueurs inscrits.", "info");
                    nomJoueurs = getNomJoueurs();
                    
                    setVisible(false);
                    observateur.recupererNomJoueurs(nomJoueurs);
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
        setSize(400, 250);
        setTitle("Inscription des joueurs");
        setVisible(true);                        
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
