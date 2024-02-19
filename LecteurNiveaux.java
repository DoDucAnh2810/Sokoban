import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class LecteurNiveaux {
   private Scanner initScanner;
   private Scanner lireScanner;

   public LecteurNiveaux(File file) {
      try {
         initScanner = new Scanner(file);
         lireScanner = new Scanner(file);
      } catch (FileNotFoundException err) {
         err.printStackTrace();
      }

   }

   private Niveau initProchainNiveau() {
      int nombreColonnes = 0;
      int nombreLignes = 0;
      String nom = "Non defini";
      boolean termine = false;

      while(initScanner.hasNextLine() && !termine) {
         String ligne = initScanner.nextLine();
         if (ligne.isEmpty()) {
            termine = true;
         } else if (ligne.charAt(0) == ';') {
            nom = ligne.substring(2);
         } else {
            ++nombreLignes;
            if (ligne.length() > nombreColonnes) {
               nombreColonnes = ligne.length();
            }
         }
      }

      if (termine) {
         return new Niveau(nombreLignes, nombreColonnes, nom);
      } else {
         initScanner.close();
         return null;
      }
   }

   public Niveau lisProchainNiveau() {
      int i = 0;
      boolean termine = false;
      Niveau niveau = initProchainNiveau();
      if (niveau == null) {
         lireScanner.close();
         return null;
      } else {
         for(; lireScanner.hasNextLine() && !termine; ++i) {
            String ligne = lireScanner.nextLine();
            if (ligne.isEmpty()) {
               termine = true;
            } else if (ligne.charAt(0) != ';') {
               for(int j = 0; j < ligne.length(); ++j) {
                  niveau.tableau[i][j] = ligne.charAt(j);
                  if (ligne.charAt(j) == '@' || ligne.charAt(j) == '+') {
                     niveau.pousseur_i = i;
                     niveau.pousseur_j = j;
                  } else if (ligne.charAt(j) == '*') {
                     niveau.nombreBut++;
                     niveau.nombreCaisseSurBut++;
                  } else if (ligne.charAt(j) == '.')
                     niveau.nombreBut++;
               }
            }
         }
         return niveau;
      }
   }
}
