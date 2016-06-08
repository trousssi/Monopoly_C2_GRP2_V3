package Ui;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.Resultat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IHM implements Observateur{
	private Controleur controleur;
        private IhmInscription ihmI;
        private JFrame w;
        private IhmJeu ihmJeu;
                
    public IHM(Controleur controleur) throws IOException {
        this.controleur = controleur;
        this.controleur.setObservateur(this);
        this.menuEntree();
    }
public void inscriptionJoueurs () {
        this.ihmI = new IhmInscription();
        ihmI.afficher();
        ihmI.setObservateur(this);
    }
    
    public void menuEntree() throws IOException {
        w = new JFrame();
        w.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        w.setSize(500, 300);
        w.setVisible(true);
       
        Color fond = new Color(206,239,203);
        w.getContentPane().setBackground(fond);
        
        w.setLayout(new BorderLayout());
        JButton jouer = new JButton("JOUER");
        JButton inscrire = new JButton("Inscrire les Joueurs");
        JButton quitter = new JButton("Quitter le Jeu");
        
        JLabel logo = new JLabel(new ImageIcon("src/Data/logoMonopoly.jpg"));
        JPanel panelBoutons = new JPanel();
        w.add(panelBoutons, BorderLayout.CENTER);
        
        JPanel panelSud = new JPanel();
        w.add(panelSud, BorderLayout.SOUTH);
        panelSud.setBackground(fond);
        
        panelBoutons.setBackground(fond);
        
        //Espace entre Boutons
        JPanel vide1 = new JPanel(); vide1.setPreferredSize(new Dimension(30,0)); vide1.setBackground(fond);
        JPanel vide2 = new JPanel(); vide2.setPreferredSize(new Dimension(30,0)); vide2.setBackground(fond);

        panelBoutons.add(quitter);panelBoutons.add(vide1);
        panelBoutons.add(jouer);panelBoutons.add(vide2);
        panelBoutons.add(inscrire);
        jouer.setEnabled(false);
        
        
        
        w.add(logo, BorderLayout.NORTH);
        
        jouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ihmJeu = new IhmJeu();
                } catch (IOException ex) {
                    Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
                }
                        
            }
        });
        inscrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscriptionJoueurs(); 
               /* for ( String i : ihmI.getNomJoueurs()) {
                    System.out.println(i);
                }*/
                if (!ihmI.getNomJoueurs().isEmpty()) { //Si les joueurs sont bien inscrits, on peut jouer
                    jouer.setEnabled(true);
                    //System.out.println("ok");
                }
            }
        });
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res = IhmBoiteMessage.afficherBoiteDialogue("Etes vous sûr de vouloir quitter?", "ouinon");
                if (res) { 
                    IhmBoiteMessage.afficherBoiteDialogue("A bientôt", "info");
                    w.dispatchEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));//Permet de fermer la fenêtre
                }
            }
        });
        
        w.setVisible(true);
    }
    
    /*public void commeTuVeux(HashSet<String> joueurs) {
        
        jouer.setEnabled(true);
    }*/
    
    
    
    
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

    public void messageCaseDepart(Joueur joueur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void joueurEnPrison(Joueur j) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
