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
import javax.swing.ImageIcon;
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
    private Observateur observateur;
    private JButton lanceDes;
    private JPanel infos;
    
    public IhmJeu()   {        
        plateau = new IhmPlateau();
        controle = new JPanel();
        
        
        this.setLayout(new BorderLayout());
        this.add(plateau, BorderLayout.CENTER);
        
        this.add(this.controle(), BorderLayout.EAST);
        this.initJoueur();
    }
    
    
    
    private JPanel controle() {
        this.infos = new JPanel();
        this.controle.add(infos);
        this.infos.setLayout(new GridLayout(8, 1));
        
        return this.controle;
    }
    
    private void initJoueur() {
        nomJoueur = new JLabel();
        nomCarte = new JLabel();
        cash = new JLabel();
        
        this.infos.add(nomJoueur);       
        this.infos.add(cash);        
        this.infos.add(nomCarte);
        
    }
    private void initInfos() {
        acheter = new JButton();
        this.infos.add(acheter);
    }
    
    //Affichera toutes les infos du joueur
    public void displayJoueur(Joueur j) {
        lanceDes = new JButton("Lancer les dés");
        
        this.nomJoueur.setText("A votre tour " + j.getNom());
        
        this.cash.setText("Cash : " + j.getCash());
        this.nomCarte.setText("Case : " + j.getPositionCourante().getNomCarreau());
        
        JPanel panelDesJoueur = new JPanel();
        JPanel panelDe1 = new JPanel();
        JPanel panelDe2 = new JPanel();
        this.controle.add(panelDesJoueur);
        panelDesJoueur.add(panelDe1);
        panelDesJoueur.add(panelDe2);
        
        this.infos.add(lanceDes);
        
        lanceDes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observateur.lanceDes(j);
            }
        });
        
    }
    
    
    public void afficherInfos(Joueur j, Resultat res, int d1 , int d2, int nbdouble) {
        
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
        
        
        /*panelDe1.add(new JLabel(new ImageIcon("src/Data/"+res.getDe1+".png")));
        panelDe2.add(new JLabel(new ImageIcon("src/Data/"+res.getDe2+".png")));*/
        
    }
    
    private void lancerDes(Resultat res) {//TODO : récupérer le résultat des deux dés, soit avec les deux entiers, soit avec res 
        
    }
    
    private void displayDes(int de1, int de2) {
        //TODO : Afficher le résultat des deux dés et l'ajouter à la fenêtre
    }
    
    public void afficher() {
        this.setTitle("Monopoly");
        this.setSize(1200, 1000);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    void setObservateur(Observateur observateur) {
        this.observateur = observateur; //To change body of generated methods, choose Tools | Templates.
    }
}
