package Données;

import Données.Case.Couleur;
import Données.Case.Etat;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * Représente une grille de Picross au niveau données.
 * @author Genest
 */
public class Grille implements GrilleObservable
{

    /**
     * Enumération représentant les états et couleurs que peut prendre une case.
     */
    private String nomGrille;
    private int nbCases;
    
    /**
     * Contient la grille complétée.
     */
    private Case[][] image;
    
    /**
     * Contient la grille remplie par le joueur.
     */
    private Case[][] plateau;
    
     /**
     * Tableau d'ArrayList à deux dimensions contenant la valeur des indices des colonnes et leurs couleurs. Le premier tableau correspond à la colonne et le second, à la valeur ou à la couleur. Ex : indicesColonnes[1][1].get(0) permet de récupérer la couleur du premier indice de la 2ème colonne.
     */
    private ArrayList[][] indicesColonnes;
    
    /**
     * Tableau d'ArrayList à deux dimensions contenant la valeur des indices des lignes et leurs couleurs. Le premier tableau correspond à la ligne et le second, à la valeur ou à la couleur. Ex : indicesLignes[1][1].get(0) permet de récupérer la couleur du premier indice de la 2ème ligne.
     */
    private ArrayList[][] indicesLignes;
    
    /**
     * Indique si les colonnes sont complètes.
     */
    private boolean[] indicesCompletColonnes;
    
    /**
     * Indique si les lignes sont complètes.
     */
    private boolean[] indicesCompletLignes;
    
    /**
     * Permet de savoir si toutes les cases de la grille sont noires. Si c'est le cas, on pourra considérer la grille complète.
     */
    private boolean ttCasesNonColoree;
    
    /**
     * LListe des couleurs contenues dans la grille.
     */
    private ArrayList<Couleur> listeCouleurs;
    
    /**
     * Durée moyenne ncessaire pour finir la grille.
     */
    private String dureeMoyenne;
    
    /**
     * Nombre de fois où le joueur s'est trompé.
     */
    private int nbErreurs;
    
    /**
     * Difficulté de la grille. Comprise entre 1 et 5.
     */
    private int difficulte;
    
    private boolean complete;
    private File fic;
    private ArrayList<GrilleObservateur> listeObs;
    

    
    /**
     * Charge la grille contenue dans le fichier passé en paramètre et d'autres informations. Si le fichier est incorrecte, une exception est levée.
     * @param f Fichier contenant la grille à charger.
     */
    public Grille(File f) throws GrilleException
    {
        fic = f;
        

            recupInfosFichier();
            
            init();
            
            calculIndices();

    }
    
    /**
     * Crée une grille vide. Le fichier passé en paramètre ne contient donc pas encore la grille.
     * @param f Fichier qui contiendra la grille.
     * @param nb Nombre de cases que doit faire la grille.
     * @param nom Nom de la grille.
     * @param tps Durée moyenne pour finir la grille.
     */
    public Grille(File f, int nb, String nom, String tps, int dif)
    {
        init();

        nbCases = nb;
        nomGrille = nom;
        fic = f;
        dureeMoyenne = tps;
        difficulte = dif;
        
        image = new Case[nbCases][nbCases];
        plateau = new Case[nbCases][nbCases];
        listeCouleurs = new ArrayList();
        
        //On remplit la grille du joueur par des cases noires ainsi que l'image
        for(int i = 0; i < nbCases; i++)
        {
            for(int j = 0; j < nbCases; j++)
            {
                plateau[i][j] = new Case(Etat.NON_COLORE);
                image[i][j] = new Case(Etat.NON_COLORE);
            }
        }
    }
    
    /**
     * Initialise les variables.
     */
    private void init()
    {
        listeObs = new ArrayList();
        
        complete = false;
        
        indicesColonnes = new ArrayList[nbCases][];
        indicesLignes = new ArrayList[nbCases][];
        for(int i = 0; i < nbCases; i++)
        {
            indicesColonnes[i] = new ArrayList[2];
            indicesColonnes[i][0] = new ArrayList();//Valeurs des incides
            indicesColonnes[i][1] = new ArrayList();//Couleurs des indices
            
            indicesLignes[i] = new ArrayList[2];
            indicesLignes[i][0] = new ArrayList();//Valeurs des incides
            indicesLignes[i][1] = new ArrayList();//Couleurs des indices
        }
        
        indicesCompletColonnes = new boolean[nbCases];
        indicesCompletLignes = new boolean[nbCases];
        ttCasesNonColoree = true;
    }
    
    /**
     * Calcul les indices des lignes et colonnes.
     */
    private void calculIndices()
    {
        //Colonnes
        int cpt = 0;
        int cptVerifNoir = 0;
        for(int i = 0; i < nbCases; i++)
        {            
            for(int j = 0; j < nbCases; j++)
            {
                //Si on est sur une case colorée
                if(image[i][j].getEtat() == Etat.COLORE)
                {
                    cpt++;
                    ttCasesNonColoree = false;
                    
                    //Si la case d'après a une couleur différente, ou si on est à la fin de la rangée ou si la prochaine case n'est pas colorée
                    if(j+1 < nbCases && image[i][j+1].getCouleurGrille() != Couleur.NC && image[i][j+1].getCouleurGrille() != image[i][j].getCouleurGrille() || j+1 == nbCases || image[i][j+1].getEtat() == Etat.NON_COLORE )
                    {
                        indicesColonnes[i][0].add(cpt);
                        indicesColonnes[i][1].add(image[i][j].getCouleurGrilleColor());
                        cpt = 0;
                    }
                }
                else if(image[i][j].getEtat() == Etat.NON_COLORE)
                    cptVerifNoir++;
            }
            
            //On vérifie si la colonne est entièrement noire. Dans ce cas on la considère comme complète
            if(cptVerifNoir == nbCases)
                indicesCompletColonnes[i] = true;
            
            cptVerifNoir = 0;
        }
        
        //Lignes
        cpt = 0;
        for(int j = 0; j < nbCases; j++)
        {            
            for(int i = 0; i < nbCases; i++)
            {
                if(image[i][j].getEtat() == Etat.COLORE)
                {
                    cpt++;
                    ttCasesNonColoree = false;

                    if(i+1 < nbCases && image[i+1][j].getCouleurGrille() != Couleur.NC && image[i+1][j].getCouleurGrille() != image[i][j].getCouleurGrille() || i+1 == nbCases || image[i+1][j].getEtat() == Etat.NON_COLORE)
                    {
                        
                        indicesLignes[j][0].add(cpt);
                        indicesLignes[j][1].add(image[i][j].getCouleurGrilleColor());
                        cpt = 0;
                    }
                }
                else if(image[i][j].getEtat() == Etat.NON_COLORE)
                    cptVerifNoir++;
            }
            
            if(cptVerifNoir == nbCases)
                indicesCompletLignes[j] = true;
            
            cptVerifNoir = 0;
        }
        
        if(ttCasesNonColoree)
        {
            grilleComplete();
            complete = true;
        }
    }
    
    public void enregistrerGrille()
    {
        PrintWriter pw;
        
        if(!fic.isFile())
            try {
            fic.createNewFile();
        } catch (IOException ex) {}
        
        try {
            pw =  new PrintWriter(new BufferedWriter(new FileWriter(fic)));
            pw.println(dureeMoyenne);
            pw.println(difficulte);
             
            for(int j = 0; j < nbCases; j++)
            {
                for(int i = 0; i < nbCases; i++)
                {
                    if(plateau[i][j].getCouleurApercu() != null)
                        pw.print("("+ plateau[i][j].getCouleurApercu().getRed() + "," + plateau[i][j].getCouleurApercu().getGreen() + "," + plateau[i][j].getCouleurApercu().getBlue() + "," + plateau[i][j].getCouleurGrille() + ")");
                    else
                        pw.print("(" + Couleur.NC + ")");
                }
                pw.println();
            }

           
            pw.close();
        } catch (IOException ex) {}
    }
    
    /**
     * Retourne la durée moyenne ncessaire pour finir la grille.
     * @return Le temps moyen de la grille.
     */
    public String getDureeMoyenne()
    {
        return dureeMoyenne;
    }
    
    /**
     * Indique si la grille est complète.
     * @return true si la grille est complète.
     */
    public boolean getComplete()
    {
        return complete;
    }
    
    public int getDifficulte()
    {
        return difficulte;
    }
    
    public File getFichier()
    {
        return fic;
    }
    
    /**
     * Permet de récupérer la grille complète.
     * @return La grille complète.
     */
    public Case[][] getImage()
    {
        return image;
    }
    
    /**
     * Retourne les indices des colonnes.
     * @return Les indices des colonnes.
     */
    public ArrayList[][] getIndicesColonnes()
    {
        return indicesColonnes;
    }
    
    /**
     * Retourne un tableau de booléen indiquant si les colonnes sont complètes.
     * @return Un tableau dans lequel true signifie que la colonne est complète.
     */
    public boolean[] getIndicesCompletColonnes()
    {
        return indicesCompletColonnes;
    }
    
    /**
     * Retourne un tableau de booléen indiquant si les lignes sont complètes.
     * @return Un tableau dans lequel true signifie que la ligne est complète.
     */
    public boolean[] getIndicesCompletLignes()
    {
        return indicesCompletLignes;
    }
    
    /**
     * Retourne les indices des lignes.
     * @return Les indices des lignes.
     */
    public ArrayList[][] getIndicesLignes()
    {
        return indicesLignes;
    }
    
    /**
     * Retourne la liste des couleurs contenues dans la grille.
     * @return La liste des couleurs.
     */
    public ArrayList<Couleur> getListeCouleurs()
    {
        return listeCouleurs;
    }
    
    /**
     * Retourne le plus grand nombre d'indices pour la colonne de la grille en ayant le plus.
     * @return Le nombre maximal d'indices sur les colonnes de la grille.
     */
    public int getMaxIndicesColonnes()
    {
        int maxIndicesColonnes = 1;
        
        for(int i = 0; i < getNbCases(); i++)
        {
            if(maxIndicesColonnes < indicesColonnes[i][0].size())
                maxIndicesColonnes = indicesColonnes[i][0].size();
        }
        
        return maxIndicesColonnes;
    }
    
    /**
     * Retourne le plus grand nombre d'indices pour la ligne de la grille en ayant le plus.
     * @return Le nombre maximal d'indices sur les lignes de la grille.
     */
    public int getMaxIndicesLignes()
    {
        int maxIndicesLignes = 1;
        
        for(int j = 0; j < getNbCases(); j++)
        {
            if(maxIndicesLignes < indicesLignes[j][0].size())
                maxIndicesLignes = indicesLignes[j][0].size();
        }
        
        return maxIndicesLignes;
    }
    
    /**
     * Retourne le nombre de blocs composant la grille dans une dimension.
     * @return Le nombre de blocs.
     */
    public int getNbCases()
    {
        return nbCases;
    }
    
    /**
     * Retourne le nombre d'erreurs du joueur.
     * @return Le nombre d'erreurs.
     */
    public int getNbErreurs()
    {
        return nbErreurs;
    }
    
    /**
     * Retourne le nom de la grille
     * @return Le nom de grille.
     */
    public String getNomGrille()
    {
        return nomGrille;
    }
    
    /**
     * Permet de récupérer le plateau de Case représentant la grille jouée par le joueur.
     * @return La grille jouée par le joueur.
     */
    public Case[][] getPlateau()
    {
        return plateau;
    }
    
    /**
     * Permet de remplir le tableau de Case à partir du fichier ainsi que de récupérer les autres informations de la grille.
     * @throws GrilleException si le fichier est incorrecte.
     */
    private void recupInfosFichier() throws GrilleException
    {      
        BufferedReader br;
        try 
        {
            br = new BufferedReader (new FileReader (fic));
            
            //On récupère le nom de la grille
            nomGrille = fic.getName().substring(0, fic.getName().lastIndexOf("."));

            //On récupère le temps moyen sur la première ligne
            dureeMoyenne = br.readLine();
            difficulte = Integer.valueOf(br.readLine());

            //On compte le nombre de lignes pour connaitre le nombre de cases
            String str = br.readLine();
            int nbLignes = 0;
            while(str != null)
            {
                str = br.readLine();
                nbLignes++;
            }
            br.close();

            nbCases = nbLignes;
            
            image = new Case[nbCases][nbCases];
            plateau = new Case[nbCases][nbCases];
            listeCouleurs = new ArrayList();
            
            br = new BufferedReader (new FileReader (fic));
            br.readLine();//On saute la ligne contenant le temps
            br.readLine();//Et celle de la difficulté
            
            str = br.readLine();
            for(int j = 0; str != null; j++)
            {
                //Découpage de la ligne en groupe de nombres sous le format "R,G,B"
                String[] couleursLigneRGB = str.split("\\(|\\(\\)|\\)");
                int i = 0;
                for(int numLigne = 0; numLigne < couleursLigneRGB.length; numLigne++)
                {
                    String couleurCase = couleursLigneRGB[numLigne];
                    if(!couleurCase.isEmpty())
                    {
                        String rgb[] = couleurCase.split("\\,");

                        if(rgb.length == 1)
                            image[i][j] = new Case(Etat.NON_COLORE);
                        else
                        {
                            Couleur couleurGrille = null;
                            
                            switch (rgb[3]) {
                                case "NC":
                                    couleurGrille = Couleur.NC;
                                    break;
                                case "BLANC":
                                    couleurGrille = Couleur.BLANC;
                                    break;
                                case "BLEU":
                                    couleurGrille = Couleur.BLEU;
                                    break;
                                case "JAUNE":
                                    couleurGrille = Couleur.JAUNE;
                                    break;
                                case "ORANGE":
                                    couleurGrille = Couleur.ORANGE;
                                    break;
                                case "ROUGE":
                                    couleurGrille = Couleur.ROUGE;
                                    break;
                                case "VERT":
                                    couleurGrille = Couleur.VERT;
                                    break;
                                case "ROSE":
                                    couleurGrille = Couleur.ROSE;
                                    break;
                                case "MARRON":
                                    couleurGrille = Couleur.MARRON;
                                    break;
                            }

                            image[i][j] = new Case(Etat.COLORE, couleurGrille, new Color(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2])));
                        }
                        //On ajoute la couleur à la palette si elle n'y est pas déjà
                        if(!listeCouleurs.contains(image[i][j].getCouleurGrille()) && image[i][j].getEtat() != Etat.NON_COLORE)
                            listeCouleurs.add(image[i][j].getCouleurGrille());
                        
                        i++;
                        
                    }
                }
                
                str = br.readLine();
            }
            
            br.close();
            
        } 
        catch (IOException | NumberFormatException ex) {
            throw new GrilleException(fic.getName());
        }
        
        
        //On remplit la grille du joueur par des cases noires
        for(int i = 0; i < nbCases; i++)
        {
            for(int j = 0; j < nbCases; j++)
            {
                plateau[i][j] = new Case(Etat.NON_COLORE);
            }
        }
    }
    
    /**
     * Modifie le nom de la grille.
     * @param nom Le nom de la grille.
     */
    public void setNom(String nom)
    {
        nomGrille = nom;
        fic.delete();
        fic = new File(fic.getParent() + "/" + nomGrille + ".txt");
    }
    
    /**
     * Récupère la colonne et la ligne de la case sélectionnée et modifie la grille du joueur en conséquence.
     * @param colonne Numéro de la colonne de la case jouée.
     * @param ligne Numéro de la ligne de la case jouée.
     * @param clicEffet Permet de savoir si le joueur a voulu marquer la case d'un drapeau ou jouer.
     * @param couleurUtilisee Couleur avec laquelle on a cliqué.
     */
    public void verifClic(int colonne, int ligne, int clicEffet, Couleur couleurUtilisee)
    {
        //Cas où on veut mettre un drapeau
        if(plateau[colonne][ligne].getEtat() == Etat.NON_COLORE && clicEffet == MouseEvent.BUTTON3) 
        {
            plateau[colonne][ligne].setModif(Etat.DRAPEAU);
        }
        //Cas où on veut enlever un drapeau
        else if(plateau[colonne][ligne].getEtat() == Etat.DRAPEAU && clicEffet == MouseEvent.BUTTON3)
        {
            plateau[colonne][ligne].setModif(Etat.NON_COLORE);
        }
        //Cas où la sélection est correcte
        else if(Options.get().getDifficulte() != Options.Difficulte.DIFFICILE && image[colonne][ligne].getCouleurGrille() != Couleur.NC && image[colonne][ligne].getCouleurGrille() == couleurUtilisee && (plateau[colonne][ligne].getEtat() == Etat.NON_COLORE || plateau[colonne][ligne].getEtat() == Etat.DRAPEAU || plateau[colonne][ligne].getEtat() == Etat.MAUVAISE_COULEUR) && clicEffet == MouseEvent.BUTTON1)
        {
            plateau[colonne][ligne].setModif(Etat.COLORE, couleurUtilisee, image[colonne][ligne].getCouleurApercu());

            verifComplet(colonne, ligne);
            
            plateau[colonne][ligne].setModif(Etat.ANIME, couleurUtilisee, image[colonne][ligne].getCouleurApercu());
      
        }
        //Cas d'erreur
        else if(Options.get().getDifficulte() != Options.Difficulte.DIFFICILE && image[colonne][ligne].getEtat() == Etat.NON_COLORE && plateau[colonne][ligne].getEtat() != Etat.FAUX && clicEffet == MouseEvent.BUTTON1)
        {
            nbErreurs++;
            plateau[colonne][ligne].setModif(Etat.FAUX);
            erreurCase();
        }
        //Cas où la couleur est la mauvaise (on recompte faux si on utilise une couleur différente de la dernière fois)
        else if(Options.get().getDifficulte() != Options.Difficulte.DIFFICILE && image[colonne][ligne].getCouleurGrille() != Couleur.NC && image[colonne][ligne].getCouleurGrille() != couleurUtilisee && plateau[colonne][ligne].getEtat() != Etat.COLORE && plateau[colonne][ligne].getCouleurGrille() != couleurUtilisee)
        {
            nbErreurs++;
            plateau[colonne][ligne].setModif(Etat.MAUVAISE_COULEUR, couleurUtilisee, null);
            erreurCouleur();
        }
        //Cas où est en mode difficile : on n'affiche aucune information que la case sot correcte ou non
        else if(Options.get().getDifficulte() == Options.Difficulte.DIFFICILE)
        {
            //Cas d'erreur
            if(image[colonne][ligne].getEtat() == Etat.NON_COLORE && plateau[colonne][ligne].getCouleurGrille() != couleurUtilisee && couleurUtilisee != Couleur.NC && clicEffet == MouseEvent.BUTTON1)
                nbErreurs++;
            //Cas où la couleur est la mauvaise (on recompte faux si on utilise une couleur différente de la dernière fois)
            else if(image[colonne][ligne].getCouleurGrille() != Couleur.NC && image[colonne][ligne].getCouleurGrille() != couleurUtilisee && plateau[colonne][ligne].getCouleurGrille() != couleurUtilisee && couleurUtilisee != Couleur.NC)
                 nbErreurs++;

            
            if(couleurUtilisee == Couleur.NC)//Si on veut effacer une case
                plateau[colonne][ligne].setModif(Etat.NON_COLORE);
            else
                plateau[colonne][ligne].setModif(Etat.ANIME, couleurUtilisee, image[colonne][ligne].getCouleurApercu());
            
            
            if(verifComplet(colonne, ligne))
            {
                
                //Si la grille est complète, ob redonne les bonnes couleurs à l'aperçu.
                for(int i = 0; i < nbCases; i++)
                {
                    for(int j = 0; j < nbCases; j++)
                    {
                          plateau[i][j].setModif(image[i][j].getEtat(), image[i][j].getCouleurGrille(), image[i][j].getCouleurApercu());
                    }
                }
                erreurCase();
            }
            
        }
    }
    
    /**
     * Vérifie si les lignes et colonnes sont complètes.
     * @param colonne Numéro de la colonne à vérifier.
     * @param ligne Numéro de la ligne à vérifier.
     */
    private boolean verifComplet(int colonne, int ligne)
    {
        //On regarde si la ligne ou la colonne sont désormais complètes
        indicesCompletColonnes[colonne] = true;
        indicesCompletLignes[ligne] = true;
        
        for(int k = 0; k < nbCases; k++)
        {
            if(image[colonne][k].getCouleurGrille() != plateau[colonne][k].getCouleurGrille())
                indicesCompletColonnes[colonne] = false;
            
            if(image[k][ligne].getCouleurGrille() != plateau[k][ligne].getCouleurGrille())
                indicesCompletLignes[ligne] = false;
        }
        
        //Si la ligne et la colonne sont complètes, on peut regarder si la grille est complète, sinon on est sur qu'elle est pas finie
        boolean tsColores = false;
        if(indicesCompletColonnes[colonne] && indicesCompletLignes[ligne])
        {
            tsColores = true;
            for(int k = 0; k < nbCases; k++)
            {
                if(indicesCompletColonnes[k] == false)
                    tsColores = false;
                
                if(indicesCompletLignes[k] == false)
                    tsColores = false;
            }
        }
        
        if(tsColores)
        {
            complete = true;
            grilleComplete();
        }
        
        return tsColores;
    }
    
    /**
     * Vérifie si le fichier contenant la grille est valide.
     */
    private boolean verifFichier() {
        
        boolean fichierOK = true;
        
        /* Si le fichier a la bonne extension */
        if(!fic.getName().endsWith(".txt"))
            fichierOK = false;

        /* Vérification du contenu du fichier */
        BufferedReader br;
        try 
        {
            br = new BufferedReader (new FileReader (fic));
            try
            {
                /* Première ligne à vérifier : le temps moyen de la grille */
                String str = br.readLine();
                
                boolean testDuree = str.matches("[0-9]{1,3}");
                if(!testDuree)
                    fichierOK = false;
                
                /* Deuxième ligne à vérifier : la difficulté de la grille */
                str = br.readLine();
                
                boolean testDifficulte = str.matches("[1-5]");
                if(!testDifficulte)
                    fichierOK = false;
                
                /* Troisème donnée : la grille */
                
                //Nombre de colonne de la première ligne
                str = br.readLine();

                String[] couleursPremiereLigneRGB = str.split("\\(|\\(\\)|\\)");
                
                int nbColonnesPremiereLigne = 0;
                for(int numLigne = 0; numLigne < couleursPremiereLigneRGB.length; numLigne++)
                {
                    String couleurCase = couleursPremiereLigneRGB[numLigne];
                    if(!couleurCase.isEmpty())
                        nbColonnesPremiereLigne++;
                }
                
                int nbLignesGrille = 1;
                str = br.readLine();
                for(int j = 0; str != null; j++)
                {
                    
                    nbLignesGrille++;
                    String[] couleursLigneRGB = str.split("\\(|\\(\\)|\\)");
                    
                    int nbColonnes = 0;
                    for(int numLigne = 0; numLigne < couleursLigneRGB.length; numLigne++)
                    {
                        String couleurCase = couleursLigneRGB[numLigne];
                        if(!couleurCase.isEmpty())
                            nbColonnes++;
                    }
                    
                    //Nombre différent de colonnes entre les lignes
                    if(nbColonnes != nbColonnesPremiereLigne)
                        fichierOK = false;
                    
                    //Vérification du format d'écriture du fichier

                    
                    str = br.readLine();
                }

                //Si le nombre de lignes et de colonnes et différent
                if(nbColonnesPremiereLigne != nbLignesGrille)
                    fichierOK = false;
                
                br.close();
                
            } catch (IOException ex)
            {
                fichierOK = false;
            }
            
            
        } catch (FileNotFoundException ex) {
            fichierOK = false;
        }
        finally
        {
            return fichierOK;
        }
    }
    
    @Override
    public void addGrilleObservateur(GrilleObservateur obs) {
        listeObs.add(obs);
    }
    
    @Override
    public void grilleComplete() {
        for(int i = 0; i < listeObs.size(); i++)
            listeObs.get(i).grilleComplete();
    }
    
    @Override
    public void erreurCase() {
        for(int i = 0; i < listeObs.size(); i++)
            listeObs.get(i).erreurCase();
    }
    
    @Override
    public void erreurCouleur() {
        for(int i = 0; i < listeObs.size(); i++)
            listeObs.get(i).erreurCouleur();
    }
    
    @Override
    public void delGrilleObservateur() {
        listeObs.removeAll(listeObs);
    }
}
