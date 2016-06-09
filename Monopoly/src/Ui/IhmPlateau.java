/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
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
    private ArrayList<int[]> pos;private int x, y;
     //1ère dimension = x, 2ème = y, 3ème = numJoueur
    
    public IhmPlateau() throws InterruptedException   {
        super();
        pions = new ArrayList<>();
        pos = new ArrayList<>();
        
        
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
        
        int[] posInit = {786, 786};
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
        while (i<nbJoueurs) {
            
            if(i==3) {
                dy=0;
                dx+=13;
            }
            
            
            System.out.println("x = " + this.pos.get(0)[0] + "y = " + this.pos.get(0)[1] + i);
            g.drawImage(pions.get(i), this.pos.get(i)[0] +dx , this.pos.get(i)[1] + dy, (ImageObserver) observateur);
            dy+=13;
            i++;
            
        }   try {
                /*if (i<nbJoueurs) i++;
                if (i<= 3) this.pos.get(i)[1]+=13;
                if (i> 3) this.pos.get(i)[0]+=13;*/
 
                dessiner(g, 5, 1, 5);
 
            } catch (InterruptedException ex) {
                Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//Coordonnés du premier carreau x, y = 786
    
    

    
    
   
    private void dessiner(Graphics g, int numCarreauDest, int numCarreauDep, int numJoueur) throws InterruptedException {
        int ESPACEMENT = 3;
        int LARGEUR_PION = 21;
        int EMPILEMENT = 13;
        int HAUTEUR_PION = 26;
        int DECALAGE = 3;
        int BASE = 786;
        int compteur;
        
        int numCarreauCourant = numCarreauDep;
        
        do {
            numCarreauCourant = this.numCarreauSuivant(numCarreauCourant);//Si on est pas arrivé, on avance
            compteur = 0;
            if(numCarreauCourant == 1) {//On arrive à la case départ
                pos.get(numJoueur)[0] = BASE;// Pour le joueur courant x = 786
                pos.get(numJoueur)[1] = BASE;// y = 786;
                
                /*g.drawImage(pions.get(numJoueur), pos.get(numJoueur)[0], pos.get(numJoueur)[1], (ImageObserver) observateur);
                
                if(numJoueur == DECALAGE) {//Pour le 3 eme joueur il y a un décalage
                    pos.get(numJoueur)[1] += ESPACEMENT + HAUTEUR_PION; 
                    pos.get(numJoueur)[0]=BASE;//Utile ?
                }*/            
            }
            else if(numCarreauCourant <= 10) { //LIGNE BAS
                pos.get(numJoueur)[0] = BASE-74*(numCarreauCourant-1);
                pos.get(numJoueur)[1] = 844;
                
                
                /*g.drawImage(pions.get(numJoueur), pos.get(numJoueur)[0], pos.get(numJoueur)[1], (ImageObserver) observateur);
                pos.get(numJoueur)[1] += EMPILEMENT;
                
                if(numJoueur == DECALAGE) {//Pour le 3 eme joueur il y a un décalage
                    pos.get(numJoueur)[0] += LARGEUR_PION + ESPACEMENT; 
                    pos.get(numJoueur)[1] = 844;
                }              */
            }
            else if (numCarreauCourant == 11) {//VISITE PRISON 
                pos.get(numJoueur)[0] = 5;
                pos.get(numJoueur)[1] = BASE;
                
                
                /*g.drawImage(pions.get(numJoueur), pos.get(numJoueur)[0], pos.get(numJoueur)[1], (ImageObserver) observateur);
                pos.get(numJoueur)[1] += EMPILEMENT;*/
                
                
            }
            else if (numCarreauCourant <= 20) {//LIGNE GAUCHE
                pos.get(numJoueur)[0] = 16;
                pos.get(numJoueur)[1] = BASE-74*(numCarreauCourant-11);
                
                /*g.drawImage(pions.get(numJoueur), pos.get(numJoueur)[0], pos.get(numJoueur)[1], (ImageObserver) observateur);
                pos.get(numJoueur)[0] += LARGEUR_PION + ESPACEMENT;
                
                if(numJoueur == DECALAGE) {//Pour le 3 eme joueur il y a un décalage
                    pos.get(numJoueur)[1] += EMPILEMENT;
                }*/
            }
            else if (numCarreauCourant == 21) {//PARC GRATUIT
                pos.get(numJoueur)[0] = 16;
                pos.get(numJoueur)[1] = 16;
                
                /*g.drawImage(pions.get(numJoueur), pos.get(numJoueur)[0], pos.get(numJoueur)[1], (ImageObserver) observateur);
                pos.get(numJoueur)[0] += ESPACEMENT + LARGEUR_PION;
                
                if(numJoueur == DECALAGE) {
                    pos.get(numJoueur)[1] += ESPACEMENT + HAUTEUR_PION; 
                }*/
            }
            else if (numCarreauCourant <= 30) {//LIGNE HAUT
                pos.get(numJoueur)[0] = 120+74*(numCarreauCourant-22); ;
                pos.get(numJoueur)[1] = 16;
                
                
            }
            else if (numCarreauCourant == 31) {//ALLER EN PRISON
                pos.get(numJoueur)[0] = 786;
                pos.get(numJoueur)[1] = 16;
                
                
            }
            else if (numCarreauCourant <= 40) {//LIGNE DROITE
                pos.get(numJoueur)[0] = 841;
                pos.get(numJoueur)[1] = 123+74*(numCarreauCourant-32);
               
            }
            for (int[] i : pos) {
                g.drawImage(pions.get(compteur), i[0], i[1], (ImageObserver) observateur);
                compteur++;
            }
            
            Thread.sleep(500);
            g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur);
            
            System.out.println("numCarreauCourant = " + numCarreauSuivant(2) + "numCarreauDest = " + numCarreauDest);
        } while (numCarreauCourant!=numCarreauDest);
    }
    
    private int numCarreauSuivant(int numCar) {
        if(numCar++ % 40  == 0) 
            return 1;
        else return numCar++ % 40;
    }
   
}
