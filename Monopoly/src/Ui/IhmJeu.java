/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.ProprieteAConstruire;
import Jeu.Resultat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
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
    private JLabel labelinfoCarte2;
    private Carreau DepartJcourant;
    private JButton construire;
    private JButton afficherProp;
    private JLabel labelCaseDep;
    private int compteurlanceDes;
    private int compteuroui;
    private int compteurnon;
    private int compteurJsuivant = 0;
    private int compteurRejouer = 0;
    private int compteurConstruire = 0;
    private JLabel nbMaison;
    private JLabel nbHotel;
    private Color fond = new Color(218,233,212);

    
    public IhmJeu(HashSet<String> noms) {        
        plateau = new IhmPlateau(noms);
        this.getContentPane().setBackground(fond);
        controle = new JPanel();
        controle.setBackground(fond);
        
        
        this.setLayout(new BorderLayout());
        this.add(plateau, BorderLayout.CENTER);
        
        compteurlanceDes = 0;
        this.compteurnon = 0;
        this.compteurnon = 0;
        this.add(this.controle(), BorderLayout.EAST);
        
        JPanel panelJoueur = new JPanel();
        panelJoueur.setBackground(fond);
        this.nbMaison = new JLabel();
        this.nbHotel = new JLabel();
        
        panelJoueur.add(nbMaison);
        panelJoueur.add(nbHotel);
        this.add(panelJoueur, BorderLayout.SOUTH);
        
        String couleur[] ={"#F41C25", "#083052", "#25980E", "#5a2400", "#D101FF", "#FF6800"};
        int numCouleur = 0;
        for (String n : noms) {
           JLabel l = new JLabel("<html><font color = "+ couleur[numCouleur] + " >" + n + "      </font></html>");
           
           //l.set(Color.getColor(couleur[numCouleur]));
            panelJoueur.add(l);
            numCouleur++;
        }
        
        this.initJoueur();
        this.initInfos();
    }
    
    
    
    private JPanel controle() {
        this.infos = new JPanel();
        infos.setBackground(fond);
        this.controle.add(infos);
        this.infos.setLayout(new GridLayout(17, 1));
        this.infos.add(new JLabel("----------------------------Contrôle----------------------------"));
        return this.controle;
    }
    
    private void initJoueur() {
        
        
        this.getContentPane().setBackground(fond);

   
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
        panelDes.setBackground(fond);
        this.infos.add(this.panelDes);
        this.labelDe1 = new JLabel();
        this.labelDe2 = new JLabel();
        this.panelDes.add(this.labelDe1);
        this.panelDes.add(this.labelDe2);
        
        this.labelCaseDep = new JLabel();
        this.infos.add(this.labelCaseDep);
        this.labelinfoCarte = new JLabel();
        this.infos.add(this.labelinfoCarte);
        this.labelinfoCarte2 = new JLabel();
        this.infos.add(this.labelinfoCarte2);
        
        this.labelInfoCase = new JLabel();
        this.infos.add(this.labelInfoCase);
        
        panelRep = new JPanel();
        panelRep.setBackground(fond);
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
        
        JPanel boutonsProp = new JPanel();
        boutonsProp.setBackground(fond);
        boutonsProp.setLayout(new GridLayout(1,2));
        this.infos.add(boutonsProp);
        this.construire = new JButton("Construire");
        this.construire.setVisible(false);
        boutonsProp.add(this.construire);
        this.afficherProp = new JButton("Afficher vos propriétés");
        this.afficherProp.setVisible(false);
        boutonsProp.add(this.afficherProp);
        
        
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
        //IhmMonopoly test = new IhmMonopoly(j);
        //this.add(test, BorderLayout.WEST);
        
         
        this.majJoueur(j);
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
                if (compteurlanceDes >= 1) {
                    
                }
                else {
                compteurlanceDes ++;
                lanceDes.setEnabled(false);
                observateur.lanceDes(j,nbdouble);
                }
            }
        });
        
        this.setVisible(true);
        
    }
    
    private void majJoueur(Joueur j) {
        this.cash.setText("Cash : " + j.getCash());
        this.nomCarte.setText("Case : " + j.getPositionCourante().getNomCarreau());
        this.nbMaison.setText("Maison disponibles : " + this.observateur.getNbMaison() + "  ");
        this.nbHotel.setText("Hotel disponibles : " + this.observateur.getNbHotel()+"   ");
    }
    
    
    public void afficherInfos(Joueur j, Resultat res, int d1 , int d2, int nbdouble) throws InterruptedException {
        
            
            
            this.nbdouble = nbdouble;
            this.dDouble = d1 == d2;
            this.majJoueur(j);
            
            this.plateau.recupDonneesJoueur(j, j.getPositionCourante(), this.DepartJcourant, res.isEnPrison());
            
            this.labelDe1.setIcon(new ImageIcon("src/Data/"+d1+".png"));
            this.labelDe2.setIcon(new ImageIcon("src/Data/"+d2+".png"));
            
            if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) { // Autre Carreau
                if (res.getNomCarte() != null && res.getNomCarreau() != null) { // Carreau avec Tirage de Carte
                    if  (res.getNomCarte().contains("!")) {
                        String[] nomCarteTronque = res.getNomCarte().split("!");
                        
                        this.labelinfoCarte.setText(nomCarteTronque[0]);
                        this.labelinfoCarte2.setText(nomCarteTronque[1]);
                        this.observateur.Reponse(0, j, res);
                    } else {
                       this.labelinfoCarte.setText(res.getNomCarte());
                       this.observateur.Reponse(0, j, res);
                    }
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
                    this.observateur.Reponse(0, j, res);
                }
                else if ("Taxe de Luxe".equals(res.getNomCarreau())) {
                    this.labelinfoCarte.setText("Vous Payez 100€ de Taxe");
                    this.observateur.Reponse(0, j, res);
                }
                else if ("Départ".equals(res.getNomCarreau())) {
                    this.observateur.Reponse(0, j, res);
                }
                else if ("Simple Visite / En Prison".equals(res.getNomCarreau())) {
                    this.observateur.Reponse(0, j, res);
                }
                else if ("Parc Gratuit".equals(res.getNomCarreau())) {
                    this.observateur.Reponse(0, j, res);
                }
                else if (res.isEnPrison()) {
                    this.labelinfoCarte.setText("Vous Allez en Prison");
                    this.observateur.Reponse(6, j, res);
                }
                
            }
            else if (res.getNomCarreau() == null && res.isEnPrison()) {
                if (res.getDeplacement() == -1) {
                    this.labelinfoCarte.setText("Vous allez en Prison");
                    this.observateur.Reponse(0, j, res);
                }
                else {
                        this.labelinfoCarte.setText("Vous êtes en prison");
                        this.observateur.Reponse(0, j, res);
                }
                
            }
            //Propriete --> Acheter ou payer le loyer
            else if (res.getProprietairePropriete() != null && res.getProprietairePropriete() != j) {
                //System.out.println("Loyer = " + res.getLoyerPropriete());//Nom déjà affiché + paiement obligatoire du loyer
                this.labelInfoCase.setText("Vous avez Payé " + res.getLoyerPropriete() + "€" + " à " + res.getProprietairePropriete().getNom());
                this.observateur.Reponse(0, j, res);
                
            }
            else if(res.getPrixPropriete() == -2) { // Cas où le joueur n'a pas assez d'argent pour acheter la propriété
                this.labelInfoCase.setText("Vous ne pouvez pas acheter " + j.getPositionCourante().getNomCarreau());
                this.observateur.Reponse(0, j, res);
            }
            else if (res.getPrixPropriete() != -1) { // Cas où le joueur peux acheter la propriété
                this.labelInfoCase.setText("Voulez-vous acheter " + j.getPositionCourante().getNomCarreau() + " Pour " + res.getPrixPropriete()  +"€ ?");
                
                oui.setVisible(true);
                oui.setEnabled(true);
                non.setVisible(true);
                non.setEnabled(true);
                
                
                oui.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (compteuroui >= 1) {
                    
                        }
                        else {
                            compteuroui ++;
                            oui.setEnabled(false);
                            non.setEnabled(false);
                            observateur.Reponse(2, j, res);
                        }
                    }
                });
                
                non.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (compteurnon >= 1) {
                    
                        }
                        else {
                            compteurnon ++;
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
            //plateau.createBufferStrategy(2);

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
    
    public int notification(String message, Joueur j) {
        if (message == "deplacement" ) {
            IhmBoiteMessage.afficherBoiteDialogue("Deplacement, l'action de la case vas être lancé", "info");
            this.effacer();
            this.majJoueur(j);
        }
        else{
        this.labelInfoReponce.setText(message);
        this.majJoueur(j);
        if (!this.dDouble || j.getToursEnPrison() != -1) {
            jSuivant.setVisible(true);
            jSuivant.setEnabled(true);
            this.nbdouble = 0;
            jSuivant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (compteurJsuivant >= 1) {
                     
                    }
                    else {
                        compteurJsuivant ++;
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
                    if (compteurRejouer >= 1) {
                    
                    }
                    else {
                        compteurRejouer ++;
                        effacer();
                        rejouer.setEnabled(false);
                        observateur.rejouer(j, nbdouble);
                    }
                }
            });
        }
        
        this.afficherProp.setVisible(true);
        this.afficherProp.setEnabled(false);
        this.afficherProp.setToolTipText("Vous ne possédez pas de propriétés");
        if (j.getProprietesAconstruire().size() > 0 || j.getGares().size() > 0 || j.getCompagnies().size() > 0) {
            this.afficherProp.setEnabled(true);
            this.afficherProp.setToolTipText("Affichez vos propriétés");
        }
        ArrayList<ProprieteAConstruire> props = calculIhmConst(j);
        IhmJeu ihmJeu = this;
        construire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IhmConstruire ihmProp = new IhmConstruire(j, props, ihmJeu);
                ihmProp.setObservateur(observateur);
            }
        });
        this.setVisible(true);
        }
        return this.nbdouble;
    }
        
    public ArrayList<ProprieteAConstruire> calculIhmConst (Joueur j) {
        ArrayList<ProprieteAConstruire> props = new ArrayList<>();
        this.construire.setVisible(true);
        this.construire.setEnabled(false);
        this.construire.setToolTipText("Vous ne pouvez pas construire de maison/hôtel");
        for (ProprieteAConstruire prop : j.getProprietesAconstruire()) {
            if (observateur.peutConstruire(j, prop)) {
                props.add(prop);
                this.construire.setEnabled(true);
                this.construire.setToolTipText("Construisez des maisons/hôtels sur vos propriétés");
            }
        }
        return props;
    }
    
    public void refreshConst(Joueur j) {
        ArrayList<ProprieteAConstruire> props = calculIhmConst(j);
        if (props.size() != 0) {
            IhmConstruire ihmProp = new IhmConstruire(j, props, this);
            ihmProp.setObservateur(observateur);
        }
        
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
        
        this.compteurlanceDes = 0;
        this.compteuroui = 0;
        this.compteurnon = 0;
        this.compteurJsuivant = 0;
        this.compteurRejouer = 0;
        this.compteurConstruire = 0;
        this.setVisible(true);
    }
    
    public void messageCaseDepart(Joueur j){
        this.labelCaseDep.setText("Vous passez par la case Départ, recevez 200€");
        
    }
    
    public void sortiePrison(String raison) {
        
            this.labelCaseDep.setText("Vous êtes sorti de prison,"+ raison);
    }
    
    public void afficher() {
        this.setTitle("Monopoly");
        this.setSize(1300, 1000);
        controle.setSize(400, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    void setObservateur(Observateur observateur) {
        this.observateur = observateur; //To change body of generated methods, choose Tools | Templates.
    }

    public HashMap<String, String> getCouleurJoueurs() {
        return this.plateau.getCouleurJoueurs();    
    }
    
    public void ajoutMaison (int numCarr) {
        plateau.ajoutMaison(numCarr);
    }
}