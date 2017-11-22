package IHM;

import Données.Case;
import Données.Case.Etat;
import Données.Grille;
import Données.Options;
import Données.Options.Difficulte;
import Données.Options.Son;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ZoneJeu extends JPanel {

    private JPanel parent;
    private Fenêtre frame;

    private Grille grille;

    /**
     * Nombre de cases composant une ligne ou une colonne.
     */
    private int nbCases;

    /**
     * La taille d'une image représentant une case. Exprimée en pixels.
     */
    private int tailleCase;

    /**
     * Le plus grand nombre d'indices pour la colonne de la grille en ayant le
     * plus. Permet d'allouer la bonne dimension pour la grille.
     */
    private int maxIndicesColonnes;

    /**
     * Le plus grand nombre d'indices pour la ligne de la grille en ayant le
     * plus. Permet d'allouer la bonne dimension pour la grille.
     */
    private int maxIndicesLignes;

    private ArrayList indicesLignes[][];
    private ArrayList indicesColonnes[][];

    /**
     * Absicsse du message s'affichant lorsque la grille est terminée.
     */
    private int termineX;

    /**
     * Dimension graphique de la grille comprenant les indices.
     */
    private Dimension dimGrille;

    /**
     * Nombre de lignes dans le menu de droite contenant les couleurs.
     */
    private int nbLignesPots;

    private Timer timer;

    /**
     * Taille d'un coté des cases de l'aperçu, exprimé en pourcentage par
     * rapport à la grille affichée. Utilisée une fois la grille terminée, cela
     * permet de faire grossir progressivement l'aperçu lorsque la grille est
     * terminée.
     */
    private int apercuTaille;

    /**
     * Son joué lorsque le joueur a fini la grille.
     */
    private AudioClip sndTermine;

    private Case plateau[][];
    private int contour;

    //Images
    private BufferedImage imgBlanc = null;
    private BufferedImage imgBleu = null;
    private BufferedImage imgJaune = null;
    private BufferedImage imgOrange = null;
    private BufferedImage imgRouge = null;
    private BufferedImage imgVert = null;
    private BufferedImage imgRose = null;
    private BufferedImage imgMarron = null;
    private BufferedImage imgNonColore = null;
    private BufferedImage imgFaux = null;
    private BufferedImage imgDrapeau = null;
    private BufferedImage imgIndice = null;
    private BufferedImage imgCroix = null;
    private BufferedImage imgMauvaiseCouleur = null;
    private BufferedImage imgTerminee = null;

    /**
     * Conteneur affichant la grille passée en paramètre.
     *
     * @param g Grille devant être affichée.
     * @param p JPanel affichant l'instance de ZoneJeu.
     */
    public ZoneJeu(Grille g, JPanel p, Fenêtre f) {
        /* INITIALISATIONS */

        grille = g;
        parent = p;
        frame = f;
        nbCases = grille.getNbCases();
        indicesLignes = grille.getIndicesLignes();
        indicesColonnes = grille.getIndicesColonnes();
        timer = new Timer(20, new ActionGrilleFinie());
        termineX = 0;
        dimGrille = new Dimension();
        maxIndicesColonnes = grille.getMaxIndicesColonnes();
        maxIndicesLignes = grille.getMaxIndicesLignes();
        int nbPots = grille.getListeCouleurs().size();
        //En mode difficile, il y a la gomme en plus
        if (Options.get().getDifficulte() == Difficulte.DIFFICILE) {
            nbPots++;
        }

        nbLignesPots = (int) Math.ceil((double) nbPots / 4);

        apercuTaille = 1;
        plateau = grille.getPlateau();
        contour = 20;

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

        } catch (IOException ex) {
        }

        try {
            sndTermine = Applet.newAudioClip(new File("sons/termine.wav").toURL());
        } catch (MalformedURLException ex) {
        }
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
     * Retourne le timer gérant l'affichage du message de la grille terminée.
     *
     * @return le timer.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Dessine le contenu de ZoneJeu et donc la grille.
     *
     * @param g Objet Graphics permettant de dessiner dans ZoneJeu.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Lissage du texte et des dessins
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //-7 pour les bordures
        if ((parent.getWidth() - 7 - contour) / (nbCases + maxIndicesLignes) < (parent.getHeight() - 7 - contour) / (nbCases + maxIndicesColonnes)) {
            tailleCase = (parent.getWidth() - 7 - contour) / (nbCases + maxIndicesLignes);
        } else {
            tailleCase = (parent.getHeight() - 7 - contour) / (nbCases + maxIndicesColonnes);
        }

        dimGrille.setSize(tailleCase * (nbCases + maxIndicesLignes), tailleCase * (nbCases + maxIndicesColonnes));

        setPreferredSize(new Dimension(parent.getWidth(), parent.getHeight()));

        /* Bordures et Fonds */
        //Bordures bleues entre la zone de dessin et le menu
        g.setColor(new Color(21, 62, 107));
        g.fillRect(parent.getWidth() - 5, 0, 5, parent.getHeight());

        g.setColor(new Color(15, 56, 78));
        g.fillRect(parent.getWidth() - 7, 0, 2, parent.getHeight());

        //Bordures blanches
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

        /* Indices */
        float transparenceIndices = 0.2f;
        if ((float) (100 - apercuTaille) / 100 > 0.2f) {
            transparenceIndices = (float) (100 - apercuTaille) / 100;
        }

        if (grille.getComplete())//Si la grille est complète, on la met en transparence
        {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparenceIndices));
        }

        //Colonnes
        for (int i = 0; i < nbCases; i++) {
            int k = 0;
            do {
                //Si il y'a un indice, on l'affiche, sinon on affiche 0
                int nbBlocARemonter = 1;
                String nbAffiche = "0";

                if (!indicesColonnes[i][0].isEmpty()) {
                    nbBlocARemonter = indicesColonnes[i][0].size();
                    nbAffiche = String.valueOf(indicesColonnes[i][0].get(k));
                }

                g.drawImage(imgIndice, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes - nbBlocARemonter + k) * tailleCase, tailleCase, tailleCase, this);

                if (indicesColonnes[i][0].isEmpty()) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor((Color) indicesColonnes[i][1].get(k));
                }

                g.drawString(nbAffiche, (i + maxIndicesLignes) * tailleCase + tailleCase / 2 - 5, (maxIndicesColonnes - nbBlocARemonter + k) * tailleCase + tailleCase / 2 + 5);

                //On met en avant les indices dont les colonnes sont complètes si on est en mode facile
                if (grille.getIndicesCompletColonnes()[i] == true && Options.get().getDifficulte() == Difficulte.FACILE) {
                    g.drawImage(imgCroix, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes - nbBlocARemonter + k) * tailleCase, tailleCase, tailleCase, parent);
                }

                k++;
            } while (k < indicesColonnes[i][0].size());
        }

        //Lignes
        for (int j = 0; j < nbCases; j++) {
            int k = 0;
            do {
                int nbBlocARemonter = 1;
                String nbAffiche = "0";

                if (!indicesLignes[j][0].isEmpty()) {
                    nbBlocARemonter = indicesLignes[j][0].size();
                    nbAffiche = String.valueOf(indicesLignes[j][0].get(k));
                }

                g.drawImage(imgIndice, (maxIndicesLignes - nbBlocARemonter + k) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);

                if (indicesLignes[j][0].isEmpty()) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor((Color) indicesLignes[j][1].get(k));
                }

                g.drawString(nbAffiche, (maxIndicesLignes - nbBlocARemonter + k) * tailleCase + tailleCase / 2 - 5, (maxIndicesColonnes + j) * tailleCase + tailleCase / 2 + 5);

                //Si la ligne est complète et qu'on est en mode facile, on affiche une croix sur les indices
                if (grille.getIndicesCompletLignes()[j] == true && Options.get().getDifficulte() == Difficulte.FACILE) {
                    g.drawImage(imgCroix, (maxIndicesLignes - nbBlocARemonter + k) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, parent);
                }

                k++;
            } while (k < indicesLignes[j][0].size());
        }

        /* Cases */
        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {
                BufferedImage tmp = null;

                if (plateau[i][j].getEtat() == Etat.NON_COLORE) {
                    g.drawImage(imgNonColore, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                } else if (plateau[i][j].getEtat() == Etat.ANIME) {
                    g.drawImage(imgNonColore, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);

                    if (plateau[i][j].getCouleurGrille() == Case.Couleur.BLANC) {
                        tmp = imgBlanc;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.BLEU) {
                        tmp = imgBleu;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.JAUNE) {
                        tmp = imgJaune;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.ORANGE) {
                        tmp = imgOrange;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.ROUGE) {
                        tmp = imgRouge;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.VERT) {
                        tmp = imgVert;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.ROSE) {
                        tmp = imgRose;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.MARRON) {
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
                } else if (plateau[i][j].getEtat() == Etat.COLORE) {
                    if (plateau[i][j].getCouleurGrille() == Case.Couleur.BLANC) {
                        tmp = imgBlanc;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.BLEU) {
                        tmp = imgBleu;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.JAUNE) {
                        tmp = imgJaune;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.ORANGE) {
                        tmp = imgOrange;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.ROUGE) {
                        tmp = imgRouge;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.VERT) {
                        tmp = imgVert;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.ROSE) {
                        tmp = imgRose;
                    } else if (plateau[i][j].getCouleurGrille() == Case.Couleur.MARRON) {
                        tmp = imgMarron;
                    }
                    g.drawImage(tmp, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, this);
                }

            }
        }

        //On remet la transparence normale
        if (grille.getComplete()) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        /* Aperçu */
        g.setFont(new Font("Calibri", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        //Fond
        if (grille.getComplete()) {
            g.fillRect(maxIndicesLignes * (apercuTaille * tailleCase / 100), maxIndicesColonnes * (apercuTaille * tailleCase / 100), plateau.length * (apercuTaille * tailleCase / 100), plateau.length * (apercuTaille * tailleCase / 100));
        } else if (Options.get().getDifficulte() != Options.Difficulte.DIFFICILE) {
            g.fillRect(30, 30, plateau.length, plateau.length);
            g.setColor(Color.WHITE);
            g.drawString("Aperçu", 20, 20);
        }

        //cases
        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {
                if (plateau[i][j].getEtat() != Etat.NON_COLORE && plateau[i][j].getEtat() != Etat.FAUX && plateau[i][j].getEtat() != Etat.MAUVAISE_COULEUR && plateau[i][j].getEtat() != Etat.DRAPEAU) {
                    g.setColor(plateau[i][j].getCouleurApercu());

                    if (grille.getComplete()) {
                        g.fillRect((i + maxIndicesLignes) * (apercuTaille * tailleCase / 100), (j + maxIndicesColonnes) * (apercuTaille * tailleCase / 100), (apercuTaille * tailleCase / 100), (apercuTaille * tailleCase / 100));
                    } else if (Options.get().getDifficulte() != Options.Difficulte.DIFFICILE) {
                        g.fillRect(30 + i, 30 + j, 1, 1);
                    }
                }
            }
        }

        /* Message lorsque la grille est complète */
        if (grille.getComplete()) {
            if (!timer.isRunning()) {
                termineX = 100;
                timer.start();

                if (Options.get().getSon() == Son.ACTIVE) {
                    sndTermine.play(); //Son joué lors de la victoire
                }
            }

            float decimalArriveeMessage = 1 - ((float) termineX / 100);

            //Permet au message d'être gros au début puis de prendre sa taille normale
            int tailleMessage = termineX / 3;
            if (tailleMessage < 22) {
                tailleMessage = 22;
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, decimalArriveeMessage));//Permet d'afficher le message en transparent au début
            g.drawImage(imgTerminee, termineX * parent.getWidth() / 100, 30, 105, tailleMessage, this);
        }

    }

    /**
     * Créée un évènement permettant d'afficher progressivement le message de
     * fin de partie.
     */
    public class ActionGrilleFinie implements ActionListener {

        /**
         * Correspond au temps durant lequel sera affiché le message.
         */
        private int tps = 0;

        /**
         * *
         * Vaut vrai si le déplacement du message à afficher est terminé.
         */
        private boolean depMessage;

        /**
         * Vaut vrai si le déplacement de l'aperçu est terminé.
         */
        private boolean depApercu;

        /**
         * Créé une action qui sera appelée lors de la fin de partie.
         */
        public ActionGrilleFinie() {
            depMessage = false;
            depApercu = false;
        }

        /**
         * Evènement appelé lorsque la grille est terminée. Augmente l'abscisse
         * du message de fin permettant de le déplacer.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            //On commence par afficher l'aperçu en gros
            if (apercuTaille < 100) {
                apercuTaille += 2;
            } else {
                depApercu = true;
            }

            //Une fois fait, on affiche le message
            if (termineX > 2 && depApercu) {
                termineX -= 2;
            } else if (depApercu) {
                depMessage = true;
            }

            //Enfin, on laisse le tout affiché quelques secondes
            if (depMessage) {
                tps++;
            }

            //tps * temps d'attente avant de rentrer dans cette condition = 60 * 20 = 1200 ms
            if (tps == 60) {
                timer.stop();
                frame.choisirNiveau(true);
            }

            frame.repaint();
            frame.validate();
        }
    }
}
