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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author vivierlo
 */
public final class IhmPlateau extends JPanel{
    
    Timer timer;
    private BufferedImage fondPlateau;
    private BufferedImage maison;
    private BufferedImage hotelHorizontal;
    private BufferedImage hotelVertical;
    
    
    Observateur observateur;
    
    private HashMap<String, BufferedImage> pions; //Liste des pions
    private HashMap<String, Integer> joueurs; //Liste des joueurs avec String: nom joueur et Integer: numéro Carreau courant
    private int[] nbJoueursParCases;
    private String nomJoueurCourant;
    
    private HashMap<Integer, Integer> maisons;//Clé = numéro de la case, value = nombre de maisons
    private boolean prisonnier = false;
    private boolean prisonnierPeutEncoreBouger = true;//Avant d'être incarcéré un prisonnier doit continuer de faire son action
    private boolean animationEnCours;
    
    public IhmPlateau(HashSet<String> noms) {
        super();
        pions = new HashMap<>();
        joueurs = new HashMap<>();
        maisons = new HashMap<>();
        nbJoueursParCases = new int[40];
        
        String couleur[] ={"Rouge", "Bleu", "Vert", "Jaune", "Violet", "Orange"};
        int numCouleur = 0;
        //Initialisation des joueurs et des images de pions
        for (String nomJ : noms) {            
            try {
                joueurs.put(nomJ, 1); //positionnés sur le carreau de départ
                pions.put(nomJ, ImageIO.read(new File("src/Data/pion" + couleur[numCouleur] + ".png")));
                numCouleur++;
            } catch (IOException ex) {
                Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        
        //Initialisation du nombre de joueurs par case
        this.initListeCase();
        
        //Chargement des images
        try {
            maison = ImageIO.read(new File("src/Data/maison.png"));
            hotelHorizontal = ImageIO.read(new File("src/Data/hotel_horizontal.png"));
            hotelVertical = ImageIO.read(new File("src/Data/hotel_vertical.png"));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
        ** Test Maison. (à supprimer)
        */
        ajoutMaison(2);ajoutMaison(2);ajoutMaison(2);ajoutMaison(2);
        ajoutMaison(14);ajoutMaison(14);ajoutMaison(14);ajoutMaison(14);
        ajoutMaison(30);ajoutMaison(30);ajoutMaison(30);ajoutMaison(30);
        ajoutMaison(38);ajoutMaison(38);ajoutMaison(38);ajoutMaison(38);
        ajoutMaison(27);ajoutMaison(27);ajoutMaison(27);ajoutMaison(27);ajoutMaison(27); //--> 5 maisons = Hotel
        ajoutMaison(15);ajoutMaison(15);ajoutMaison(15);ajoutMaison(15);ajoutMaison(15);
        ajoutMaison(40);ajoutMaison(40);ajoutMaison(40);ajoutMaison(40);ajoutMaison(40);
        ajoutMaison(4);ajoutMaison(4);ajoutMaison(4);ajoutMaison(4);ajoutMaison(4);

    }
    
    //private void trouveNbJoueursParCase() {
        //for (String nomJ : joueurs.keySet()) {
            //nbJoueursParCases[joueurs.get(nomJ)]++;//On ajoute 1 pour la case où se trouve un joueur
        //}
    //}
    
    private void initListeCase() {
        for(int i= 0; i<40; i++) {
            nbJoueursParCases[i] = 0;
        }
    }
    
    /**
    * Dessine le contenu du canvas, c'est-à-dire l'icone
    * @param g un contexte graphique
    */
    @Override
    public void paintComponent(Graphics g) {
        Dimension d = new Dimension(900, 900);
        super.setSize(d);
        super.paintComponent(g);
        
        try {
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        //dessinage des maisons
        Point p = new Point();
        for(int numCase : maisons.keySet()) {//Pour toute les cases
            if (maisons.get(numCase) == 5) {//Dans ce cas on doit construire un hotel
                p = trouveCoordonneesMaison(numCase, 5);
                if (p.x == 94 || p.x == 787) {//Les lignes gauche et droite ont un hotel vertical 
                    g.drawImage(hotelVertical, p.x, p.y, (ImageObserver) observateur);
                } else {
                    g.drawImage(hotelHorizontal, p.x, p.y, (ImageObserver) observateur);
                }
            }
            else {
                for (int i = 0; i< maisons.get(numCase); i++) {//et toutes les maisons
                    p = trouveCoordonneesMaison(numCase, i);
                    g.drawImage(maison, p.x, p.y, (ImageObserver) observateur);
                }
            }
        }
        
        //dessin du plateau
        this.initListeCase();//Il y 0 joueurs à dessiner pour le moment
        if (!animationEnCours) {//Si on ne fait pas d'animation
            for (String nomJ : joueurs.keySet()) {//On dessine tous les joueurs
                nbJoueursParCases[joueurs.get(nomJ)-1]++;//On ajoute un joueur à dessiner sur la case où se trouve "nomJ"
                p = trouveCoordonneesCase((nomJ), 0);//On trouve les coordonnées du pion
                p = positionnePionSurCase(joueurs.get(nomJ), nbJoueursParCases[joueurs.get(nomJ)-1], p.x, p.y);//On réarrange ce pion selon le nombre de joueurs présent à dessiner en plus de "nomJ" sur cette case
                
                g.drawImage(pions.get(nomJ), p.x, p.y, (ImageObserver) observateur);//On dessine le pion avec les coordonnées calculées
            }
            if (prisonnier) {//Si un joueur a été fait prisonnier
                nbJoueursParCases[joueurs.get(nomJoueurCourant)-1]--;//Il n'est plus ici, on décremente donc le nb de joueurs présent sur cette case
                joueurs.replace(nomJoueurCourant, 11);//Il ne faudrait pas qu'il séchappe de la case n°11 (= case prison)
                nbJoueursParCases[11]++;
                p = trouveCoordonneesCase((nomJoueurCourant), 0);
                p = positionnePionSurCase(joueurs.get(nomJoueurCourant), nbJoueursParCases[joueurs.get(nomJoueurCourant)-1], p.x, p.y);//On réarrange ce pion selon le nombre de joueurs présent à dessiner en plus de "nomJ" sur cette case
                
                g.drawImage(pions.get(nomJoueurCourant), p.x, p.y, (ImageObserver) observateur);
                prisonnierPeutEncoreBouger = false; //Le joueur ne pourra même plus pouvoir participer aux animations.
            }
        }
        
        //dessin de l'animation
        else if (animationEnCours) {
            for (String nomJ : joueurs.keySet()) {
                //On ajoute un joueur à dessiner sur la case où se trouve "nomJ"
                if (nomJoueurCourant.equals(nomJ)) {
                    nbJoueursParCases[joueurs.get(nomJ)-1]--;//Le joueur ne sera plus sur cette case
                    p = trouveCoordonneesCase((nomJ), 1);//Ce joueur doit avancer
                    nbJoueursParCases[joueurs.get(nomJ)-1]++;//Il a avancé sur celle-ci
                }
                else {
                    nbJoueursParCases[joueurs.get(nomJ)-1]++;
                    p = trouveCoordonneesCase((nomJ), 0);
                }
                p = positionnePionSurCase(joueurs.get(nomJ), nbJoueursParCases[joueurs.get(nomJ)-1], p.x, p.y);
                
                
                g.drawImage(pions.get(nomJ), p.x, p.y, (ImageObserver) observateur);                  
            }
        }
        
    }
    
    public Point trouveCoordonneesMaison(int numCase, int nbMaison) {
        Point p = new Point();
        
        if (numCase <= 10) { //LIGNE BAS
            if (nbMaison<=4) {
                p.x = (122+74*(10-numCase))+17*nbMaison;
                p.y = 789;
            } else {
                p.x = (124+74*(10-numCase));
                p.y = 787;
            }
            
        } else if (numCase <= 20) { //LIGNE GAUCHE
            if (nbMaison<=4) {
                p.x = 96;
                p.y = (122+74*(20-numCase))+17*nbMaison;
            } else {
                p.x = 94;
                p.y = (124+74*(20-numCase));
            }
            
        } else if (numCase <= 30) { //LIGNE HAUTE
            if (nbMaison<=4) {
                p.x = (122+74*(numCase-22))+17*nbMaison;
                p.y = 96;
            } else {
                p.x = (124+74*(numCase-22));
                p.y = 94;    
            }
            
        } else { //LIGNE DROITE
            if (nbMaison<=4) {
                p.x = 790;
                p.y = (122+74*(numCase-32))+17*nbMaison;
            } else {
                p.x = 787;
                p.y = (124+74*(numCase-32));
            }
        }
        return p;
    }
    
    //Donne les coordonnées précises du pion après qu'il ai avancé 
    //public Point avancerPion(HashMap<String, Integer> j) throws InterruptedException {
    public Point trouveCoordonneesCase(String nomJ, int mode) { 
        int BASE = 786; // Coordonnées x et y de la case départ pour le 1er pion
        int x = 0, y = 0;        
        int numCase = joueurs.get(nomJ);
        
        if (mode == 1) { //Si on doit avancer
            numCase = this.numCarreauSuivant(numCase);//La case doit être la suivante
            joueurs.replace(nomJ, numCase);//Le joueur doit être mit à jour
        }
        
        if(numCase == 1) {//CASE DEPART
            x = BASE;// Pour le joueur courant x = 786
            y = BASE;// y = 786;
        }
        else if(numCase <= 10) { //LIGNE BAS
            x = BASE-74*(numCase-1);
            y = 844;
        }
        else if (numCase == 11 && this.prisonnier) {//PRISON
            x = 40;
            y = 801;
        }
        else if (numCase == 11 ) {//VISITE PRISON 
            x = 5;
            y = BASE;
        }
        else if (numCase <= 20) {//LIGNE GAUCHE
            x = 16;
            y = BASE-74*(numCase-11);
        }
        else if (numCase == 21) {//PARC GRATUIT
            x = 16;
            y = 16;
        }
        else if (numCase <= 30) {//LIGNE HAUT
            x = 120+74*(numCase-22);
            y = 16;
        }
        else if (numCase == 31) {//ALLER EN PRISON
            x = 786;
            y = 16;
        }
        else if (numCase <= 40) {//LIGNE DROITE
            x = 841;
            y = 123+74*(numCase-32);
        }
        
        return new Point(x, y);
    }
    
    private int numCarreauSuivant(int numCar) {
        int carreauSuivant = numCar+1;
        carreauSuivant = carreauSuivant % 41;
        if(carreauSuivant  == 0) 
            return 1;
        else return carreauSuivant;
    }
    
    //Sélectionne les coordonnées d'affichage du pion sur la case
    private Point positionnePionSurCase(int numCase, int nbJoueurCase, int x, int y) {
        Point p = new Point(x, y);
        boolean ligneBas = numCase >= 2 && numCase <= 10;
        boolean ligneHaut = numCase >= 22 && numCase <= 30;
        boolean enPrison = numCase==11 && this.prisonnier;
        
        
        if (ligneBas || ligneHaut || enPrison) {
            p = ensembleJoueursHorizontal(nbJoueurCase, x, y);
        } else if(!enPrison && numCase == 11) {//Visite de la prison
            if (nbJoueurCase != 1) p.y = y+15*(nbJoueurCase-1);
        } else {
            p = ensembleJoueursVertical(nbJoueurCase, x, y);
        }
                
        return p;
    }
    
    private Point ensembleJoueursVertical(int nbJoueurCase, int x, int y) {
        //Va retourner un point de sorte à ce que les joueurs puissent se placer en un groupe compact.
        //La position du joueur est calculé selon les coordonnés du premier joueur
        /* Les pions seront dispositionnés de la sorte :
        **   J1 J2
        **   J3 J4
        **   J5 J6
        */
        int DEC_HOR = 20;//Decalement horizontal par rapport au joueur 1 pour être à droite de lui
        int DEC_VERT = 20;//Decalement vertical par rapport au joueur 1 pour être en dessous de lui
        switch(nbJoueurCase) { 
            case 2 :
                x += DEC_HOR; 
                break;
            case 3 :
                y += DEC_VERT;
                break;
            case 4 :
                x += DEC_HOR; 
                y += DEC_VERT;
                break;
            case 5 :
                y += 2*DEC_VERT;
                break;
            case 6 :
                x += DEC_HOR;
                y += 2*DEC_VERT;
                break;
        }
        return new Point(x, y);
    }
    
    private Point ensembleJoueursHorizontal(int nbJoueurCase, int x, int y) {
        /* Les pions seront dispositionnés de la sorte :
        **   J1 J2 J3
        **   J4 J5 J6
        */
        
        int DEC_HOR = 23;//Decalement horizontal par rapport au joueur 1 pour être à droite de lui
        int DEC_VERT = 20;//Decalement vertical par rapport au joueur 1 pour être en dessous de lui
        switch(nbJoueurCase) { 
            case 2 :
                x += DEC_HOR; 
                break;
            case 3 :
                x += 2*DEC_HOR;
                break;
            case 4 :
                y += DEC_VERT;
                break;
            case 5 :
                x += DEC_HOR; 
                y += DEC_VERT;
                break;
            case 6 :
                x += 2*DEC_HOR;
                y += DEC_VERT;
                break;
        }
        return new Point(x, y);
    }

    public void recupDonneesJoueur(Joueur j, Carreau positionCourante, Carreau anciennePosition, boolean prison) {
        //numCarreauCourant = anciennePosition.getNumero();
        
        nomJoueurCourant = j.getNom();
        int numCarreauDestination = positionCourante.getNumero();
        
        
        animationEnCours = true;
        timer = new Timer(+400, new ActionListener() {//Toutes les 400 ms on va repeindre
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (joueurs.get(nomJoueurCourant) == numCarreauDestination) {                        
                        timer.stop();
                        animationEnCours = false;
                        if (prison) {
                            prisonnier = prison;
                        }
                        else prisonnier = false;
                    }
                    else {
                        repaint();
                    } 
                }
        });
        if (joueurs.get(nomJoueurCourant) != numCarreauDestination) {//
            timer.start();
        }
    }
    
    public void ajoutMaison(int numCase) {//Il faudra construire une maison ou un hotel (nbMaison = 5) sur la case
        //TODO: Créer un  hashMap<numCase, nbMaison> [appartenant à la classe] qui va permettre le dessin à chaque actualisation de paint
        //Actualiser l'hashMap avec cette fonction
        //Dans paint, avant le dessin des joueurs, dessiner les maisons selon l'hashMap
        
        if (maisons.get(numCase) == null) {//Si cette propriete ne comportais aucune maison
            maisons.put(numCase, 1);//elle doit maintenant en comporter une.
        } else {
            maisons.replace(numCase, maisons.get(numCase)+1);//on ajoute une maison si la propriéte en possedait déjà
        }
    }
}
