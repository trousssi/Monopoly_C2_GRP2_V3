/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import static Ui.Controleur.RANDOM;
import java.awt.BorderLayout;
import java.util.HashSet;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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
    private final int HAUTEUR_PAR_JOUEUR = 70;
    private JButton lancerDes;
    private JPanel panelDes;
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
        
        JPanel panelNom = new JPanel();
        this.panelDes = new JPanel();
        this.add(panelNom, BorderLayout.WEST);
        this.add(panelDes, BorderLayout.EAST);

        panelNom.setLayout(new GridLayout(joueurs.size()+1, 1));
        panelDes.setLayout(new GridLayout(joueurs.size()+1, 1));

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
                //afficherResultatsDes();
               // for (int i=0; i<joueurs.size(); i++) {
                    /**
                     * AFFICHER LES DES ICI.
                     */
                    //panelDes.add(new JButton("lol"));
                    //System.out.println("lol");
               // }   
                
                lancerDes.setEnabled(false); //Une fois les résultats obtenus on ne peut plus les lancer
                
            }
        });
    }

    private void lancerDes() {
        
        
        
        //while (!determine) {
            int de = 0;
            for(String nomJ : joueurs) {
                int de1 = RANDOM.nextInt(6)+1;
                int de2 = RANDOM.nextInt(6)+1;
                int deTemp = de1+de2;
                if (deTemp > de) de = deTemp;
                int [] des= {de1, de2};
                this.resLancerDes.put(nomJ, des);
            }
            int nbMax = 0;
            for(String nomJ : joueurs) {
                int[] resdes = this.resLancerDes.get(nomJ);
                int sommeDes = resdes[0] + resdes[1];
                if (sommeDes == de) nbMax++;
            }
            
            if (nbMax >= 2) {
                
                this.lancerDes();
            }
        //}
    }
    
    private void afficheLancer() {
        for (String NomJ : joueurs) {
            int[] res = this.resLancerDes.get(NomJ);
            int de1 = res[0];
            int de2 = res[1];
            System.out.println(NomJ + " :" + de1 + " " + de2);
            JPanel panelDesJoueur = new JPanel();
            JPanel panelDe1 = new JPanel();
            JPanel panelDe2 = new JPanel();
            this.panelDes.add(panelDesJoueur);
            panelDesJoueur.add(panelDe1);
            panelDesJoueur.add(panelDe2);
            
            panelDe1.add(new JLabel("" + de1));
            panelDe2.add(new JLabel("" + de2));
        }
        
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
    
    
    
}
