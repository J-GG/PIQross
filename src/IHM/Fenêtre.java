package IHM;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Affiche une Fenêtre permettant de jouer à pIQcross.
 *
 * @author J-GG
 */
public class Fenêtre extends JFrame {

    private Container pane;

    /**
     * Créée une Fenêtre,la centre, et affiche le menu principal du jeu.
     */
    public Fenêtre() {
        setTitle("pIQross");
        setVisible(true);
        setPreferredSize(new Dimension(850, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack();

        //On centre la fenêtre
        Dimension d = getToolkit().getScreenSize();
        setLocation((int) (d.getWidth() - getWidth()) / 2, (int) (d.getHeight() - getHeight()) / 2);

        initComponent();
        menuPrincipal();
    }

    /**
     * Initialise les composants de la Fenêtre.
     */
    private void initComponent() {
        pane = getContentPane();
        pane.setLayout(new BorderLayout());
    }

    /**
     * Créée une instance de MenuPrincipal, fille de JPanel, et l'affiche.
     */
    public void menuPrincipal() {
        pane.removeAll();
        pane.validate();

        MenuPrincipal menuPrincipal = new MenuPrincipal(this);
        pane.add(menuPrincipal, BorderLayout.CENTER);

        pane.repaint();
        pane.validate();
    }

    /**
     * Créée une instance de PanelNiveaux, fille de JPanel, et l'affiche.
     *
     * @param jeu Vaut vrai si l'affichage de la grille a pour but de jouer.
     * Faux si c'est pour l'édition.
     */
    public void choisirNiveau(boolean jeu) {
        pane.removeAll();
        pane.validate();

        PanelNiveaux panelNiveaux = new PanelNiveaux(this, jeu);
        pane.add(panelNiveaux, BorderLayout.CENTER);

        pane.repaint();
        pane.validate();
    }

    /**
     * Créée une instance de Instructions, fille de JPanel, et l'affiche.
     */
    public void instructions() {
        pane.removeAll();
        pane.validate();

        Instructions instructions = new Instructions(this);
        pane.add(instructions, BorderLayout.CENTER);

        pane.repaint();
        pane.validate();
    }

    /**
     * Créée une instance de PanelOptions, fille de JPanel, et l'affiche.
     */
    public void options() {
        pane.removeAll();
        pane.validate();

        PanelOptions options = new PanelOptions(this);
        pane.add(options, BorderLayout.CENTER);

        pane.repaint();
        pane.validate();
    }
}
