package Données;

import java.io.File;
import java.util.ArrayList;

/**
 * Représente la liste des niveaux du jeu. La liste pouvant varier suivant le répertoire utilisé.
 * @author Genest
 */
public class ListeNiveaux
{
    private ArrayList<Grille> grilles;
    
    /**
     * Créee un tableau de Grille de toutes les grilles du répertoire voulu.
     */
    public ListeNiveaux(boolean repBase)
    {
        File dosNiveaux;
        
        if(repBase)
            dosNiveaux = new File("niveaux");//Lecture du répertoire de base
        else
            dosNiveaux = new File("persos");//Lecture du répertoire personnalisé

        //Si le dossier nexiste pas, on le crée
        if(!dosNiveaux.isDirectory())
            dosNiveaux.mkdir();
        
        grilles = new ArrayList();
        Grille[] tmp = new Grille[dosNiveaux.listFiles().length];

        for(int i = 0; i < dosNiveaux.listFiles().length; i++)
        {
            try
            {
                //On ajoute la grille seulement si le fichier est correcte
                tmp[i] = new Grille(dosNiveaux.listFiles()[i]);
                grilles.add(tmp[i]);
                
            } catch (GrilleException ex) {}
        }
        
    }
    
    /**
     * Retourne un tableau de Grille des grilles contenus dans le répertoire.
     * @return Un tableau de Grille.
     */
    public ArrayList<Grille> getGrilles()
    {
        return grilles;
    }
}
