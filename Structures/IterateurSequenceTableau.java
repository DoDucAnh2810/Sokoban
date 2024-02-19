package Structures;

import java.util.NoSuchElementException;

public class IterateurSequenceTableau<Type> implements Iterateur<Type> {
   private SequenceTableau<Type> sequence;
   private int position;

   public IterateurSequenceTableau(SequenceTableau<Type> sequence) {
      this.sequence = sequence;
      position = 0;
   }

   public boolean aProchain() {
      return sequence.dedans(position);
   }

   public Type prochain() throws NoSuchElementException {
      if (!aProchain()) {
         throw new NoSuchElementException();
      } else {
         Type valeur = sequence.tableau[position];
         position = sequence.circulaire(position + 1);
         return valeur;
      }
   }

   public void supprime() throws IllegalStateException {
      if (position == sequence.tete) {
         throw new IllegalStateException();
      } else {
         if (sequence.queue == sequence.tete) {
            sequence.tete = sequence.queue = -1;
         } else {
            int i;
            for(i = sequence.circulaire(position - 1); sequence.dedans(i) && sequence.dedans(sequence.circulaire(i - 1)); i = sequence.circulaire(i - 1)) {
               sequence.tableau[sequence.circulaire(i)] = sequence.tableau[sequence.circulaire(i - 1)];
            }

            sequence.tete = i;
         }

      }
   }
}
