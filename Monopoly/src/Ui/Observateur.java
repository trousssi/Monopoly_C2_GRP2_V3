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
    
    public void lanceDes(Joueur j,int nbDouble);
    
    public void Reponse(int cas, Joueur j, Jeu.Resultat res);
    
     public void notification(String message,Joueur j);
     
     public void joueurSuivant(Joueur j);
     
     public void rejouer(Joueur j,int nbdouble);
     
    public boolean sortiePrisonCarte(Joueur j);
    
    public void sortiePrison();
}
