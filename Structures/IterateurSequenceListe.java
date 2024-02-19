package Structures;

import java.util.NoSuchElementException;

public class IterateurSequenceListe<Type> implements Iterateur<Type> {
   private SequenceListe<Type> sequence;
   private Cellule<Type> position;

   public IterateurSequenceListe(SequenceListe<Type> sequence) {
      this.sequence = sequence;
      position = sequence.tete;
   }

   public boolean aProchain() {
      return position != null;
   }

   public Type prochain() throws NoSuchElementException {
      if (!aProchain()) {
         throw new NoSuchElementException();
      } else {
         Type valeur = position.valeur;
         position = position.suivant;
         return valeur;
      }
   }

   public void supprime() throws IllegalStateException {
      if (position == sequence.tete) {
         throw new IllegalStateException();
      } else {
         if (position == sequence.tete.suivant) {
            sequence.tete = sequence.tete.suivant;
         } else {
            Cellule<Type> tmp;
            for(tmp = sequence.tete; tmp.suivant != position && tmp.suivant.suivant != position; tmp = tmp.suivant) {
            }

            tmp.suivant = position;
         }

      }
   }
}
