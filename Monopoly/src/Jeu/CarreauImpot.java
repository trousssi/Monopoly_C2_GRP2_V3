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
public class CarreauImpot extends Carreau{
    
    private int prix;
    
    public CarreauImpot(int numero, String nomCarreau, int prix) {
        super(numero, nomCarreau);
        this.prix = prix;
    }

    @Override
    public Resultat action(Joueur j, int sommeDe, ArrayList<Carte> cartes) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        res.setNumeroCarreau(this.getNumero());
        res.setNomCarreau(this.getNomCarreau());
        j.payer(prix);
        return res;
    }
    

    
}
