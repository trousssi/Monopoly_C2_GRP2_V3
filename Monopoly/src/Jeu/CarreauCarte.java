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
public class CarreauCarte extends AutreCarreau{
    
    public CarreauCarte(int numero, String nomCarreau) {
        super(numero, nomCarreau);
    }
    
    @Override
    public Resultat action (Joueur j, int sommeDes, Carte[] c) {
        Resultat res = new Resultat();
        Carte carte = tirerCarte(c);
        res.setCarte(carte);
        return res;
    }
    
    private Carte tirerCarte (Carte[] cartes) {
        Carte ca = null;
        if (this.getNumero() == 8 || this.getNumero() == 23 || this.getNumero() == 37) {
            ca = cartes[0];
        } else if (this.getNumero() == 3 || this.getNumero() == 18 || this.getNumero() == 34) {
            ca = cartes[1];
        } else System.out.println("ERREUR CARTE");
        return ca;
    }
}
