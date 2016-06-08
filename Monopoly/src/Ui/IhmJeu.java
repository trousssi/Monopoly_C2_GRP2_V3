/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import Jeu.Resultat;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author fallm
 */
public class IhmJeu extends JFrame{
    private final IhmPlateau plateau; 
    private final JPanel controle;
    
    public IhmJeu() throws IOException  {        
        plateau = new IhmPlateau();
        controle = new JPanel();
        
        this.setLayout(new BorderLayout());
        this.add(plateau, BorderLayout.CENTER);
        
        this.add(this.controle(), BorderLayout.EAST);
        
        afficher();
    }
    
    private JPanel controle() {
        this.controle.setLayout(new GridLayout());
        this.controle.add(new JLabel("controle"));
        
        
        return this.controle;
    }
    
    //Affichera toutes les infos du joueur
    private void displayJoueur(Joueur j, Resultat res) {
        this.controle.add(new JLabel("A votre tour " + j.getNom()));
        
        this.controle.add(new JLabel("Cash : " + j.getCash()));
        this.controle.add(new JLabel("Case : " + j.getPositionCourante().getNomCarreau()));
        
        
        JButton acheter = new JButton("Acheter cette propriéte.");
        this.controle.add(acheter);
        if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) {
            acheter.setEnabled(false);
            this.controle.add(new JButton("Cette case n'est pas achetable."));
        }
        else if (res.getProprietairePropriete() != null && res.getProprietairePropriete() != j) {
            acheter.setEnabled(false);
            this.controle.add(new JButton("Case déjà acheté, vous payer : " + res.getLoyerPropriete() + " €."));
        }
        else if (res.getPrixPropriete() == -2) {            
            acheter.setEnabled(false);
            this.controle.add(new JButton("Vous n'avez pas les fonds suffisants."));
        }
        else if (res.getProprietairePropriete() == j) {
            this.controle.add(new JButton("Vous possedez déjà cette case ! "));
        }
        else {
            this.controle.add(new JButton("Vous pouvez acheter cette case."));
        }
        acheter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO : Ouvrir popup de confirmation avec le prix de la propriete
            }
        });
        
        
    }
    
    private void lancerDes(Resultat res) {//TODO : récupérer le résultat des deux dés, soit avec les deux entiers, soit avec res
        JButton lanceDes = new JButton("Lancer les dés");
        
        this.controle.add(lanceDes);
        
        lanceDes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //displayDes(de1, de2);
                lanceDes.setEnabled(false);
            }
        });
        
        
    }
    
    private void displayDes(int de1, int de2) {
        //TODO : Afficher le résultat des deux dés et l'ajouter à la fenêtre
    }
    
    public void afficher() {
        this.setTitle("Monopoly");
        this.setSize(1000, 1000);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
}
