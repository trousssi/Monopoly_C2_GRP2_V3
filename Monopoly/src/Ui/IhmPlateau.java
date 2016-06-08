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
    private int nbJoueurs;
    private int x, y;
    
    public IhmPlateau() throws IOException  {
        super();
        pions = new ArrayList<>();
        pions.add(ImageIO.read(new File("src/Data/pionRouge.png")));
        pions.add(ImageIO.read(new File("src/Data/pionBleu.png")));
        pions.add(ImageIO.read(new File("src/Data/pionVert.png")));
        pions.add(ImageIO.read(new File("src/Data/pionJaune.png")));
        pions.add(ImageIO.read(new File("src/Data/pionViolet.png")));
        pions.add(ImageIO.read(new File("src/Data/pionOrange.png")));

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
        //x =150; y =150;
        x = 712 - (74*2);
            y = 840;
        int i=0;
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
        
        while (i<6) {
            g.drawImage(pions.get(i), x, y, (ImageObserver) observateur);
            i++;y+=13;
        }   
    }
    public void dessiner(int numCarreau, int nbJoueurs) {
           this.numCarreau = numCarreau;
           this.nbJoueurs = nbJoueurs;
           repaint();
    }
    public void trouveCoordonnes(int numCarreau) {
        
        
        if (numCarreau <= 11) {
            x = 712 - (74*numCarreau);
            y = 840;
        } 
    }
}
