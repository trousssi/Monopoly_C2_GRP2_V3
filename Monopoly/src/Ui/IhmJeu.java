/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import Jeu.Resultat;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Icon;
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
    private JPanel panelDes;
    private JLabel nomJoueur;
    private JLabel cash;
    private JLabel nomCarte;
    private JButton acheter;
    private Observateur observateur;
    private JButton lanceDes;
    private JPanel infos;
    private int nbdouble;
    private boolean dDouble ;
    private JLabel labelDe1;
    private JLabel labelDe2;
    private JLabel labelInfoCase;
    
    
    
    public IhmJeu() throws InterruptedException   {        
        plateau = new IhmPlateau();
        controle = new JPanel();
        
        
        this.setLayout(new BorderLayout());
        this.add(plateau, BorderLayout.CENTER);
        
        this.add(this.controle(), BorderLayout.EAST);
        this.initJoueur();
        this.initInfos();
    }
    
    
    
    private JPanel controle() {
        this.infos = new JPanel();
        this.controle.add(infos);
        this.infos.setLayout(new GridLayout(11, 1));
        
        return this.controle;
    }
    
    private void initJoueur() {
        nomJoueur = new JLabel();
        nomCarte = new JLabel();
        cash = new JLabel();
        
        this.infos.add(nomJoueur);       
        this.infos.add(cash);        
        this.infos.add(nomCarte);
         lanceDes = new JButton("Lancer les dés");
         this.infos.add(lanceDes);
         this.lanceDes.setVisible(false);
         
    }
    private void initInfos() {
        this.panelDes = new JPanel();
        this.infos.add(this.panelDes);
        this.labelDe1 = new JLabel();
        this.labelDe2 = new JLabel();
        this.panelDes.add(this.labelDe1);
        this.panelDes.add(this.labelDe2);
        
        this.labelInfoCase = new JLabel();
        this.infos.add(this.labelInfoCase);
        //acheter = new JButton();
        //this.infos.add(acheter);
    }
    
    //Affichera toutes les infos du joueur
    public void displayJoueur(Joueur j, int nbdouble) {
        
        this.MajJoueur(j);
       
        
        this.nomJoueur.setText("A votre tour " + j.getNom());
        
        this.cash.setText("Cash : " + j.getCash());
        this.nomCarte.setText("Case : " + j.getPositionCourante().getNomCarreau());
        
       
        this.lanceDes.setVisible(true);
        this.lanceDes.setEnabled(true);
        
        
        lanceDes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lanceDes.setEnabled(false);
                observateur.lanceDes(j,nbdouble);
                
            }
        });
        
        this.setVisible(true);
        
    }
    
    private void MajJoueur(Joueur j) {
        this.cash.setText("Cash : " + j.getCash());
        this.nomCarte.setText("Case : " + j.getPositionCourante().getNomCarreau());
    }
    
    
    public void afficherInfos(Joueur j, Resultat res, int d1 , int d2, int nbdouble) {
        this.nbdouble = nbdouble;
        this.dDouble = d1 == d2;
        this.MajJoueur(j);
       
        this.labelDe1.setIcon(new ImageIcon("src/Data/"+d1+".png"));
        this.labelDe2.setIcon(new ImageIcon("src/Data/"+d2+".png"));
        
        if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) { // Autre Carreau
             this.observateur.Reponce(0, j, res);
        }
        
        //Propriete --> Acheter ou payer le loyer
        else if (res.getProprietairePropriete() != null && res.getProprietairePropriete() != j) {
            //System.out.println("Loyer = " + res.getLoyerPropriete());//Nom déjà affiché + paiement obligatoire du loyer
            this.labelInfoCase.setText("Vous avez Payer " + res.getLoyerPropriete() + " à " + res.getProprietairePropriete());
            this.observateur.Reponce(0, j, res);
        
        }
        else if(res.getPrixPropriete() == -2) { // Cas où le joueur n'a pas assez d'argent pour acheter la propriété
            this.labelInfoCase.setText("\033[31mVous ne pouvez pas acheter \033[0m" + j.getPositionCourante().getNomCarreau());
             this.observateur.Reponce(0, j, res);
        }
        else if (res.getPrixPropriete() != -1) {               // Cas où le joueur peux acheter la propriété
            this.labelInfoCase.setText("Voulez-vous acheter " + j.getPositionCourante().getNomCarreau() + " Pour " + res.getPrixPropriete()  +"€ ?");
            JPanel panelRep = new JPanel();
            this.infos.add(panelRep);
            JButton oui = new JButton("oui");
            JButton non = new JButton("non");
            panelRep.add(oui);
            panelRep.add(non);
            
            oui.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    oui.setEnabled(false);
                    non.setEnabled(false);
                     observateur.Reponce(2, j, res);
                     
                }
            });
            
            non.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    oui.setEnabled(false);
                    non.setEnabled(false);
                    observateur.Reponce(0, j, res);
                }
            });
        }
      
       else if (res.getProprietairePropriete() == j){ // Cas où le joueur tombe sur une case qu'il a déjà acheté
        this.labelInfoCase.setText("Vous êtes le proprietaire de cette case.");
         this.observateur.Reponce(0, j, res);
       }
       else {
           this.observateur.Reponce(0, j, res);
       }
       
       
        this.setVisible(true);
        //this.setVisible(true);
        /* if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) {
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
        });*/
        
        
        /*panelDe1.add(new JLabel(new ImageIcon("src/Data/"+res.getDe1+".png")));
        panelDe2.add(new JLabel(new ImageIcon("src/Data/"+res.getDe2+".png")));*/
        
    }
    
    public void notification(String message, Joueur j) {
        this.infos.add(new JLabel(message));
        this.MajJoueur(j);
        if (!this.dDouble) {
            JButton jSuivant = new JButton("Joueur Suivant");
            this.infos.add(jSuivant);
            this.nbdouble = 0;
            jSuivant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jSuivant.setEnabled(false);
                    effacer();
                    observateur.joueurSuivant();
                    
                }
            });    
        }
        else {
            this.infos.add(new JLabel("Vous avez fait un double, vous pouvez rejouer !!"));
            JButton rejouer = new JButton("Rejouer");
            this.infos.add(rejouer);
            rejouer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rejouer.setEnabled(false);
                    effacer();
                   observateur.rejouer(j, nbdouble);
                }
            });
        }
         this.setVisible(true);
    }
    
    public void effacer() {
        this.labelDe1.setIcon(new Icon());
        this.labelDe2.setText("");
        this.labelInfoCase.setText("");
        
        this.setVisible(true);
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
