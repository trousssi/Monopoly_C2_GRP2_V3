package Jeu;

// Liste des couleurs des groupes de propriétés.

import java.awt.Color;

public enum CouleurPropriete {
    bleuFonce(new Color(0,107,177)), 
    orange(new Color(237,139,2)), 
    marron(new Color(154,74,44)), 
    violet(new Color(215,46,137)), 
    bleuCiel(new Color(187,230,247)), 
    jaune(new Color(254,234,16)), 
    vert(new Color(28,169,77)), 
    rouge(new Color(226,0,13));
    
    private Color color;
    
    CouleurPropriete(Color color) {
        this.color = color;
    }
    
    public Color getColor () {
        return color;
    }
}