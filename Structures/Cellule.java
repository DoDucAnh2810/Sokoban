package Structures;

public class Cellule<Type> {
   Type valeur;
   Cellule<Type> suivant;

   Cellule(Type valeur, Cellule<Type> suivant) {
      this.valeur = valeur;
      this.suivant = suivant;
   }
}
