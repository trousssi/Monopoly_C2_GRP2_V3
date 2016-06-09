/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

/**
 *
 * @author Jérémy
 */
public abstract class Carte {
    

    private String nom;


    public Carte(String nom) {
        this.nom = nom;
    }

    public abstract Resultat action (Joueur j);

    public Resultat renvoyerNom (Resultat res) {
        res.setNomCarte(this.nom);
        return res;
    }
    
    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isPossede() {
        return false;
    }
    
    
    
}
