package Données;

import java.awt.Color;

/**
 * Représente une case de la grille de pIQcross.
 *
 * @author J-GG
 */
public class Case {

    /**
     * Etats possible que peut prendre une case.
     */
    public enum Etat {
        COLORE, NON_COLORE, MAUVAISE_COULEUR, FAUX, DRAPEAU, ANIME, SUPPRESSION, AJOUT
    };

    /**
     * Enumeration représentant les couleurs pouvant être affichées sur la
     * grille.
     */
    public enum Couleur {
        NC, BLANC, BLEU, JAUNE, ORANGE, ROUGE, VERT, ROSE, MARRON
    };

    /**
     * Couleur utilisée pour l'affichage sur l'aperçu.
     */
    private Color couleurApercu;

    /**
     * Couleur utilisée pour l'affichage sur la grille au format Color.
     */
    private Color couleurGrilleColor;

    /**
     * Couleur utilisée pour l'affichage sur la grille.
     */
    private Couleur couleurGrille;

    /**
     * Compteur utilisé pour faire avancer l'animiation de la case.
     */
    private int cptAnimation;

    private Etat etat;

    /**
     * Crée une case comportant un état et deux couleurs.
     *
     * @param etat Etat de la case.
     * @param couleurGrille Couleur utilisée pour l'affichage sur la grille.
     * @param couleurApercu Couleur utilisée pour l'affichage sur l'aperçu.
     */
    public Case(Etat etat, Couleur couleurGrille, Color couleurApercu) {
        this.etat = etat;
        this.couleurApercu = couleurApercu;
        this.couleurGrille = couleurGrille;
        couleurGrilleColor = convertirCouleur(couleurGrille);
        cptAnimation = 0;
    }

    /**
     * Créé une case avec seulement un état.
     *
     * @param etat Etat de la case.
     */
    public Case(Etat etat) {
        this.etat = etat;
        couleurApercu = null;
        couleurGrille = Couleur.NC;
        couleurGrilleColor = null;
        cptAnimation = 0;
    }

    /**
     * Retourne la couleur de la case qui est affichée sur l'aperçu.
     *
     * @return La couleur de la case.
     */
    public Color getCouleurApercu() {
        return couleurApercu;
    }

    /**
     * Retourne la couleur de la case qui est affichée sur la grille au format
     * Color.
     *
     * @return La couleur de la case.
     */
    public Color getCouleurGrilleColor() {
        return couleurGrilleColor;
    }

    /**
     * Retourne la couleur de la case qui est affichée sur la grille.
     *
     * @return La couleur de la case.
     */
    public Couleur getCouleurGrille() {
        return couleurGrille;
    }

    /**
     * Retourne le compteur de l'animation.
     *
     * @return Le compteur de l'animation.
     */
    public int getCptAnimation() {
        return cptAnimation;
    }

    /**
     * Retourne l'état actuel de la case.
     *
     * @return L'état de la case.
     */
    public Etat getEtat() {
        return etat;
    }

    /**
     * Augmente le compteur d'animation de 1.
     */
    public void augmenterCpt() {
        cptAnimation++;
    }

    /**
     * Modifie l'état et les couleurs d'une case
     *
     * @param etat Nouvel état.
     * @param couleurGrille Nouvelle couleur affichée sur la grille.
     * @param couleurApercu Nouvelle couleur affichée sur l'aperçu.
     */
    public void setModif(Etat etat, Couleur couleurGrille, Color couleurApercu) {
        this.etat = etat;
        this.couleurApercu = couleurApercu;
        this.couleurGrille = couleurGrille;
        couleurGrilleColor = convertirCouleur(couleurGrille);
    }

    /**
     * Modifie l'état d'une case.
     *
     * @param etat Nouvel état.
     */
    public void setModif(Etat etat) {
        this.etat = etat;
        couleurApercu = null;
        couleurGrille = Couleur.NC;
        couleurGrilleColor = null;
        cptAnimation = 0;
    }

    /**
     * Modifie la couleur de l'apercu.
     *
     * @param couleurApercu La couleur de l'apercu.
     */
    public void setModif(Color couleurApercu) {
        this.couleurApercu = couleurApercu;
    }

    /**
     * Convertie couleurGrille, de type Couleur en Color pour permettre une
     * utilisation plus facile en mode graphique.
     *
     * @param couleur La couleur au format Couleur.
     * @return La couleur au format Color.
     */
    public static Color convertirCouleur(Couleur couleur) {
        Color couleurColor = null;

        if (couleur == Couleur.BLANC) {
            couleurColor = Color.WHITE;
        } else if (couleur == Couleur.BLEU) {
            couleurColor = new Color(0, 194, 255);
        } else if (couleur == Couleur.JAUNE) {
            couleurColor = Color.YELLOW;
        } else if (couleur == Couleur.ORANGE) {
            couleurColor = Color.ORANGE;
        } else if (couleur == Couleur.ROUGE) {
            couleurColor = Color.RED;
        } else if (couleur == Couleur.VERT) {
            couleurColor = Color.GREEN;
        } else if (couleur == Couleur.ROSE) {
            couleurColor = Color.PINK;
        } else if (couleur == Couleur.MARRON) {
            couleurColor = new Color(134, 105, 105);
        } else if (couleur == Couleur.NC) {
            couleurColor = null;
        }

        return couleurColor;
    }
}
