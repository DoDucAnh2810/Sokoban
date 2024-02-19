package Structures;

import java.util.ArrayList;
import java.util.List;

public class FAP<Type> {
   private List<Couple<Type>> heap;

   public FAP() {
      heap = new ArrayList<Couple<Type>>();
   }

   private int fils_gauche(int index) {
      return 2 * index + 1;
   }

   private int fils_droite(int index) {
      return 2 * index + 2;
   }

   private int pere(int index) {
      return (index - 1) / 2;
   }

   private boolean estNoeud(int index) {
      return index < heap.size();
   }

   private boolean estNoeudInterne(int index) {
      return estNoeud(fils_gauche(index)) || estNoeud(fils_droite(index));
   }

   private int priorite(int index) {
      return heap.get(index).priorite;
   }

   private int plus_grand_fils(int index) {
      if (!estNoeudInterne(index)) {
         return -1;
      } else if (!estNoeud(fils_gauche(index))) {
         return fils_droite(index);
      } else if (!estNoeud(fils_droite(index))) {
         return fils_gauche(index);
      } else {
         return priorite(fils_gauche(index)) > priorite(fils_droite(index)) ? fils_gauche(index) : fils_droite(index);
      }
   }

   public boolean estVide() {
      return heap.size() == 0;
   }

   public void insere(Type valeur, int priorite) {
      Couple<Type> newCouple = new Couple<Type>(valeur, priorite);
      int index = heap.size();
      heap.add(newCouple);

      while(index > 0 && priorite(index) > priorite(pere(index))) {
         Couple<Type> tmp = heap.get(index);
         heap.set(index, heap.get(pere(index)));
         heap.set(pere(index), tmp);
         index = pere(index);
      }

   }

   public Type extrait() {
      Type valeur = heap.get(0).valeur;
      int lastIndex = heap.size() - 1;
      heap.set(0, heap.get(lastIndex));
      heap.remove(lastIndex);

      int index = 0; 
      while (estNoeudInterne(index) && (priorite(index) < priorite(plus_grand_fils(index)))) {
         int pgf = plus_grand_fils(index);
         Couple<Type> tmp = heap.get(index);
         heap.set(index, heap.get(pgf));
         heap.set(pgf, tmp);
         index = pgf;
      }

      return valeur;
   }
}
