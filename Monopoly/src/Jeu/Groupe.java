package Jeu;

import java.util.HashSet;

public class Groupe {
	private CouleurPropriete couleur; //Couleur des propriétés du groupe
	public HashSet<ProprieteAConstruire> proprietes = new HashSet<ProprieteAConstruire>(); //HashSet contenant toutes les propriétés du groupe

        public Groupe(CouleurPropriete couleur) {
            this.couleur = couleur;
        }
        
        public void addPropriete(ProprieteAConstruire propriete) { 
            proprietes.add(propriete);
        }

	public int getNbPropriete() {
            return this.proprietes.size();
	}

    public int getMinMaison() {
        int minMaison = 4;
        for (ProprieteAConstruire p : this.proprietes) {
            if (p.getNbMaison() <=  minMaison) {
                minMaison = p.getNbMaison();
            }
            if (p.getNbHotel() == 1) {
                minMaison =4;
            }
        }
        return minMaison;
    }
}