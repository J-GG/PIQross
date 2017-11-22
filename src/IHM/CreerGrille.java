package IHM;

import Données.Case.Couleur;
import Données.Case.Etat;
import Données.Grille;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;

/**
 * Affiche une boîte de dialogue permettant de paramétrer la nouvelle grille qui sera créée.
 * @author Genest
 */
public class CreerGrille extends JDialog
{
    private Fenêtre parent;
    
    /**
     * Nom de la grille.
     */
    private String nomGrille;
    
    /**
     * Dimension de la grille.
     */
    private int taille;
    
    /**
     * Durée moyenne estimée pour finir la grille.
     */
    private String duree;
    
    private JTextField txtNom;
    private JFormattedTextField txtDuree;
    private JSlider slideTaille;
    private JLabel lblTaille;
    private Timer timer;
    private JLabel lblerreur;
    
    /**
     * Crée la boîte de dialogue.
     * @param p Fenêtre affichant cette boîte de dialogue.
     */
    public CreerGrille(Fenêtre p)
    {
        /* INITIALISATIONS */
        super(p, "Créer une grille", true);
        
        this.parent = p;
        nomGrille = "";
        duree = "";
        taille = 25;
        
        //Modifications de la fenêtre
        setSize(300, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setUndecorated(true);//Supprime la bordure
         
        //Panel contenant tout l'affichage
        JPanel infos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        infos.setBackground(new Color(22, 67, 111));
        infos.setBorder(new BevelBorder(BevelBorder.RAISED));
        
        //Composants
        JLabel lblCreerVide = new JLabel("Créer une grille vide :");
        lblCreerVide.setForeground(Color.WHITE);
        
        JLabel lblNom = new JLabel("Nom : ");
        lblNom.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblNom.setForeground(Color.WHITE);
        
        txtNom = new JTextField();

        lblTaille = new JLabel("Taille : 25 * 25");
        lblTaille.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblTaille.setForeground(Color.WHITE);
        
        slideTaille = new JSlider();
        slideTaille.setMaximum(40);
        slideTaille.setMinimum(2);
        slideTaille.setValue(25);
        slideTaille.setBackground(new Color(22, 67, 111));
        
        JLabel lblDuree = new JLabel("Duree moyenne (min) : ");
        lblDuree.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblDuree.setForeground(Color.WHITE);

        txtDuree = new JFormattedTextField(NumberFormat.getIntegerInstance());
        
        JButton btnOK = new JButton("OK");
        JButton btnAnnuler = new JButton("Annuler");
        
        //Charger à partir d'une image
        JLabel lblChargerImage = new JLabel("A partir d'une image (Test.png):");
        lblChargerImage.setForeground(Color.WHITE);

        JButton btnChargerImage = new JButton("Charger une image");
        
        //Message affiché en cas d'erreur
        lblerreur = new JLabel();
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblerreur.setIcon(null);
                timer.stop();
            }
        });
        
        /* AJOUTS */
        
        gbc.insets.top = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infos.add(lblCreerVide, gbc);
        
        gbc.gridy = 1;
        infos.add(lblNom, gbc);
        
        gbc.gridx = 1;
        infos.add(txtNom, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        infos.add(lblDuree, gbc);
        
        gbc.gridx = 1;
        infos.add(txtDuree, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        infos.add(lblTaille, gbc);
        
        gbc.gridy = 4;
        infos.add(slideTaille, gbc);

        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets.top = 20;
        infos.add(btnOK, gbc);
        
        gbc.gridx = 1;
        infos.add(btnAnnuler, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        infos.add(lblChargerImage, gbc);
 
        gbc.gridy = 7;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        infos.add(btnChargerImage, gbc);
       
        gbc.gridy = 8;
        infos.add(lblerreur, gbc);
        
        add(infos);
        
 
        
        /* EVENEMENTS */
        slideTaille.addChangeListener(new ChangeListener(){
            
            //Modifie la taille affichée lorsque le curseur est déplacé
            @Override
            public void stateChanged(ChangeEvent e){
                lblTaille.setText("Taille : " + slideTaille.getValue() + " * " + slideTaille.getValue());
            }
        });
        
        btnChargerImage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog chooser = new FileDialog(parent, "Ouvrir...", FileDialog.LOAD);
                chooser.setVisible(true);
                boolean ok = true;
                
                if(chooser.getFile() != null)
                {
                    Grille grille = convertirImage(new File(chooser.getFile()));
                    if(grille != null)
                    {
                        setVisible(false);
                        
                        parent.getContentPane().removeAll();
                        parent.getContentPane().validate();
                        
                        
                        Editeur editeur = new Editeur(parent, grille);
                        parent.getContentPane().add(editeur, BorderLayout.CENTER);
                        
                        
                        parent.getContentPane().repaint();
                        parent.getContentPane().validate();
                    }
                    else
                        ok = false;
                }
                else
                    ok = false;
               
                if(!ok)
                {
                    lblerreur.setIcon(new ImageIcon("images/erreurFic.png"));
                    timer.start();
                }
                
            }
        });
        
        btnOK.addActionListener(new ActionListener() {
            
            //En cliquant sur le bouton, on vérifie et si la vérif est bonne, on crée la grille
            @Override
            public void actionPerformed(ActionEvent e) {
               
                if(controleInfos())
                {
                    parent.getContentPane().removeAll();
                    parent.getContentPane().validate();
                    
                    Editeur editeur = new Editeur(parent, new Grille(new File("persos/" + nomGrille + ".txt"), taille, nomGrille, duree, 1));
                    parent.getContentPane().add(editeur, BorderLayout.CENTER);
                    setVisible(false);
                    
                }
                else
                {
                    lblerreur.setIcon(new ImageIcon("images/erreurFic.png"));
                    timer.start();
                }
            }
        });
        
        btnAnnuler.addActionListener(new ActionListener() {

            //Si on veut fermer la fenêtre
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        setVisible(true);
    }
    
    /**
     * Vérifie les informations entrées et les enregistre si elles sont correctes.
     * @return Vrai si les informations sont correctes.
     */
    private boolean controleInfos()
    {
        boolean ok = true;
        
        nomGrille = txtNom.getText();
        
        if(txtDuree.getText().matches("[0-9][0-9]?[0-9]?"))
            duree = txtDuree.getText();
        else
            ok = false;

        
        taille = slideTaille.getValue();
        
        return ok;
        
    }
    
    /**
     * Converti le fichier passé en paramètre en grille.
     * @param ficImage Fichier à convertir.
     * @return Grille construite à partir du fichier.
     */
    private Grille convertirImage(File ficImage) {
        /**
         * Chargement de l'image
         * Si trop grande=>redimensionnement
         * récupération des couleurs des pixels : lecture par ligne
         * création d'une grille de x*x avec x le plus grand coté de l'image
         * calcul des couleurs de la grille
         * insertion des couleurs dans l'apercu et lagrille
         */
        double redimensionnement = 1.0;
        Grille grille = null;
        


            /*
             * PROBLEME QUAND L'IMAGE EST TROP GRANDE
             */
            ImageIcon img = new ImageIcon(ficImage.getAbsolutePath());
  
            Image g = img.getImage();

                        //image = scale(image, redimensionnement);
            BufferedImage jo = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
             jo.getGraphics().drawImage(g, 0 , 0, 40, 40, null);
            int w = jo.getWidth();
            int h = jo.getHeight();
            int[] rgb = new int[w*h];
            jo.getRGB(0, 0, w, h, rgb, 0, w);
            System.out.println(new Color(jo.getRGB(0, 0)).getRed());
            int nbCases = h;
            if(h < w)
                nbCases = w;

            grille = new Grille(new File("persos/" + ficImage.getName() + ".txt"), nbCases, ficImage.getName(), "45", 1);

            Color couleur;
            int k = 0;

            for(int i = 0; i < h; i++)
            {
                for(int j = 0; j < w; j++)
                {
                    couleur = new Color(rgb[k]);
                    grille.getImage()[i][j].setModif(Etat.COLORE, Couleur.BLANC, couleur);
                    grille.getPlateau()[i][j].setModif(Etat.COLORE, Couleur.BLANC, couleur);
                    k++;
                }
            }

        
        return grille;
    }
    
    /**
     * Redimensionne une image par un facteur passé en paramètre.
     * @param bi Image à redimensionner.
     * @param scaleValue Facteur de redimensionnement.
     * @return R L'image redimensionnée.
     */
    public static BufferedImage scale(BufferedImage bi, double scaleValue) {
        AffineTransform tx = new AffineTransform();
        
        tx.scale(scaleValue, scaleValue);
        
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        
        BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),(int) (bi.getHeight() * scaleValue), bi.getType());
        
        return op.filter(bi, biNew);
    }
}
