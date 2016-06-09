/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author fallm
 */
public class IhmPlateau extends Canvas{
    private BufferedImage fondPlateau;
    Observateur observateur;
    private ArrayList<BufferedImage> pions;
    private int numCarreau;
    private int nbJoueurs = 6;//L'ajouter comme paramètre 
    private ArrayList<int[]> pos;
     //1ère dimension = x, 2ème = y, 3ème = numJoueur
    
    public IhmPlateau()   {
        super();
        pions = new ArrayList<>();
        pos = new ArrayList<>();
        
        
        this.initListePositions();
        
        try {
            pions.add(ImageIO.read(new File("src/Data/pionRouge.png")));
            pions.add(ImageIO.read(new File("src/Data/pionBleu.png")));
            pions.add(ImageIO.read(new File("src/Data/pionBleu.png")));
            pions.add(ImageIO.read(new File("src/Data/pionVert.png")));
            pions.add(ImageIO.read(new File("src/Data/pionJaune.png")));
            pions.add(ImageIO.read(new File("src/Data/pionViolet.png")));
            pions.add(ImageIO.read(new File("src/Data/pionOrange.png")));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
     /**
     * Dessine le contenu du canvas, c'est-à-dire l'icone
     * @param g un contexte graphique
     */
    @Override
    public void paint(Graphics g) {
        Dimension d = new Dimension(900, 900);
        super.setSize(d);
        super.paint(g);
        
        try {
            //PION DE 21x26 espacés de 2 pixels entre eux + y += 13px  pour superposer
            if (true) { //INTEGRER LA CONTRAINTE DU NOMBRE DE JOUEURS
                
            }
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg")); 
        } 
        // Ne pas considérer ces lignes
        catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Affichage sur le Canvas
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        
        int i=0;
        while (i<nbJoueurs) {
            System.out.println("x = " + this.pos.get(0)[0] + "y = " + this.pos.get(0)[1] + i);
            g.drawImage(pions.get(i), this.pos.get(i)[0], this.pos.get(i)[1], (ImageObserver) observateur);
            i++;this.pos.get(i)[1]+=13;
        }   
    }//Coordonnés du premier carreau x, y = 786
    
    private void initListePositions() {
        
        int[] posInit = {786, 786};
        for(int i = 0; i<nbJoueurs; i++) {
            pos.add(posInit);
        }
        System.out.println("nbJoueurs = " + nbJoueurs + "size = " + pos.size());
    }

    
    
    public void trouveCoordonnes(int numCarreau, int numJoueur) {
        this.numCarreau = numCarreau;
        
        
        if (numCarreau <= 11) {
            this.pos.get(numJoueur)[0] = 786 - (74*numCarreau);
            this.pos.get(numJoueur)[1] = 786;
        }
        
        repaint();
    }
}
