package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.Resultat;
import java.util.ArrayList;
import java.util.Scanner;

public class IHM implements Observateur{
	public Controleur controleur;
        public IhmInscription ihmI;

    public IHM(Controleur controleur) {
        this.controleur = controleur;
    }

       public void debutPartie () {
            this.ihmI = new IhmInscription();
            ihmI.afficher();
            ihmI.setObservateur(this);
            //ArrayList<String> joueurs = new ArrayList<>();
           /* System.out.println("Combien de joueurs participent ? (Entre 2 et 6)");
            Scanner sc = new Scanner(System.in);
            int i =0;
            try {
                i = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("\033[31mErreur, entrez un nombre de joueurs entre 2 et 6 inclus\033[0m");
                return joueurs;
            }

            if (i > 1 && i < 7) {   
                for (int n = 1; n<i+1; n++) {
                    System.out.println("Entrer le nom du joueur n°" + n + " : ");
                    String nom = sc.nextLine();
                    joueurs.add(nom);
                }
                return joueurs;
            } else {
                System.out.println("\033[31mErreur, entrez un nombre de joueurs entre 2 et 6 inclus\033[0m");
                return joueurs;
            }*/
  
           /* while (joueurs.isEmpty()) {
           joueurs = ihmI.getJoueurs();
           Thread.sleep(1); // ne marche pas sans, pour une raison obscure
           }*/
            
            
        }
    
    
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
        
        
        public int action(Resultat res, Joueur j) {
            Resultat retour = new Resultat();
            
            if(res.getNomCarreau() != null && res.getProprietairePropriete() == null) {
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
            System.out.println("\n \n");

            return 0;
            
        }
        
        public void perte(Joueur joueur) { //Un joueur perd la partie et est éliminé
            System.out.println("\033[1m-----------------------------------\033[0m");
            System.out.println("Le joueur "+ joueur.getNom() + " a perdu et est éliminé de la partie !");
            System.out.println("\033[1m-----------------------------------\033[0m");
        }
        public void gagne(Joueur joueur) { //Le dernier joueur gagne la partie
            System.out.println("\033[1m-----------------------------------\033[0m");
            System.out.println("Fin de la partie, "+ joueur.getNom() + " a remporté la victoire");
            System.out.println("\033[1m-----------------------------------\033[0m");
        }
        
         public void jouer(ArrayList<String> joueurs) {
            this.controleur.initPartie(joueurs);
        }

    void messageCaseDepart(Joueur joueur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void joueurEnPrison(Joueur j) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
