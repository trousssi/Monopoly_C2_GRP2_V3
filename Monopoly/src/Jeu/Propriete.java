package Jeu;

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
	public Resultat action(Joueur j) {
            Resultat res = new Resultat();
            Joueur jProprio;
            //int l; //loyer --> voir résultat
            jProprio = getProprietaire();
            if (jProprio!=null) { //Il y a un propriétaire
                if (j!=jProprio) { //S'il n'est pas propriétaire
                    return calculLoyer();
                }
                else {return null;} //Le joueur courant est propriétaire : rien à faire
            } else { //Pas de propriétaire
                return acheterPropriete(j);
            }
	}

	protected abstract Resultat calculLoyer();

	protected Joueur getProprietaire() {
		return this.proprietaire;
	}

	protected Resultat acheterPropriete(Joueur aJ) {
		throw new UnsupportedOperationException();
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

        public void setProprietaire(Joueur proprietaire) {
            this.proprietaire = proprietaire;
        }
        
}