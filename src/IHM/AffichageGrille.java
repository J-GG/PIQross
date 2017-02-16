package IHM;

import Données.Case;
import Données.Case.Couleur;
import Données.Case.Etat;
import Données.Grille;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Affiche les cases d'une grille.
 *
 * @author J-GG
 */
public abstract class AffichageGrille extends JPanel {

    /**
     * JPanel contenant la grille.
     */
    protected JPanel parent;

    /**
     * Grille à afficher.
     */
    protected Grille grille;

    /**
     * La taille d'un côté d'une image représentant une case. Exprimée en
     * pixels.
     */
    protected int tailleCase;

    /**
     * Nombre de cases de la grille.
     */
    protected int nbCases;

    /**
     * Le plus grand nombre d'indices pour la colonne de la grille en ayant le
     * plus. Permet d'allouer la bonne dimension pour la grille.
     */
    protected int maxIndicesColonnes;

    /**
     * Le plus grand nombre d'indices pour la ligne de la grille en ayant le
     * plus. Permet d'allouer la bonne dimension pour la grille.
     */
    protected int maxIndicesLignes;

    /**
     * Dimension graphique de la grille comprenant les indices.
     */
    protected Dimension dimGrille;

    /**
     * Nombre de lignes dans le menu de droite contenant les couleurs. Permet de
     * savoir où placer les lignes horizontales blanches derrière la grille.
     */
    protected int nbLignesPots;

    /**
     * Référence vers le plateau de la grille.
     */
    protected Case plateau[][];

    /**
     * Taille du contour bleu foncé de la grille.
     */
    protected int contour;

    //Images
    protected BufferedImage imgBlanc = null;
    protected BufferedImage imgBleu = null;
    protected BufferedImage imgJaune = null;
    protected BufferedImage imgOrange = null;
    protected BufferedImage imgRouge = null;
    protected BufferedImage imgVert = null;
    protected BufferedImage imgRose = null;
    protected BufferedImage imgMarron = null;
    protected BufferedImage imgNonColore = null;
    protected BufferedImage imgFaux = null;
    protected BufferedImage imgDrapeau = null;
    protected BufferedImage imgIndice = null;
    protected BufferedImage imgCroix = null;
    protected BufferedImage imgMauvaiseCouleur = null;
    protected BufferedImage imgTerminee = null;
    protected BufferedImage imgSuppr = null;
    protected BufferedImage imgAjout = null;

    /**
     * Initialise les variables et composants.
     *
     * @param g Grille devant être affichée.
     * @param p JPanel affichant la grille.
     */
    public AffichageGrille(Grille g, JPanel p) {
        /* INITIALISATIONS */
        grille = g;
        parent = p;
        dimGrille = new Dimension(0, 0);
        plateau = grille.getPlateau();
        nbCases = grille.getNbCases();

        contour = 20;

        setPreferredSize(new Dimension(parent.getWidth(), parent.getHeight()));

        try {
            imgBlanc = ImageIO.read(new File("images/blanc.png"));
            imgBleu = ImageIO.read(new File("images/bleu.png"));
            imgJaune = ImageIO.read(new File("images/jaune.png"));
            imgOrange = ImageIO.read(new File("images/orange.png"));
            imgRouge = ImageIO.read(new File("images/rouge.png"));
            imgVert = ImageIO.read(new File("images/vert.png"));
            imgRose = ImageIO.read(new File("images/rose.png"));
            imgMarron = ImageIO.read(new File("images/marron.png"));
            imgNonColore = ImageIO.read(new File("images/noir.png"));
            imgFaux = ImageIO.read(new File("images/faux.png"));
            imgDrapeau = ImageIO.read(new File("images/drapeau.png"));
            imgIndice = ImageIO.read(new File("images/indice.png"));
            imgCroix = ImageIO.read(new File("images/croix.png"));
            imgMauvaiseCouleur = ImageIO.read(new File("images/mauvaiseCouleur.png"));
            imgTerminee = ImageIO.read(new File("images/termine.png"));
            imgSuppr = ImageIO.read(new File("images/supprimer.png"));
            imgAjout = ImageIO.read(new File("images/plus.png"));

        } catch (IOException ex) {
        }

        majTaille();

        /* EVENEMENTS */
        addComponentListener(new ComponentAdapter() {

            //Méthode appelée lors du redimensionement de la fenêtre, permettant de recalculer la taille des cases
            @Override
            public void componentResized(ComponentEvent e) {
                majTaille();
            }

        });
    }

    /**
     * Retourne la taille de l'image représentant une case.
     *
     * @return La taille d'une case. Exprimée en pixel.
     */
    public int getTailleCase() {
        return tailleCase;
    }

    /**
     * Permet de calculer la taille des cases et de la grille.
     */
    public void majTaille() {
        //-7 pour le liseré vertical
        if ((parent.getWidth() - 7 - contour) / (grille.getNbCases() + maxIndicesLignes) < (parent.getHeight() - 7 - contour) / (grille.getNbCases() + maxIndicesColonnes)) {
            tailleCase = (parent.getWidth() - 7 - contour) / (grille.getNbCases() + maxIndicesLignes);
        } else {
            tailleCase = (parent.getHeight() - 7 - contour) / (grille.getNbCases() + maxIndicesColonnes);
        }

        dimGrille.setSize(tailleCase * (grille.getNbCases() + maxIndicesLignes), tailleCase * (grille.getNbCases() + maxIndicesColonnes));
    }

    /**
     * Dessine le fond de la grille.
     *
     * @param g Objet Graphics permettant de dessiner.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Lissage du texte et des dessins
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(22, 67, 111));
        g.fillRect(0, 0, parent.getWidth(), parent.getHeight());


        /* Bordures et Fonds */
        //Liserés bleus entre la zone de dessin et le menu
        g.setColor(new Color(21, 62, 107));
        g.fillRect(parent.getWidth() - 5, 0, 5, parent.getHeight());

        g.setColor(new Color(15, 56, 78));
        g.fillRect(parent.getWidth() - 7, 0, 2, parent.getHeight());

        //Liserés blancs
        g.setColor(new Color(200, 200, 200));
        g.fillRect(parent.getWidth() - 5, parent.getHeight() / 2 - 88 - nbLignesPots * 32, 5, 30);
        g.fillRect(parent.getWidth() - 5, parent.getHeight() / 2 + 87 - nbLignesPots * 32, 5, 30);

        g.setColor(new Color(150, 150, 150));
        g.fillRect(parent.getWidth() - 7, parent.getHeight() / 2 - 88 - nbLignesPots * 32, 2, 30);
        g.fillRect(parent.getWidth() - 7, parent.getHeight() / 2 + 87 - nbLignesPots * 32, 2, 30);

        //Lignes blanches horizontales
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, parent.getHeight() / 2 - 88 - nbLignesPots * 32, parent.getWidth() - 7, 30);
        g.fillRect(0, parent.getHeight() / 2 + 87 - nbLignesPots * 32, parent.getWidth() - 7, 30);

        //Fond le plus foncé
        g.setColor(new Color(16, 47, 78));
        g.fillRect(0, 0, dimGrille.width + contour, dimGrille.height + contour);
    }

    /**
     * Dessine les cases de la grille.
     *
     * @param g Objet Graphics permettant de dessiner.
     */
    public void dessinerCase(Graphics g, float transparence) {
        BufferedImage tmp = null;

        Graphics2D g2d = (Graphics2D) g;

        //Permet de mettre en transparence lorsque la grille est terminée
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparence));

        //Lissage du texte et des dessins
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {
                if (plateau[i][j].getEtat() == Case.Etat.NON_COLORE || (grille.getImage()[i][j].getEtat() == Case.Etat.NON_COLORE && plateau[i][j].getEtat() == Etat.SUPPRESSION)) {
                    g.drawImage(imgNonColore, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);

                    if (plateau[i][j].getEtat() == Etat.SUPPRESSION) {
                        g.drawImage(imgSuppr, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                    }
                } else if (plateau[i][j].getEtat() == Case.Etat.ANIME) {

                    g.drawImage(imgNonColore, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);

                    if (plateau[i][j].getCouleurGrille() == Couleur.BLANC) {
                        tmp = imgBlanc;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.BLEU) {
                        tmp = imgBleu;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.JAUNE) {
                        tmp = imgJaune;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.ORANGE) {
                        tmp = imgOrange;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.ROUGE) {
                        tmp = imgRouge;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.VERT) {
                        tmp = imgVert;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.ROSE) {
                        tmp = imgRose;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.MARRON) {
                        tmp = imgMarron;
                    }

                    g.drawImage(tmp, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, plateau[i][j].getCptAnimation() * tailleCase / 10, plateau[i][j].getCptAnimation() * tailleCase / 10, this);

                    if (plateau[i][j].getCptAnimation() < 10) {
                        plateau[i][j].augmenterCpt();
                        parent.repaint();
                    } else {
                        plateau[i][j].setModif(Etat.COLORE, plateau[i][j].getCouleurGrille(), plateau[i][j].getCouleurApercu());
                    }

                } else if (plateau[i][j].getEtat() == Etat.MAUVAISE_COULEUR) {
                    g.drawImage(imgMauvaiseCouleur, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                } else if (plateau[i][j].getEtat() == Etat.FAUX) {
                    g.drawImage(imgFaux, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                } else if (plateau[i][j].getEtat() == Etat.DRAPEAU) {
                    g.drawImage(imgDrapeau, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                } else if (plateau[i][j].getEtat() == Etat.COLORE || (grille.getImage()[i][j].getEtat() == Case.Etat.COLORE && plateau[i][j].getEtat() == Etat.SUPPRESSION)) {
                    if (plateau[i][j].getCouleurGrille() == Couleur.BLANC) {
                        tmp = imgBlanc;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.BLEU) {
                        tmp = imgBleu;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.JAUNE) {
                        tmp = imgJaune;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.ORANGE) {
                        tmp = imgOrange;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.ROUGE) {
                        tmp = imgRouge;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.VERT) {
                        tmp = imgVert;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.ROSE) {
                        tmp = imgRose;
                    } else if (plateau[i][j].getCouleurGrille() == Couleur.MARRON) {
                        tmp = imgMarron;
                    }
                    g.drawImage(tmp, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);

                    if (plateau[i][j].getEtat() == Etat.SUPPRESSION) {
                        g.drawImage(imgSuppr, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                    }
                } else if (plateau[i][j].getEtat() == Etat.AJOUT) {
                    g.drawImage(imgNonColore, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                    g.drawImage(imgAjout, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                }
            }
        }
    }
}
