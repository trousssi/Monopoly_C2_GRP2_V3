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
public class CarteLiberePrison extends Carte{
    
    private boolean possede;
    
    public CarteLiberePrison(String nom) {
        super(nom);
        this.possede = false;
    }

    @Override
    public Resultat action(Joueur j) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        renvoyerNom(res);
        
        j.addCartePrison(this);
        this.setPossede(true);
        
        return res;
    }

    public boolean isPossede() {
        return possede;
    }

    public void setPossede(boolean possede) {
        this.possede = possede;
    }
    
    
    
}
