import javax.swing.JFrame;

public class InterfaceGraphique implements Runnable {
	JFrame frame;
    Jeu jeu;

    public InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
    }

	public void run() {
		// Creation d'une fenetre
		frame = new JFrame("Sokoban");
	
		// Ajout de notre composant de dessin dans la fenetre
		NiveauGraphique niveauGraphique = new NiveauGraphique(jeu);
		frame.add(niveauGraphique);
		frame.addMouseListener(new EcouteurDeSouris(niveauGraphique));
		frame.addKeyListener(new EcouteurDeClavier(niveauGraphique));

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setSize(500, 300);
		frame.setVisible(true);
	}
}
