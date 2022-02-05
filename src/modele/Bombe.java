package modele;

/**
 * Classe représentant l'objet Bombe sur le plateau de jeu, ses variables
 * d'instances représentent sa visibilité selon l'observateur, ses dégats, et
 * son état
 */

public class Bombe {

    private Case cas;
    private boolean visible;
    private int exploseEtat;
    private int degat;

    public Bombe(Case cas) {
        this.cas = cas;
        this.cas.setBombe(true);
        this.visible = false;
        this.exploseEtat = 3;
        this.degat = 99;
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
     * @return int
     */
    public int getExploseStat() {
        return this.exploseEtat;
    }

    /**
     * @return int
     */
    public int getDegat() {
        return this.degat;
    }

    public void setExploseStat() {
        this.exploseEtat--;
    }

    /**
     * @param bool
     */
    public void setVisible(boolean bool) {
        this.visible = bool;
    }

}
