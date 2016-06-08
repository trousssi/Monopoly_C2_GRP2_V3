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
    private JLabel nomJoueur;
    private JLabel cash;
    private JLabel nomCarte;
    private JButton acheter;
    
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
    private void initJoueur() {
        nomJoueur = new JLabel();
        nomCarte = new JLabel();
        cash = new JLabel();
        acheter = new JButton();
        this.controle.add(nomJoueur);       
        this.controle.add(cash);        
        this.controle.add(nomCarte);
        this.controle.add(acheter);
    }
    
    
    //Affichera toutes les infos du joueur
    private void displayJoueur(Joueur j, Resultat res) {
        this.nomJoueur.setText("A votre tour " + j.getNom());
        
        this.nomJoueur.setText("Cash : " + j.getCash());
        this.nomJoueur.setText("Case : " + j.getPositionCourante().getNomCarreau());
        
        if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) {
            acheter.setEnabled(false);
            this.acheter.setText("Cette case n'est pas achetable.");
        }
        else if (res.getProprietairePropriete() != null && res.getProprietairePropriete() != j) {
            acheter.setEnabled(false);
            this.acheter.setText("Case déjà acheté, vous payer : " + res.getLoyerPropriete() + " €.");
        }
        else if (res.getPrixPropriete() == -2) {            
            acheter.setEnabled(false);
            this.acheter.setText("Vous n'avez pas les fonds suffisants.");
        }
        else if (res.getProprietairePropriete() == j) {
            this.acheter.setText("Vous possedez déjà cette case ! ");
        }
        else {
            this.acheter.setText("Acheter cette propriéte");
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
