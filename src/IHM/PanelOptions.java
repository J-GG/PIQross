package IHM;

import Données.Options;
import Données.Options.Difficulte;
import Données.Options.Son;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Conteneur affichant les Options.
 *
 * @author J-GG
 */
public class PanelOptions extends JPanel {

    private Options options;
    private Timer timer;

    public PanelOptions(final Fenêtre parent) {
        /* INITIALISATIONS */

        options = Options.get();

        setLayout(new BorderLayout());

        JPanel paneOptions = new JPanel(new GridBagLayout());
        paneOptions.setBackground(new Color(22, 67, 111));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitre = new JLabel("Options");
        lblTitre.setFont(new Font("Calibri", Font.PLAIN, 30));
        lblTitre.setForeground(Color.WHITE);

        //Reglage difficulté
        JLabel lblDifficulte = new JLabel("Difficulté : ");
        lblDifficulte.setFont(new Font("Calibri", Font.PLAIN, 16));
        lblDifficulte.setForeground(Color.WHITE);
        ButtonGroup bgDifficulte = new ButtonGroup();
        final JRadioButton radioDifficulteFacile = new JRadioButton("Facile");
        final JRadioButton radioDifficulteMoyen = new JRadioButton("Moyen");
        JRadioButton radioDifficulteDifficile = new JRadioButton("Difficile");

        radioDifficulteFacile.setBackground(new Color(22, 67, 111));
        radioDifficulteMoyen.setBackground(new Color(22, 67, 111));
        radioDifficulteDifficile.setBackground(new Color(22, 67, 111));
        radioDifficulteFacile.setFont(new Font("Calibri", Font.PLAIN, 16));
        radioDifficulteFacile.setForeground(Color.WHITE);
        radioDifficulteMoyen.setFont(new Font("Calibri", Font.PLAIN, 16));
        radioDifficulteMoyen.setForeground(Color.WHITE);
        radioDifficulteDifficile.setFont(new Font("Calibri", Font.PLAIN, 16));
        radioDifficulteDifficile.setForeground(Color.WHITE);

        bgDifficulte.add(radioDifficulteFacile);
        bgDifficulte.add(radioDifficulteMoyen);
        bgDifficulte.add(radioDifficulteDifficile);

        if (options.getDifficulte() == Difficulte.DIFFICILE) {
            radioDifficulteDifficile.setSelected(true);
        } else {
            radioDifficulteFacile.setSelected(true);
        }

        //Son
        JLabel lblSon = new JLabel("Son : ");
        lblSon.setFont(new Font("Calibri", Font.PLAIN, 16));
        lblSon.setForeground(Color.WHITE);
        ButtonGroup bgSon = new ButtonGroup();
        final JRadioButton radioSonOn = new JRadioButton("Activé");
        JRadioButton radioSonOff = new JRadioButton("Désactivé");

        radioSonOn.setBackground(new Color(22, 67, 111));
        radioSonOff.setBackground(new Color(22, 67, 111));
        radioSonOn.setFont(new Font("Calibri", Font.PLAIN, 16));
        radioSonOn.setForeground(Color.WHITE);
        radioSonOff.setFont(new Font("Calibri", Font.PLAIN, 16));
        radioSonOff.setForeground(Color.WHITE);

        bgSon.add(radioSonOn);
        bgSon.add(radioSonOff);

        if (options.getSon() == Son.DESACTIVE) {
            radioSonOff.setSelected(true);
        } else {
            radioSonOn.setSelected(true);
        }

        //Enregistrer ou annuler
        JButton btnRetour = new JButton("Retour");
        JButton btnEnregistrer = new JButton("Enregistrer");

        final JLabel lblModifOK = new JLabel();
        lblModifOK.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblModifOK.setForeground(Color.WHITE);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblModifOK.setIcon(null);
                timer.stop();
            }
        });

        /* AJOUTS */
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 4;
        paneOptions.add(lblTitre, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        paneOptions.add(lblDifficulte, gbc);

        gbc.gridx = 1;
        paneOptions.add(radioDifficulteFacile, gbc);

        gbc.gridx = 2;
        paneOptions.add(radioDifficulteMoyen, gbc);

        gbc.gridx = 3;
        paneOptions.add(radioDifficulteDifficile, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        paneOptions.add(lblSon, gbc);

        gbc.gridx = 1;
        paneOptions.add(radioSonOn, gbc);

        gbc.gridx = 2;
        paneOptions.add(radioSonOff, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        paneOptions.add(btnRetour, gbc);

        gbc.gridx = 2;
        paneOptions.add(btnEnregistrer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        paneOptions.add(lblModifOK, gbc);

        add(paneOptions, BorderLayout.CENTER);

        /* EVENEMENTS */
        btnRetour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.menuPrincipal();
            }
        });

        btnEnregistrer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioDifficulteFacile.isSelected()) {
                    options.setDifficulte(Difficulte.FACILE);
                } else if (radioDifficulteMoyen.isSelected()) {
                    options.setDifficulte(Difficulte.MOYEN);
                } else {
                    options.setDifficulte(Difficulte.DIFFICILE);
                }

                if (radioSonOn.isSelected()) {
                    options.setSon(Son.ACTIVE);
                } else {
                    options.setSon(Son.DESACTIVE);
                }

                options.enregistrerOptions();

                lblModifOK.setText(null);
                lblModifOK.setIcon(new ImageIcon("images/termine.png"));

                timer.start();
            }
        });

    }
}
