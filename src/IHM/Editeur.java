package IHM;

import Données.Case;
import Données.Case.Couleur;
import Données.Case.Etat;
import Données.ControleZ;
import Données.Grille;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Conteneur affichant un éditeur de Grille.
 *
 * @author J-GG
 */
public class Editeur extends ConteneurGrille {

    /**
     * Zone de dessin contenant l'affichage de la grille.
     */
    private GrilleEdition grilleEdition;

    /**
     * Indique quelle touche est pressée.
     */
    private int touchePressee;

    /**
     * Vaut vrai si il faut afficher la grille, faux si il faut afficher
     * l'aperçu.
     */
    private boolean afficherGrille;

    /**
     * Contient la liste des modifications de cases.
     */
    private ArrayList<ControleZ> z;

    private Color couleurChoisieApercu;

    private JTextField txtCouleur;

    private JSlider slideRouge;
    private JSlider slideVert;
    private JSlider slideBleu;
    private JPanel affichageCouleur;
    private JLabel lblRouge;
    private JLabel lblVert;
    private JLabel lblBleu;

    private boolean majSlider;

    /**
     * Construit l'interface de l'éditeur.
     */
    public Editeur(final Fenêtre parent, Grille g) {
        /* INITIALISATIONS */
        super(parent, g);

        parent.requestFocus();

        z = new ArrayList();
        afficherGrille = true;

        //On copie l'image de la grille dans le plateau afin d'afficher le contenu de la grille
        for (int i = 0; i < grille.getNbCases(); i++) {
            for (int j = 0; j < grille.getNbCases(); j++) {
                grille.getPlateau()[i][j].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
            }
        }

        /* Composants */
        //Panel contenant la grille
        JPanel paneGrille = new JPanel(new BorderLayout());
        grilleEdition = new GrilleEdition(grille, paneGrille, this);

        //Panel contenant les pots de couleurs, affiché en mode grille
        final JPanel paneCouleurs = new JPanel(new GridBagLayout());
        paneCouleurs.setBackground(new Color(22, 67, 111));

        lblImagePotBlanc.setIcon(new ImageIcon("images/potBlanc.png"));
        lblImagePotBleu.setIcon(new ImageIcon("images/potBleu.png"));
        lblImagePotRouge.setIcon(new ImageIcon("images/potRouge.png"));
        lblImagePotOrange.setIcon(new ImageIcon("images/potOrange.png"));
        lblImagePotJaune.setIcon(new ImageIcon("images/potJaune.png"));
        lblImagePotVert.setIcon(new ImageIcon("images/potVert.png"));
        lblImagePotRose.setIcon(new ImageIcon("images/potRose.png"));
        lblImagePotMarron.setIcon(new ImageIcon("images/potMarron.png"));
        lblImageGomme.setIcon(new ImageIcon("images/gomme.png"));

        //Panel contenant la couleur de la case de l'apercu, afficé en mode apercu
        final JPanel paneApercu = new JPanel(new GridBagLayout());
        paneApercu.setBackground(new Color(22, 67, 111));
        paneApercu.setVisible(false);

        couleurChoisieApercu = Color.black;
        majSlider = true;

        JLabel lblImageCouleur2 = new JLabel(lblImageCouleurs.getIcon());

        lblRouge = new JLabel("Rouge : ");
        lblRouge.setForeground(Color.WHITE);
        lblVert = new JLabel("Vert : ");
        lblVert.setForeground(Color.WHITE);
        lblBleu = new JLabel("Bleu : ");
        lblBleu.setForeground(Color.WHITE);

        slideRouge = new JSlider();
        slideRouge.setMaximum(255);
        slideRouge.setMinimum(0);
        slideRouge.setValue(0);
        slideRouge.setBackground(new Color(22, 67, 111));
        slideRouge.setFocusable(false);

        slideVert = new JSlider();
        slideVert.setMaximum(255);
        slideVert.setMinimum(0);
        slideVert.setValue(0);
        slideVert.setBackground(new Color(22, 67, 111));
        slideVert.setFocusable(false);

        slideBleu = new JSlider();
        slideBleu.setMaximum(255);
        slideBleu.setMinimum(0);
        slideBleu.setValue(0);
        slideBleu.setBackground(new Color(22, 67, 111));
        slideBleu.setFocusable(false);

        affichageCouleur = new JPanel();
        affichageCouleur.setMinimumSize(new Dimension(70, 70));
        affichageCouleur.setBackground(new Color(0, 0, 0));

        txtCouleur = new JTextField("000");
        txtCouleur.setFocusable(false);
        txtCouleur.setEditable(false);

        potSelectionne(Couleur.BLANC);

        JButton btnSwitch = new JButton("Grille/Aperçu");
        btnSwitch.setFocusable(false);

        JButton btnEnregistrer = new JButton("Enregistrer");
        btnEnregistrer.setFocusable(false);

        btnRetour.setFocusable(false);

        /* AJOUTS */
        paneGrille.add(grilleEdition);

        gbc.insets.bottom = 40;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        menu.add(lblNomGrille, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        menu.add(lblDifficulte, gbc);

        gbc.gridy++;
        gbc.insets.top = 30;
        gbc.insets.bottom = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        paneCouleurs.add(lblImageCouleurs, gbc);
        paneApercu.add(lblImageCouleur2, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        paneCouleurs.add(lblImagePotBlanc, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        paneApercu.add(lblRouge, gbc);

        gbc.gridx = 1;
        paneCouleurs.add(lblImagePotBleu, gbc);
        paneApercu.add(slideRouge, gbc);

        gbc.gridx = 2;
        paneCouleurs.add(lblImagePotRouge, gbc);

        gbc.gridx = 3;
        paneCouleurs.add(lblImagePotOrange, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paneCouleurs.add(lblImagePotJaune, gbc);
        gbc.insets.top = 0;
        paneApercu.add(lblVert, gbc);

        gbc.gridx = 1;
        gbc.insets.top = 30;
        paneCouleurs.add(lblImagePotVert, gbc);
        paneApercu.add(slideVert, gbc);

        gbc.gridx = 2;
        paneCouleurs.add(lblImagePotRose, gbc);

        gbc.gridx = 3;
        paneCouleurs.add(lblImagePotMarron, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paneCouleurs.add(lblImageGomme, gbc);
        gbc.insets.top = 0;
        paneApercu.add(lblBleu, gbc);

        gbc.gridx = 1;
        paneApercu.add(slideBleu, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets.right = 10;
        gbc.insets.top = 8;
        paneApercu.add(affichageCouleur, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets.right = 0;
        paneApercu.add(txtCouleur, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets.top = 30;
        menu.add(paneCouleurs, gbc);
        menu.add(paneApercu, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        menu.add(btnSwitch, gbc);

        gbc.gridy++;
        menu.add(btnEnregistrer, gbc);

        gbc.gridy++;
        menu.add(btnRetour, gbc);

        add(paneGrille, BorderLayout.CENTER);
        add(menu, BorderLayout.EAST);

        /* EVENEMENTS */
        slideRouge.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (majSlider) {
                    couleurChoisieApercu = new Color(slideRouge.getValue(), slideVert.getValue(), slideBleu.getValue());
                    affichageCouleur.setBackground(couleurChoisieApercu);
                    txtCouleur.setText(Integer.toHexString(slideRouge.getValue()) + "" + Integer.toHexString(slideVert.getValue()) + "" + Integer.toHexString(slideBleu.getValue()));
                }
            }
        });

        slideVert.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (majSlider) {
                    couleurChoisieApercu = new Color(slideRouge.getValue(), slideVert.getValue(), slideBleu.getValue());
                    affichageCouleur.setBackground(couleurChoisieApercu);
                    txtCouleur.setText(Integer.toHexString(slideRouge.getValue()) + "" + Integer.toHexString(slideVert.getValue()) + "" + Integer.toHexString(slideBleu.getValue()));
                }
            }
        });

        slideBleu.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (majSlider) {
                    couleurChoisieApercu = new Color(slideRouge.getValue(), slideVert.getValue(), slideBleu.getValue());
                    affichageCouleur.setBackground(couleurChoisieApercu);
                    txtCouleur.setText(Integer.toHexString(slideRouge.getValue()) + "" + Integer.toHexString(slideVert.getValue()) + "" + Integer.toHexString(slideBleu.getValue()));
                }
            }
        });

        btnSwitch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!afficherGrille) {
                    afficherGrille = true;
                    paneApercu.setVisible(false);
                    paneCouleurs.setVisible(true);
                } else {
                    afficherGrille = false;
                    paneApercu.setVisible(true);
                    paneCouleurs.setVisible(false);

                    //Si on presse le "P" ou le "M", on remet la grille comme avant car dans l'apercu on édite pas la taille de grille
                    if (touchePressee == KeyEvent.VK_P || touchePressee == KeyEvent.VK_M) {
                        for (int i = 0; i < grille.getNbCases(); i++) {
                            for (int j = 0; j < grille.getNbCases(); j++) {
                                grille.getPlateau()[i][j].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                            }
                        }
                        grilleEdition.setGrille(grille);

                        touchePressee = -1;

                        grilleEdition.repaint();
                        grilleEdition.validate();
                    }
                }

                repaint();
                validate();
            }
        });

        btnRetour.addActionListener(new ActionListener() {

            /**
             * Appel le menuPrincipal.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.choisirNiveau(false);
            }
        });

        btnEnregistrer.addActionListener(new ActionListener() {

            /**
             * Appel le menuPrincipal.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                grille.setNom(lblNomGrille.getText());
                grille.enregistrerGrille();
            }
        });

        parent.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                touchePressee = e.getKeyCode();

                if (touchePressee == KeyEvent.VK_M || touchePressee == KeyEvent.VK_P && afficherGrille) {
                    //On remet la grille comme elle était avant, sinon plusieurs colonnes/lignes peuvent-être en Etat de "Suppression"
                    for (int i = 0; i < grille.getNbCases(); i++) {
                        for (int j = 0; j < grille.getNbCases(); j++) {
                            grille.getPlateau()[i][j].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                        }
                    }

                    int colonne = (sourisX / grilleEdition.getTailleCase());
                    int ligne = (sourisY / grilleEdition.getTailleCase());

                    //Si on presse "M", on change l'état des cases de la ligne et colonne
                    if (touchePressee == KeyEvent.VK_M) {
                        if (colonne < grille.getNbCases() && ligne < grille.getNbCases()) {

                            for (int k = 0; k < grille.getNbCases(); k++) {
                                grille.getPlateau()[k][ligne].setModif(Etat.SUPPRESSION, grille.getImage()[k][ligne].getCouleurGrille(), grille.getImage()[k][ligne].getCouleurApercu());
                                grille.getPlateau()[colonne][k].setModif(Etat.SUPPRESSION, grille.getImage()[colonne][k].getCouleurGrille(), grille.getImage()[colonne][k].getCouleurApercu());
                            }

                        }
                    } //Si on presse "P", on affiche une ligne et une colonne de plus
                    else if (touchePressee == KeyEvent.VK_P) {
                        if (colonne < grille.getNbCases() + 1 && ligne < grille.getNbCases() + 1 && grille.getNbCases() < 40) {
                            Grille g = new Grille(grille.getFichier(), grille.getNbCases() + 1, grille.getNomGrille(), grille.getDureeMoyenne(), grille.getDifficulte());

                            int i = 0, j = 0;
                            for (int gi = 0; gi < g.getNbCases(); gi++) {
                                j = 0;

                                for (int gj = 0; gj < g.getNbCases(); gj++) {
                                    if (gi == colonne || gj == ligne) {
                                        g.getPlateau()[gi][gj].setModif(Etat.AJOUT);
                                        g.getImage()[gi][gj].setModif(Etat.AJOUT);
                                    } else {
                                        g.getPlateau()[gi][gj].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                                        g.getImage()[gi][gj].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                                        j++;
                                    }

                                }

                                if (colonne != gi) {
                                    i++;
                                }
                            }
                            grilleEdition.setGrille(g);
                        }

                    }
                    grilleEdition.repaint();
                    grilleEdition.validate();
                } //Si on presse "Ctrl+Z", on revient en arrière
                else if (touchePressee == KeyEvent.VK_Z && z.size() > 0 && e.getModifiers() == InputEvent.CTRL_MASK) {
                    int colonne = z.get(z.size() - 1).getColonne();
                    int ligne = z.get(z.size() - 1).getLigne();
                    Case cImage = z.get(z.size() - 1).getCaseImage();
                    Case cPlateau = z.get(z.size() - 1).getCasePlateau();

                    grille.getImage()[colonne][ligne].setModif(cImage.getEtat(), cImage.getCouleurGrille(), cImage.getCouleurApercu());
                    grille.getPlateau()[colonne][ligne].setModif(cPlateau.getEtat(), cPlateau.getCouleurGrille(), cPlateau.getCouleurApercu());

                    z.remove((z.size() - 1));

                    grilleEdition.setGrille(grille);

                    grilleEdition.repaint();
                    grilleEdition.validate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                //Si on relâche le "P" ou le "M", on remet la grille comme avant
                if (touchePressee == KeyEvent.VK_P || touchePressee == KeyEvent.VK_M) {
                    for (int i = 0; i < grille.getNbCases(); i++) {
                        for (int j = 0; j < grille.getNbCases(); j++) {
                            grille.getPlateau()[i][j].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                        }
                    }
                    grilleEdition.setGrille(grille);

                    touchePressee = -1;

                    grilleEdition.repaint();
                    grilleEdition.validate();
                }
            }
        });

        grilleEdition.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                sourisX = e.getX();
                sourisY = e.getY();

                if (touchePressee != KeyEvent.VK_M && touchePressee != KeyEvent.VK_P) {
                    if (!afficherGrille && boutonClique == MouseEvent.BUTTON3)//Si on affiche l'apercu et qu'on clique sur le bouton droit, on passe en mode pipette
                    {
                        recupererCouleur();

                        grilleEdition.repaint();
                        grilleEdition.validate();
                    } else {
                        colorerCase(e);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                sourisX = e.getX();
                sourisY = e.getY();

                if ((touchePressee == KeyEvent.VK_M || touchePressee == KeyEvent.VK_P) && afficherGrille) {
                    //On remet la grille comme elle était avant, sinon plusieurs colonnes/lignes peuvent-être en Etat de "Suppression"
                    for (int i = 0; i < grille.getNbCases(); i++) {
                        for (int j = 0; j < grille.getNbCases(); j++) {
                            grille.getPlateau()[i][j].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                        }
                    }

                    int colonne = (sourisX / grilleEdition.getTailleCase());
                    int ligne = (sourisY / grilleEdition.getTailleCase());

                    if (touchePressee == KeyEvent.VK_M) {
                        if (colonne < grille.getNbCases() && ligne < grille.getNbCases()) {
                            for (int k = 0; k < grille.getNbCases(); k++) {
                                grille.getPlateau()[k][ligne].setModif(Etat.SUPPRESSION, grille.getImage()[k][ligne].getCouleurGrille(), grille.getImage()[k][ligne].getCouleurApercu());
                                grille.getPlateau()[colonne][k].setModif(Etat.SUPPRESSION, grille.getImage()[colonne][k].getCouleurGrille(), grille.getImage()[colonne][k].getCouleurApercu());
                            }

                        }
                    } else if (touchePressee == KeyEvent.VK_P) {
                        if (colonne < grille.getNbCases() + 1 && ligne < grille.getNbCases() + 1 && grille.getNbCases() < 40) {
                            Grille g = new Grille(grille.getFichier(), grille.getNbCases() + 1, grille.getNomGrille(), grille.getDureeMoyenne(), grille.getDifficulte());

                            int i = 0, j;
                            for (int gi = 0; gi < g.getNbCases(); gi++) {
                                j = 0;

                                for (int gj = 0; gj < g.getNbCases(); gj++) {
                                    if (gi == colonne || gj == ligne) {
                                        g.getPlateau()[gi][gj].setModif(Etat.AJOUT);
                                        g.getImage()[gi][gj].setModif(Etat.AJOUT);
                                    } else {
                                        g.getPlateau()[gi][gj].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                                        g.getImage()[gi][gj].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                                        j++;
                                    }

                                }

                                if (colonne != gi) {
                                    i++;
                                }
                            }

                            grilleEdition.setGrille(g);

                        }
                    }

                }
                grilleEdition.repaint();
                grilleEdition.validate();
            }
        });

        grilleEdition.addMouseListener(new MouseAdapter() {

            /**
             * Met à jour la zone de dessin lors d'un clic.
             */
            @Override
            public void mouseClicked(MouseEvent e) {

                if (touchePressee == KeyEvent.VK_M && e.getButton() == MouseEvent.BUTTON1 && afficherGrille) {
                    supprimerRangee();
                } else if (touchePressee == KeyEvent.VK_P && e.getButton() == MouseEvent.BUTTON1 && afficherGrille) {
                    ajouterRangee();
                } else if (touchePressee != KeyEvent.VK_P && touchePressee != KeyEvent.VK_M) {
                    if (!afficherGrille && e.getButton() == MouseEvent.BUTTON3) {
                        recupererCouleur();
                    } else {
                        colorerCase(e);
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                boutonClique = e.getButton();

                //Si on clique sur le bouton droit, on passe sur la gomme
                if (boutonClique == MouseEvent.BUTTON3) {
                    couleurChoisieGrillePrec = couleurChoisieGrille;
                    potSelectionne(Couleur.NC);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Si on avait cliqué sur le bouton droit, on récupère la dernière couleur
                if (boutonClique == MouseEvent.BUTTON3) {
                    potSelectionne(couleurChoisieGrillePrec);
                }

                boutonClique = MouseEvent.NOBUTTON;
            }

        });
    }

    /**
     * Ajoute la ligne et colonne de la grille affichée à la grille de
     * l'Editeur.
     */
    private void ajouterRangee() {
        grille = grilleEdition.getGrille();

        for (int i = 0; i < grille.getNbCases(); i++) {
            for (int j = 0; j < grille.getNbCases(); j++) {
                if (grille.getImage()[i][j].getEtat() == Etat.AJOUT) {
                    grille.getImage()[i][j].setModif(Etat.NON_COLORE);
                    grille.getPlateau()[i][j].setModif(Etat.NON_COLORE);
                }
            }
        }

        grilleEdition.repaint();
        grilleEdition.validate();
    }

    /**
     * Ajoute les informations à l'ArrayList destinée au retour en arrière.
     *
     * @param colonne Colonne de la case à enregistrer.
     * @param ligne Ligne de la case à enregistrer.
     */
    public void ajouterControleZ(int colonne, int ligne) {
        Case caseImage = new Case(grille.getImage()[colonne][ligne].getEtat(), grille.getImage()[colonne][ligne].getCouleurGrille(), grille.getImage()[colonne][ligne].getCouleurApercu());
        Case casePlateau = new Case(grille.getPlateau()[colonne][ligne].getEtat(), grille.getPlateau()[colonne][ligne].getCouleurGrille(), grille.getPlateau()[colonne][ligne].getCouleurApercu());

        ControleZ cZ = new ControleZ(caseImage, casePlateau, colonne, ligne);

        //On vérifie qu'il n'a pas déjà ajouté juste avant (en cas de Drag and Drop, ça peut arriver)
        if (z.size() < 2) {
            z.add(cZ);
        } else if (!z.get(z.size() - 1).comparer(cZ) && !z.get(z.size() - 2).comparer(cZ)) {
            z.add(cZ);
        }
    }

    /**
     * Modifie la couleur de la case pointée par la souris.
     *
     * @param e Evènement généré par la souris permettant de connaitre sa
     * position.
     */
    protected void colorerCase(MouseEvent e) {
        int colonne = sourisX / grilleEdition.getTailleCase();
        int ligne = sourisY / grilleEdition.getTailleCase();

        //Si on a cliqué sur une case, on demande la grille de se modifier en conséquence
        if (colonne >= 0 && colonne < grille.getNbCases() && ligne >= 0 && ligne < grille.getNbCases()) {
            //Si on affiche la grille
            if (afficherGrille) {
                if (couleurChoisieGrille == Couleur.NC || boutonClique == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON3) {
                    if (Etat.NON_COLORE != grille.getPlateau()[colonne][ligne].getEtat())//On ajoute au controleZ seulement si la case était colorée avant
                    {
                        ajouterControleZ(colonne, ligne);
                    }

                    grille.getPlateau()[colonne][ligne].setModif(Etat.NON_COLORE);
                    grille.getImage()[colonne][ligne].setModif(Etat.NON_COLORE);

                } else if (boutonClique == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON1) {
                    if (couleurChoisieGrille != grille.getPlateau()[colonne][ligne].getCouleurGrille())//On ajoute au controleZ seulement si la couleur change
                    {
                        ajouterControleZ(colonne, ligne);
                    }

                    grille.getPlateau()[colonne][ligne].setModif(Etat.COLORE, couleurChoisieGrille, Case.convertirCouleur(couleurChoisieGrille));
                    grille.getImage()[colonne][ligne].setModif(Etat.COLORE, couleurChoisieGrille, Case.convertirCouleur(couleurChoisieGrille));
                }
            } //Si on affiche l'apercu, on modifie uniquement la couleur de celui-ci
            else {
                if (grille.getPlateau()[colonne][ligne].getEtat() != Etat.NON_COLORE) {
                    if (couleurChoisieApercu != grille.getPlateau()[colonne][ligne].getCouleurApercu())//On ajoute au controleZ seulement si la couleur change
                    {
                        ajouterControleZ(colonne, ligne);
                    }

                    grille.getPlateau()[colonne][ligne].setModif(couleurChoisieApercu);
                    grille.getImage()[colonne][ligne].setModif(couleurChoisieApercu);
                }
            }

            grilleEdition.repaint();
            grilleEdition.validate();
        }

    }

    /**
     * Indique si ce qui est affiché est la grille ou l'aperçu.
     *
     * @return Vrai si il faut afficher la grille, faux si il faut afficher
     * l'aperçu.
     */
    public boolean getAfficherGrille() {
        return afficherGrille;
    }

    /**
     * Retourne la position en abscisse de la souris.
     *
     * @return La position en abscisse de la souris.
     */
    public int getSourisX() {
        return sourisX;
    }

    /**
     * Retourne la position en ordonnée de la souris.
     *
     * @return La position en ordonnéee de la souris.
     */
    public int getSourisY() {
        return sourisY;
    }

    /**
     * Permet de récupérer la couleur de la case sur laquelle est la souris et
     * la sélectionner dans la couleur pour l'aperçu.
     */
    public void recupererCouleur() {
        int colonne = sourisX / grilleEdition.getTailleCase();
        int ligne = sourisY / grilleEdition.getTailleCase();

        if (colonne < grille.getNbCases() && ligne < grille.getNbCases() && grille.getPlateau()[colonne][ligne].getCouleurApercu() != null) {
            couleurChoisieApercu = grille.getPlateau()[colonne][ligne].getCouleurApercu();

            //Mise à jour de l'affichage de la couleur dans le menu.
            majSlider = false;//On empeche les sliders de se mettre à jour tant qu'on a pas modifié les trois valeurs
            slideRouge.setValue(couleurChoisieApercu.getRed());
            slideVert.setValue(couleurChoisieApercu.getGreen());
            slideBleu.setValue(couleurChoisieApercu.getBlue() + 1);
            majSlider = true;
            slideBleu.setValue(couleurChoisieApercu.getBlue() - 1);
        }
    }

    /**
     * Supprime de la grille de l'Editeur la ligne et colonne où est la souris.
     */
    private void supprimerRangee() {
        Grille g = new Grille(grille.getFichier(), grille.getNbCases() - 1, grille.getNomGrille(), grille.getDureeMoyenne(), grille.getDifficulte());

        int colonne = (sourisX / grilleEdition.getTailleCase());
        int ligne = (sourisY / grilleEdition.getTailleCase());

        if (colonne < grille.getNbCases() && ligne < grille.getNbCases() && grille.getNbCases() > 2) {

            //Copie de la grille dans g
            int gi = 0;
            int gj = 0;
            for (int i = 0; i < grille.getNbCases() && gi < grille.getNbCases() - 1; i++) {
                for (int j = 0; j < grille.getNbCases() && gj < grille.getNbCases() - 1; j++) {
                    if (j != ligne) {
                        g.getImage()[gi][gj].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                        g.getPlateau()[gi][gj].setModif(grille.getImage()[i][j].getEtat(), grille.getImage()[i][j].getCouleurGrille(), grille.getImage()[i][j].getCouleurApercu());
                        gj++;
                    }
                }
                gj = 0;
                if (i != colonne) {
                    gi++;
                }
            }

            grille = g;
            grilleEdition.setGrille(grille);

            grilleEdition.repaint();
            grilleEdition.validate();
        }
    }

    @Override
    protected void potSelectionne(Couleur potSelec) {

        couleurChoisieGrille = potSelec;
        if (potSelec != Couleur.NC) {
            affichageCouleur.setBackground(Case.convertirCouleur(potSelec));
            majSlider = false;//On empeche les sliders de se mettre à jour tant qu'on a pas modifié les trois valeurs
            slideRouge.setValue(affichageCouleur.getBackground().getRed());
            slideVert.setValue(affichageCouleur.getBackground().getGreen());
            slideBleu.setValue(affichageCouleur.getBackground().getBlue() + 1);
            majSlider = true;
            slideBleu.setValue(affichageCouleur.getBackground().getBlue() - 1);
        }
        if (potSelec == Couleur.BLANC) {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlancSelec.png"));
        } else {
            lblImagePotBlanc.setIcon(new ImageIcon("images/potBlanc.png"));
        }

        if (potSelec == Couleur.BLEU) {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleuSelec.png"));
        } else {
            lblImagePotBleu.setIcon(new ImageIcon("images/potBleu.png"));
        }

        if (potSelec == Couleur.JAUNE) {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJauneSelec.png"));
        } else {
            lblImagePotJaune.setIcon(new ImageIcon("images/potJaune.png"));
        }

        if (potSelec == Couleur.ORANGE) {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrangeSelec.png"));
        } else {
            lblImagePotOrange.setIcon(new ImageIcon("images/potOrange.png"));
        }

        if (potSelec == Couleur.ROUGE) {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRougeSelec.png"));
        } else {
            lblImagePotRouge.setIcon(new ImageIcon("images/potRouge.png"));
        }

        if (potSelec == Couleur.VERT) {
            lblImagePotVert.setIcon(new ImageIcon("images/potVertSelec.png"));
        } else {
            lblImagePotVert.setIcon(new ImageIcon("images/potVert.png"));
        }

        if (potSelec == Couleur.ROSE) {
            lblImagePotRose.setIcon(new ImageIcon("images/potRoseSelec.png"));
        } else {
            lblImagePotRose.setIcon(new ImageIcon("images/potRose.png"));
        }

        if (potSelec == Couleur.MARRON) {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarronSelec.png"));
        } else {
            lblImagePotMarron.setIcon(new ImageIcon("images/potMarron.png"));
        }

        if (potSelec == Couleur.NC) {
            lblImageGomme.setIcon(new ImageIcon("images/gommeSelec.png"));
        } else {
            lblImageGomme.setIcon(new ImageIcon("images/gomme.png"));
        }
    }
}
