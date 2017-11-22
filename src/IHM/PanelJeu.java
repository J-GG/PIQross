package IHM;

import Données.Case;
import Données.Grille;
import Données.GrilleObservateur;
import Données.Options;
import Données.Options.Difficulte;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Conteneur affichant une partie de pIQross.
 *
 * @author Genest
 */
public class PanelJeu extends JPanel {

    /**
     * Zone de dessin contenant l'affichage de la grille.
     */
    private ZoneJeu zoneJeu;

    /**
     * Partie données de la grille affichée.
     */
    private Grille grille;

    private Timer timer;

    /**
     * Indique quel bouton de la souris a été cliqué.
     */
    private int boutonClique;

    /**
     * Son devant être joué en cas d'erreur de case sélectionnée.
     */
    private AudioClip sndErreur;

    /**
     * Couleur utilisée pour cliquer sur la grille.
     */
    private Case.Couleur couleurChoisie;

    /*
     * JLabels contenant les images des couleurs utilisées pour la grille.
     */
    private JLabel lblImagePotBlanc;
    private JLabel lblImagePotBleu;
    private JLabel lblImagePotRouge;
    private JLabel lblImagePotOrange;
    private JLabel lblImagePotJaune;
    private JLabel lblImagePotVert;
    private JLabel lblImagePotRose;
    private JLabel lblImagePotMarron;
    private JLabel lblImageGomme;

    /**
     * Construit l'interface du jeu comprenant la zone de la grille et le menu à
     * droite.
     *
     * @param parent Fenêtre affichant ce conteneur.
     * @param g Grille à afficher.
     */
    public PanelJeu(final Fenêtre parent, Grille g) {
        /* INITIALISATIONS */
        grille = g;
        boutonClique = MouseEvent.NOBUTTON;

        setLayout(new BorderLayout());
        setBackground(new Color(22, 67, 111));

        try {
            sndErreur = Applet.newAudioClip(new File("sons/erreur.wav").toURL());
        } catch (MalformedURLException ex) {
        }

        //Panel contenant la zone de jeu affichée au centre
        JPanel paneJeu = new JPanel(new BorderLayout());
        zoneJeu = new ZoneJeu(grille, paneJeu, parent);

        //Panel contenant les informations affichées à droite
        JPanel informations = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        informations.setPreferredSize(new Dimension(150, 0));
        informations.setBackground(new Color(22, 67, 111));

        JTextArea lblNomGrille = new JTextArea(grille.getNomGrille());
        lblNomGrille.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblNomGrille.setForeground(Color.WHITE);
        lblNomGrille.setLineWrap(true);
        lblNomGrille.setWrapStyleWord(true);
        lblNomGrille.setEnabled(false);
        lblNomGrille.setBackground(new Color(22, 67, 111));
        lblNomGrille.setMinimumSize(new Dimension(150, 90));
        lblNomGrille.setPreferredSize(new Dimension(150, 90));

        JLabel lblImageVague = new JLabel(new ImageIcon("images/vague1.png"));

        JPanel paneScore = new JPanel();
        paneScore.setBackground(new Color(22, 67, 111));

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
        timer = new Timer(1000, new Chronometre(lblCompteur));
        timer.start();

        JLabel lblImageVagueRetourne = new JLabel(new ImageIcon("images/vague2.png"));
        JButton btnMenu = new JButton("Menu");

        JLabel lblImageCouleurs = new JLabel(new ImageIcon("images/couleurs.png"));
        lblImagePotBlanc = new JLabel("");
        lblImagePotBleu = new JLabel("");
        lblImagePotRouge = new JLabel("");
        lblImagePotOrange = new JLabel("");
        lblImagePotJaune = new JLabel("");
        lblImagePotVert = new JLabel("");
        lblImagePotRose = new JLabel("");
        lblImagePotMarron = new JLabel("");
        lblImageGomme = new JLabel("");

        if (grille.getListeCouleurs().contains(Case.Couleur.BLANC)) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlanc.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.BLEU)) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleu.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.ROUGE)) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRouge.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.ORANGE)) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrange.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.JAUNE)) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJaune.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.VERT)) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVert.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.ROSE)) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRose.png"));
        }

        if (grille.getListeCouleurs().contains(Case.Couleur.MARRON)) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarron.png"));
        }

        if (Options.get().getDifficulte() == Difficulte.DIFFICILE)//En mode difficile, on peut effacer une case
        {
            lblImageGomme.setIcon(new ImageIcon("images/gomme.png"));
        }

        //Couleur utilisée lors de l'affichage de la grille avec vérification au cas où la grille n'aurait aucune case à colorier
        if (!grille.getListeCouleurs().isEmpty()) {
            couleurSelectionnee(grille.getListeCouleurs().get(0));
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
        informations.add(lblNomGrille, gbc);

        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.insets.bottom = 0;
        informations.add(lblImageVague, gbc);

        gbc.gridy = 2;
        informations.add(paneScore, gbc);

        gbc.gridy = 3;
        informations.add(paneCpt, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        informations.add(lblImageVagueRetourne, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets.top = 30;
        informations.add(lblImageCouleurs, gbc);

        int i = 0;

        gbc.gridwidth = 1;
        gbc.gridy = 6;

        if (lblImagePotBlanc.getIcon() != null) {
            gbc.gridx = i % 4;

            informations.add(lblImagePotBlanc, gbc);
            i++;
        }

        if (lblImagePotBleu.getIcon() != null) {
            gbc.gridx = i % 4;
            informations.add(lblImagePotBleu, gbc);
            i++;

        }

        if (lblImagePotRouge.getIcon() != null) {
            gbc.gridx = i % 4;
            informations.add(lblImagePotRouge, gbc);
            i++;
        }

        if (lblImagePotOrange.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            informations.add(lblImagePotOrange, gbc);
            i++;
        }

        if (lblImagePotJaune.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            informations.add(lblImagePotJaune, gbc);
            i++;
        }

        if (lblImagePotVert.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            informations.add(lblImagePotVert, gbc);
            i++;
        }

        if (lblImagePotRose.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            informations.add(lblImagePotRose, gbc);
            i++;
        }

        if (lblImagePotMarron.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            informations.add(lblImagePotMarron, gbc);
            i++;
        }

        if (lblImageGomme.getIcon() != null) {
            gbc.gridx = i % 4;
            if (i % 4 == 0) {
                gbc.gridy++;
            }
            informations.add(lblImageGomme, gbc);
            i++;
        }

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        informations.add(btnMenu, gbc);

        //Zone de jeu
        paneJeu.add(zoneJeu, BorderLayout.CENTER);

        //Panel principal
        add(paneJeu, BorderLayout.CENTER);
        add(informations, BorderLayout.EAST);

        paneJeu.repaint();
        paneJeu.validate();

        /* EVENEMENTS */
        lblImagePotBlanc.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.BLANC);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotBleu.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.BLEU);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotJaune.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.JAUNE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotOrange.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.ORANGE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotRouge.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.ROUGE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotVert.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.VERT);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotRose.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.ROSE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImagePotMarron.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.MARRON);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        lblImageGomme.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                couleurSelectionnee(Case.Couleur.NC);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        });

        /*
        addComponentListener(new ComponentAdapter() {


            @Override
            public void componentResized(ComponentEvent e) {
                zoneJeu.repaint();
                zoneJeu.validate();
            }
        });*/
        zoneJeu.addMouseListener(new MouseAdapter() {

            /**
             * Met à jour la zone de dessin lors d'un clic.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!grille.getComplete()) {
                    int x = e.getX();
                    int y = e.getY();

                    int colonne = (x / zoneJeu.getTailleCase()) - grille.getMaxIndicesLignes();
                    int ligne = (y / zoneJeu.getTailleCase()) - grille.getMaxIndicesColonnes();

                    //Si on a cliqué sur une case, on demande la grille de se modifier en conséquence
                    if (colonne >= 0 && colonne < grille.getNbCases() && ligne >= 0 && ligne < grille.getNbCases()) {
                        grille.verifClic(colonne, ligne, e.getButton(), couleurChoisie);
                        repaint();
                        validate();
                    }
                }
            }
        });

        zoneJeu.addMouseMotionListener(new MouseMotionAdapter() {

            /**
             * Permet de remplir les cases en cascade en restant appuyé sur un
             * bouton de la souris.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!grille.getComplete()) {
                    int x = e.getX();
                    int y = e.getY();

                    int colonne = (x / zoneJeu.getTailleCase()) - grille.getMaxIndicesLignes();
                    int ligne = (y / zoneJeu.getTailleCase()) - grille.getMaxIndicesColonnes();

                    //Si on a cliqué sur une case, on demande la grille de se modifier en conséquence
                    if (colonne >= 0 && colonne < grille.getNbCases() && ligne >= 0 && ligne < grille.getNbCases() && boutonClique == MouseEvent.BUTTON1) {
                        grille.verifClic(colonne, ligne, boutonClique, couleurChoisie);
                        repaint();
                        validate();
                    }
                }
            }
        });

        /**
         * Récupère le bouton de souris qui est préssé. Associé au
         * MouseMotionAdapter, permet de remplir les cases en Drag and Drop.
         */
        zoneJeu.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                boutonClique = e.getButton();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boutonClique = MouseEvent.NOBUTTON;
            }

        });

        btnMenu.addActionListener(new ActionListener() {

            /**
             * Appel le menuPrincipal.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //Arrête le timer de la zone de jeu si la grille était finie
                zoneJeu.getTimer().stop();

                parent.menuPrincipal();
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
    }

    /**
     * Permet de changer la couleur utilisée pour compléter la grille.
     *
     * @param potSelec Couleur sélectionnée.
     */
    private void couleurSelectionnee(Case.Couleur potSelec) {
        couleurChoisie = potSelec;

        if (potSelec == Case.Couleur.BLANC && grille.getListeCouleurs().contains(Case.Couleur.BLANC)) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlancSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.BLANC)) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlanc.png"));
        }

        if (potSelec == Case.Couleur.BLEU && grille.getListeCouleurs().contains(Case.Couleur.BLEU)) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleuSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.BLEU)) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleu.png"));
        }

        if (potSelec == Case.Couleur.JAUNE && grille.getListeCouleurs().contains(Case.Couleur.JAUNE)) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJauneSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.JAUNE)) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJaune.png"));
        }

        if (potSelec == Case.Couleur.ORANGE && grille.getListeCouleurs().contains(Case.Couleur.ORANGE)) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrangeSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.ORANGE)) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrange.png"));
        }

        if (potSelec == Case.Couleur.ROUGE && grille.getListeCouleurs().contains(Case.Couleur.ROUGE)) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRougeSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.ROUGE)) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRouge.png"));
        }

        if (potSelec == Case.Couleur.VERT && grille.getListeCouleurs().contains(Case.Couleur.VERT)) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVertSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.VERT)) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVert.png"));
        }

        if (potSelec == Case.Couleur.ROSE && grille.getListeCouleurs().contains(Case.Couleur.ROSE)) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRoseSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.ROSE)) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRose.png"));
        }

        if (potSelec == Case.Couleur.MARRON && grille.getListeCouleurs().contains(Case.Couleur.MARRON)) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarronSelec.png"));
        } else if (grille.getListeCouleurs().contains(Case.Couleur.MARRON)) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarron.png"));
        }

        if (potSelec == Case.Couleur.NC && Options.get().getDifficulte() == Difficulte.DIFFICILE) {
            lblImageGomme.setIcon(new ImageIcon("images/gommeSelec.png"));
        } else if (Options.get().getDifficulte() == Difficulte.DIFFICILE) {
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

            String temps = "";

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
