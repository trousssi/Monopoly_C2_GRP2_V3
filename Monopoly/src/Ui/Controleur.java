package Ui;

import Jeu.Carreau;
import Jeu.Carte;
import Jeu.Groupe;
import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;
import java.util.Random;

public class Controleur {
	public IHM ihm;
	public Monopoly monopoly;

    public Controleur() {
        this.ihm = new IHM(this);
        this.monopoly = new Monopoly();

         ihm.debutPartie();
    }
        
        public static final Random RANDOM = new Random();
        
	public void jouerUnCoup(Joueur joueur) {
            Carreau car;
            car = lancerDésAvancer(joueur);
               
	}
        
        public void initPartie (ArrayList<String> joueurs)  {
            //ArrayList<String> joueurs = ihm.debutPartie(); //On renvoie le nom des joueurs
            if (joueurs!=null){
                //while (joueurs.size() < 2 || joueurs.size() > 6) {//On ne prend que des valeurs appartenat à l'intervalle [2; 6]
                //    joueurs = ihm.debutPartie();
                //}
                for (String nom : joueurs) {
                    Joueur joueur = new Joueur(nom, monopoly.getCarreau(1)); //On ajoute les joueurs sur la première case du plateau
                    monopoly.addJoueur(joueur);
                }
            }
            
            this.lancePartie();
        }
        
        public static int lancerDes() {
            return RANDOM.nextInt(6)+1;
        }
        
	private Carreau lancerDésAvancer(Joueur j) {
            int resDes1 = lancerDes();
            int resDes2 = lancerDes();
            int sommeDes = resDes1+resDes2; //Si on a deux dés égaux le joueur joue deux fois
            if (resDes1 == resDes2) { 
                Carreau carreau = monopoly.avancerJoueur(j, sommeDes);
                ihm.messageJoueurAvance(j, sommeDes, carreau, true);       
                Jeu.Resultat res = carreau.action(j,sommeDes, monopoly.pickCartes());
                this.action(ihm.action(res, j), j, res, monopoly.pickCartes());
                resDes1 = lancerDes();
                resDes2 = lancerDes();
                sommeDes = resDes1+resDes2;
            }
            Carreau carreau = monopoly.avancerJoueur(j, sommeDes);
            ihm.messageJoueurAvance(j, sommeDes, carreau, false);       //Affiche les infos types : nomJoueur, cash, carreau ...
            Jeu.Resultat res = carreau.action(j,sommeDes, monopoly.pickCartes());
            //ihm.action(res, j);
            this.action(ihm.action(res, j), j, res, monopoly.pickCartes()); //L'ihm action envoie le cas dans lequel on se trouve sous forme d'entier
            return carreau;
	}
        
        private void action (int cas, Joueur j, Jeu.Resultat res, Carte[] cartes) { // Selon le cas, on gère les actions à faire
            switch (cas) {
                case 0:
                    //Il ne se passe rien
                break;
                case 2:
                    //On veut acheter une propriete et on peut le faire
                    j.payer(res.getPrixPropriete());
                    res.getProprieteAchete().setProprietaire(j);
                break;
                    
            }
        }
        
        public String construire(ProprieteAConstruire p, Joueur j) {
        if (p.getNbHotel() == 0) { // si il n'y a pas déjà d'hotel sur la case on peut contruire
            Groupe gr = p.getGroupe();
            if  (p.possedeToutesPropGroupe(j)) { // si le joueur possede toute les propriétés du goupe de la propriété on peut construire
                int minMaison = gr.getMinMaison(); // retourne le nb minimum de maison sur les propriété du groupe
                int nbMaison = p.getNbMaison();
                if (minMaison == nbMaison) {  // si la propriété posséde le nombre minimal de maison on peut contruire
                    if (minMaison == 4) { // si le nombre minimal de maison est égal à 4 alors on construit un Hotel
                        
                    }
                    else { // sinon on contruit une maison
                        this.monopoly.removeMaison();
                        p.addMaison();
                        j.payer(p.getPrixMaison());
                        return "Maison Construite";
                    }     
                }else {
                    return "Construction impossible; Vous devez constuire uniformement";
                }
            }else {
                
            }
            
        }

        //if (this.monopoly.resteMaison()) {
            
        //}
    }
        
        private void lancePartie() { //Contient la boucle principale pour le lancement de la partie
            boolean continuer = true;
            int i = 0; 
            int nbTour = 0;
                       
            do {
                if (i==monopoly.getJoueurs().size() || i == 0) {  //Si on a fait le tour on recommence et on passe au prochain tour.
                    i=0;
                    nbTour++;
                    if (!ihm.debutTour(monopoly.getJoueurs(), nbTour)) { //On doit pouvoir s'arreter
                        continuer = false;
                    }

                }
                Joueur j = monopoly.getJoueurs().get(i);

                if (continuer) {        //renvoie true si le joueur veut continuer à jouer;
                    this.lancerDésAvancer(j);
                    i++;
                }
                
                if (j.getCash() < 0) {          //Si le joueur n'a plus d'argent, il a perdu
                    ihm.perte(j);
                    monopoly.removeJoueur(j);
                }
            } while (monopoly.getJoueurs().size() > 1 && continuer);
            
            if (monopoly.getJoueurs().size() == 1) {
                ihm.gagne(monopoly.getJoueurs().get(0));
            }
        }
}
