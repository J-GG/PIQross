package IHM;

import Données.Case.Couleur;
import Données.Grille;
import Données.GrilleObservateur;
import Données.Options;
import Données.Options.Difficulte;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Conteneur affichant une partie de pIQcross.
 *
 * @author J-GG
 */
public class Jeu extends ConteneurGrille {

    /**
     * Zone de dessin contenant l'affichage de la grille.
     */
    private GrilleJeu grilleJeu;

    /**
     * Son devant être joué en cas d'erreur de case sélectionnée.
     */
    private AudioClip sndErreur;

    /**
     * Construit l'interface du jeu comprenant la zone de la grille et le menu à
     * droite.
     *
     * @param parent Fenêtre affichant ce conteneur.
     * @param g Grille à afficher.
     */
    public Jeu(final Fenêtre parent, Grille g) {
        /* INITIALISATIONS */
        super(parent, g);

        try {
            sndErreur = Applet.newAudioClip(new File("sons/erreur.wav").toURL());
        } catch (MalformedURLException ex) {
        }

        //Panel contenant la zone de jeu affichée au centre
        JPanel paneJeu = new JPanel(new BorderLayout());
        grilleJeu = new GrilleJeu(grille, paneJeu, parent);

        JPanel paneScore = new JPanel();
        paneScore.setBackground(new Color(22, 67, 111));

        /* Composants */
        JLabel lblScore = new JLabel("Score : ");
        lblScore.setForeground(Color.WHITE);
        lblScore.setFont(new Font("Calibri", Font.PLAIN, 20));

        final JLabel lblScoreValeur = new JLabel("0");
        lblScoreValeur.setForeground(Color.WHITE);
        lblScoreValeur.setFont(new Font("Calibri", Font.PLAIN, 20));

        JPanel paneCpt = new JPanel();
        paneCpt.setBackground(new Color(22, 67, 111));

        JLabel lblImageCompteur = new JLabel(new ImageIcon("images/temps.png"));

        JLabel lblCompteur = new JLabel("00:00:00");
        lblCompteur.setForeground(Color.WHITE);
        lblCompteur.setFont(new Font("Calibri", Font.PLAIN, 20));

        //Création d'un Timer qui appellera la méthode actionPerformed de l'instance de Chronometre toutes les secondes pour mettre à jour lblCompteur
        final Timer timer = new Timer(1000, new Chronometre(lblCompteur));
        timer.start();

        if (grille.getListeCouleurs().contains(Couleur.BLANC)) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlanc.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.BLEU)) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleu.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.ROUGE)) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRouge.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.ORANGE)) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrange.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.JAUNE)) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJaune.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.VERT)) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVert.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.ROSE)) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRose.png"));
        }

        if (grille.getListeCouleurs().contains(Couleur.MARRON)) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarron.png"));
        }

        if (Options.get().getDifficulte() == Difficulte.DIFFICILE)//En mode difficile, on peut effacer une case
        {
            lblImageGomme.setIcon(new ImageIcon("images/gomme.png"));
        }

        //Couleur utilisée lors de l'affichage de la grille avec vérification au cas où la grille n'aurait aucune case à colorier
        if (!grille.getListeCouleurs().isEmpty()) {
            potSelectionne(grille.getListeCouleurs().get(0));
        }

        /* AJOUTS */
        //Panels d'informations
        paneScore.add(lblScore);
        paneScore.add(lblScoreValeur);

        paneCpt.add(lblImageCompteur);
        paneCpt.add(lblCompteur);

        gbc.insets.bottom = 40;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        menu.add(lblNomGrille, gbc);

        gbc.gridy++;
        gbc.gridheight = 1;
        gbc.insets.bottom = 0;
        menu.add(lblImageVague, gbc);

        gbc.gridy++;
        menu.add(paneScore, gbc);

        gbc.gridy++;
        menu.add(paneCpt, gbc);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        menu.add(lblImageVagueRetourne, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets.top = 30;
        menu.add(lblImageCouleurs, gbc);

        int i = 0;

        gbc.gridwidth = 1;
        gbc.gridy = 6;

        if (lblImagePotBlanc.getIcon() != null) {
            gbc.gridx = i % 4;

            menu.add(lblImagePotBlanc, gbc);
            i++;
        }

        if (lblImagePotBleu.getIcon() != null) {
            gbc.gridx = i % 4;
            menu.add(lblImagePotBleu, gbc);
            i++;

        }

        if (lblImagePotRouge.getIcon() != null) {
            gbc.gridx = i % 4;
            menu.add(lblImagePotRouge, gbc);
            i++;
        }

        if (lblImagePotOrange.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            menu.add(lblImagePotOrange, gbc);
            i++;
        }

        if (lblImagePotJaune.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            menu.add(lblImagePotJaune, gbc);
            i++;
        }

        if (lblImagePotVert.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            menu.add(lblImagePotVert, gbc);
            i++;
        }

        if (lblImagePotRose.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            menu.add(lblImagePotRose, gbc);
            i++;
        }

        if (lblImagePotMarron.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            menu.add(lblImagePotMarron, gbc);
            i++;
        }

        if (lblImageGomme.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            menu.add(lblImageGomme, gbc);
            i++;
        }

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        menu.add(btnRetour, gbc);

        //Zone de jeu
        paneJeu.add(grilleJeu, BorderLayout.CENTER);

        //Panel principal
        add(paneJeu, BorderLayout.CENTER);
        add(menu, BorderLayout.EAST);

        paneJeu.repaint();
        paneJeu.validate();

        /* EVENEMENTS */
        btnRetour.addActionListener(new ActionListener() {

            /**
             * Appel le menuPrincipal.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //Arrête le timer de la zone de jeu si la grille était finie.
                grilleJeu.getTimer().stop();

                parent.choisirNiveau(true);
            }
        });

        grille.addGrilleObservateur(new GrilleObservateur() {

            @Override
            public void grilleComplete() {
                //Arrête le timer de la grille lorsqu'elle est complétée.
                timer.stop();
            }

            @Override
            public void erreurCase() {
                //On augmente le score
                lblScoreValeur.setText(String.valueOf((int) (grille.getNbErreurs() * (grille.getNbCases() * grille.getNbCases()) / 10)));

                //On joue un son
                if (Options.get().getSon() == Options.Son.ACTIVE) {
                    sndErreur.play();
                }
            }

            @Override
            public void erreurCouleur() {
                //On augmente le score.
                lblScoreValeur.setText(String.valueOf((int) (grille.getNbErreurs() * (grille.getNbCases() * grille.getNbCases()) / 10)));

                //On joue un son
                if (Options.get().getSon() == Options.Son.ACTIVE) {
                    sndErreur.play();
                }
            }
        });

        grilleJeu.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                sourisX = e.getX();
                sourisY = e.getY();
            }

            /*
             * Permet de remplir les cases en cascade en restant appuyé sur un bouton de la souris.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                sourisX = e.getX();
                sourisY = e.getY();

                if (!grille.getComplete()) {
                    colorerCase(e);
                }
            }
        });

        /**
         * Récupère le bouton de souris qui est préssé. Associé au
         * MouseMotionAdapter, permet de remplir les cases en Drag and Drop.
         */
        grilleJeu.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (!grille.getComplete()) {
                    colorerCase(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boutonClique = e.getButton();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boutonClique = MouseEvent.NOBUTTON;
            }

        });

    }

    @Override
    protected void colorerCase(MouseEvent e) {
        int colonne = sourisX / grilleJeu.getTailleCase() - grille.getMaxIndicesLignes();
        int ligne = sourisY / grilleJeu.getTailleCase() - grille.getMaxIndicesColonnes();

        //Si on a cliqué sur une case, on demande la grille de se modifier en conséquence
        if (colonne >= 0 && colonne < grille.getNbCases() && ligne >= 0 && ligne < grille.getNbCases()) {
            if (boutonClique == 0) {
                boutonClique = e.getButton();
            }

            grille.verifClic(colonne, ligne, boutonClique, couleurChoisieGrille);

            repaint();
            validate();
        }
    }

    @Override
    protected void potSelectionne(Couleur potSelec) {
        couleurChoisieGrille = potSelec;

        if (potSelec == Couleur.BLANC && grille.getListeCouleurs().contains(Couleur.BLANC)) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlancSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.BLANC)) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlanc.png"));
        }

        if (potSelec == Couleur.BLEU && grille.getListeCouleurs().contains(Couleur.BLEU)) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleuSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.BLEU)) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleu.png"));
        }

        if (potSelec == Couleur.JAUNE && grille.getListeCouleurs().contains(Couleur.JAUNE)) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJauneSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.JAUNE)) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJaune.png"));
        }

        if (potSelec == Couleur.ORANGE && grille.getListeCouleurs().contains(Couleur.ORANGE)) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrangeSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.ORANGE)) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrange.png"));
        }

        if (potSelec == Couleur.ROUGE && grille.getListeCouleurs().contains(Couleur.ROUGE)) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRougeSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.ROUGE)) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRouge.png"));
        }

        if (potSelec == Couleur.VERT && grille.getListeCouleurs().contains(Couleur.VERT)) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVertSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.VERT)) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVert.png"));
        }

        if (potSelec == Couleur.ROSE && grille.getListeCouleurs().contains(Couleur.ROSE)) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRoseSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.ROSE)) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRose.png"));
        }

        if (potSelec == Couleur.MARRON && grille.getListeCouleurs().contains(Couleur.MARRON)) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarronSelec.png"));
        } else if (grille.getListeCouleurs().contains(Couleur.MARRON)) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarron.png"));
        }

        if (potSelec == Couleur.NC && Options.get().getDifficulte() == Options.Difficulte.DIFFICILE) {
            lblImageGomme.setIcon(new ImageIcon("images/gommeSelec.png"));
        } else if (Options.get().getDifficulte() == Options.Difficulte.DIFFICILE) {
            lblImageGomme.setIcon(new ImageIcon("images/gomme.png"));
        }
    }

    /**
     * Chronomètre en seconde qui modifie l'affichage d'un JLabel associé.
     */
    public class Chronometre implements ActionListener {

        private JLabel lblChrono;
        private int seconde = 0;
        private int minute = 0;
        private int heure = 0;

        /**
         * Crée un Chronomètre qui compte le temps écoulé en seconde. Le délai
         * associé dans le Timer doit donc être d'une seconde.
         *
         * @param lbl Label qui doit être mis à jour toutes les secondes.
         */
        public Chronometre(JLabel lbl) {
            lblChrono = lbl;
        }

        /**
         * Ajoute une seconde au compteur et met à jour le JLabel.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            String temps;

            //Ajout d'une seconde
            seconde++;
            if (seconde >= 60) {
                minute++;
                seconde = 0;
            }

            if (minute >= 60) {
                heure++;
                minute = 0;
            }

            //Ajoute des 0 devant les valeurs si nécessaire
            if (heure < 10) {
                temps = "0" + heure;
            } else {
                temps = String.valueOf(heure);
            }

            if (minute < 10) {
                temps += ":0" + minute;
            } else {
                temps += ":" + minute;
            }

            if (seconde < 10) {
                temps += ":0" + seconde;
            } else {
                temps += ":" + String.valueOf(seconde);
            }

            //Mise à jour du label
            lblChrono.setText(temps);
        }
    }
}
