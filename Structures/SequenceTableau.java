package Structures;

public class SequenceTableau<Type> implements Sequence<Type> {
   int tete;
   int queue;
   Type[] tableau;
   int size = 10000;

    @SuppressWarnings("unchecked")
    public SequenceTableau() {
      tableau = (Type[]) new Object[size];
    }

    @SuppressWarnings("unchecked")
    public SequenceTableau(Type[] valeurs) {
        tableau = (Type[]) new Object[size];
        if (valeurs.length == 0) {
            tete = queue = -1;
        } else {
            tete = 0;
            queue = valeurs.length - 1;

            for(int i = 0; i < valeurs.length; ++i) {
            tableau[i] = valeurs[i];
            }
        }

    }

    public int circulaire(int index) throws IndexOutOfBoundsException {
        if (index < -1) {
            throw new IndexOutOfBoundsException();
        } else {
            return index == -1 ? size - 1 : index % size;
        }
    }

    public boolean estVide() {
        return tete == -1;
    }

    public boolean dedans(int index) {
        return !estVide() && 
               (tete <= queue && tete <= index && index <= queue || 
               tete > queue && index >= tete || index <= queue);
    }

    public void insereTete(Type element) throws RuntimeException {
        int nouvelle_position = circulaire(tete - 1);
        if (nouvelle_position == queue) {
            throw new RuntimeException("Overflow");
        } else {
            tableau[nouvelle_position] = element;
            tete = nouvelle_position;
        }
    }

    public void insereQueue(Type element) {
        int nouvelle_position = circulaire(queue + 1);
        if (nouvelle_position == tete) {
            throw new RuntimeException("Overflow");
        } else {
            tableau[nouvelle_position] = element;
            queue = nouvelle_position;
        }
    }

    public Type extraitTete() throws RuntimeException {
        if (estVide()) {
            throw new RuntimeException("Sequence vide");
        } else {
            Type valeur = tableau[tete];
            if (tete == queue) {
                tete = queue = -1;
            } else {
                tete = circulaire(tete + 1);
            }

            return valeur;
        }
    }

    public void affiche() {
        for(int i = tete; dedans(i); ++i) {
            Object var10001 = tableau[i % size];
            System.out.print(String.valueOf(var10001) + " ");
        }

        System.out.println();
    }

    public int length() {
        return tableau.length;
    }

    public Iterateur<Type> iterateur() {
        return new IterateurSequenceTableau<Type>(this);
    }
}
