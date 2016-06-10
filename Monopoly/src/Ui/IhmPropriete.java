/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import Jeu.ProprieteAConstruire;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jérémy
 */
public class IhmPropriete  extends JFrame{
    private ArrayList<ProprieteAConstruire> proprietes;
    private Joueur j;
    private Observateur observateur;
    
    public IhmPropriete(Joueur j, ArrayList<ProprieteAConstruire> proprietes) {
        super();
        this.proprietes = proprietes;
        this.j = j;
        if (proprietes != null) {
            initUIComponents(proprietes);
            afficher();
        }
    }

    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    
    private void initUIComponents(ArrayList<ProprieteAConstruire> p) {
        this.setLayout(new GridLayout(p.size(),1));
        for (int i=0; i<p.size(); i++) {
            JPanel propriete = new JPanel();
            this.add(propriete);
            propriete.setLayout(new BorderLayout());
            JPanel couleur = new JPanel();
            propriete.add(couleur, BorderLayout.WEST);
            couleur.setBackground(Color.red);
            JPanel nom = new JPanel();
            propriete.add(nom, BorderLayout.CENTER);
            JLabel nomProp = new JLabel(p.get(i).getNomCarreau());
            nom.add(nomProp);
            JPanel prix = new JPanel();
            propriete.add(prix, BorderLayout.EAST);
            JLabel prixProp = new JLabel(Integer.toString(p.get(i).getPrixMaison()));
            nom.add(prixProp);
            propriete.setName(p.get(i).getNomCarreau());
            
            
            propriete.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String nomPropriete = propriete.getName();
                    for (ProprieteAConstruire p : proprietes) {
                        if (nomPropriete.equals(p.getNomCarreau())) {
                            //observateur.construire(p, j);
                            System.out.println("PROP CONSTRUITE" + p.getNomCarreau());
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
        }
    }
         
    public void afficher() {
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);                     

    }
}
