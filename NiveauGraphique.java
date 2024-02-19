import static Global.Configuration.ouvre;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;


public class NiveauGraphique extends JComponent{
    Jeu jeu;
    Integer cellSize;
    Image but;
    Image caisseSurBut;
    Image caisse;
    Image mur;
    Image pousseur;
    Image sol;

    public NiveauGraphique(Jeu jeu) {
        try {
            this.jeu = jeu;
			but = ImageIO.read(ouvre("Data/But.png"));
			caisseSurBut = ImageIO.read(ouvre("Data/Caisse_sur_but.png"));
			caisse = ImageIO.read(ouvre("Data/Caisse.png"));
			mur = ImageIO.read(ouvre("Data/Mur.png"));
			pousseur = ImageIO.read(ouvre("Data/Pousseur.png"));
			sol = ImageIO.read(ouvre("Data/Sol.png"));
		} catch (IOException err) {
			err.printStackTrace();
			System.exit(3);
		}
    }

    @Override
    public void paintComponent(Graphics g) {
        Niveau niveau = jeu.niveau();
        Graphics2D drawable = (Graphics2D) g;
        int totalWidth = getWidth();
        int totalHeight = getHeight();
        int nbRows = niveau.lignes();
        int nbCols = niveau.colonnes();
        cellSize = Math.min(totalWidth / nbCols, totalHeight / nbRows);
        setSize(cellSize*nbCols, cellSize*nbRows);

        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbCols; j++) {
                Image currentImage;
                switch (niveau.valeurCase(i, j)) {
                case '.':
                    currentImage = but;
                    break;
                case '*':
                    currentImage = caisseSurBut;
                    break;
                case '$':
                    currentImage = caisse;
                    break;
                case '#':
                    currentImage = mur;
                    break;
                case '@':
                    currentImage = pousseur;
                    drawable.drawImage(sol, j*cellSize, i*cellSize,  
                                       cellSize, cellSize, null);
                    break;
                case '+':
                    currentImage = pousseur;
                    drawable.drawImage(but, j*cellSize, i*cellSize,
                                    cellSize, cellSize, null);
                    break;
                default:
                    currentImage = sol;
                    break;
                }
                drawable.drawImage(currentImage, j*cellSize, i*cellSize,  
                                   cellSize, cellSize, null);
            }
        }

	}
}
