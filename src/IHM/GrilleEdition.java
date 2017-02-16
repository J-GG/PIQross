package IHM;

import Données.Grille;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Affiche une grille que l'on peut éditer.
 *
 * @author J-GG
 */
public class GrilleEdition extends AffichageGrille {

    private Editeur editeur;

    /**
     * Initialise les variables permettant d'editer la grille.
     *
     * @param g Grille devant être affichée.
     * @param p JPanel affichant la grille.
     */
    public GrilleEdition(Grille g, JPanel p, Editeur e) {
        /* INITIALISATIONS */
        super(g, p);

        //Permet de décaler d'une case la grille
        maxIndicesColonnes = 0;
        maxIndicesLignes = 0;
        editeur = e;

        nbLignesPots = (int) Math.ceil((double) 9 / 4); //9 : Nombre de pots de couleur + gomme, 4 : nombre de pots par ligne
    }

    /**
     * Retourne la Grille affichée.
     *
     * @return La grille affichée.
     */
    public Grille getGrille() {
        return grille;
    }

    /**
     * Modifie la grille à afficher.
     *
     * @param g Nouvelle grille à afficher.
     */
    public void setGrille(Grille g) {
        grille = g;
        plateau = g.getPlateau();
        nbCases = g.getNbCases();

        majTaille();
    }

    /**
     * Dessine la grille.
     *
     * @param g Objet Graphics permettant de dessiner.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //On affiche la grille
        if (editeur.getAfficherGrille()) {
            super.dessinerCase(g, 1);
        } //Ou on affiche l'aperçu
        else {
            for (int i = 0; i < nbCases; i++) {
                for (int j = 0; j < nbCases; j++) {
                    if (grille.getPlateau()[i][j].getCouleurApercu() != null)//On laisse transparente les cases sans couleur
                    {
                        g.setColor(grille.getPlateau()[i][j].getCouleurApercu());
                        g.fillRect(i * tailleCase, j * tailleCase, tailleCase, tailleCase);
                    }
                }
            }

            int colonne = (editeur.sourisX / tailleCase);
            int ligne = (editeur.sourisY / tailleCase);

            if (colonne < grille.getNbCases() && ligne < grille.getNbCases()) {
                g.setColor(Color.YELLOW);
                g.drawLine(colonne * tailleCase, ligne * tailleCase, (colonne + 1) * tailleCase, ligne * tailleCase);
                g.drawLine(colonne * tailleCase, (ligne + 1) * tailleCase, (colonne + 1) * tailleCase, (ligne + 1) * tailleCase);
                g.drawLine(colonne * tailleCase, ligne * tailleCase, colonne * tailleCase, (ligne + 1) * tailleCase);
                g.drawLine((colonne + 1) * tailleCase, ligne * tailleCase, (colonne + 1) * tailleCase, (ligne + 1) * tailleCase);
            }
        }
    }
}
