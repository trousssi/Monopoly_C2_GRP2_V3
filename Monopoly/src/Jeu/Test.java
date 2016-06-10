/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import Ui.IhmJeu;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author fallm
 */
public class Test {
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
        buildCartes("src/Data/cartes.txt");
        //buildGamePlateau("src/Data/data.txt");
        //IhmJeu jeu = new IhmJeu();
        
    }
    
    private static void buildCartes(String dataFilename)
	{
		try{
			ArrayList<String[]> data = readDataFile(dataFilename, ",");
			
			//TODO: create cases instead of displaying
			for(int i=0; i<data.size()/2; ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("LI") == 0){
                                        //Carte li = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("DE") == 0){
                                        //Carte de = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("RE") == 0){
                                        //Carte re = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("AR") == 0){
                                        //Carte ar = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("PR") == 0){
                                        //Carte pr = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else
					System.err.println("[buildGamePleateau()] : Invalid Data type");
			}
			for(int i=16; i<data.size(); ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("LI") == 0){
                                        //Carte li = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("DE") == 0){
                                        //Carte de = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("RE") == 0){
                                        //Carte re = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("AR") == 0){
                                        //Carte ar = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else if(caseType.compareTo("PR") == 0){
                                        //Carte pr = new Carte(data.get(i)[0], data.get(i)[1], Integer.parseInt(data.get(i)[2]), Integer.parseInt(data.get(i)[3]));
					System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
				}
				else
					System.err.println("[buildGamePleateau()] : Invalid Data type");
			}
                        
			
		} 
		catch(FileNotFoundException e){
			System.err.println("[buildGamePlateau()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[buildGamePlateau()] : Error while reading file!");
		}
	}
	
	private static ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		BufferedReader reader  = new BufferedReader(new FileReader(filename));
		String line = null;
		while((line = reader.readLine()) != null){
			data.add(line.split(token));
		}
		reader.close();
		
		return data;
	}
}
