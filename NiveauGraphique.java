import static Global.Configuration.ouvre;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;


public class NiveauGraphique extends JComponent{
    JFrame frame;
    Jeu jeu;
    int totalWidth;
    int totalHeight;
    Niveau niveau;
    int nbRows;
    int nbCols;
    int cellSize;
    Point offset;
    Image but;
    Image caisseSurBut;
    Image caisse;
    Image mur;
    Image pousseur;
    Image sol;

    public NiveauGraphique(JFrame frame, Jeu jeu) {
        try {
            this.frame = frame;
            this.jeu = jeu;
            offset = new Point();
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

    private void updateCellSize() {
        cellSize =  Math.min(totalWidth / nbCols, totalHeight / nbRows);
    }

    private void updateOffset() {
        int levelWidth = cellSize * nbCols;
        int levelHeight = cellSize * nbRows;
        offset.x = (totalWidth - levelWidth) / 2;
        offset.y = (totalHeight - levelHeight) / 2;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        totalWidth = frame.getWidth();
        totalHeight = frame.getHeight() - 30;
        niveau = jeu.niveau();
        nbRows = niveau.lignes();
        nbCols = niveau.colonnes();
        updateCellSize();
        updateOffset();

        for (int i = 0; i*cellSize <= totalHeight; i++)
            for (int j = 0; j*cellSize <= totalWidth; j++)
                drawable.drawImage(sol, j*cellSize, i*cellSize,
                                    cellSize, cellSize, null);  

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
                    drawable.drawImage(sol, offset.x + j*cellSize, offset.y + i*cellSize,  
                                       cellSize, cellSize, null);
                    break;
                case '+':
                    currentImage = pousseur;
                    drawable.drawImage(but, offset.x + j*cellSize, offset.y + i*cellSize,
                                    cellSize, cellSize, null);
                    break;
                default:
                    currentImage = sol;
                    break;
                }
                drawable.drawImage(currentImage, offset.x + j*cellSize, offset.y + i*cellSize,  
                                   cellSize, cellSize, null);
            }
        }

	}
}
