/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.CarreauSansAction;
import Jeu.CouleurPropriete;
import Jeu.Groupe;
import Jeu.Joueur;
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;

/**
 *
 * @author Jérémy
 */
public class TESTCONSTRUIRE {
    private IhmMonopoly mono;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Joueur j = new Joueur("JTest", new CarreauSansAction(1, "testcarreau"));
        
        /*        ArrayList<Integer> prix = new ArrayList<>();
        prix.add(5);
        prix.add(10);
        prix.add(15);
        Groupe bleuFonce = new Groupe(CouleurPropriete.bleuFonce);
        
        ProprieteAConstruire prop = new ProprieteAConstruire(1, "PROP swxcwxcwxcwxcwxcwxcwxcxwcwxc1", 50, 20, prix, 50, j, bleuFonce);
        ProprieteAConstruire prop2 = new ProprieteAConstruire(1, "PROP 2", 50, 20, prix, 10, j, bleuFonce);
        ArrayList<ProprieteAConstruire> props = new ArrayList<>();
        props.add(prop);
        props.add(prop2);
        IhmPropriete ihmPropriete2 = new IhmPropriete(j, props);
    // TODO code application logic here*/    }
    
}
