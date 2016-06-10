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
public class CarreauSansAction extends Carreau {
    
    public CarreauSansAction(int numero, String nomCarreau) {
        super(numero, nomCarreau);
    }

    @Override
    public Resultat action(Joueur j, int sommeDes, ArrayList<Carte> cartes) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        res.setNumeroCarreau(this.getNumero());
        res.setNomCarreau(this.getNomCarreau());
        return res;
    }
    
}
