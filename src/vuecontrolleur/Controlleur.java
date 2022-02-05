package vuecontrolleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import modele.Direction;
import modele.Personnage;
import plateau.Plateau;
import viewmode.*;

import java.awt.*;

public class Controlleur implements KeyListener {

    private ViewMode viewMode;
    private Plateau plateau;
    private Personnage joueur;
    public boolean haut, bas, gauche, droit;
    public boolean botHaut, botBas, botGauche, botDroit;
    public Personnage bot;
    public List<Point> botPos;
    public List<Point> pos;

    public Controlleur(Plateau plateau) {
        this.plateau = plateau;
        this.viewMode = new ViewModeImpl(plateau);
    }

    /**
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        this.joueur = this.plateau.getJoueur();

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_Z)
            this.plateau.deplacer(this.plateau.getMove(this.joueur, Direction.HAUT), this.joueur);

        if (code == KeyEvent.VK_S)
            this.plateau.deplacer(this.plateau.getMove(this.joueur, Direction.BAS), this.joueur);

        if (code == KeyEvent.VK_Q)
            this.plateau.deplacer(this.plateau.getMove(this.joueur, Direction.GAUCHE), this.joueur);

        if (code == KeyEvent.VK_D)
            this.plateau.deplacer(this.plateau.getMove(this.joueur, Direction.DROIT), this.joueur);

        if (code == KeyEvent.VK_UP && this.joueur.getNombreDeMunitions() > 0) {
            pos = this.plateau.getTir(Direction.HAUT, this.joueur);
            this.plateau.shoot(pos, this.joueur);
            haut = true;
        }

        if (code == KeyEvent.VK_DOWN && this.joueur.getNombreDeMunitions() > 0) {
            pos = this.plateau.getTir(Direction.BAS, this.joueur);
            this.plateau.shoot(pos, this.joueur);
            bas = true;
        }

        if (code == KeyEvent.VK_LEFT && this.joueur.getNombreDeMunitions() > 0) {
            pos = this.plateau.getTir(Direction.GAUCHE, this.joueur);
            this.plateau.shoot(pos, this.joueur);
            gauche = true;
        }

        if (code == KeyEvent.VK_RIGHT && this.joueur.getNombreDeMunitions() > 0) {
            pos = this.plateau.getTir(Direction.DROIT, this.joueur);
            this.plateau.shoot(pos, this.joueur);
            droit = true;
        }

        if (code == KeyEvent.VK_SPACE)
            this.plateau.restart();

        if (code == KeyEvent.VK_V) {
            if (this.viewMode.getMode())
                this.viewMode.modeJoueur();
            else
                this.viewMode.modeLecteur();
        }

    }

    /**
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP)
            haut = false;

        if (code == KeyEvent.VK_DOWN)
            bas = false;

        if (code == KeyEvent.VK_LEFT)
            gauche = false;

        if (code == KeyEvent.VK_RIGHT)
            droit = false;

    }

}
