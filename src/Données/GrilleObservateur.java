package Données;

/**
 * Interface implémentant le DP Observer permettant d'exécuter des actions liées
 * à l'affichage de la grille.
 *
 * @author J-GG
 */
public interface GrilleObservateur {

    /**
     * Appelée lorsque la grille est terminée.
     */
    public abstract void grilleComplete();

    /**
     * Appelée lorsqu'une mauvaise case est sélectionnée.
     */
    public abstract void erreurCase();

    /**
     * Appelée lorsqu'une case est sélectionnée avec une mauvaise couleur.
     */
    public abstract void erreurCouleur();
}
