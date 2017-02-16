package IHM;

import Données.Case.Couleur;
import Données.Grille;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Contient la grille à afficher et gère les évènements qui lui sont liés.
 *
 * @author J-GG
 */
public abstract class ConteneurGrille extends JPanel {

    /**
     * Partie données de la grille affichée.
     */
    protected Grille grille;

    /**
     * Couleur utilisée pour colorier la grille.
     */
    protected Couleur couleurChoisieGrille;

    /**
     * Couleur utilisée avant de faire un clic droit et donc de passer sur la
     * gomme. Cela permet de revenir sur cette couleur en relachant le bouton
     * droit.
     */
    protected Couleur couleurChoisieGrillePrec;

    /**
     * Indique quel bouton de la souris a été cliqué.
     */
    protected int boutonClique;

    /**
     * JPanel contenant le menu affiché à droite.
     */
    protected JPanel menu;

    /**
     * Abscisse de la souris.
     */
    protected int sourisX;

    /**
     * Ordonnée de la souris.
     */
    protected int sourisY;

    protected GridBagConstraints gbc;
    protected JTextArea lblNomGrille;
    protected JLabel lblDifficulte;
    protected JLabel lblImageCouleurs;
    protected JLabel lblImageVague;
    protected JLabel lblImageVagueRetourne;
    protected JButton btnRetour;

    /*
     * JLabels contenant les images des couleurs utilisées pour la grille.
     */
    protected JLabel lblImagePotBlanc;
    protected JLabel lblImagePotBleu;
    protected JLabel lblImagePotRouge;
    protected JLabel lblImagePotOrange;
    protected JLabel lblImagePotJaune;
    protected JLabel lblImagePotVert;
    protected JLabel lblImagePotRose;
    protected JLabel lblImagePotMarron;
    protected JLabel lblImageGomme;

    public ConteneurGrille(final Fenêtre parent, Grille g) {
        /* INITALISATIONS */
        grille = g;
        boutonClique = MouseEvent.NOBUTTON;

        setLayout(new BorderLayout());
        setBackground(new Color(22, 67, 111));

        //Panel contenant le menu affiché à droite
        menu = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        menu.setPreferredSize(new Dimension(150, 0));
        menu.setBackground(new Color(22, 67, 111));

        /* Composants */
        lblNomGrille = new JTextArea(grille.getNomGrille());
        lblNomGrille.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblNomGrille.setForeground(Color.WHITE);
        lblNomGrille.setLineWrap(true);
        lblNomGrille.setWrapStyleWord(true);
        lblNomGrille.setBackground(new Color(22, 67, 111));
        lblNomGrille.setMinimumSize(new Dimension(150, 90));
        lblNomGrille.setPreferredSize(new Dimension(150, 90));
        lblNomGrille.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblNomGrille.setForeground(Color.WHITE);
        lblNomGrille.setEnabled(false);

        lblDifficulte = new JLabel(new ImageIcon("images/0etoiles.png"));

        lblImageCouleurs = new JLabel(new ImageIcon("images/couleurs.png"));
        lblImagePotBlanc = new JLabel();
        lblImagePotBleu = new JLabel();
        lblImagePotRouge = new JLabel();
        lblImagePotOrange = new JLabel();
        lblImagePotJaune = new JLabel();
        lblImagePotVert = new JLabel();
        lblImagePotRose = new JLabel();
        lblImagePotMarron = new JLabel();
        lblImageGomme = new JLabel();

        lblImageVague = new JLabel(new ImageIcon("images/vague1.png"));
        lblImageVagueRetourne = new JLabel(new ImageIcon("images/vague2.png"));

        btnRetour = new JButton("Retour");

        /* EVENEMENTS */
        lblImagePotBlanc.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                potSelectionne(Couleur.BLANC);
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
                potSelectionne(Couleur.BLEU);
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
                potSelectionne(Couleur.JAUNE);
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
                potSelectionne(Couleur.ORANGE);
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
                potSelectionne(Couleur.ROUGE);
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
                potSelectionne(Couleur.VERT);
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
                potSelectionne(Couleur.ROSE);
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
                potSelectionne(Couleur.MARRON);
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
                potSelectionne(Couleur.NC);
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

        addComponentListener(new ComponentAdapter() {

            /**
             * Met à jour la zone de dessin lors du redimensionnement de la
             * fenêtre.
             */
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
                validate();
            }
        });
    }

    /**
     * Permet de mettre à jour l'affichage du pot utilisé pour compléter la
     * grille.
     *
     * @param potSelec Couleur sélectionnée.
     */
    protected abstract void potSelectionne(Couleur potSelec);

    /**
     * Permet de colorer la case pointée par la souris.
     *
     * @param e Evènement lié à la souris.
     */
    protected abstract void colorerCase(MouseEvent e);
}
