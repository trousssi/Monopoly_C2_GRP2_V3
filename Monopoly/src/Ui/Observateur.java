/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.Resultat;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author boby
 */
public interface Observateur {
    
    
    public boolean debutTour(ArrayList<Joueur> joueurs, int nbTour);
    
    public void perte(Joueur joueur);
    
    public void gagne(Joueur joueur);
    
    public void messageCaseDepart(Joueur joueur);
    

    
    public void messageJoueurAvance(Joueur joueur, int sommeDes, Carreau carreau, boolean desDouble);
    
    public void action(Resultat res, Joueur j, int d1, int d2, int nbdouble);
    
    public void recupererNomJoueurs(HashSet<String> joueurs);
    
    public void inscriptionJoueurs ();
    
    public void ordreDuJeu();
    
    public void lancerJeu();
    
    /*public void paint(Graphics g) {
        Dimension d = new Dimension(900, 900);
        super.setSize(d);
        super.paint(g);
        try {
            nbJoueurs = 6;
            
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg")); 
        } 
        // Ne pas considérer ces lignes
        catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // Affichage sur le Canvas
            dessiner(g);
        } catch (InterruptedException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    private void dessiner(Graphics g) throws InterruptedException {
        final int ESPACEMENT = 2;
        final int EMPILEMENT = 13;
        final int HAUTEUR_PION = 26;
        final int LARGEUR_PION = 21;
        final int DECALAGE = 3;
        int i=1;
        int xBase;
        
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        
        
       
        numCarreau = 1;
        
        do {
            i++;
            switch (numCarreau) {
                case 1: { //DEPART
                    xBase = 786; 
                    y = 786;
                    x = xBase;
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    x += ESPACEMENT + LARGEUR_PION;

                    if (i==DECALAGE) {  
                        y += ESPACEMENT + HAUTEUR_PION; 
                        x=xBase;
                    }
                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                
                
                case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: { //LIGNE BAS
                    xBase = 786-74*(numCarreau-1); 
                    y = 844;
                    x = xBase; 
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    y += EMPILEMENT;

                    if (i==DECALAGE) {  
                        x += LARGEUR_PION + ESPACEMENT; 
                        y = 844;
                    }

                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                case 11: { //VISITE PRISON
                    xBase = 5; 
                    y = 786;
                    x = xBase;
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    y += EMPILEMENT;

                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: { //LIGNE GAUCHE
                    xBase = 16; 
                    y = 786-74*(numCarreau-11);
                    x = xBase; 
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    x += LARGEUR_PION + ESPACEMENT;

                    if (i==DECALAGE) {  
                        x = xBase; 
                        y += EMPILEMENT;
                    }

                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                case 21: { //PARC GRATUIT
                    xBase = 16; 
                    y = 16;
                    x = xBase;
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    x += ESPACEMENT + LARGEUR_PION;

                    if (i==DECALAGE) {  
                        y += ESPACEMENT + HAUTEUR_PION; 
                        x=xBase;
                    }
                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
               case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: { //LIGNE BAS
                    xBase = 120+74*(numCarreau-22); 
                    y = 16;
                    x = xBase; 
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    y += EMPILEMENT;

                    if (i==DECALAGE) {  
                        x += LARGEUR_PION + ESPACEMENT; 
                        y = 16;
                    }

                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                case 31: { //ALLER EN PRISON
                    xBase = 786; 
                    y = 16;
                    x = xBase;
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    x += ESPACEMENT + LARGEUR_PION;

                    if (i==DECALAGE) {  
                        y += ESPACEMENT + HAUTEUR_PION; 
                        x=xBase;
                    }
                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                case 32: case 33: case 34: case 35: case 36: case 37: case 38: case 39: case 40: { //LIGNE BAS
                    xBase = 841; 
                    y = 123+74*(numCarreau-32);
                    x = xBase; 
                    g.drawImage(pions.get(i-1), x, y, (ImageObserver) observateur);
                    //On décale le pion
                    x += LARGEUR_PION + ESPACEMENT;

                    if (i==DECALAGE) {  
                        y += EMPILEMENT; 
                        x = xBase;
                    }

                    if (i==nbJoueurs) {
                        numCarreau++;
                    }
                    Thread.sleep(500);
                    g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
                } break;
                
                
            } 
        } while (i<=nbJoueurs);
    }*/
    public void lanceDes(Joueur j,int nbDouble);
    
    public void Reponce(int cas, Joueur j, Jeu.Resultat res);
    
     public void notification(String message,Joueur j);
     
     public void joueurSuivant();
     
     public void rejouer(Joueur j,int nbdouble);
     
    public boolean sortiePrisonCarte(Joueur j);
}
