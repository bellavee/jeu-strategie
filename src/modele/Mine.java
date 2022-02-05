package modele;

/**
 * Classe représentant l'objet Mine sur le plateau de jeu, ses variables
 * d'instances représentent sa visibilité selon l'observateur, ses dégats, et
 * son état
 */

public class Mine {

    private Case cas;
    private boolean visible;
    private boolean exploseEtat;
    private int degat;

    public Mine(Case cas) {
        this.cas = cas;
        this.cas.setMine(true);
        this.visible = false;
        this.exploseEtat = false;
        this.degat = 20;
    }

    /**
     * @return Case
     */
    public Case getCase() {
        return this.cas;
    }

    /**
     * @return boolean
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * @return boolean
     */
    public boolean getExploseStat() {
        return this.exploseEtat;
    }

    /**
     * @return int
     */
    public int getDegat() {
        return this.degat;
    }

    /**
     * @param bool
     */
    public void setVisible(boolean bool) {
        this.visible = bool;
    }

    public void setExploseStat() {
        this.exploseEtat = true;
    }

}
