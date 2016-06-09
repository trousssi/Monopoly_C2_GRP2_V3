package Jeu;

import java.util.ArrayList;

public class AutreCarreau extends Carreau {

    public AutreCarreau(int numero, String nomCarreau) {
        super(numero, nomCarreau);
    }
   
    //Renvoie à Résultat le Numéro et le nom de cet AutreCarreau.
    @Override
    public Resultat action(Joueur aj, int sommeDe, ArrayList<Carte> cartes) {
        Resultat res = new Resultat(this.getNumero(), this.getNomCarreau());
        return res;
    }
}
