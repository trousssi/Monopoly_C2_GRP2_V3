/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private int nbJoueurs = 6;//L'ajouter comme paramètre 
    private ArrayList<Point> pos;
    private int x, y;
     //1ère dimension = x, 2ème = y, 3ème = numJoueur
    
    public IhmPlateau() throws InterruptedException   {
        super();
        pions = new ArrayList<>();
        pos = new ArrayList<>(); //Liste des positions de chaque joueur 
        
        
        this.initListePositions();
        
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
    private void initListePositions() {
        
        Point posInit = new Point(786, 786);
        for(int i = 0; i<nbJoueurs; i++) {
            pos.add(posInit);
        }
        System.out.println("nbJoueurs = " + nbJoueurs + "size = " + pos.size());
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
            //PION DE 21x26 espacés de 2 pixels entre eux + y += 13px  pour superposer
            if (true) { //INTEGRER LA CONTRAINTE DU NOMBRE DE JOUEURS
                
            }
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg")); 
        } 
        // Ne pas considérer ces lignes
        catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Affichage sur le Canvas
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        
        int i=0;
        int dx=0;
        int dy=0; 
        /*while (i<nbJoueurs) {
            
            if(i==3) {
                dy=0;
                dx+=13;
            }
            
            
            System.out.println("x = " + this.pos.get(0)[0] + "y = " + this.pos.get(0)[1] + i);
            g.drawImage(pions.get(i), this.pos.get(i)[0] +dx , this.pos.get(i)[1] + dy, (ImageObserver) observateur);
            dy+=13;
            i++;
            
        }*/   try {
                /*if (i<nbJoueurs) i++;
                if (i<= 3) this.pos.get(i)[1]+=13;
                if (i> 3) this.pos.get(i)[0]+=13;*/
 
                mouvementPion(g, 1, 5, 0);
 
            } catch (InterruptedException ex) {
                Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//Coordonnés du premier carreau x, y = 786
    
    

    
    
   
    private void mouvementPion(Graphics g, int numCarreauDep, int numCarreauDest, int numJoueur) throws InterruptedException {
        int ESPACEMENT = 2; // Espacement en pixel entre 2 pion cote à cote
        int LARGEUR_PION = 21; // Largeur d'un pion en pixels
        int EMPILEMENT = 13; // Décalage en pixels entre deux pions qui s'empilent vers le bas
        int HAUTEUR_PION = 26; // Hauteur d'un pion en pixels
        int CHANGE_LIGNE = 3; // Conctante définissant le nombre de pions par ligne 
        int BASE = 786; // Coordonnées x et y de la case départ pour le 1er pion
        int compteur;
        int x = 0, y = 0;
        int numCarreauCourant = numCarreauDep;
        
        do {
            if(numCarreauCourant == 1) {//On arrive à la case départ
                x = BASE;// Pour le joueur courant x = 786
                y = BASE;// y = 786;
                 
                /*g.drawImage(pions.get(numJoueur), x, y, (ImageObserver) observateur);
                
                if(numJoueur == DECALAGE) {//Pour le 3 eme joueur il y a un décalage
                    y += ESPACEMENT + HAUTEUR_PION; 
                    x=BASE;//Utile ?
                }*/            
            }
            else if(numCarreauCourant <= 10) { //LIGNE BAS
                x = BASE-74*(numCarreauCourant-1);
                y = 844;
                
                
                /*g.drawImage(pions.get(numJoueur), x, y, (ImageObserver) observateur);
                y += EMPILEMENT;
                
                if(numJoueur == DECALAGE) {//Pour le 3 eme joueur il y a un décalage
                    x += LARGEUR_PION + ESPACEMENT; 
                    y = 844;
                }              */
            }
            else if (numCarreauCourant == 11) {//VISITE PRISON 
                x = 5;
                y = BASE;
                
                
                /*g.drawImage(pions.get(numJoueur), x, y, (ImageObserver) observateur);
                y += EMPILEMENT;*/
                
                
            }
            else if (numCarreauCourant <= 20) {//LIGNE GAUCHE
                x = 16;
                y = BASE-74*(numCarreauCourant-11);
                
                /*g.drawImage(pions.get(numJoueur), x, y, (ImageObserver) observateur);
                x += LARGEUR_PION + ESPACEMENT;
                
                if(numJoueur == DECALAGE) {//Pour le 3 eme joueur il y a un décalage
                    y += EMPILEMENT;
                }*/
            }
            else if (numCarreauCourant == 21) {//PARC GRATUIT
                x = 16;
                y = 16;
                
                /*g.drawImage(pions.get(numJoueur), x, y, (ImageObserver) observateur);
                x += ESPACEMENT + LARGEUR_PION;
                
                if(numJoueur == DECALAGE) {
                    y += ESPACEMENT + HAUTEUR_PION; 
                }*/
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
            
            compteur = 0;
            for (Point posJoueur : pos) {
                g.drawImage(pions.get(compteur), posJoueur.x, posJoueur.y, (ImageObserver) observateur);
                System.out.println("Compteur:" + compteur + "|X:" + posJoueur.x + "|" + "Y:" + posJoueur.y);
                compteur++;
            }
            
            Thread.sleep(500);
            g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur);
            
            System.out.println("numCarreauCourant = " + numCarreauCourant + "|numCarreauSuivant = " + numCarreauSuivant(numCarreauCourant) + "|numCarreauDest = " + numCarreauDest);
            
            pos.get(numJoueur).x = x;
            pos.get(numJoueur).y = y;
            System.out.println("Numjoueur = " + numJoueur + " |X=" + x + " |Y=" + y);
            System.out.println(" ");
            
            
            numCarreauCourant = this.numCarreauSuivant(numCarreauCourant);//Si on est pas arrivé, on avance
        } while (numCarreauCourant!=numCarreauDest);
    }
    
    private int numCarreauSuivant(int numCar) {
        if(numCar++ % 40  == 0) 
            return 1;
        else return numCar++ % 40;
    }
   
}
