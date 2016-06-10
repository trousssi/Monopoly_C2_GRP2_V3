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
public class CarreauPrison extends Carreau{

    public CarreauPrison(int numero, String nomCarreau) {
        super(numero, nomCarreau);
    }

    @Override
    public Resultat action(Joueur j, int sommeDes, ArrayList<Carte> cartes) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        res.setNomCarreau(this.getNomCarreau());
        res.setNomCarreau(this.getNomCarreau());
        res.setEnPrison(true);
        return res;
    }
    
}
