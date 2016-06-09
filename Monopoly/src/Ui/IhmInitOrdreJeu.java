/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.awt.BorderLayout;
import java.util.HashSet;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author vivierlo
 */
public class IhmInitOrdreJeu extends JFrame {
    private Observateur observateur;
    private HashSet<String> joueurs;
    private final int LONGUEUR_BASE = 400;
    private final int HAUTEUR_PAR_JOUEUR = 150;
    private JButton lancerDes;
    private JPanel panelDes;
    private JPanel panelNom;
    private HashMap<String, int[]> resLancerDes = new HashMap<>(); 
    public static final Random RANDOM = new Random();
    
    
    public IhmInitOrdreJeu(HashSet<String> joueurs) {
        super();
        this.setJoueurs(joueurs);
        initUIComponents();
    }
    
    private void initUIComponents() {
        this.setSize(LONGUEUR_BASE,HAUTEUR_PAR_JOUEUR*joueurs.size());
        this.setLayout(new BorderLayout());
        lancerDes = new JButton("Lancer les dés");
        
        this.panelNom = new JPanel();
        this.panelDes = new JPanel();
        this.add(panelNom, BorderLayout.WEST);
        this.add(panelDes, BorderLayout.EAST);

        panelNom.setLayout(new GridLayout(joueurs.size()+2, 1));
        panelDes.setLayout(new GridLayout(joueurs.size()+2, 1));

        //En-tête
        panelNom.add(new JLabel("Nom des Joueurs:"));
        panelDes.add(lancerDes);
        
        for (String nomJoueur : joueurs) {
            panelNom.add(new JLabel(nomJoueur));
        }
        
        lancerDes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerDes();
                afficheLancer();
                lancerDes.setEnabled(false); //Une fois les résultats obtenus on ne peut plus les lancer
            }
        });
    }

    private void lancerDes() {
        int sommeMaxDes = 0;
        for(String nomJ : joueurs) {
            int de1 = RANDOM.nextInt(6)+1;
            int de2 = RANDOM.nextInt(6)+1;
            if (de1+de2 > sommeMaxDes) sommeMaxDes = de1+de2;
            int [] des= {de1, de2};
            this.resLancerDes.put(nomJ, des);
        }
        int nbMax = 0;
        for(String nomJ : joueurs) {
            int[] resdes = this.resLancerDes.get(nomJ);
            int sommeDes = resdes[0] + resdes[1];
            if (sommeDes == sommeMaxDes) nbMax++;
        }

        if (nbMax >= 2) {

            this.lancerDes();
        }
    }
    
    private void afficheLancer() {
        for (String nomJ : joueurs) {
            int[] res = this.resLancerDes.get(nomJ);
            
            JPanel panelDesJoueur = new JPanel();
            JPanel panelDe1 = new JPanel();
            JPanel panelDe2 = new JPanel();
            this.panelDes.add(panelDesJoueur);
            panelDesJoueur.add(panelDe1);
            panelDesJoueur.add(panelDe2);
            
            panelDe1.add(new JLabel(new ImageIcon("src/Data/"+res[0]+".png")));
            panelDe2.add(new JLabel(new ImageIcon("src/Data/"+res[1]+".png")));
        }
        
        this.panelNom.add(new JLabel(this.getNomPremier() + " commencera la partie."));
        JButton jouer = new JButton("Lancer le jeu");
        this.panelDes.add(jouer);
        
        jouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observateur.lancerJeu();
            }
        });
        
        this.setVisible(true);
    }
    public void afficher() {
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Détermination de l'ordre de jeu");
        
        
    }

    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    public void setJoueurs(HashSet<String> joueurs) {
        this.joueurs = joueurs;
    }
    
    public String getNomPremier() {
        int max = 0;
        String nomPremier = "";
        for (String nomJ : resLancerDes.keySet()) {            
            if (resLancerDes.get(nomJ)[0] + resLancerDes.get(nomJ)[1] > max) {
                max = resLancerDes.get(nomJ)[0] + resLancerDes.get(nomJ)[1];
                nomPremier = nomJ;
            }
        }
        
        return nomPremier;
    }
    
}
