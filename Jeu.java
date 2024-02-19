import java.io.File;

public class Jeu {
    private LecteurNiveaux lecteur;
    private Niveau niveau;
    
    public Jeu() {
        lecteur = new LecteurNiveaux(new File("Data/niveaux.txt"));
        niveau = lecteur.lisProchainNiveau();
    }

    Niveau niveau() {
        return niveau;
    }

    boolean prochainNiveau() {
        niveau = lecteur.lisProchainNiveau();
        if (niveau == null)
            return false;
        else
            return true;
    }
}
