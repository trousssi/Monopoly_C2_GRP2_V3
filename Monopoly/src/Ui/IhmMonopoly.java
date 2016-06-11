/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jeremy
 */
public class IhmMonopoly extends JPanel{
    private Joueur joueur;

    public IhmMonopoly(Joueur joueur) {
        this.joueur = joueur;
        initUIComponents();
    }
    
    private void initUIComponents () {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new GridLayout(5, 1));
        JLabel nom = new JLabel(joueur.getNom());
        this.add(nom);
        JLabel cash = new JLabel(Integer.toString(joueur.getCash()));
        this.add(cash);
        JLabel position = new JLabel(joueur.getPositionCourante().getNomCarreau());
        this.add(position);
        JPanel panelPrison = new JPanel();
        panelPrison.setLayout(new GridLayout(1,2));
        this.add(panelPrison);
        JLabel prison = new JLabel(joueurEnPrison());
        panelPrison.add(prison);
        JLabel cartePrison = new JLabel("Cartes sortie de prison" + Integer.toString(joueur.getCartesPrison().size()));
        panelPrison.add(cartePrison);
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new GridLayout(1,3));
        this.add(panelBoutons);
        JButton boutonGares =  new JButton("Afficher Gares");
        JButton boutonCompanies =  new JButton("Afficher Companies");
        JButton boutonProp =  new JButton("Afficher Propriétés");
        panelBoutons.add(boutonGares);
        panelBoutons.add(boutonCompanies);
        panelBoutons.add(boutonProp);
        
        
    }
        
    
    private String joueurEnPrison () {
        if (joueur.getToursEnPrison() != -1) {
            return "En prison";
        }
        return "";
    }
    
    
}
