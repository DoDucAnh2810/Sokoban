package Structures;

public interface Sequence<Type> {
   void insereTete(Type var1);

   void insereQueue(Type var1);

   Type extraitTete();

   boolean estVide();

   void affiche();

   int length();
}
