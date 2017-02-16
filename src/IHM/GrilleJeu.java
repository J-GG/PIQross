package IHM;

import Données.Case;
import Données.Grille;
import Données.Options;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Affiche une grille permettant de jouer. Elle affiche donc les cases, les
 * indices et l'aperçu.
 *
 * @author J-GG
 */
public class GrilleJeu extends AffichageGrille {

    private Timer timer;
    private Fenêtre fenetre;

    /**
     * Absicsse du message s'affichant lorsque la grille est terminée.
     */
    private int termineX;

    /**
     * Taille d'un coté des cases de l'aperçu, exprimé en pourcentage par
     * rapport à la taille d'une case de la grille affichée.
     */
    private int caseApercuTaille;

    /**
     * Son joué lorsque le joueur a fini la grille.
     */
    private AudioClip sndTermine;

    private ArrayList indicesLignes[][];
    private ArrayList indicesColonnes[][];

    /**
     * Initialise les variables permettant d'afficher les indices...
     *
     * @param g Grille devant être affichée.
     * @param p JPanel affichant la grille.
     * @param f Fenêtre principale.
     */
    public GrilleJeu(Grille g, JPanel p, Fenêtre f) {
        /* INITIALISATIONS */
        super(g, p);

        fenetre = f;
        indicesLignes = grille.getIndicesLignes();
        indicesColonnes = grille.getIndicesColonnes();
        timer = new Timer(20, new ActionDeplacementMessage());
        termineX = 0;
        maxIndicesColonnes = grille.getMaxIndicesColonnes();
        maxIndicesLignes = grille.getMaxIndicesLignes();

        caseApercuTaille = 10;//La taille de l'aperçu est fixée à 10% de la grille

        int nbPots = grille.getListeCouleurs().size();
        //En mode difficile, il y a la gomme à afficher en plus
        if (Options.get().getDifficulte() == Options.Difficulte.DIFFICILE) {
            nbPots++;
        }

        nbLignesPots = (int) Math.ceil((double) nbPots / 4);

        try {
            sndTermine = Applet.newAudioClip(new File("sons/termine.wav").toURL());
        } catch (MalformedURLException ex) {
        }

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
     * Dessine la grille.
     *
     * @param g Objet Graphics permettant de dessiner.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        //Calcul du niveau de transparence
        float transparenceIndices = 0.2f;
        if ((float) (100 - caseApercuTaille) / 100 > 0.2f) {
            transparenceIndices = (float) (100 - caseApercuTaille) / 100;
        }

        super.paintComponent(g);
        super.dessinerCase(g, transparenceIndices);

        Graphics2D g2d = (Graphics2D) g;

        //Lissage du texte et des dessins
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /**
         * **
         * Indices
         ************
         */
        if (grille.getComplete())//Si la grille est complète, on met les indices en transparence
        {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparenceIndices));
        }

        //Colonnes
        for (int i = 0; i < grille.getNbCases(); i++) {
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
                if (grille.getIndicesCompletColonnes()[i] == true && Options.get().getDifficulte() == Options.Difficulte.FACILE) {
                    g.drawImage(imgCroix, (i + maxIndicesLignes) * tailleCase, (maxIndicesColonnes - nbBlocARemonter + k) * tailleCase, tailleCase, tailleCase, parent);
                }

                k++;
            } while (k < indicesColonnes[i][0].size());
        }

        //Lignes
        for (int j = 0; j < grille.getNbCases(); j++) {
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
                if (grille.getIndicesCompletLignes()[j] == true && Options.get().getDifficulte() == Options.Difficulte.FACILE) {
                    g.drawImage(imgCroix, (maxIndicesLignes - nbBlocARemonter + k) * tailleCase, (maxIndicesColonnes + j) * tailleCase, tailleCase, tailleCase, parent);
                }

                k++;
            } while (k < indicesLignes[j][0].size());
        }

        //On remet la transparence normale
        if (grille.getComplete()) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        /**
         * **
         * Aperçu
         ************
         */
        g.setFont(new Font("Calibri", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        //Fond
        if (Options.get().getDifficulte() != Options.Difficulte.DIFFICILE || grille.getComplete())//Si on est en difficile et qu'on a pas fini la grille, on affiche pas l'aperçu
        {
            int caseApercuTaillePixel = 1;
            if (caseApercuTaille * tailleCase / 100 > 1) {
                caseApercuTaillePixel = caseApercuTaille * tailleCase / 100;
            }
            /*
            //fond
            if(grille.getComplete())
                g.fillRect(maxIndicesLignes*(caseApercuTaille*tailleCase/100), maxIndicesColonnes*(caseApercuTaille*tailleCase/100), plateau.length*(caseApercuTaille*tailleCase/100), plateau.length*(caseApercuTaille*tailleCase/100));
            else
            {
                g.fillRect(30, 30, caseApercuTaillePixel*plateau.length, caseApercuTaillePixel*plateau.length);
                g.setColor(Color.WHITE);
                g.drawString("Aperçu", 20, 20);
            }*/

            if (!grille.getComplete()) {
                g.drawRect(30, 30, caseApercuTaillePixel * plateau.length, caseApercuTaillePixel * plateau.length);
            }

            //Cases de l'aperçu
            for (int i = 0; i < grille.getNbCases(); i++) {
                for (int j = 0; j < grille.getNbCases(); j++) {
                    if (plateau[i][j].getEtat() != Case.Etat.NON_COLORE && plateau[i][j].getEtat() != Case.Etat.FAUX && plateau[i][j].getEtat() != Case.Etat.MAUVAISE_COULEUR && plateau[i][j].getEtat() != Case.Etat.DRAPEAU) {
                        if (plateau[i][j].getCouleurApercu() != null)//On laisse transparente les cases sans couleur
                        {
                            g.setColor(plateau[i][j].getCouleurApercu());

                            if (grille.getComplete()) {
                                g.fillRect((i + maxIndicesLignes) * caseApercuTaillePixel, (j + maxIndicesColonnes) * caseApercuTaillePixel, caseApercuTaillePixel, caseApercuTaillePixel);
                            } else {
                                g.fillRect(30 + i * caseApercuTaillePixel, 30 + j * caseApercuTaillePixel, caseApercuTaillePixel, caseApercuTaillePixel);
                            }
                        }
                    }
                }
            }
        }

        /**
         * **
         * Grille complète
         ************
         */
        if (grille.getComplete()) {
            if (!timer.isRunning()) {
                termineX = 100;
                timer.start();

                if (Options.get().getSon() == Options.Son.ACTIVE) {
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
    public class ActionDeplacementMessage implements ActionListener {

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
        public ActionDeplacementMessage() {
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
            if (caseApercuTaille < 100) {
                caseApercuTaille += 2;
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
                fenetre.choisirNiveau(true);
            }

            fenetre.repaint();
            fenetre.validate();
        }
    }
}
