/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Carreau;
import Jeu.Carte;
import Jeu.Joueur;
import Jeu.Resultat;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vivierlo
 */
public class IhmPlateau extends Canvas{
    private int x, y;
    
    
    private BufferedImage fondPlateau;
    Observateur observateur;
    
    private ArrayList<BufferedImage> pions; //Liste des pions
    private HashMap<String, Integer> joueurs; //Liste des joueurs avec String: nom joueur et Integer: numéro Carreau courant
    private ArrayList<Integer> nbJoueursParCases;
    
    private HashMap<String, Integer> joueurCourant;
    private int numCarreauCourant;
    private int numCarreauDestination;
    private int nbJoueurs;
    private String nomJoueurCourant;
    
    
    
    
    public IhmPlateau(HashSet<String> noms) throws InterruptedException   {
        super();
        pions = new ArrayList<>();
        joueurs = new HashMap<>();
        joueurCourant = new HashMap<>();
        nbJoueursParCases = new ArrayList<>();
        nbJoueurs = joueurs.size();
        
        
        //Initialisation des joueurs
        for (String nomJ : noms) {
            joueurs.put(nomJ, 1); //positionnés sur le carreau de départ
        }
        
        //Initialisation des pions
        try {
            pions.add(ImageIO.read(new File("src/Data/pionRouge.png")));
            pions.add(ImageIO.read(new File("src/Data/pionBleu.png")));
            pions.add(ImageIO.read(new File("src/Data/pionVert.png")));
            pions.add(ImageIO.read(new File("src/Data/pionJaune.png")));
            pions.add(ImageIO.read(new File("src/Data/pionViolet.png")));
            pions.add(ImageIO.read(new File("src/Data/pionOrange.png")));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        /**
         * Initialisation des coordonnées des pions.
        */
        
        //Initialisation du nombre de joueurs par case
        for(int i= 1; i<=40; i++) {
            nbJoueursParCases.add(0);
        }
        nbJoueursParCases.set(0, nbJoueurs); // Tous les joueurs sont sur la case départ
        
    }
    
    /**
    * Dessine le contenu du canvas, c'est-à-dire l'icone
    * @param g un contexte graphique
    */
    @Override
    public void paint(Graphics g) {
        Dimension d = new Dimension(900, 900);
        super.setSize(d);
        super.paint(g);
        try {
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        /*
            while (joueurCourant.getPositionCourante()!=destination) {
                avancerPion()
                //Changement de case
                nbJoueursParCases.set(numCarreauCourant-1, nbJoueursParCases.get(numCarreauCourant-1)-1);
                numCarreauCourant = this.numCarreauSuivant(numCarreauCourant);
                nbJoueursParCases.set(numCarreauCourant-1, nbJoueursParCases.get(numCarreauCourant-1)+1);
            }*/
        Point p = new Point(); 
        while (numCarreauCourant!=numCarreauDestination) {
            try {
                p = avancerPion(joueurCourant);
            } catch (InterruptedException ex) {
                Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
            }
            int i = joueurs.get(nomJoueurCourant);
            g.drawImage(pions.get(i), p.x, p.y, (ImageObserver) observateur);
        }
    }
    
    //Donne les coordonnées précises du pion après qu'il ai avancé
    public Point avancerPion(HashMap<String, Integer> j) throws InterruptedException {
        int BASE = 786; // Coordonnées x et y de la case départ pour le 1er pion
        int x = 0, y = 0;
        //int numCarreauCourant = j.get(nomJoueurCourant).intValue();
        
        if(numCarreauCourant == 1) {//CASE DEPART
            x = BASE;// Pour le joueur courant x = 786
            y = BASE;// y = 786;
        }
        else if(numCarreauCourant <= 10) { //LIGNE BAS
            x = BASE-74*(numCarreauCourant-1);
            y = 844;
        }
        else if (numCarreauCourant == 11) {//VISITE PRISON 
            x = 5;
            y = BASE;
        }
        else if (numCarreauCourant <= 20) {//LIGNE GAUCHE
            x = 16;
            y = BASE-74*(numCarreauCourant-11);
        }
        else if (numCarreauCourant == 21) {//PARC GRATUIT
            x = 16;
            y = 16;
        }
        else if (numCarreauCourant <= 30) {//LIGNE HAUT
            x = 120+74*(numCarreauCourant-22);
            y = 16;
        }
        else if (numCarreauCourant == 31) {//ALLER EN PRISON
            x = 786;
            y = 16;
        }
        else if (numCarreauCourant <= 40) {//LIGNE DROITE
            x = 841;
            y = 123+74*(numCarreauCourant-32);
        }
        positionnePionSurCase(numCarreauCourant, nbJoueursParCases.get(numCarreauCourant), x, y);

        return new Point(x,y);
        /*int numJ=0;
        while (numJ<nbJoueurs) {
            Point posJoueur = getPositionJoueur(numJ);
            positionnePionSurCase(numJ, numCarreauCourant, nbJoueursParCases.get(numCarreauCourant), posJoueur.x, posJoueur.y);

            System.out.println("numJ:" + numJ + "|numCarreauCourant:" + numCarreauCourant + " |nbJoueursSurCase:" 
                    + nbJoueursParCases.get(numCarreauCourant) + " |posJoueur.x:" + posJoueur.x + " |posJoueur.y:" + posJoueur.y);
            numJ++;
        }
         System.out.println("||||||||||||||");*/
        //g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur);

    }
    
    private int numCarreauSuivant(int numCar) {
        if(numCar++ % 40  == 0) 
            return 1;
        else return numCar++ % 40;
    }
    
    //Sélectionne les coordonnées d'affichage du pion sur la case
    private void positionnePionSurCase(int numCase, int nbJoueurCase, int xPion1, int yPion1) {
        int ESPACEMENT = 2; // Espacement en pixel entre 2 pion cote à cote
        int LARGEUR_PION = 21; // Largeur d'un pion en pixels
        int EMPILEMENT = 13; // Décalage en pixels entre deux pions qui s'empilent vers le bas
        int HAUTEUR_PION = 26; // Hauteur d'un pion en pixels
        int CHANGE_LIGNE = 3; // Conctante définissant le nombre de pions par ligne 
        
        if(numCase == 1) {//CASE DEPART 
            for (int i=1; i<nbJoueurCase; i++) {
                xPion1 += ESPACEMENT + LARGEUR_PION;
                if (nbJoueurCase >= CHANGE_LIGNE) {  
                    yPion1 += ESPACEMENT + HAUTEUR_PION; 
                }  
            }
            
        } else if(numCase <= 10) { //LIGNE BAS
            for (int i=1; i<nbJoueurCase; i++) {
                yPion1 += EMPILEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) {   
                    xPion1 += LARGEUR_PION + ESPACEMENT; 
                }  
            }

        } else if (numCase == 11) {//VISITE PRISON 
            for (int i=1; i<nbJoueurCase; i++) {
                yPion1 += EMPILEMENT;
            }
            
        } else if (numCase <= 20) {//LIGNE GAUCHE
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += LARGEUR_PION + ESPACEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                    yPion1 += EMPILEMENT;
                }  
            }
            
        } else if (numCase == 21) {//PARC GRATUIT
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += ESPACEMENT + LARGEUR_PION;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   yPion1 += ESPACEMENT + HAUTEUR_PION; 
                }  
            }
            
        } else if (numCase <= 30) {//LIGNE HAUT
            for (int i=1; i<nbJoueurCase; i++) {                    
                yPion1 += EMPILEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   xPion1 += LARGEUR_PION + ESPACEMENT; 
                }  
            }
            
        } else if (numCase == 31) {//ALLER EN PRISON
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += ESPACEMENT + LARGEUR_PION;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   yPion1 += ESPACEMENT + HAUTEUR_PION;
                }  
            }
            
        } else if (numCase <= 40) {//LIGNE DROITE
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += LARGEUR_PION + ESPACEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   yPion1 += EMPILEMENT;
                }  
            }
        }
        
    }
   
    /*public Point getPositionJoueur(int numJoueur) {
        return pos.get(numJoueur);
    }
    
    public void setPosition(int numJ, int x, int y) {
        pos.set(numJ, new Point (x,y));
    }*/
    
    /*public void dessinerPions() {
        int numJ=0;
        while (numJ<nbJoueurs) {
            Point posJoueur = getPositionJoueur(numJ);
            positionnePionSurCase(g, numJ, numCarreauCourant, nbJoueursParCases.get(numCarreauCourant), posJoueur.x, posJoueur.y);

            System.out.println("numJ:" + numJ + "|numCarreauCourant:" + numCarreauCourant + " |nbJoueursSurCase:" 
                    + nbJoueursParCases.get(numCarreauCourant) + " |posJoueur.x:" + posJoueur.x + " |posJoueur.y:" + posJoueur.y);
            numJ++;
        }
         System.out.println("||||||||||||||");
        Thread.sleep(500);
        //g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur);
    }*/

    /**public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    public void setJoueurs(HashSet<String> joueurs) {
        this.joueurs = joueurs;
    }*/

    public void recupDonneesJoueur(Joueur j, Carreau positionCourante, Carreau anciennePosition) {
        numCarreauCourant = anciennePosition.getNumero();
        nomJoueurCourant = j.getNom();
        joueurCourant.put(nomJoueurCourant, numCarreauCourant);
        numCarreauDestination = positionCourante.getNumero();
    }
}
