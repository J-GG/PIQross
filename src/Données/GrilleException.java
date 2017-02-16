package Données;

/**
 * Exception levée lorsque un fichier contenant une grille n'est pas au bon
 * format.
 *
 * @author J-GG
 */
public class GrilleException extends Exception {

    /**
     * Construit une exception liée à un fichier incorrect.
     *
     * @param nomFichier Nom du fichier ayant levé cette exception.
     */
    public GrilleException(String nomFichier) {
        System.out.println("GrilleException : Le fichier " + nomFichier + " devant contenir une grille est incorrecte.");
    }
}
