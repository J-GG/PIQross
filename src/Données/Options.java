package Données;

import java.io.*;

/**
 * Gère les options du jeu. Implémente le DP singleton.
 * @author Genest
 */
public class Options 
{
    /**
     * Unique instance d'Options dans le programme.
     */
    private static Options instance = null;

    /**
     * Enumération représentant le mode de difficulté du jeu.
     */
    public enum Difficulte{FACILE, MOYEN, DIFFICILE};
    
    /**
     * Enumération indiquant si le son est activé ou désactive.
     */
    public enum Son{ACTIVE, DESACTIVE};
    
    private File ficOptions;
    private Difficulte difficulte;
    private Son son;
    
    /**
     * Créée une classe gérant les options du jeu
     */
    private Options()
    {
        ficOptions = new File("options.txt");
        
        verifFichier();
        
        lectureFic();
        
    }
    
    /**
     * Permet de récupérer l'instance d'Options.
     * @return L'unique instance d'Options.
     */
    public static Options get()
    {
        if(instance == null)
            instance = new Options();
        
        return instance;
    }
    
    /**
     * Récupération des informations inscrites dans le fichier.
     */
    private void lectureFic()
    {
        BufferedReader br = null;
        
        try {
            br = new BufferedReader (new FileReader (ficOptions));
            
            try {
                String str =  br.readLine();
                if(str.equals("DIFFICILE"))
                    difficulte = Difficulte.DIFFICILE;
                else if(str.equals("MOYEN"))
                    difficulte = Difficulte.MOYEN;
                else
                    difficulte = Difficulte.FACILE;
 
                str =  br.readLine();
                if(str.equals("DESACTIVE"))
                    son = Son.DESACTIVE;
                else
                    son = Son.ACTIVE;
                
                br.close();
                
            } catch (IOException ex) {}
           
        } catch (FileNotFoundException ex) {}
    }
    
    
    /**
     * Sauvegarde les données d'options dans le fichier.
     */
    public void enregistrerOptions()
    {
        PrintWriter pw = null;
        
        try {
            pw =  new PrintWriter(new BufferedWriter(new FileWriter(ficOptions)));
            
            pw.println(difficulte);
            pw.println(son);
            
           
            pw.close();
        } catch (IOException ex) {}
        
    }
            
    /**
     * Retourne la difficulté du jeu.
     * @return La difficulté du jeu.
     */
    public Difficulte getDifficulte()
    {
        return difficulte;
    }
    
    /**
     * Retourne l'information sur le son.
     * @return Si le son est activé ou pas.
     */
    public Son getSon()
    {
        return son;
    }

    /**
     * Modifie la valeur de difficulte.
     * @param difficulte La nouvelle valeur de difficulte.
     */
    public void setDifficulte(Difficulte difficulte) {
        this.difficulte = difficulte;
    }
    
    /**
     * Modifie la valeur de son.
     * @param son La nouvelle valeur de son.
     */
    public void setSon(Son son) {
        this.son = son;
    }
    

    /**
     * Vérifie si le fichier est correct.
     */
    private void verifFichier()
    {
        boolean fichierOK = true;
        
        //Création du fichier si il n'existe pas. On peut sauter la vérification dans ce cas puisqu'on va enregistrer des valeurs par défaut
        if(!ficOptions.isFile())
        {
            try {
                ficOptions.createNewFile();
                fichierOK = false;
        
            } catch (IOException ex) {}
        }
        else
        {
            BufferedReader br = null;
            
            try {
                br = new BufferedReader (new FileReader (ficOptions));
                
                try {
                    String str =  br.readLine();
                    if(str == null || !str.equals("DIFFICILE") && !str.equals("FACILE") && !str.equals("MOYEN"))
                        fichierOK = false;
                    
                    str = br.readLine();
                    if(str == null || !str.equals("DESACTIVE") && !str.equals("ACTIVE"))
                        fichierOK = false;
                    
                    br.close();
                    
                } catch (IOException ex) {}
                
            } catch (FileNotFoundException ex) {}
            
        }
        
        
        //Si les données sont erronnées, on refait un fichier avec des valeurs par défaut
        if(!fichierOK)
        {
            difficulte = Difficulte.FACILE;
            son = Son.ACTIVE;
            enregistrerOptions();
        }
    }
}
