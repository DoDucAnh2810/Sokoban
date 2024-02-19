package Structures;

public class SequenceListe<Type> implements Sequence<Type> {
   Cellule<Type> tete;
   Cellule<Type> queue;
   int length;

   public SequenceListe() {
      tete = null;
      queue = null;
      length = 0;
   }

   public SequenceListe(Type[] valeurs) {
      tete = queue = null;

      for(int i = 0; i < valeurs.length; ++i) {
         insereQueue(valeurs[i]);
      }

      length = valeurs.length;
   }

   public void insereTete(Type element) {
      if (tete == null) {
         tete = queue = new Cellule<Type>(element, (Cellule<Type>)null);
      } else {
         tete = new Cellule<Type>(element, tete);
      }

      ++length;
   }

   public void insereQueue(Type element) {
      if (queue == null) {
         tete = queue = new Cellule<Type>(element, (Cellule<Type>)null);
      } else {
         queue.suivant = new Cellule<Type>(element, (Cellule<Type>)null);
         queue = queue.suivant;
      }

      ++length;
   }

   public Type extraitTete() throws RuntimeException {
      if (estVide()) {
         throw new RuntimeException("Sequence vide");
      } else {
         Type valeur = tete.valeur;
         if (tete.suivant == null) {
            tete = queue = null;
         } else {
            tete = tete.suivant;
         }

         --length;
         return valeur;
      }
   }

   public boolean estVide() {
      return tete == null;
   }

   public void affiche() {
      for(Cellule<Type> tmp = tete; tmp != null; tmp = tmp.suivant) {
         System.out.print(String.valueOf(tmp.valeur) + " ");
      }

      System.out.println();
   }

   public int length() {
      return length;
   }

   public Iterateur<Type> iterateur() {
      return new IterateurSequenceListe<Type>(this);
   }
}
