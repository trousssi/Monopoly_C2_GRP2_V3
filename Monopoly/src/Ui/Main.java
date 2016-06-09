/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.io.IOException;

/**
 *
 * @author trousssi
 */
public class Main {
    
    
    public static void main(String[] args) {
        Controleur controleur = new Controleur();  
        IHM ihm = new IHM(controleur);
    }
}
