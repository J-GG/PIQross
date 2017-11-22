package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Conteneur affichant les instructions du pIQross.
 * @author Genest
 */
public class Instructions extends JPanel
{
    private Fenêtre parent;
    
    /**
     * Etape actuellement affichée.
     */
    private int etape;
    
    private JLabel lblEtape;
    private JLabel lblImage;
    private JTextArea textArea;
    private JButton btnPrec;
    private JButton btnSuiv;
    
    /**
     * Créée l'interface des instructions.
     * @param p Fenêtre affichant les instructions.
     */
    public Instructions(Fenêtre p)
    {
        /* INITIALISATIONS */
        
        parent = p;
        etape = 1;
        
        setLayout(new BorderLayout());
        
        JPanel paneInstructions = new JPanel(new GridBagLayout());
        paneInstructions.setBackground(new Color(22, 67, 111));
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel lblTitre = new JLabel("Instructions");
        lblTitre.setFont(new Font("Calibri", Font.PLAIN, 30));
        lblTitre.setForeground(Color.WHITE);
        
        lblEtape = new JLabel();
        lblEtape.setFont(new Font("Calibri", Font.PLAIN, 22));
        lblEtape.setForeground(Color.WHITE);

        lblImage = new JLabel();
        
        btnPrec = new JButton("Précédent");
        btnPrec.setEnabled(false);
        
        JButton btnMenu = new JButton("Menu");
        
        btnSuiv = new JButton("Suivant");
        
        textArea = new JTextArea(null, "", 8, 0);
        textArea.setFont(new Font("Calibri", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMaximumSize(new Dimension(getWidth(), getHeight()));
        textArea.setBackground(new Color(22, 67, 111));
        
        //On commence les instructions à la première étape
        etape1();
        
        /* AJOUTS */
        
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 4;
        paneInstructions.add(lblTitre, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        paneInstructions.add(lblEtape, gbc);
        
        gbc.gridy = 2;
        gbc.insets.top = 20;
        paneInstructions.add(lblImage, gbc);
        
        gbc.insets.top = 20;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        paneInstructions.add(textArea, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.insets.top = 50;
        gbc.insets.right = 20;
        paneInstructions.add(btnPrec, gbc);
        
        
        gbc.gridx = 1;
        paneInstructions.add(btnMenu, gbc);
        
        gbc.gridx = 2;
        paneInstructions.add(btnSuiv, gbc);
        
        add(paneInstructions, BorderLayout.CENTER);
        
        
        /* EVENEMENTS */
        btnPrec.addActionListener(new ActionListener() {

            /**
             * Apppel l'étape précédente si on est pas déjà à la première.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(etape - 1 >= 1)
                    etape--;
                
                if(etape == 1)
                    etape1();
                else if(etape == 2)
                    etape2();
                else if(etape == 3)
                    etape3();
                else if(etape == 4)
                    etape4();
                else if(etape == 5)
                    etape5();
                else if(etape == 6)
                    etape6();
                else if(etape == 7)
                    etape7();
                else if(etape == 8)
                    etape8();
            }
        });
        
        btnMenu.addActionListener(new ActionListener() {

            /**
             * Retourne au menu principal.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.menuPrincipal();
            }
        });
        
        btnSuiv.addActionListener(new ActionListener() {
            
            /**
             * Apppel l'étape suivante si on est pas déjà à la dernière.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(etape + 1 <= 9)
                    etape++;
                
                if(etape == 2)
                    etape2();
                else if(etape == 3)
                    etape3();
                else if(etape == 4)
                    etape4();
                else if(etape == 5)
                    etape5();
                else if(etape == 6)
                    etape6();
                else if(etape == 7)
                    etape7();
                else if(etape == 8)
                    etape8();
                else if(etape == 9)
                    etape9();
            }
        });
        
        addComponentListener(new ComponentAdapter() {
            
            /**
             * Met à jour la zone de dessin lors du redimensionnement de la fenêtre.
             */
            @Override
            public void componentResized(ComponentEvent e) {
                parent.repaint();
                parent.validate();
            }
        });
    }

    /**
     * Permet d'afficher les informations de l'étape n°1.
     */
    private void etape1()
    {
        lblImage.setIcon(new ImageIcon("images/etape1.png"));
        lblEtape.setText("Etape 1");
        textArea.setText("Ok, donc vous ne savez pas jouer au pIQross ? Pas de problème, suivez le guide !");
        
        btnPrec.setEnabled(false);
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°2.
     */
    private void etape2()
    {
        lblImage.setIcon(new ImageIcon("images/etape1.png"));
        lblEtape.setText("Etape 2");
        textArea.setText("pIQross est présenté sous une forme de grille carrée contenant des nombres au dessus d'elle. Ces nombres représentent le nombre de croix que vous devez faire. Les nombres sur la gauche renseignent le nombre de croix par lignes et les croix en haut donnent le nombre de croix par colonnes.");

        btnPrec.setEnabled(true);
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°3.
     */
    private void etape3()
    {
        lblImage.setIcon(new ImageIcon("images/etape3.png"));
        lblEtape.setText("Etape 3");
        textArea.setText("Les nombres vous indiquent combien de cases sont colorées (et de quelle couleur) et de quelle longueur est la zone qu'elles délimitent. Par exemple, si les nombres indiquent '1 1 1' alors il y a trois cases à cocher, avec au moins un espace entre elles.");
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°4.
     */
    private void etape4()
    {
        lblImage.setIcon(new ImageIcon("images/etape4.png"));
        lblEtape.setText("Etape 4");
        textArea.setText("Quand le nombre est '5' par exemple, alors il y a une rangée de cinq cases à cocher consécutivement.");
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°5.
     */
    private void etape5()
    {
        lblImage.setIcon(new ImageIcon("images/etape5.png"));
        lblEtape.setText("Etape 5");
        textArea.setText("Maintenant, la dernière ligne indique '2 2'. Cela signifie qu'il y a deux zones de deux cases à cocher avec un espace entre les deux.");
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°6.
     */
    private void etape6()
    {
        lblImage.setIcon(new ImageIcon("images/etape6.png"));
        lblEtape.setText("Etape 6");
        textArea.setText("Vous pouvez effectuer un clic droit sur une case pour la marquer d'une croix noire. Celle-ci peut vous aider à repérer les cases que vous savez ne pas être colorées. Vous pouvez la retirer en effctuant la même manipulation ou en la cochant.");
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°7.
     */
    private void etape7()
    {
        lblImage.setIcon(new ImageIcon("images/etape7.png"));
        lblEtape.setText("Etape 7");
        textArea.setText("Si vous cliquez sur une case qui ne devait pas l'être, une croix rouge apparaîtra. Ce qui fera augmenter votre score. Le but étant d'avoir le plus petit score possible.");
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°8.
     */
    private void etape8()
    {
        lblImage.setIcon(new ImageIcon("images/etape1.png"));
        lblEtape.setText("Etape 8");
        textArea.setText("Amusez-vous et finissez toutes les grilles. Vous pouvez également créer vos propres grilles grâce à l'éditeur.");
        
        btnSuiv.setEnabled(true);
    }
    
    /**
     * Permet d'afficher les informations de l'étape n°8.
     */
    private void etape9()
    {
        lblImage.setIcon(new ImageIcon("images/etape9.png"));
        lblEtape.setText("Etape 9");
        textArea.setText("Appuyez sur \"P\" ou sur \"M\" en éditant la grille afin de modifier le nombre de lignes et colonnes. En cliquant sur \"Grille\\Apercu\", switchez pour modifier soit la grille soit l'aperçu. Attention : vous ne vous pouvez ajouter des cases/lignes/colonnes qu'en mode Grille.");
        
        btnSuiv.setEnabled(false);
    }
}
