import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class Niveau {
   private int lignes;
   private int colonnes;
   char[][] tableau;
   private String nom;
   int pousseur_i;
   int pousseur_j;
   int nombreBut;
   int nombreCaisseSurBut;
   Clip super_idol_hardstyle;
   Clip metal_pipe;
   Clip footstep;
   Clip moan;
   Clip sweden;

   public Niveau(int lignes, int colonnes, String nom) {
      this.lignes = lignes;
      this.colonnes = colonnes;
      tableau = new char[lignes][colonnes];

      for(int i = 0; i < lignes; ++i) {
         for(int j = 0; j < colonnes; ++j) {
            videCase(i, j);
         }
      }
      nombreBut = nombreCaisseSurBut = 0;
      fixeNom(nom);
      try {
         super_idol_hardstyle = AudioSystem.getClip();
         super_idol_hardstyle.open(AudioSystem.getAudioInputStream(new File("/home/do/Music/super_idol_hardstyle.wav")));
         metal_pipe = AudioSystem.getClip();
         metal_pipe.open(AudioSystem.getAudioInputStream(new File("/home/do/Music/metal_pipe.wav")));
         footstep = AudioSystem.getClip();
         footstep.open(AudioSystem.getAudioInputStream(new File("/home/do/Music/block_short.wav")));
         moan = AudioSystem.getClip();
         moan.open(AudioSystem.getAudioInputStream(new File("/home/do/Music/moan.wav")));
         sweden = AudioSystem.getClip();
         sweden.open(AudioSystem.getAudioInputStream(new File("/home/do/Music/Sweden.wav")));
         FloatControl moanControl = (FloatControl) moan.getControl(FloatControl.Type.MASTER_GAIN);
         moanControl.setValue(-17.0f);
         FloatControl metal_pipeControl = (FloatControl) metal_pipe.getControl(FloatControl.Type.MASTER_GAIN);
         metal_pipeControl.setValue(6.0f);
         FloatControl footstepControl = (FloatControl) footstep.getControl(FloatControl.Type.MASTER_GAIN);
         footstepControl.setValue(6.0f);
         FloatControl swedenControl = (FloatControl) sweden.getControl(FloatControl.Type.MASTER_GAIN);
         swedenControl.setValue(-17.0f);
         sweden.start();
      } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
         e.printStackTrace();
      }
   }

   public int lignes() {
      return lignes;
   }

   public int colonnes() {
      return colonnes;
   }

   public char valeurCase(int i, int j) {
      return tableau[i][j];
   }

   public String nom() {
      return nom;
   }

   public void fixeNom(String s) {
      nom = s;
   }

   public void videCase(int i, int j) {
      tableau[i][j] = ' ';
   }

   public void ajouteMur(int i, int j) {
      tableau[i][j] = '#';
   }

   public void ajoutePousseur(int i, int j) {
      pousseur_i = i;
      pousseur_j = j;
      if (tableau[i][j] == '.')
         tableau[i][j] = '+';
      else
         tableau[i][j] = '@';
   }

   public void ajouteCaisse(int i, int j) {
      if (tableau[i][j] == '.') {
         tableau[i][j] = '*';
         nombreCaisseSurBut++;
         if (!gagne()) {
            metal_pipe.setFramePosition(0);
            metal_pipe.start();
         }
      } else
         tableau[i][j] = '$';
   }

   public void ajouteBut(int i, int j) {
      tableau[i][j] = '.';
   }

   public boolean estVide(int i, int j) {
      if (tableau[i][j] == ' ' || tableau[i][j] == '.')
         return true;
      else {
         if (!aCaisse(i, j)){
            moan.setFramePosition(0);
            moan.start();
            moan.drain();
         }
         return false;
      }
   }

   public boolean aMur(int i, int j) {
      return tableau[i][j] == '#';
   }

   public boolean aPousseur(int i, int j) {
      return tableau[i][j] == '@' || tableau[i][j] == '+';
   }

   public boolean aCaisse(int i, int j) {
      return tableau[i][j] == '$' || tableau[i][j] == '*';
   }

   public boolean aBut(int i, int j) {
      return tableau[i][j] == '.';
   }

   public void affiche() {
      System.out.println("Niveau: " + nom);

      for(int i = 0; i < lignes; ++i) {
         System.out.print(tableau[i]);
         System.out.println();
      }
   }

   public boolean adjacentPousseur(int i, int j) {
      if (j == pousseur_j && (i == (pousseur_i - 1) ||
                              i == (pousseur_i + 1))) 
         return true;
      else if (i == pousseur_i && (j == (pousseur_j - 1) ||
                                  j == (pousseur_j + 1))) 
         return true;
      else {
         moan.setFramePosition(0);
         moan.start();
         moan.drain();
         return false; 
      }
   }

   public void removePousseur() {
      if (tableau[pousseur_i][pousseur_j] == '+')
         tableau[pousseur_i][pousseur_j] = '.';
      else 
         tableau[pousseur_i][pousseur_j] = ' ';
   }

   public void movePousseur(int i, int j) {
      removePousseur();
      ajoutePousseur(i, j);
   }

   public void removeCaisse(int i, int j) {
      if (tableau[i][j] == '*') {
         tableau[i][j] = '.';
         nombreCaisseSurBut--;
      } else 
         tableau[i][j] = ' ';  
   }

   public boolean valideLigne(int i) {
      return 0 <= i && i < lignes;
   }

   public boolean valideColonne(int j) {
      return 0 <= j && j < colonnes;
   }
   public boolean movedCaisse(int i, int j) {
      if (!adjacentPousseur(i, j) || !aCaisse(i, j))
         return false;
      int target_i, target_j;
      if (i == pousseur_i) {
         target_i = pousseur_i;
         if (j == pousseur_j - 1)
            target_j = pousseur_j - 2;
         else
            target_j = pousseur_j + 2;
         if (!valideColonne(target_j))
            return false;
      } else { // j == pousseur_j
         target_j = pousseur_j;
         if (i == pousseur_i - 1)
            target_i = pousseur_i - 2;
         else
            target_i = pousseur_i + 2;
         if (!valideLigne(target_i))
            return false;
      }
      if (estVide(target_i, target_j)) {
         removeCaisse(i, j);
         ajouteCaisse(target_i, target_j);
         if (gagne()) {
            super_idol_hardstyle.setFramePosition(0);
            super_idol_hardstyle.start();
         }
         footstep.setFramePosition(0);
         footstep.start();
         footstep.drain();
         return true;
      } else {
         moan.setFramePosition(0);
         moan.start();
         moan.drain();
         return false;
      }
   }

   public boolean gagne() {
      return nombreBut == nombreCaisseSurBut;
   }
}
 