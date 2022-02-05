package modele;

import java.awt.Point;

/**
 * Classe représentant une Case du plateau de jeu constituée de variables
 * d'instances définissants : ce qui se trouve sur cette case, et ses
 * coordonnées
 */

public class Case {
    private Point point;
    private boolean estMine;
    private boolean estBombe;
    private boolean estPerso;
    private boolean mur;
    private boolean bouclier;

    public Case(Point point) {
        this.point = point;
        this.estMine = false;
        this.estBombe = false;
        this.estPerso = false;
        this.mur = false;
        this.bouclier = false;
    }

    /**
     * @return Point
     */
    public Point getPoint() {
        return this.point;
    }

    /**
     * @return boolean
     */
    public boolean estMine() {
        return this.estMine;
    }

    /**
     * @return boolean
     */
    public boolean estBombe() {
        return this.estBombe;
    }

    /**
     * @return boolean
     */
    public boolean estPerso() {
        return this.estPerso;
    }

    /**
     * @return boolean
     */
    public boolean estMur() {
        return this.mur;
    }

    /**
     * @return boolean
     */
    public boolean estBouclier() {
        return this.bouclier;
    }

    /**
     * @param bool
     */
    public void setMine(boolean bool) {
        this.estMine = bool;
    }

    /**
     * @param bool
     */
    public void setBombe(boolean bool) {
        this.estBombe = bool;
    }

    /**
     * @param bool
     */
    public void setPerso(boolean bool) {
        this.estPerso = bool;
    }

    public void setMurs() {
        this.mur = true;
    }

    /**
     * @param bool
     */
    public void setBouclier(boolean bool) {
        this.bouclier = bool;
    }

    /**
     * @return int
     */
    public int getX() {
        return this.point.x;
    }

    /**
     * @return int
     */
    public int getY() {
        return this.point.y;
    }

}
