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
public class CarteReparation  extends Carte{
    
    int prixMaison;
    int prixHotel;
    
    public CarteReparation(String nom, int prixMaison, int prixHotel) {
        super(nom);
        this.prixHotel = prixHotel;
        this.prixMaison = prixMaison;
        
    }

    @Override
    public Resultat action(Joueur j) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        renvoyerNom(res);
        int nbMaison = 0;
        int nbHotel = 0;
        for (ProprieteAConstruire prop : j.getProprietesAconstruire()) {
            nbMaison = nbMaison + prop.getNbMaison();
            nbHotel = nbHotel + prop.getNbHotel();
        }
        j.payer((nbMaison*prixMaison)+(nbHotel*prixHotel));
        return res;
    }
    
}
