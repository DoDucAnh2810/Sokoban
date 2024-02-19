package Structures;

public interface Iterateur<Type> {
   boolean aProchain();

   Type prochain();

   void supprime();
}
