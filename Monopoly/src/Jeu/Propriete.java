package Jeu;

import java.util.ArrayList;

public abstract class Propriete extends Carreau {
	private int prix;
	private int loyer;
	public Joueur proprietaire;

        public Propriete(int numero, String nomCarreau, int prix, int loyer, Joueur proprietaire) {
            super(numero, nomCarreau);
            this.prix = prix;
            this.loyer = loyer;
            this.proprietaire = proprietaire;
        }


        @Override
	public Resultat action(Joueur j, int sommeDe, ArrayList<Carte> cartes) {
            // Resultat res = new Resultat();
            Joueur jProprio;
            //int l; //loyer --> voir résultat
            jProprio = getProprietaire();
            if (jProprio!=null) { //Il y a un propriétaire
                if (j!=jProprio) { //S'il n'est pas propriétaire
                    Resultat res = calculLoyer(sommeDe);
                    j.payer(res.getLoyerPropriete()); //Il doit forcément payer le loyer
                    jProprio.crediter(res.getLoyerPropriete());
                    res.setProprietairePropriete(jProprio);
                    return res;
                }
                else {
                    Resultat res = new Resultat();
                    res.setProprietairePropriete(j);
                    return res;
                } //Le joueur courant est propriétaire : rien à faire
            } else { //Pas de propriétaire
                return acheterPropriete(j);
            }
            
	}

	protected abstract Resultat calculLoyer(int sommeDe);

	protected Joueur getProprietaire() {
		return this.proprietaire;
	}

	protected Resultat acheterPropriete(Joueur j) {
		Resultat res = new Resultat();
                if (j.peuxPayer(this.prix)) {
                    res.setPrixPropriete(prix);
                    res.setProprieteAchete(this);
                } else {
                    res.setPrixPropriete(-2);
                }
                return res;
	}

	public int getPrix() {
		return this.prix;
	}

	public int getLoyer() {
		return this.loyer;
	}
        
        //Setters
        public void setPrix(int prix) {
            this.prix = prix;
        }

        public void setLoyer(int loyer) {
            this.loyer = loyer;
        }

       public abstract void setProprietaire(Joueur j);
        
}
