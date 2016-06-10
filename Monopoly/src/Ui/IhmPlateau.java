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
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author fallm
 */
public class IhmPlateau extends Canvas{
    private BufferedImage fondPlateau;
    Observateur observateur;
    private ArrayList<BufferedImage> pions;
    private int numCarreau;
    private int nbJoueurs;//L'ajouter comme paramètre 
    private ArrayList<Point> pos;
    private int x, y;
    private ArrayList<Integer> nbJoueursParCases;
     //1ère dimension = x, 2ème = y, 3ème = numJoueur
    private HashSet<String> nomJoueurs;
    private ArrayList<Joueur> joueurs;
    
    
    
    
    public IhmPlateau() throws InterruptedException   {
        super();
        pions = new ArrayList<>();
        pos = new ArrayList<>(); //Liste des positions de chaque joueu
        joueurs = new ArrayList<>();
        nbJoueursParCases = new ArrayList<>();
        nomJoueurs = new HashSet<>();
        observateur.recupererNomJoueurs(nomJoueurs);
        
        this.init();
        
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

    }
    private void init() {
        nbJoueurs = nomJoueurs.size();
        Point posInit = new Point(786, 786);
        for(int i = 0; i<nbJoueurs; i++) {
            pos.add(posInit);
        }
        
        for(int i= 1; i<=40; i++) {
            nbJoueursParCases.add(0);
        }
        nbJoueursParCases.set(0, nbJoueurs); // Tous les joueurs sur la case départ
        for (String nomJ : nomJoueurs) {
            joueurs.add(new Joueur(nomJ, new Carreau(1, nomJ) {
                @Override
                public Resultat action(Joueur j, int sommeDes, ArrayList<Carte> cartes) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
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
        
        // Affichage sur le Canvas
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        mouvementPion(g, joueurs.get(0).getPositionCourante(), joueurs.get(0).getPositionCourante(), joueurs.get(0));
    }
    
   
    private void mouvementPion(Graphics g, Carreau carreauDep, Carreau carreauDest, Joueur j) throws InterruptedException {
        int BASE = 786; // Coordonnées x et y de la case départ pour le 1er pion
        int x = 0, y = 0;
        int numCarreauCourant = carreauDep.getNumero();
        
        
        
        while (numCarreauCourant!=carreauDest.getNumero()+1) {
            
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
            pos.set(joueurs.indexOf(j), new Point(x, y));
            
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
            
            //Changement de case
            nbJoueursParCases.set(numCarreauCourant-1, nbJoueursParCases.get(numCarreauCourant-1)-1);
            numCarreauCourant = this.numCarreauSuivant(numCarreauCourant);
            nbJoueursParCases.set(numCarreauCourant-1, nbJoueursParCases.get(numCarreauCourant-1)+1);
        }
    }
    
    private int numCarreauSuivant(int numCar) {
        if(numCar++ % 40  == 0) 
            return 1;
        else return numCar++ % 40;
    }
    
    private void positionnePionSurCase(Graphics g, int numJoueur, int numCase, int nbJoueurCase, int xPion1, int yPion1) {
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
        
        setPosition(numJoueur, xPion1, yPion1);
        g.drawImage(pions.get(numJoueur), getPositionJoueur(numJoueur).x, getPositionJoueur(numJoueur).y, (ImageObserver) observateur);
    }
   
    public Point getPositionJoueur(int numJoueur) {
        return pos.get(numJoueur);
    }
    
    public void setPosition(int numJ, int x, int y) {
        pos.set(numJ, new Point (x,y));
    }
    
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

}
