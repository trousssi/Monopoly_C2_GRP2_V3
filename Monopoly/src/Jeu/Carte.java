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
public class Carte {
    
    private String type;
    private String nom;
    private int prix;
    private Carreau deplacement;
    private boolean possede;

    public Carte(String type, String nom, int prix, Carreau deplacement) {
        this.type = type;
        this.nom = nom;
        this.prix = prix;
        this.deplacement = deplacement;
        possede = false;
    }

    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public int getPrix() {
        return prix;
    }

    public Carreau getDeplacement() {
        return deplacement;
    }

    public boolean isPossede() {
        return possede;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setDeplacement(Carreau deplacement) {
        this.deplacement = deplacement;
    }

    public void setPossede(boolean possede) {
        this.possede = possede;
    }
    
    
    
}
