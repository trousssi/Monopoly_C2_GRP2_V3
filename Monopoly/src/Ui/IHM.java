package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.ProprieteAConstruire;
import Jeu.Resultat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.JFrame;

public final class IHM implements Observateur{
    private Controleur controleur;
    private JFrame w;
    private IhmMenu ihmMenu;
    private IhmInscription ihmI;
    private IhmJeu ihmJeu;
    private IhmInitOrdreJeu ihmInit;
    private IhmFinTour ihmFinTour;
    private HashSet<String> joueurs;
    private int numJoueur;
    private int numTour = 1;
        
          
    public IHM(Controleur controleur) {
        this.controleur = controleur;
        this.controleur.setObservateur(this);
        this.menuEntree();
    }
    
    public void menuEntree() {
        this.ihmMenu = new IhmMenu();
        ihmMenu.afficher();
        ihmMenu.setObservateur(this);       
    }
    
    
    
    @Override
    public void messageJoueurAvance(Joueur joueur, int sommeDes, Carreau carreau, boolean desDouble) {
        System.out.println("[Joueur = " + joueur.getNom()+"]" + " [Cash = "+ joueur.getCash()+"]");
        System.out.println("La somme de dés vaut : " + "\033[34m" + sommeDes + "\033[0m");
        //System.out.println("Carreau courant : " + joueur.getPositionCourante().getNomCarreau());
        System.out.println("Destination : " + carreau.getNomCarreau());
        if (carreau.getNomCarreau().contains("Gare")) {
        } 
        if (desDouble) {
            System.out.println("\033[31mVous avez fait un double, vous pouvez rejouer !!\033[0m");
        }
    }

    public boolean infoJoueur(Joueur joueur) { //Affichage des différentes infos sur le joueur (nom, cash, et carreau courant)
        System.out.println("[Joueur =" + joueur.getNom()+"]" + " [Cash = "+joueur.getCash());
        System.out.println("Carreau courant : " + joueur.getPositionCourante().getNomCarreau());

        return true;
    }

    @Override
    public boolean debutTour(ArrayList<Joueur> joueurs, int nbTour) { //Affichage de l'interface du début de tour
        System.out.println("\033[1m-----------------------------------\033[0m");
        System.out.println("\033[1m--------------\033[0m" + "Tour n°" + nbTour + "\033[1m-------------\033[0m");
        System.out.println("\033[1m-----------------------------------\033[0m");


        for (Joueur j : joueurs) { //Résumé des infos des différents joueurs de la partie
            System.out.println("Joueur : " + j.getNom());
            System.out.println("Cash : " + j.getCash());
            System.out.println("Position : Case n°" + j.getPositionCourante().getNumero() + " " + j.getPositionCourante().getNomCarreau());
        System.out.println("\033[1m-----------------------------------\033[0m");
        }
        String rep = "";
        while (!"o".equals(rep) && !"n".equals(rep) && rep != null) { //Les joueurs veulent-ils continuer à jouer ?
            System.out.println("Voulez-vous Continuer ? (O/N)");
            Scanner sc = new Scanner(System.in);
            rep = sc.nextLine().toLowerCase();
        }

        return rep.charAt(0) == 'o';

    }


    @Override
    public void action(Resultat res, Joueur j,int d1, int d2, int nbdouble) {

        //Resultat retour = new Resultat();

        /*if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) {
        System.out.println("Carreau = " + res.getNomCarreau() + ", case n° " + res.getNumeroCarreau());
        }

        //Propriete --> Acheter ou payer le loyer
        else if (res.getProprietairePropriete() != null && res.getProprietairePropriete() != j) {
        System.out.println("Loyer = " + res.getLoyerPropriete());//Nom déjà affiché + paiement obligatoire du loyer

        }
        else if(res.getPrixPropriete() == -2) { // Cas où le joueur n'a pas assez d'argent pour acheter la propriété
        System.out.println("\033[31mVous ne pouvez pas acheter cette propriété\033[0m");
        }
        else if (res.getPrixPropriete() != -1) {               // Cas où le joueur peux acheter la propriété
        String rep = "";
        while (!"o".equals(rep) && !"n".equals(rep) && rep != null) {
        System.out.println("Prix = " + "\033[35m" + res.getPrixPropriete()  +" €\033[0m, voulez-vous acheter cette proprieté ? (O/N) ");
        Scanner sc = new Scanner(System.in);
        rep = sc.nextLine().toLowerCase();
        }

        if (rep.charAt(0) == 'o') {     // Le joueur a acheté la propriété
        System.out.println(j.getPositionCourante().getNomCarreau() + " achetée");
        System.out.println("\n \n");
        return 2;//On lance l'achat de la proprieté
        }
        }
        else if (res.getProprietairePropriete() == j){ // Cas où le joueur tombe sur une case qu'il a déjà acheté
        System.out.println("Vous êtes le proprietaire de cette case.");
        }
        System.out.println("\n \n");*/

        //return 0;

        this.ihmJeu.afficherInfos(j, res,d1,d2,nbdouble);      
    }

    @Override
    public void perte(Joueur joueur) { //Un joueur perd la partie et est éliminé
        System.out.println("\033[1m-----------------------------------\033[0m");
        System.out.println("Le joueur "+ joueur.getNom() + " a perdu et est éliminé de la partie !");
        System.out.println("\033[1m-----------------------------------\033[0m");
    }
    @Override
    public void gagne(Joueur joueur) { //Le dernier joueur gagne la partie
        System.out.println("\033[1m-----------------------------------\033[0m");
        System.out.println("Fin de la partie, "+ joueur.getNom() + " a remporté la victoire");
        System.out.println("\033[1m-----------------------------------\033[0m");
    }

    

    @Override
    public void messageCaseDepart(Joueur joueur) {
        this.ihmJeu.messageCaseDepart(joueur);   
    }

   
    @Override
    public void recupererNomJoueurs(HashSet<String> joueurs) {
        this.joueurs = joueurs;
        
        this.ihmMenu.activeJouer(joueurs);
    }

    @Override
    public void inscriptionJoueurs () {
        this.ihmI = new IhmInscription(); 
        ihmI.afficher();
        ihmI.setObservateur(this);
    }    
      
    @Override
    public void ordreDuJeu() {
        this.ihmMenu.setVisible(false);
        this.ihmInit = new IhmInitOrdreJeu(this.joueurs);
        this.ihmInit.setObservateur(this);
        this.ihmInit.afficher();

    }
    
    @Override
    public void lancerJeu() {
       this.controleur.initPartie(joueurs,ihmInit.getNomPremier());
       this.ihmInit.setVisible(false);
           
       this.ihmJeu = new IhmJeu(this.joueurs);
       this.ihmJeu.setObservateur(this);
       this.ihmJeu.afficher();
       
       this.numJoueur = 0;
       this.ihmJeu.displayJoueur(this.controleur.getJoueur(numJoueur),0);      
    }
    
    
    @Override
    public void lanceDes(Joueur j, int nbDouble){
        this.controleur.lancerDesAvancer(j, nbDouble);
    }
    
    @Override
    public void Reponse(int cas, Joueur j, Jeu.Resultat res) {
        this.controleur.action(cas, j, res);
    }
    
    @Override
    public int notification(String message, Joueur j) {
        return this.ihmJeu.notification(message, j);
    }
    
    @Override
    public void joueurSuivant(Joueur j){
        if (j.getCash() < 0) {
            this.controleur.perte(j);
            
        }
        
        if (controleur.getJoueurs().size() > 1) {


            this.numJoueur++;
            if (numJoueur==this.controleur.getJoueurs().size() || this.numJoueur == 0) {  //Si on a fait le tour on recommence et on passe au prochain tour.
                passeTour(); 
            }

            this.ihmJeu.displayJoueur(this.controleur.getJoueur(numJoueur), 0);
            }
        else {
            this.ihmJeu.setVisible(false);
            IhmBoiteMessage.afficherBoiteDialogue(this.controleur.getJoueur(0).getNom() + " a Gagné !!", "info");
        }
        
    }
    
    @Override
    public void rejouer(Joueur j,int nbdouble) {
         if (j.getCash() < 0) {
            this.controleur.perte(j);
            
        }
        
        if (controleur.getJoueurs().size() > 1) {
        this.ihmJeu.displayJoueur(j, nbdouble);
        }
        else {
            this.ihmJeu.setVisible(false);
            IhmBoiteMessage.afficherBoiteDialogue(this.controleur.getJoueur(0).getNom() + " a Gagné !!", "info");
        }
    }
    
    @Override
    public boolean sortiePrisonCarte(Joueur j) {
        return IhmBoiteMessage.afficherBoiteDialogue("Voulez vous utiliser votre carte Sortie de Prison", "ouinon");
    }
    
    @Override
    public void sortiePrison(String raison) {
        this.ihmJeu.sortiePrison(raison);
    }

    @Override
    public boolean peutConstruire(Joueur j, ProprieteAConstruire prop) {
        return controleur.peutConstruire(j, prop);
    }
    
    @Override
    public int getNbMaison() {
        return this.controleur.getNbMaison();
    }
    
    @Override
    public int getNbHotel() {
        return this.controleur.getNbHotel();
    }
    
    public void passeTour() {
        this.numJoueur=0;
        ihmFinTour = new IhmFinTour(numTour, controleur.getJoueurs(), ihmJeu.getCouleurJoueurs());
        ihmFinTour.setObservateur(this);
        this.numTour++;
    }
    
    @Override
    public void retourAuJeu() {
        ihmFinTour.setVisible(false);
    }
    
  
    @Override
    public void construire(ProprieteAConstruire p, Joueur j) {
        controleur.construire(p, j);
    }

}
