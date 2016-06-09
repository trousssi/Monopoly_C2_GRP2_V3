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
    public void jouer(ArrayList<String> joueurs);
    
    public boolean debutTour(ArrayList<Joueur> joueurs, int nbTour);
    
    public void perte(Joueur joueur);
    
    public void gagne(Joueur joueur);
    
    public void messageCaseDepart(Joueur joueur);
    
    public void joueurEnPrison(Joueur j);
    
    public void messageJoueurAvance(Joueur joueur, int sommeDes, Carreau carreau, boolean desDouble);
    
    public int action(Resultat res, Joueur j);
    
    public void recupererNomJoueurs(HashSet<String> joueurs);
    
    public void inscriptionJoueurs ();
    
    public void ordreDuJeu();
    
    public void lancerJeu();
}
