package IHM;

import Données.Grille;
import Données.GrilleException;
import Données.ListeNiveaux;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Conteneur affichant la liste des niveaux de pIQross.
 * @author Genest
 */
public class PanelNiveaux extends JPanel
{
    private Fenêtre parent;
    
    /**
     * Liste de toutes les grilles qui sont affichées.
     */
    private ArrayList<Grille> grilles;
    
    /**
     * Nombre de lignes (contenant les informations des niveaux) pouvant être affichées à la fois.
     */
    private int nbPartPossAffichees; 
    
    /*
     * Position du curseur du scroll. Exprimé en pourcentage.
     */
    private int posCurseur; 
    
    /**
     * Tous les niveaux ne pouvant êter affichés, n° de celle affichée en premier. Change en scrollant.
     */
    private int premPartAffichee; 
    
    private int distanceEntreLignes;
    private boolean jeu;
    private boolean erreurFichier;
    private Timer timer;

    //Images
    private BufferedImage imgPartie;
    private BufferedImage imgScroll;
    private BufferedImage imgCurseur;
    private BufferedImage imgErreurFichier;
    private BufferedImage imgTitreNiveauxOff;
    private BufferedImage imgNiveaux;
    private BufferedImage imgTitreNiveauxPerso;
    private BufferedImage imgChargerFichier;
    private BufferedImage imgRetour;
    private BufferedImage imgTemps;
    private BufferedImage spriteDifficultes;
    
    /**
     * Créée le conteneur et initialise les données.
     * @param p Fenêtre ayant créée l'instance de PanelNiveaux.
     * @param jeu Vaut vrai si l'affichage de la grille a pour but de jouer. Faux si c'est pour l'édition.
     */
    public PanelNiveaux(Fenêtre p, boolean j)
    {
        /* INITIALISATIONS */
        parent = p;
        jeu = j;
        
        setLayout(new BorderLayout());

        ListeNiveaux niveaux = new ListeNiveaux(jeu);
        grilles = niveaux.getGrilles();
        posCurseur = 0;
        distanceEntreLignes = 80;
        erreurFichier = false;
        
        try {
            imgPartie = ImageIO.read(new File("images/partie.png"));
            imgCurseur = ImageIO.read(new File("images/curseur.png"));
            imgScroll = ImageIO.read(new File("images/scroll.png"));
            imgErreurFichier = ImageIO.read(new File("images/erreurFic.png"));
            imgTitreNiveauxOff = ImageIO.read(new File("images/titreNiveauxOff.png"));
            imgNiveaux = ImageIO.read(new File("images/boutonsNiveaux.png"));
            imgTitreNiveauxPerso = ImageIO.read(new File("images/titreNiveauxPersos.png"));
            imgChargerFichier = ImageIO.read(new File("images/chargerGrille.png"));
            imgRetour = ImageIO.read(new File("images/retour.png"));
            imgTemps = ImageIO.read(new File("images/temps.png"));
            spriteDifficultes = ImageIO.read(new File("images/etoiles.png"));
            
        } catch (IOException ex) {}
        
        
        
        /* EVENEMENTS */
                
        addMouseMotionListener(new MouseMotionListener() {

            /**
             * Changement de l'apparence du pointeur.
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                
                //Si la souris passe sur un niveau...
                int numLigne = (e.getY() - 85) / distanceEntreLignes;
                
                boolean surPartie = false;
                if((numLigne < nbPartPossAffichees && numLigne < grilles.size()) && (e.getY() >= 85) && (e.getX() >= 25 + parent.getWidth()/4) && (e.getX() <= parent.getWidth() - 55))
                    surPartie = true;

                
                //...ou si la souris passe sur le scroll...
                boolean surScroll = false;
                if(e.getX() >= parent.getWidth() - 50 && e.getX() <= parent.getWidth() - 29 && e.getY() >= 85 + ((parent.getHeight() - 105 - 100) * posCurseur/100) && e.getY() <= 85 + ((parent.getHeight() - 105 - 100) * posCurseur/100) + 21)
                    surScroll = true;
                
               //..ou sur Niveaux Officiels..
                boolean surNiveauxOff = false;
                if(e.getX() >= 15&& e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= 100 && e.getY() <= 100 + imgNiveaux.getHeight() && jeu)
                    surNiveauxOff = true;
                
                //..ou sur Niveaux Persos..
                boolean surNiveauxPersos = false;
                if((e.getX() >= 15 && e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= 185 && e.getY() <= 185 + imgNiveaux.getHeight() && jeu) || (e.getX() >= 15 && e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= 100 && e.getY() <= 100 + imgNiveaux.getHeight() && !jeu))
                    surNiveauxOff = true;

                //..ou sur Créer Grille..
                boolean surCreer= false;
                if(e.getX() >= 15 && e.getX() <=15 + imgNiveaux.getWidth() && e.getY() >= parent.getHeight() - 300 && e.getY() <= parent.getHeight() - 300 + imgChargerFichier.getHeight() && !jeu)
                    surCreer = true;
                
                //..ou sur Charger Grille..
                boolean surCharger= false;
                if(e.getX() >= 15 && e.getX() <=15 + imgChargerFichier.getWidth() && e.getY() >= parent.getHeight() - 230 && e.getY() <= parent.getHeight() - 230 + imgChargerFichier.getHeight())
                    surCharger = true;
                
                //..ou sur Retour...
                boolean surRetour = false;
                if(e.getX() >= 15 && e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= parent.getHeight() - 165 && e.getY() <= parent.getHeight() - 165 + imgNiveaux.getHeight())
                    surRetour = true;
                
                //...on change le curseur de la souris
                if(surPartie || surScroll || surNiveauxOff || surNiveauxPersos || surCreer || surCharger || surRetour)
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
            /**
             * Déplacement du curseur.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                
                //Vérification que l'on est sur le curseur
                if(e.getX() >= parent.getWidth() - 50 && e.getX() <= parent.getWidth() - 29)
                {
                    int taille = parent.getHeight() - 105 - 100;
                    
                    if((e.getY() - 85) >= 0 && (e.getY() - 85) <= taille)
                        posCurseur = (e.getY() - 85)*100/taille;
               
                    repaint();
                    validate();
                }
            }
            
        });
        
        addMouseWheelListener(new MouseWheelListener() {

            /**
             * Déplacement du curseur.
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                //Si on avance ou on recule la molette
                if (e.getWheelRotation() > 0 && posCurseur + e.getWheelRotation() <= 100)
                    posCurseur += e.getWheelRotation();
                else if(e.getWheelRotation() < 0 && posCurseur + e.getWheelRotation() >= 0)
                    posCurseur += e.getWheelRotation();
                
                repaint();
                validate();
            }
        });
        
        addMouseListener(new MouseAdapter() {
            
            /**
             * Gestion des clics de souris.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                
                //Clic sur un niveau
                int numNiveau = (e.getY() - 85) / distanceEntreLignes + premPartAffichee;
                if((numNiveau < nbPartPossAffichees + premPartAffichee && numNiveau < grilles.size()) && (e.getY() >= 85) && (e.getX() >= 25 + parent.getWidth()/4) && (e.getX() <= parent.getWidth() - 55))
                {
                    parent.getContentPane().removeAll();
                    parent.getContentPane().validate();
                    
                    if(jeu)//Si on veut lancer le jeu
                    {
                        Jeu panelJeu = new Jeu(parent, grilles.get(numNiveau));
                        parent.getContentPane().add(panelJeu, BorderLayout.CENTER);
                    }
                    else//Si on veut éditer la grille
                    {
                        Editeur editeur = new Editeur(parent, grilles.get(numNiveau));
                        parent.getContentPane().add(editeur, BorderLayout.CENTER);
                    }
                }
                //Clic sur Niveaux Officiels
                else if(e.getX() >= 15&& e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= 100 && e.getY() <= 100 + imgNiveaux.getHeight() && jeu)
                {
                    //On affiche les niveaux officiels
                    ListeNiveaux niveaux = new ListeNiveaux(true);
                    grilles = niveaux.getGrilles();
                    posCurseur = 0;
                }
                //Clic sur Niveaux Persos
                else if((e.getX() >= 15 && e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= 185 && e.getY() <= 185 + imgNiveaux.getHeight() && jeu) || (e.getX() >= 15 && e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= 100 && e.getY() <= 100 + imgNiveaux.getHeight() && !jeu))
                {
                    //On affiche les niveaux persos
                    ListeNiveaux niveaux = new ListeNiveaux(false);
                    grilles = niveaux.getGrilles();
                    posCurseur = 0;
                }
                //Clic sur Créer une grille
                else if(e.getX() >= 15 && e.getX() <=15 + imgChargerFichier.getWidth() && e.getY() >= parent.getHeight() - 300 && e.getY() <= parent.getHeight() - 300 + imgChargerFichier.getHeight() && !jeu)
                {

                    CreerGrille creerGrille = new CreerGrille(parent);

                }
                //Clic sur Charger une grille
                else if(e.getX() >= 15 && e.getX() <=15 + imgChargerFichier.getWidth() && e.getY() >= parent.getHeight() - 230 && e.getY() <= parent.getHeight() - 230 + imgChargerFichier.getHeight())
                {
                    //Affiche une fenêtre de dialogue permettant de choisir un fichier
                    FileDialog chooser = new FileDialog(parent, "Ouvrir...", FileDialog.LOAD);
                    chooser.setVisible(true);
                    
                    if(chooser.getFile() != null)
                    {                        
                        
                        try//Si le fichier est correcte, on peut afficher la grille
                        {
                            Grille grilleChargee = new Grille(new File(chooser.getDirectory() + "" +chooser.getFile()));
                            
                            parent.getContentPane().removeAll();
                            parent.getContentPane().validate();
                            
                            if(jeu)//Si on veut lancer le jeu
                            {
                                Jeu panelJeu = new Jeu(parent, grilleChargee);
                                parent.getContentPane().add(panelJeu, BorderLayout.CENTER);
                            }
                            else//Si on veut éditer la grille
                            {
                                Editeur editeur = new Editeur(parent, grilleChargee);
                                parent.getContentPane().add(editeur, BorderLayout.CENTER);
                            }
                            
                        } catch(GrilleException ex)//Sinon, on affiche un message d'erreur
                        {
                            timer = new Timer(1500, new ActionListener() {
                                
                                
                                /**
                                 * Indique que le message liée à l'erreur de fichier doit-être affiché.
                                 */
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    erreurFichier = false;
                                    timer.stop();
                                    
                                    parent.getContentPane().repaint();
                                    parent.getContentPane().validate();
                                }
                            });
                            
                            erreurFichier = true;
                            timer.start();
                        }
                    }
                }
                //Clic sur Retour au menu
                else if(e.getX() >= 15 && e.getX() <= 15 + imgNiveaux.getWidth() && e.getY() >= parent.getHeight() - 165 && e.getY() <= parent.getHeight() - 165 + imgNiveaux.getHeight())
                {
                    //On appelle le menu principal
                    parent.menuPrincipal();
                }
                
                parent.getContentPane().repaint();
                parent.getContentPane().validate();
            }
        });
    }
    
    /**
     * Dessine le contenu de PanelNiveaux. Cette méthode affiche donc la liste des niveaux.
     * @param g Objet Graphics permettant de dessiner.
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        /** Lissage du texte et des dessins */
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        /* Calculs */
        
        //Nombre de parties qu'on peut afficher
        nbPartPossAffichees = (parent.getHeight() - 105 - 90)/distanceEntreLignes;
        
        //On soustrait le nombre de parties qu'on peut afficher au nombre total pour eviter les lignes vides en déplaçant le curseur
        int nbLignes = grilles.size() - nbPartPossAffichees;
        if(nbLignes < 0)
            nbLignes = 0;
        
        //Calcul de la première partie à afficher à partir de la position du curseur
        premPartAffichee = (nbLignes*posCurseur)/100;
        
        
        /* Fonds */
        
        //Fond bleu foncé recouvrant toute la Fenêtre
        g.setColor(new Color(22, 67, 111));
        g.fillRect(0, 0, parent.getWidth(), parent.getHeight());
        
        //Fond plus clair ne laissant qu'un liseré en haut et en bas du précédent fond
        g.setColor(new Color(28, 83, 144));
        g.fillRect(0, 50, parent.getWidth(), parent.getHeight() - 130);
        
        //Fonds bleu foncé du menu et de la liste des niveaux
        g.setColor(new Color(22, 67, 111));
        g.fillRect(10, 60, parent.getWidth()/4, parent.getHeight() - 150);
        g.fillRect(20 + parent.getWidth()/4, 60, parent.getWidth() - parent.getWidth()/4 - 40, parent.getHeight() - 150);
        
        /* Menu de gauche */
        g.setColor(Color.white);
        int ordonneePacks = 75;
        if(jeu)
        {
            g.drawImage(imgTitreNiveauxOff,  15, ordonneePacks, this);
            g.drawImage(imgNiveaux,  15, ordonneePacks + 25, this);
            g.drawString("Niveaux Officiels", 45, ordonneePacks + 55);
            ordonneePacks += 85;
        }
        
        g.drawImage(imgTitreNiveauxPerso, 15, ordonneePacks, this);
        g.drawImage(imgNiveaux, 15, ordonneePacks + 25, this);
        g.drawString("Niveaux Persos", 45, ordonneePacks + 55);
        
        if(!jeu)
        {
            g.drawImage(imgNiveaux,  15, parent.getHeight() - 300, this);
            g.drawString("Créer Grille", 60, parent.getHeight() - 270);
        }
        
        g.drawImage(imgChargerFichier,  15, parent.getHeight() - 230, this);
        g.drawImage(imgRetour, 15, parent.getHeight() - 165, this);

        
        /* Scroll */
        g.drawImage(imgScroll, parent.getWidth() - 50, 85, 24, parent.getHeight() - 105 - 80, this);
        g.drawImage(imgCurseur, parent.getWidth() - 50, 85 + ((parent.getHeight() - 125 - 80) * posCurseur / 100), this);

        /* Titre différent suivant si l'affichage lance le jeu ou l'éditeur */
        if(jeu)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Calibri", Font.PLAIN, 30));
            g.drawString("Jouer", 50, 35);
        }
        else
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Calibri", Font.PLAIN, 30));
            g.drawString("Editeur", 50, 35);
        }
        
        
        /* Liste des niveaux */
        g.setFont(new Font("Calibri", Font.PLAIN, 20));
        g.drawString(grilles.size() + " niveaux", parent.getWidth()/4 + 40, 80);
        
        int partAffichee = premPartAffichee;
        for(int i = 0; i < nbPartPossAffichees && partAffichee < grilles.size(); i++) 
        {
            //Fond
            g.drawImage(imgPartie, 25 + parent.getWidth()/4,  85 + i*distanceEntreLignes, parent.getWidth() - parent.getWidth()/4 - 80, 76, this);
            
            //Nom
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString(grilles.get(partAffichee).getNomGrille(), 40 + parent.getWidth()/4, 105 + i*distanceEntreLignes);
            
            //Taille
            g.setFont(new Font("Calibri", Font.PLAIN, 12));
            g.drawString("Taille : " + grilles.get(partAffichee).getNbCases() + " X " + grilles.get(partAffichee).getNbCases(), parent.getWidth() - 160, 103 + i*distanceEntreLignes);
            
            //Nb couleurs
            g.drawString(grilles.get(i).getListeCouleurs().size() + " couleurs", parent.getWidth() - 160, 120 + i*distanceEntreLignes);
            
            //Temps
            g.drawImage(imgTemps,  parent.getWidth() - 380,  120 + i*distanceEntreLignes, this);
            g.drawString("Temps moyen", parent.getWidth() - 350, 132 + i*distanceEntreLignes);
            g.drawString(grilles.get(i).getDureeMoyenne() + " min", parent.getWidth() - 350, 142 + i*distanceEntreLignes);
            
            //Difficulté
            g.drawImage(spriteDifficultes.getSubimage(0, 25*grilles.get(partAffichee).getDifficulte(), 94, 25), 40 + parent.getWidth()/4,  120 + i*distanceEntreLignes, this);

            
            partAffichee++;
        }
        
        /* Erreur du fichier à charger */
        if(erreurFichier)
            g.drawImage(imgErreurFichier, parent.getWidth()/2, parent.getHeight()/2, null);
    }
}
