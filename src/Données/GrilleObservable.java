package Données;

/**
 * Interface implémentant le DP Observer permettant de prévenir les observateur
 * du changement de l'état d'une grille.
 *
 * @author J-GG
 */
public interface GrilleObservable {

    /**
     * Ajoute un GrilleObservateur à la liste.
     *
     * @param obs Observateur à ajouter.
     */
    public void addGrilleObservateur(GrilleObservateur obs);

    /**
     * Prévient les observateurs que la grille est complète.
     */
    public void grilleComplete();

    /**
     * Prévient les observateurs que la case sélectionnée est fausse.
     */
    public void erreurCase();

    /**
     * Prévient les observateurs que la case sélectionnée l'est avec une
     * mauvaise couleur.
     */
    public void erreurCouleur();

    /**
     * Supprime tous les observateurs.
     */
    public void delGrilleObservateur();
}
