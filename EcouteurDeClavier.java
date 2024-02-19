

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EcouteurDeClavier implements KeyListener {
    NiveauGraphique niveauGraphique;

    public EcouteurDeClavier(NiveauGraphique niveauGraphique) {
        this.niveauGraphique = niveauGraphique;
    }
    @Override public void keyPressed(KeyEvent event) {
		Jeu jeu = niveauGraphique.jeu;
		Niveau niveau = jeu.niveau();
		if (niveau.gagne()) {
			if (jeu.prochainNiveau()){
				niveauGraphique.repaint();
				return;
			} else
				System.exit(0);
		}
        int keyCode = event.getKeyCode();
        int ligne, colonne;
        ligne = niveau.pousseur_i;
        colonne = niveau.pousseur_j;
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                ligne--;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                ligne++;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                colonne--;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                colonne++;
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_ESCAPE:
                // Code pour basculer entre le mode fenêtré et plein écran
                return;
            default:
                return;
        }
        if (niveau.estVide(ligne, colonne) || 
            (niveau.aCaisse(ligne, colonne) &&
             niveau.movedCaisse(ligne, colonne))) {
            niveau.movePousseur(ligne, colonne);
            niveauGraphique.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent var1) { }
 
    @Override
    public void keyReleased(KeyEvent var1) { }
}
