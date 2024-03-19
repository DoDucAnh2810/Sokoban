/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 *
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 *
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 *
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 *
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

//import javax.swing.*;
package Controleur;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Vue.*;
import Modele.*;

public class EcouteurDeSouris implements MouseListener {
	private NiveauGraphique niveauGraphique;

	public EcouteurDeSouris(NiveauGraphique niveauGraphique) {
		this.niveauGraphique = niveauGraphique;
	}

	private Point calculateCaisse(MouseEvent event) {
		Point choosenCaisse = new Point();
		Integer cellSize = niveauGraphique.cellSize;
		choosenCaisse.x = (event.getX() - niveauGraphique.offset.x)/ cellSize;
		choosenCaisse.y = (event.getY() - niveauGraphique.offset.y - 30) / cellSize;
		return choosenCaisse;
	}

	@Override
	public void mousePressed(MouseEvent event) {
		Point choosenCaisse = calculateCaisse(event);
		int ligne = choosenCaisse.y;
		int colonne = choosenCaisse.x;
		Jeu jeu = niveauGraphique.jeu;
		Niveau niveau = jeu.niveau();
		if (niveau.gagne()) {
			if (jeu.prochainNiveau()){
				niveauGraphique.repaint();
				return;
			} else
				System.exit(0);
		}
		if (niveau.validMove(ligne, colonne)) {
			niveau.movePousseur(ligne, colonne);
			niveauGraphique.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }
	
	@Override
	public void mouseExited(MouseEvent e) { }
}
