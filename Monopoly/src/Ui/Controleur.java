package Ui;

import Jeu.Carreau;
import Jeu.Carte;
import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Controleur {
	private Observateur obs;
	public Monopoly monopoly;

    public Controleur()  {
        
        this.monopoly = new Monopoly();

    }
    
    public void setObservateur(Observateur obs) {
        this.obs = obs;
    }
        
        public static final Random RANDOM = new Random();
        
	public void jouerUnCoup(Joueur joueur) {
            Carreau car;
           // car = lancerDésAvancer(joueur);
               
	}
        
        public void initPartie (HashSet<String> joueurs, String nomPremierJoueur)  {
            //ArrayList<String> joueurs = ihm.debutPartie(); //On renvoie le nom des joueurs
            if (joueurs!=null){
                //while (joueurs.size() < 2 || joueurs.size() > 6) {//On ne prend que des valeurs appartenat à l'intervalle [2; 6]
                //    joueurs = ihm.debutPartie();
                //}
                monopoly.addJoueur(new Joueur(nomPremierJoueur, monopoly.getCarreau(1)));
                for (String nom : joueurs) {
                    if (nomPremierJoueur != nom) {
                        Joueur joueur = new Joueur(nom, monopoly.getCarreau(1)); //On ajoute les joueurs sur la première case du plateau
                        monopoly.addJoueur(joueur);
                    }
                }
            }
            
           // this.lancePartie();
        }
        
        
        
        public static int lancerDes() {
            return RANDOM.nextInt(6)+1;
            //Scanner sc = new Scanner(System.in);
            //return sc.nextInt();
            
        }
        
        public Carreau lancerDesAvancer(Joueur j, int nbDouble) {
            /*int resDes1 = 0;
            int resDes2 = 0;
            Carreau carreau = null;
            int sommeDes = 0; //Si on a deux dés égaux le joueur joue deux fois
            int nbDouble = 0;
            while (resDes1 == resDes2 && nbDouble < 3) {
            resDes1 = lancerDes();
            resDes2 = lancerDes();
            sommeDes = resDes1+resDes2; //Si on a deux dés égaux le joueur joue deux fois
            if (nbDouble == 2 && resDes1 == resDes2) {
            allerEnPrison(j);
            } else {
            carreau = this.avancerJoueur(j, sommeDes);
            obs.messageJoueurAvance(j, sommeDes, carreau, true);
            Jeu.Resultat res = carreau.action(j,sommeDes, monopoly.pickCartes());
            this.action(obs.action(res, j), j, res);
            nbDouble++;
            }
            }
            return carreau;*/
            
            
            
            
            /*  int resDes1 = 0;
            int resDes2 = 0;
            Carreau carreau = null;
            int sommeDes = 0; //Si on a deux dés égaux le joueur joue deux fois
            
            //while (resDes1 == resDes2 && nbDouble < 3) {
            resDes1 = lancerDes();
            resDes2 = lancerDes();
            sommeDes = resDes1+resDes2; //Si on a deux dés égaux le joueur joue deux fois
            if (nbDouble == 2 && resDes1 == resDes2) {
            allerEnPrison(j);
            } else {
            carreau = this.avancerJoueur(j, sommeDes);
            //obs.messageJoueurAvance(j, sommeDes, carreau, true);
            
            Jeu.Resultat res = carreau.action(j,sommeDes, monopoly.pickCartes());
            nbDouble++;
            this.obs.action(res, j, resDes1, resDes2, nbDouble);
            
            }
            //     }
            return carreau;*/
            
            
            
            
            int resDes1 = 0;
            int resDes2 = 0;
            Carreau carreau = null;
            int sommeDes = 0; //Si on a deux dés égaux le joueur joue deux fois
            boolean desDouble = true;
                    resDes1 = lancerDes();
                    resDes2 = lancerDes();
                    sommeDes = resDes1+resDes2; //Si on a deux dés égaux le joueur joue deux fois 
                    desDouble = resDes1 == resDes2;               
                    if (nbDouble == 2 && desDouble) {
                        allerEnPrison(j);
                        
                    } else {
                        
                        if (tourEnPrison(j, desDouble)) {
                            carreau = this.avancerJoueur(j, sommeDes);
                                            
                            Jeu.Resultat res = carreau.action(j,sommeDes, monopoly.pickCartes());
                            
                            nbDouble++;
                             this.obs.action(res, j, resDes1, resDes2, nbDouble);
                        }
                    }

                
            
            return carreau;
        }

        
        
        /*	private Carreau lancerDésAvancer(Joueur j) {
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
        }*/       
    public Carreau avancerJoueur(Joueur joueur, int sommeDes) { // Méthode permettant au pion du joueur d'avancer dans le jeu en fonction de la somme des dés lancés.
        Carreau carreau = joueur.getPositionCourante();
        int numCarDep = carreau.getNumero();
        carreau = monopoly.getCarreau(numCarDep+sommeDes);
        joueur.setPositionCourante(carreau);
        int numCarArr = joueur.getPositionCourante().getNumero();
        if (numCarArr < numCarDep) {
            this.obs.messageCaseDepart(joueur);
            joueur.crediter(200);
        }
        return carreau;
    }      
        
        
        public void action (int cas, Joueur j, Jeu.Resultat res) { // Selon le cas, on gère les actions à faire
            /*if (res.getCarte() != null) {
                actionCarte(j, res.getCarte());
            }
            
            switch (cas) {
                case 0:
                    //Il ne se passe rien
                    this.obs.notification("", j);
                break;
                case 2:
                    //On veut acheter une propriete et on peut le faire
                    j.payer(res.getPrixPropriete());
                    res.getProprieteAchete().setProprietaire(j);
                    this.obs.notification("Proprieté achetée", j);
                break;
                    
            }
        }*/
            /*if (res.getNomCarte() != null || res.getNomCarreau() != null) {
            if (res.isDeplace()) {
            if (res.getDeplacement() != 0) {
            this.avancerJoueur(j, res.getDeplacement());
            } else if (res.getDeplacement() == -3) {
            j.setPositionCourante(monopoly.getCarreau(j.getPositionCourante().getNumero()-3));
            }
            } else if (res.isAnniversaire()) {
            this.anniversaire(j);
            } else if (res.isEnPrison()) {
            this.allerEnPrison(j);
            }
            }*/
            
            
            switch (cas) {
                case 0:
                    //Il ne se passe rien
                    this.obs.notification("", j);
                break;
                case 2:
                    //On veut acheter une propriete et on peut le faire
                    j.payer(res.getPrixPropriete());
                    res.getProprieteAchete().setProprietaire(j);
                    this.obs.notification("Proprieté achetée", j);
                    
                break;
                
                case 3:
                    //on veut déplacer le joueur sur une case
                    this.avancerJoueur(j, res.getDeplacement());

                break;
                case 4:
                    //on veut reculer le joueur de 3 cases
                    j.setPositionCourante(monopoly.getCarreau(j.getPositionCourante().getNumero()-3));
                   
                break;
                case 5:
                    // Carte Anniversaire
                    this.anniversaire(j);
                    
                break;
                case 6:
                    // Carte Allez en prison
                    this.allerEnPrison(j);
                    
                    break;
                   
            }
        }

        
        public boolean peutConstruire (Joueur j, ProprieteAConstruire prop) {
            boolean peut = false;
            if (j.getProprietesAconstruire().contains(prop)) {
                if (prop.getNbHotel() < 1 ) {
                    if ((prop.getNbMaison() < 4 && monopoly.resteMaison()) || (prop.getNbMaison() == 4 && monopoly.resteHotel())) {
                        if (prop.possedeToutesPropGroupe(j) && j.peuxPayer(prop.getPrixMaison()) && prop.getNbMaison() == prop.getGroupe().getMinMaison()) {
                            peut = true;
                        }
                    }
                }
            }
            
            return peut;
        }
        
        public void construire(ProprieteAConstruire p, Joueur j) {
        
            if (peutConstruire(j, p)) {
                if (p.getNbMaison() < 4) {
                    p.addMaison();
                    j.payer(p.getPrixMaison());
                    monopoly.removeMaison();
                } else if (p.getNbMaison() == 4) {
                    p.removeMaison(4);
                    this.monopoly.addMaison(4);
                    this.monopoly.removeHotel();
                    p.addHotel();
                    j.payer(p.getPrixMaison());
                }
            }
            
            
            /*        if (p.getNbMaison() == 4) {
            
            }
            
            if (p.getNbHotel() == 0) { // si il n'y a pas déjà d'hotel sur la case on peut contruire
            Groupe gr = p.getGroupe();
            if (p.possedeToutesPropGroupe(j)) { // si le joueur possede toute les propriétés du goupe de la propriété on peut construire
            int minMaison = gr.getMinMaison(); // retourne le nb minimum de maison sur les propriété du groupe
            int nbMaison = p.getNbMaison();
            if (minMaison == nbMaison) {  // si la propriété posséde le nombre minimal de maison on peut contruire
            if (j.peuxPayer(p.getPrixMaison())) {
            
            if (minMaison == 4) { // si le nombre minimal de maison est égal à 4 alors on construit un Hotel
            if (this.monopoly.resteHotel()) {
            p.removeMaison(4);
            this.monopoly.addMaison(4);
            this.monopoly.removeHotel();
            p.addHotel();
            j.payer(p.getPrixMaison());
            }
            else {
            return "Plus d'Hotel disponible";
            }
            } else { // sinon on contruit une maison
            if (this.monopoly.resteMaison()) {
            this.monopoly.removeMaison();
            p.addMaison();
            j.payer(p.getPrixMaison());
            return "Maison Construite";
            }
            else {return "Plus de maison disponible";}
            }
            
            } else {return "Vous n'avez pas les fonds suffisants";}
            
            } else {return "Construction impossible; Vous devez constuire uniformement";}
            
            } else {return "Vous devez posséder toutes les propriétés du groupe.";}
            }
            else {
            return "il y a déjà un hotel sur la propriété";
            }
            
            return "Erreur Inconu";*/        
            
      
    }
        
        private void lancePartie() { //Contient la boucle principale pour le lancement de la partie
            boolean continuer = true;
            int i = 0; 
            int nbTour = 0;
                       
            do {
                if (i==monopoly.getJoueurs().size() || i == 0) {  //Si on a fait le tour on recommence et on passe au prochain tour.
                    i=0;
                    nbTour++;
                    if (!obs.debutTour(monopoly.getJoueurs(), nbTour)) { //On doit pouvoir s'arreter
                        continuer = false;
                    }

                }
                Joueur j = monopoly.getJoueurs().get(i);

                if (continuer) {        //renvoie true si le joueur veut continuer à jouer;
                   // this.lancerDésAvancer(j);
                    i++;
                }
                
                if (j.getCash() < 0) {          //Si le joueur n'a plus d'argent, il a perdu
                    obs.perte(j);
                    monopoly.removeJoueur(j);
                }
            } while (monopoly.getJoueurs().size() > 1 && continuer);
            
            if (monopoly.getJoueurs().size() == 1) {
                obs.gagne(monopoly.getJoueurs().get(0));
            }
        }
        
    public void perte(Joueur joueur) {
        monopoly.removeJoueur(joueur);
    }
        
     
    private void allerEnPrison(Joueur j) {
        Carreau prison = monopoly.getCarreau(11);
        j.setPositionCourante(prison);
        j.setEnPrison();
        
    }
    
    
    private void sortirDePrison (Joueur j, boolean paye) {
        j.sorsDePrison();
        if (paye) {
            j.payer(50);
        }
        this.obs.sortiePrison();
    }
    

    
    private boolean tourEnPrison (Joueur j, boolean dDouble) {
        boolean utilise = false;
        boolean sorti = false;
        if (j.getToursEnPrison() != -1) {
            if (j.getCartesPrison().size() > 0) {utilise = obs.sortiePrisonCarte(j);}
            if (utilise) {
                sortirDePrison(j, false);            
                sorti = true;
            } else if (dDouble) {
                sortirDePrison(j, false);
                sorti = true;
            } else if (j.getToursEnPrison() == 2) {
                sortirDePrison(j, true);
                sorti = true;
            } else {
                j.tourEnPrison();
            }
        } else {
            sorti = true;
        }
        
        return sorti;
    }

    private void anniversaire(Joueur j) {
        for (Joueur jo : monopoly.getJoueurs()) {
            if (jo != j) {jo.payer(10);
            } else if (jo == j) {
                jo.crediter(10*(monopoly.getJoueurs().size()-1));
            }
        }
    }


   

    public Joueur getJoueur(int numJ) {
       return this.monopoly.getJoueurs().get(numJ);
    }
    
    public ArrayList<Joueur> getJoueurs() {
        return this.monopoly.getJoueurs();
    }

}
