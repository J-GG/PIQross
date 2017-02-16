package IHM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Conteneur affichant le menu principal du jeu.
 *
 * @author J-GG
 */
public class MenuPrincipal extends JPanel {

    /**
     * Créée un conteneur disposant de tous les composants nécessaire au menu
     * principal.
     *
     * @param parent
     */
    public MenuPrincipal(final Fenêtre parent) {

        /* INITIALISATIONS */
        setLayout(new BorderLayout());

        //Panel principal contenant tous les éléments du menu
        JPanel paneDebut = new JPanel(new GridBagLayout());
        paneDebut.setBackground(new Color(22, 67, 111));
        GridBagConstraints gbc = new GridBagConstraints();

        //Composants du Menu
        JLabel lblTitre = new JLabel("<html>p<font color=\"#5599BB\">IQ</font>cross</html>");
        lblTitre.setForeground(Color.WHITE);
        lblTitre.setFont(new Font("Calibri", Font.PLAIN, 40));
        JButton btnCommencer = new JButton("Jouer");
        JButton btnInstructions = new JButton("Instructions");
        JButton btnOptions = new JButton("Options");
        JButton btnEditeur = new JButton("Editeur");

        /* AJOUTS */
        gbc.gridy = 0;
        paneDebut.add(lblTitre, gbc);

        gbc.gridy = 1;
        gbc.insets.top = 100;
        paneDebut.add(btnCommencer, gbc);

        gbc.gridy = 2;
        gbc.insets.top = 10;
        paneDebut.add(btnInstructions, gbc);

        gbc.gridy = 3;
        paneDebut.add(btnOptions, gbc);

        gbc.gridy = 4;
        paneDebut.add(btnEditeur, gbc);

        add(paneDebut, BorderLayout.CENTER);

        /* EVEMENENTS */
        btnCommencer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.choisirNiveau(true);
            }
        });

        btnInstructions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.instructions();
            }
        });

        btnOptions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.options();
            }
        });

        btnEditeur.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.choisirNiveau(false);
            }
        });
    }
}
