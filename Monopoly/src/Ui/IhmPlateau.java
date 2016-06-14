/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
    private HashMap<String, String> couleurJoueurs;

    private int[] nbJoueursParCases;//Sert à éviter la superposition de pions lors de l'affichage
    private String nomJoueurCourant;
    
    private HashMap<Integer, Integer> maisons;//Clé = numéro de la case, value = nombre de maisons
    private HashSet<String> prisonniers;//Liste des prisonniers
    private HashSet<String> prisonnierNePeutPlusBouger;//Avant d'être incarcéré un prisonnier doit continuer de faire son animation
    private boolean animationEnCours;//Tant que le timer n'est pas fini, les pios se déplaçent case par case
    
    public IhmPlateau(HashSet<String> noms) {
        super();
        pions = new HashMap<>();
        joueurs = new HashMap<>();
        maisons = new HashMap<>();
        couleurJoueurs = new HashMap<>();
        prisonniers = new HashSet<>();
        prisonnierNePeutPlusBouger = new HashSet<>();
        nbJoueursParCases = new int[40];
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        
        String couleur[] ={"Rouge", "Bleu", "Vert", "Marron", "Violet", "Orange"};
        int numCouleur = 0;
        //Initialisation des joueurs et des images de pions
        for (String nomJ : noms) {            
            try {
                joueurs.put(nomJ, 1); //positionnés sur le carreau de départ
                pions.put(nomJ, ImageIO.read(new File("src/Data/pion" + couleur[numCouleur] + ".png")));
                couleurJoueurs.put(nomJ, couleur[numCouleur]);
                numCouleur++;
            } catch (IOException ex) {//Exception au niveau de la lecture d'iamge
                ex.printStackTrace();
            }
        }    
        
        //Initialisation du nombre de joueurs par case
        this.initListeCase();
        
        //Chargement des images
        try {
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg"));
            maison = ImageIO.read(new File("src/Data/maison.png"));
            hotelHorizontal = ImageIO.read(new File("src/Data/hotel_horizontal.png"));
            hotelVertical = ImageIO.read(new File("src/Data/hotel_vertical.png"));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
        
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        
        //Dessinage des maisons et des hôtels
        Point p = new Point();
        for(int numCase : maisons.keySet()) {//Pour toutes les cases
            if (maisons.get(numCase) == 5) {//5 maisons = construction d'un hôtel
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
                if (!prisonniers.contains(nomJ)) {// sauf les prisonniers (cas à part)
                    nbJoueursParCases[joueurs.get(nomJ)-1]++;//On ajoute un joueur à dessiner sur la case du joueur
                    p = trouveCoordonneesCase((nomJ), 0);//On trouve les coordonnées du pion
                    p = positionnePionSurCase(nomJ, nbJoueursParCases[joueurs.get(nomJ)-1], p.x, p.y);//On réarrange la position selon le nombre de joueurs présent sur la case
                    
                    g.drawImage(pions.get(nomJ), p.x, p.y, (ImageObserver) observateur);//On dessine le pion avec les coordonnées calculées
                }
            }
            int nbJoueursEnPrison = 0;
            for(String nomP : prisonniers) {//Pour tous les prisonniers
                nbJoueursEnPrison++;//A chaque fois on dessine un joueur en plus
                if (joueurs.get(nomP) != 11) {//Si le joueur n'était pas encore en prison, il va l'être 
                    nbJoueursParCases[joueurs.get(nomP)-1]--;//Il n'est plus ici, on décremente donc le nb de joueurs présent sur cette case
                    joueurs.replace(nomP, 11);//Il ne faudrait pas qu'il séchappe de la case n°11 (= case prison)
                }
                
                p = trouveCoordonneesCase((nomP), 0);
                p = positionnePionSurCase(nomP, nbJoueursEnPrison, p.x, p.y);

                g.drawImage(pions.get(nomP), p.x, p.y, (ImageObserver) observateur);
                prisonnierNePeutPlusBouger.add(nomP); //Le joueur ne pourra plus pouvoir participer aux animations
            }            
        }
        
        //Dessin de l'animation
        else if (animationEnCours) {
            for (String nomJ : joueurs.keySet()) {
                if (!prisonnierNePeutPlusBouger.contains(nomJ)) {   //Animation seulement si le prisonnier peut encore bouger
                    if (nomJoueurCourant.equals(nomJ)) {                //Le joueur courant va avancer
                        nbJoueursParCases[joueurs.get(nomJ)-1]--;           //Il ne sera plus sur cette case
                        p = trouveCoordonneesCase((nomJ), 1);               // On calcule les coordonnes de la prochaine case et on le fait avancer
                        nbJoueursParCases[joueurs.get(nomJ)-1]++;           //Il sera sur la case suivante
                    }
                    else {
                        nbJoueursParCases[joueurs.get(nomJ)-1]++;       //Il y a un joueur an plus a dessiner sur sa case
                        p = trouveCoordonneesCase((nomJ), 0);
                    }
                    p = positionnePionSurCase(nomJ, nbJoueursParCases[joueurs.get(nomJ)-1], p.x, p.y);


                    g.drawImage(pions.get(nomJ), p.x, p.y, (ImageObserver) observateur);
                }
                int nbJoueursEnPrison = 0;
                for (String nomP : prisonnierNePeutPlusBouger) {//Dessin des prisonniers qui ne peuvent bouger
                    nbJoueursEnPrison++;
                    p = trouveCoordonneesCase((nomP), 0);
                    p = positionnePionSurCase(nomP, nbJoueursEnPrison, p.x, p.y);
                    
                    g.drawImage(pions.get(nomP), p.x, p.y, (ImageObserver) observateur);
                }
            }
        }
        
    }
    
    /*
    * Renvoie les coordonnes de la maison ou de l'hotel
    * @param numCase, nbMaison
    */
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
    
    /**
    **Donne les coordonnées précises du pion avant (mode == 0) ou après (mode == 1) qu'il ait avancé 
    * @param nomJ, mode
    */
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
        else if (numCase == 11 && this.prisonniers.contains(nomJ)) {//PRISON
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
    
    /**
    ** Renvoie le numéro du carreau suivant
    *  @param numCar
    *  @return 
    */
    private int numCarreauSuivant(int numCar) {
        int carreauSuivant = numCar+1;
        carreauSuivant = carreauSuivant % 41;
        if(carreauSuivant  == 0) 
            return 1;
        else return carreauSuivant;
    }
    
    /**
     * Renvoie la position d'un pio
     * @param nomJ
     * @param nbJoueurCase
     * @param x
     * @param y
     */
    private Point positionnePionSurCase(String nomJ, int nbJoueurCase, int x, int y) {
        Point p = new Point(x, y);
        int numCase = joueurs.get(nomJ);
        boolean ligneBas = numCase >= 2 && numCase <= 10;
        boolean ligneHaut = numCase >= 22 && numCase <= 30;
        boolean enPrison = numCase==11 && this.prisonniers.contains(nomJ);
        
        
        if (ligneBas || ligneHaut || enPrison) {
            p = ensembleJoueursHorizontal(nbJoueurCase, x, y);
        } else if (enPrison){
            p = ensembleJoueursHorizontal(prisonniers.size(), x, y);
        } else if(!enPrison && numCase == 11) {//Visite de la prison
            if (nbJoueurCase != 1) p.y = y+15*(nbJoueurCase-1);
        } else {
            p = ensembleJoueursVertical(nbJoueurCase, x, y);
        }
                
        return p;
    }
    /**
     * Va retourner un point de sorte à ce que les joueurs puissent se placer en un groupe compact avec une colonne de 3 joueurs.
     * La position du joueur est calculé selon les coordonnés du premier joueur
     * @param nbJoueurCase
     * @param x
     * @param y
     */
    private Point ensembleJoueursVertical(int nbJoueurCase, int x, int y) {
        /* Les pions seront dispositionnés de la sorte :
        **   J1 J2
        **   J3 J4
        **   J5 J6
        */
        int DEC_HOR = 23;//Decalement horizontal par rapport au joueur 1 pour être à droite de lui
        int DEC_VERT = 23;//Decalement vertical par rapport au joueur 1 pour être en dessous de lui
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
    
    /**
     * Va retourner un point de sorte à ce que les joueurs puissent se placer en un groupe compact avec une ligne de 3 joueurs.
     * La position du joueur est calculé selon les coordonnés du premier joueur
     * @param nbJoueurCase
     * @param x
     * @param y
     */
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
    /**
     * Recupère les données utiles et lance le dessin
     * @param j
     * @param positionCourante
     * @param anciennePosition
     * @param prison 
     */
    public void recupDonneesJoueur(Joueur j, Carreau positionCourante, Carreau anciennePosition, boolean prison) {
        //numCarreauCourant = anciennePosition.getNumero();
        
        nomJoueurCourant = j.getNom();
        int numCarreauDestination = positionCourante.getNumero();

        System.out.println("prison = " + prison);
        animationEnCours = true;
        timer = new Timer(+400, new ActionListener() {//Toutes les 1 ms on va repeindre
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (joueurs.get(nomJoueurCourant) == numCarreauDestination) {                        
                        timer.stop();
                        animationEnCours = false;
                        if (prison) {
                            prisonniers.add(nomJoueurCourant);
                            System.out.println("Repaint");
                            repaint();
                        }
                        else prisonniers.remove(nomJoueurCourant);
                        
                    }
                    else {
                        repaint();
                    } 
                }
        });
        if (joueurs.get(nomJoueurCourant) != numCarreauDestination) {
            timer.start();
        }
    }
    /**
     * Ajoute une maison sur une case
     * @param numCase 
     */
    public void ajoutMaison(int numCase) {//Il faudra construire une maison ou un hotel (nbMaison = 5) sur la case
        
        if (maisons.get(numCase) == null) {//Si cette propriete ne comportais aucune maison
            maisons.put(numCase, 1);//elle doit maintenant en comporter une.
        } else {
            maisons.replace(numCase, maisons.get(numCase)+1);//on ajoute une maison si la propriéte en possedait déjà
        }
    }

    public HashMap<String, String> getCouleurJoueurs() {
        return couleurJoueurs;    
    }
    
}
