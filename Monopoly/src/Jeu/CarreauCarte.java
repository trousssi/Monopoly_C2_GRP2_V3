/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import java.util.ArrayList;

/**
 *
 * @author Jérémy
 */
public class CarreauCarte extends Carreau{
    
    public CarreauCarte(int numero, String nomCarreau) {
        super(numero, nomCarreau);
    }
    
    @Override
    public Resultat action (Joueur j, int sommeDes, ArrayList<Carte> c) {
        Resultat res = new Resultat();
        Carte carte = tirerCarte(c);
        res.setCarte(carte);
        res = carte.action(j);
        return res;
    }
    
    private Carte tirerCarte (ArrayList<Carte> cartes) {
        Carte ca = null;
        switch (this.getNumero()) {
            case 8:
            case 23:
            case 37:
                ca = cartes.get(0);
                break;
            case 3:
            case 18:
            case 34:
                ca = cartes.get(1);
                break;
            default:
                break;
        }
        return ca;
    }
}
