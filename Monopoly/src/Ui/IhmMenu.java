/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author vivierlo
 */
public class IhmMenu extends JFrame  {
    private Observateur observateur;
    
    private IhmJeu ihmJeu;
    private IhmInscription ihmI;
        
    public IhmMenu() {
        super();

        initUIComponents();
    }
    
    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }
    
    /**
     * Rend visible la fenetre
     */
    public void afficher() { 
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setVisible(true);                     
    }  
    
    private void initUIComponents() {
        
       
        Color fond = new Color(206,239,203);
        this.getContentPane().setBackground(fond);
        
        this.setLayout(new BorderLayout());
        JButton jouer = new JButton("JOUER");
        JButton inscrire = new JButton("Inscrire les Joueurs");
        JButton quitter = new JButton("Quitter le Jeu");
        
        JLabel logo = new JLabel(new ImageIcon("src/Data/logoMonopoly.jpg"));
        JPanel panelBoutons = new JPanel();
        this.add(panelBoutons, BorderLayout.CENTER);
        
        JPanel panelSud = new JPanel();
        this.add(panelSud, BorderLayout.SOUTH);
        panelSud.setBackground(fond);
        
        panelBoutons.setBackground(fond);
        
        //Espace entre Boutons
        JPanel vide1 = new JPanel(); vide1.setPreferredSize(new Dimension(30,0)); vide1.setBackground(fond);
        JPanel vide2 = new JPanel(); vide2.setPreferredSize(new Dimension(30,0)); vide2.setBackground(fond);

        panelBoutons.add(quitter);panelBoutons.add(vide1);
        panelBoutons.add(jouer);panelBoutons.add(vide2);
        panelBoutons.add(inscrire);
        jouer.setEnabled(false);
        
        
        
        this.add(logo, BorderLayout.NORTH);
        
        jouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ihmJeu = new IhmJeu();
                } catch (IOException ex) {
                    Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
                }
                        
            }
        });
        inscrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscriptionJoueurs(); 
                for ( String i : ihmI.getNomJoueurs()) {
                    System.out.println(i);
                }
                if (!ihmI.getNomJoueurs().isEmpty()) { //Si les joueurs sont bien inscrits, on peut jouer
                    jouer.setEnabled(true);
                    //System.out.println("ok");
                }
            }

        });
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res = IhmBoiteMessage.afficherBoiteDialogue("Etes vous sûr de vouloir quitter?", "ouinon");
                if (res) { 
                    quitter();
                }
            }
        });
        
        this.setVisible(true);

           
    } 
    private void quitter() {
        IhmBoiteMessage.afficherBoiteDialogue("A bientôt", "info");
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));//Permet de fermer la fenêtre
    }
    
    public void inscriptionJoueurs () {
        this.ihmI = new IhmInscription();
        ihmI.afficher();
    }
}
