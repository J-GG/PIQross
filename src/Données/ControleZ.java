package Données;

/**
 * Classe contenant des informations sur les cases et leur emplacement dans la grille permettant d'annuler une action.
 * @author Genest
 */
public class ControleZ 
{
    private Case caseImage;
    private Case casePlateau;
    
    /**
     * Colonne de la grille contenant la case.
     */
    private int colonne;
    
    /**
     * Ligne de la grille contenant la case.
     */
    private int ligne;
            
    
    /**
     * Construit une sauvegarde de la case.
     * @param cImage Partie "Image" de la grille. 
     * @param cPlateau Partie "Plateau" de la grille. 
     * @param col Colonne contenant la case.
     * @param lig Ligne contenant la case.
     */
    public ControleZ(Case cImage, Case cPlateau, int col, int lig)
    {
        caseImage = cImage;
        casePlateau = cPlateau;
        colonne = col;
        ligne = lig;
    }
    
    /**
     * Retourne la partie "Image" de la case.
     * @return La partie "Image" de la case.
     */
    public Case getCaseImage()
    {
        return caseImage;
    }
    
    /**
     * Retourne la partie "Plateau" de la case.
     * @return La partie "Plateau" de la case.
     */
    public Case getCasePlateau()
    {
        return casePlateau;
    }
    
    /**
     * Retourne la colonne contenant la case.
     * @return La colonne contenant la case.
     */
    public int getColonne()
    {
        return colonne;
    }
    
    /**
     * Retourne la ligne contenant la case.
     * @return La ligne contenant la case.
     */
    public int getLigne()
    {
        return ligne;
    }
    
    /**
     * Compare 2 ControleZ.
     * @param cz ControleZ à comparer.
     * @return Vrai si les deux controleZ sont identiques.
     */
    public boolean comparer(ControleZ cz)
    {
        boolean identique = true;
        
        if(caseImage.getCouleurApercu() != cz.getCaseImage().getCouleurApercu())
            identique = false;
        if(caseImage.getCouleurGrille() != cz.getCaseImage().getCouleurGrille())
            identique = false;
        if(caseImage.getEtat() != cz.getCaseImage().getEtat())
            identique = false;
        
        if(casePlateau.getCouleurApercu() != cz.getCasePlateau().getCouleurApercu())
            identique = false;
        if(casePlateau.getCouleurGrille() != cz.getCasePlateau().getCouleurGrille())
            identique = false;
        if(casePlateau.getEtat() != cz.getCasePlateau().getEtat())
            identique = false;
        
        if(colonne != cz.getColonne())
            identique = false;
        if(ligne != cz.getLigne())
            identique = false;
        
        return identique;
    }
}
