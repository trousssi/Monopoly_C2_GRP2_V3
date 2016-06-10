package Jeu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Monopoly {
    private HashMap<Integer, Carreau> carreaux = new HashMap<>();
    private HashMap<String, Groupe> groupes = new HashMap<>();
    private HashMap<Integer, Carte> cartesChance = new HashMap<>();
    private HashMap<Integer, Carte> cartesCommu = new HashMap<>();
    private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    private int Maison;
    private int Hotel;
    
    
    public Monopoly() {
        this.buildGamePlateau("src/Data/data.txt");
        this.buildCartes("src/Data/cartes.txt");
        this.Maison = 32;
        this.Hotel = 12;
    }
    
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public void CreerPlateau(String dataFilename) {
        buildGamePlateau(dataFilename);
    }

        

    public Carreau getCarreau(int numCarreau) {
        numCarreau = numCarreau % 40;
        if (numCarreau == 0) {
            numCarreau = 40;
        }
        Carreau car = this.carreaux.get(numCarreau);
        return car;
    }

    public void addJoueur (Joueur joueur) {
        joueurs.add(joueur);
    }
    
    public void removeJoueur(Joueur joueur) {
        joueurs.remove(joueur);
        joueur = null;
    }

    public ArrayList<Carte> pickCartes () {
        boolean possede = true;
        Carte chance = null;
        Carte communaute = null;
        while (possede) {
            chance = cartesChance.get((int) (Math.random()*16));
            communaute = cartesCommu.get((int) ((Math.random()*16)));
            possede = chance.isPossede();
            if (!possede) possede = communaute.isPossede();
        }
        ArrayList<Carte> cartes = new ArrayList<>();
        cartes.add(chance);
        cartes.add(communaute);
        return cartes;
    }
    private void buildGamePlateau(String dataFilename)
    {
        // Création des groupes de propriétés.
        Groupe bleuFonce = new Groupe(CouleurPropriete.bleuFonce);
        groupes.put(CouleurPropriete.bleuFonce.toString(), bleuFonce);
        Groupe orange = new Groupe(CouleurPropriete.orange);
        groupes.put(CouleurPropriete.orange.toString(), orange);
        Groupe mauve = new Groupe(CouleurPropriete.mauve);
        groupes.put(CouleurPropriete.mauve.toString(), mauve);
        Groupe violet = new Groupe(CouleurPropriete.violet);
        groupes.put(CouleurPropriete.violet.toString(), violet);
        Groupe bleuCiel = new Groupe(CouleurPropriete.bleuCiel);
        groupes.put(CouleurPropriete.bleuCiel.toString(), bleuCiel);
        Groupe jaune = new Groupe(CouleurPropriete.jaune);
        groupes.put(CouleurPropriete.jaune.toString(), jaune);
        Groupe vert = new Groupe(CouleurPropriete.vert);
        groupes.put(CouleurPropriete.vert.toString(), vert);
        Groupe rouge = new Groupe(CouleurPropriete.rouge);
        groupes.put(CouleurPropriete.rouge.toString(), rouge);

        
        try{ // Lecture du fichier "src/Data/data.txt"
            ArrayList<String[]> data = readDataFile(dataFilename, ","); // data contient toutes les chaines de caracteres entre les virgules d'une ligne.

            for(int i=0; i<data.size(); ++i){
                String caseType = data.get(i)[0]; // Lit le type de la case (1ère chaine de caractere de la ligne) et le stocke dans caseType
                if(caseType.compareTo("P") == 0){ // Si la case est une Propriété
                    //System.out.println("Propriété :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    ArrayList<Integer> loyer = new ArrayList<>();
                    for (int n = 6; n<11; n++) {
                        loyer.add(Integer.parseInt(data.get(i)[n]));
                    }
                    ProprieteAConstruire prop = new ProprieteAConstruire(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Integer.parseInt(data.get(i)[4]), Integer.parseInt(data.get(i)[5]), loyer, Integer.parseInt(data.get(i)[11]), null, groupes.get(data.get(i)[3]));
                    carreaux.put(Integer.parseInt(data.get(i)[1]), prop);
                    groupes.get(data.get(i)[3]).addPropriete(prop);

                }
                else if(caseType.compareTo("G") == 0){ // Si la case est une Gare
                    //System.out.println("Gare :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    Gare gare = new Gare(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Integer.parseInt(data.get(i)[3]), 0, null);
                    carreaux.put(Integer.parseInt(data.get(i)[1]), gare);
                }
                else if(caseType.compareTo("C") == 0){ // Si la case est une Compagnie
                    //System.out.println("Compagnie :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    Compagnie compagnie = new Compagnie(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Integer.parseInt(data.get(i)[3]), 0, null);
                    carreaux.put(Integer.parseInt(data.get(i)[1]), compagnie);
                }
                else if(caseType.compareTo("SA") == 0){ // Si la case est un Autre Carreau
                    //System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    CarreauSansAction carreauSansAction = new CarreauSansAction(Integer.parseInt(data.get(i)[1]), data.get(i)[2]);
                    carreaux.put(Integer.parseInt(data.get(i)[1]), carreauSansAction);
                }
                else if(caseType.compareTo("CA") == 0){ // Si la case est un Autre Carreau
                    //System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    CarreauCarte carte = new CarreauCarte(Integer.parseInt(data.get(i)[1]), data.get(i)[2]);
                    carreaux.put(Integer.parseInt(data.get(i)[1]), carte);
                }
                else if(caseType.compareTo("CI") == 0){ // Si la case est un Autre Carreau
                    //System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    CarreauImpot carreauImpot = new CarreauImpot(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Math.abs(Integer.parseInt(data.get(i)[3])));
                    carreaux.put(Integer.parseInt(data.get(i)[1]), carreauImpot);
                }
                else if(caseType.compareTo("PR") == 0){ // Si la case est un Autre Carreau
                    //System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                    CarreauPrison carreauPrison = new CarreauPrison(Integer.parseInt(data.get(i)[1]), data.get(i)[2]);
                    carreaux.put(Integer.parseInt(data.get(i)[1]), carreauPrison);
                }
                else { // S'il y a une erreur de lecture dans le data.
                    //System.err.println("[buildGamePleateau()] : Invalid Data type");
                }

            }

        } 
        catch(FileNotFoundException e){
            System.err.println("[buildGamePlateau()] : File is not found!");
        }
        catch(IOException e){
            System.err.println("[buildGamePlateau()] : Error while reading file!");
        }
    }

    
    private void buildCartes(String dataFilename)
    {
            try{
                    ArrayList<String[]> data = readDataFile(dataFilename, ",");

                    //TODO: create cases instead of displaying
                    for(int i=0; i<data.size()/2; ++i){
                            String caseType = data.get(i)[0];
                            if(caseType.compareTo("LI") == 0){
                                CarteLiberePrison lib = new CarteLiberePrison(data.get(i)[1]);
                                    cartesChance.put(i, lib);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("DE") == 0){
                                if (Integer.parseInt(data.get(i)[3]) == -3) {
                                    CarreauSansAction car = new CarreauSansAction(-3, data.get(i)[1]);
                                    CarteDeplacement dep = new CarteDeplacement(data.get(i)[1], car);
                                }
                                CarteDeplacement dep = new CarteDeplacement(data.get(i)[1], carreaux.get(Integer.parseInt(data.get(i)[3])));
                                    cartesChance.put(i, dep);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("RE") == 0){
                                int prixMaison = 0;
                                int prixHotel = 0;
                                if (Integer.parseInt(data.get(i)[2]) == 1 ) {
                                    prixMaison = 40;
                                    prixHotel = 115;
                                }
                                CarteReparation rep = new CarteReparation(data.get(i)[1], prixMaison, prixHotel);
                                cartesChance.put(i, rep);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("AR") == 0){
                                    CarteArgent ar = new CarteArgent(data.get(i)[1], Integer.parseInt(data.get(i)[2]));
                                    cartesChance.put(i, ar);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("PR") == 0){
                                    CartePrison pr = new CartePrison(data.get(i)[1]);
                                    cartesChance.put(i, pr);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else
                                    System.err.println("[buildGamePleateau()] : Invalid Data type");
                    }
                    for(int i=16; i<data.size(); ++i){
                            String caseType = data.get(i)[0];
                            if(caseType.compareTo("LI") == 0){
                                CarteLiberePrison lib = new CarteLiberePrison(data.get(i)[1]);
                                    cartesCommu.put(i-16, lib);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("DE") == 0){
                                CarteDeplacement dep = new CarteDeplacement(data.get(i)[1], carreaux.get(Integer.parseInt(data.get(i)[3])));
                                    cartesCommu.put(i-16, dep);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("RE") == 0){
                                int prixMaison = 0;
                                int prixHotel = 0;
                                if (Integer.parseInt(data.get(i)[2]) == 1 ) {
                                    prixMaison = 40;
                                    prixHotel = 115;
                                }
                                CarteReparation rep = new CarteReparation(data.get(i)[1], prixMaison, prixHotel);
                                cartesCommu.put(i-16, rep);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("AR") == 0){
                                    CarteArgent ar = new CarteArgent(data.get(i)[1], Integer.parseInt(data.get(i)[2]));
                                    cartesCommu.put(i-16, ar);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
                            }
                            else if(caseType.compareTo("PR") == 0){
                                    CartePrison pr = new CartePrison(data.get(i)[1]);
                                    cartesCommu.put(i-16, pr);
                                    //System.out.println(data.get(i)[1] + " prix " + data.get(i)[2] + " déplacement " + Integer.parseInt(data.get(i)[3]));
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

    private ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
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
    
     public boolean resteMaison() {
        return !(Maison == 0);
    }

     public boolean resteHotel() {
        return !(Hotel == 0);
        
    }

    public void removeMaison() {
        this.Maison--;
    }
    
    public void addMaison(int nb) {
        this.Maison = this.Maison + nb;
    }

    public void removeHotel() {
       this.Hotel--;
    }
    
    public void addHotel() {
        this.Hotel++;
    }

}
