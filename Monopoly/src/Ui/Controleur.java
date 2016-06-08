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
        
        public Carreau avancerJoueur(Joueur joueur, int sommeDes) { // Méthode permettant au pion du joueur d'avancer dans le jeu en fonction de la somme des dés lancés.
        Carreau carreauDep = joueur.getPositionCourante();
        Carreau carreauArr;
        carreauArr = monopoly.getCarreau(carreauDep.getNumero()+sommeDes);
        joueur.setPositionCourante(carreauArr);
        if (carreauDep.getNumero() > carreauArr.getNumero()) {
            joueur.crediter(200);
            ihm.messageCaseDepart(joueur);
        }
        return carreauArr;
    }
     
    private void allerEnPrison(Joueur j) {
        Carreau prison = monopoly.getCarreau(11);
        j.setPositionCourante(prison);
        j.setEnPrison();
        ihm.joueurEnPrison(j);
    }
    
        private void actionCarte(Joueur j, Carte carte) {
        if ("LI".equals(carte.getType())) { //Carte libéré de prison
            j.addCartePrison(carte);
            carte.setPossede(true);
        } else if ("AR".equals(carte.getType())) { //Carte Debit/Credit d'argent
            if (carte.getPrix() > 0) {
                j.crediter(carte.getPrix());
            } else if (carte.getPrix() < -1){
                j.payer(Math.abs(carte.getPrix()));
            } else if (carte.getPrix() == -1) {
                anniversaire(j);
            } else {
                System.out.println("ERREUR CARTE AR");
            }
        } else if ("DE".equals(carte.getType())) { //Carte déplacement de joueur
            if (carte.getDeplacement().getNumero() > 0) {
                
            } else if (carte.getDeplacement().getNumero() < 0) {
                //RES
            }
        } else if ("RE".equals(carte.getType())) { //Carte Reparation
            if (carte.getPrix() == 1) {
                // RES
            } else if (carte.getPrix() == 2) {
                //RES
            }
        } else if ("PR".equals(carte.getType())) { //Carte allez en prison
            //RES
        }
    }

    private void anniversaire(Joueur j) {
        for (Joueur jo : monopoly.getJoueurs()) {
            if (jo != j) {jo.payer(10);
            } else if (jo == j) {
                jo.crediter(10*(monopoly.getJoueurs().size()-1));
            }
        }
    }



}
