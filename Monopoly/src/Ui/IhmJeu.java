/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.Resultat;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    //private final IhmPlateau plateau; 
    private final JPanel controle;
    private JPanel panelDes;
    private JLabel nomJoueur;
    private JLabel cash;
    private JLabel nomCarte;
    private Observateur observateur;
    private JButton lanceDes;
    private JPanel infos;
    private int nbdouble;
    private boolean dDouble ;
    private JLabel labelDe1;
    private JLabel labelDe2;
    private JLabel labelInfoCase;
    private JPanel panelRep;
    private JButton non;
    private JButton oui;
    private JLabel labelInfoReponce;
    private JLabel labelInfoDouble;
    private JButton jSuivant;
    private JButton rejouer;
    private JLabel labelinfoCarte;
    private Carreau DepartJcourant;
    private JButton construire;
    private JLabel labelCaseDep;
    private int conteurlanceDes;
    private int conteuroui;
    private int conteurnon;
    private int conteurJsuivant = 0;
    private int conteurRejouer = 0;
    private int conteurConstruire = 0;
    
    public IhmJeu(HashSet<String> joueurs) throws InterruptedException   {        
      //  plateau = new IhmPlateau(noms);
        controle = new JPanel();
        
        
        this.setLayout(new BorderLayout());
       // this.add(plateau, BorderLayout.CENTER);
        
        conteurlanceDes = 0;
        this.conteurnon = 0;
        this.conteurnon = 0;
        this.add(this.controle(), BorderLayout.EAST);
        this.initJoueur();
        this.initInfos();
    }
    
    
    
    private JPanel controle() {
        this.infos = new JPanel();
        this.controle.add(infos);
        this.infos.setLayout(new GridLayout(15, 1));
        this.infos.add(new JLabel("                                                                                                                                                                                                                "));
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
        
        this.labelCaseDep = new JLabel();
        this.infos.add(this.labelCaseDep);
        this.labelinfoCarte = new JLabel();
        this.infos.add(this.labelinfoCarte);
        
        this.labelInfoCase = new JLabel();
        this.infos.add(this.labelInfoCase);
        
        panelRep = new JPanel();
        this.infos.add(panelRep);
        oui = new JButton("oui");
        non = new JButton("non");
        panelRep.add(oui);
        panelRep.add(non);
        oui.setVisible(false);
        non.setVisible(false);
        
        this.labelInfoReponce = new JLabel();
        this.infos.add(this.labelInfoReponce);
        
        this.labelInfoDouble = new JLabel();
        this.infos.add(this.labelInfoDouble);
        
        
        this.construire = new JButton("Construire");
        this.infos.add(this.construire);
        this.construire.setVisible(false);
        
        
        jSuivant = new JButton("Joueur Suivant");
        this.infos.add(jSuivant);
        jSuivant.setVisible(false);
        
        rejouer = new JButton("Rejouer");
        this.infos.add(rejouer);
        rejouer.setVisible(false);
        //acheter = new JButton();
        //this.infos.add(acheter);
    }
    
    //Affichera toutes les infos du joueur
    public void displayJoueur(Joueur j, int nbdouble) {
        IhmMonopoly test = new IhmMonopoly(j);
        this.add(test, BorderLayout.WEST);
        this.DepartJcourant = j.getPositionCourante();
        
        this.nomJoueur.setText("A votre tour " + j.getNom());
        
        /*        this.cash.setText("Cash : " + j.getCash());
        this.nomCarte.setText("Case : " + j.getPositionCourante().getNomCarreau());*/        
       
        this.lanceDes.setVisible(true);
        this.lanceDes.setEnabled(true);
        
        this.lanceDes.removeAll();
        lanceDes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conteurlanceDes >= 1) {
                    
                }
                else {
                conteurlanceDes ++;
                lanceDes.setEnabled(false);
                observateur.lanceDes(j,nbdouble);
                }
            }
        });
        
        this.setVisible(true);
        
    }
    
    private void MajJoueur(Joueur j) {
        this.cash.setText("Cash : " + j.getCash());
        this.nomCarte.setText("Case : " + j.getPositionCourante().getNomCarreau());
    }
    
    
    public void afficherInfos(Joueur j, Resultat res, int d1 , int d2, int nbdouble) throws InterruptedException {
        
            
            
            this.nbdouble = nbdouble;
            this.dDouble = d1 == d2;
            this.MajJoueur(j);
            
           // this.plateau.recupDonneesJoueur(j, j.getPositionCourante(), this.DepartJcourant);
            
            this.labelDe1.setIcon(new ImageIcon("src/Data/"+d1+".png"));
            this.labelDe2.setIcon(new ImageIcon("src/Data/"+d2+".png"));
            
            if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) { // Autre Carreau
                if (res.getNomCarte() != null && res.getNomCarreau() != null) { // Carreau avec Tirage de Carte
                    this.labelinfoCarte.setText(res.getNomCarte());
                    if (res.isDeplace()) { // Carte deplacement
                        if (res.getDeplacement() != 0) { // deplacement normal
                            
                            this.observateur.Reponse(3, j, res);
                        } else if (res.getDeplacement() == -3) { // reculer de 3 cases
                            
                            
                            this.observateur.Reponse(4, j, res);
                        }
                        else if (res.isAnniversaire()) {
                            
                            
                            this.observateur.Reponse(5, j, res);
                        } else if (res.isEnPrison()) {
                            
                            this.labelinfoCarte.setText("Vous Allez en Prison");
                            this.observateur.Reponse(6, j, res);
                        }
                    }
                    
                }
                else if ("Impôt sur le revenu".equals(res.getNomCarreau())) {
                    this.labelinfoCarte.setText("Vous Payez 200€ d'impots");
                }
                else if ("Taxe de Luxe".equals(res.getNomCarreau())) {
                    this.labelinfoCarte.setText("Vous Payez 100€ de Taxe");
                }
                else if (res.isEnPrison()) {
                    this.labelinfoCarte.setText("Vous Allez en Prison");
                    this.observateur.Reponse(6, j, res);
                }
                this.observateur.Reponse(0, j, res);
            }
            //Propriete --> Acheter ou payer le loyer
            else if (res.getProprietairePropriete() != null && res.getProprietairePropriete() != j) {
                //System.out.println("Loyer = " + res.getLoyerPropriete());//Nom déjà affiché + paiement obligatoire du loyer
                this.labelInfoCase.setText("Vous avez Payer " + res.getLoyerPropriete() + " à " + res.getProprietairePropriete().getNom());
                this.observateur.Reponse(0, j, res);
                
            }
            else if(res.getPrixPropriete() == -2) { // Cas où le joueur n'a pas assez d'argent pour acheter la propriété
                this.labelInfoCase.setText("Vous ne pouvez pas acheter " + j.getPositionCourante().getNomCarreau());
                this.observateur.Reponse(0, j, res);
            }
            else if (res.getPrixPropriete() != -1) {               // Cas où le joueur peux acheter la propriété
                this.labelInfoCase.setText("Voulez-vous acheter " + j.getPositionCourante().getNomCarreau() + " Pour " + res.getPrixPropriete()  +"€ ?");
                
                oui.setVisible(true);
                oui.setEnabled(true);
                non.setVisible(true);
                non.setEnabled(true);
                
                
                oui.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (conteuroui >= 1) {
                    
                        }
                        else {
                            conteuroui ++;
                            oui.setEnabled(false);
                            non.setEnabled(false);
                            observateur.Reponse(2, j, res);
                        }
                    }
                });
                
                non.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (conteurnon >= 1) {
                    
                        }
                        else {
                            conteurnon ++;
                            oui.setEnabled(false);
                            non.setEnabled(false);
                            observateur.Reponse(0, j, res);
                        }
                    }
                });
            }
             
            else if (res.getProprietairePropriete() == j){ // Cas où le joueur tombe sur une case qu'il a déjà acheté
                this.labelInfoCase.setText("Vous êtes le proprietaire de cette case.");
                this.observateur.Reponse(0, j, res);
            }
            else {
                this.observateur.Reponse(0, j, res);
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
        this.labelInfoReponce.setText(message);
        this.MajJoueur(j);
        if (!this.dDouble) {
            jSuivant.setVisible(true);
            jSuivant.setEnabled(true);
            this.nbdouble = 0;
            jSuivant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (conteurJsuivant >= 1) {
                     
                    }
                    else {
                        conteurJsuivant ++;
                         effacer();
                        jSuivant.setEnabled(false);
                   
                        observateur.joueurSuivant(j);
                    }
                }
            });    
        }
        else {
            this.labelInfoDouble.setText("Vous avez fait un double, vous pouvez rejouer !!");
            rejouer.setVisible(true);
            rejouer.setEnabled(true);
            rejouer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (conteurRejouer >= 1) {
                    
                    }
                    else {
                        conteurRejouer ++;
                        effacer();
                        rejouer.setEnabled(false);
                        observateur.rejouer(j, nbdouble);
                    }
                }
            });
        }
        this.construire.setVisible(true);
         this.setVisible(true);
    }
    
    public void effacer() {
        /*this.labelDe1.setIcon(new ImageIcon("src/Data/deVide.png"));
        this.labelDe2.setIcon(new ImageIcon("src/Data/deVide.png"));
        this.labelInfoCase.setText("");
        
        this.oui.setVisible(false);
        this.non.setVisible(false);
        
        this.labelCaseDep.setText("");
        
        this.labelinfoCarte.setText("");
        
        this.labelInfoReponce.setText("");
        
        
        this.labelInfoDouble.setText("");
        
        this.construire.setVisible(false);
        
        this.jSuivant.setVisible(false);
        
        this.rejouer.setVisible(false);*/
        
        this.infos.removeAll();
        this.initJoueur();
        this.initInfos();
        
        this.conteurlanceDes = 0;
        this.conteuroui = 0;
        this.conteurnon = 0;
        this.conteurJsuivant = 0;
        this.conteurRejouer = 0;
        this.conteurConstruire = 0;
        this.setVisible(true);
    }
    
    public void messageCaseDepart(Joueur j){
        this.labelCaseDep.setText("Vous passez par la case Départ, recevez 200€");
        
    }
    
    public void sortiePrison() {
        this.labelCaseDep.setText("Vous êtes sorti de prison");
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
