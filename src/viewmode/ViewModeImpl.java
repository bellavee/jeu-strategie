package viewmode;

import modele.*;
import plateau.Plateau;

public class ViewModeImpl implements ViewMode {

    private Plateau plateau;
    private boolean mode;

    public ViewModeImpl(Plateau plateau) {
        this.plateau = plateau;
        this.mode = false;
    }

    @Override
    public void modeJoueur() {
        for (Bombe b : this.plateau.getBombeListe())
            b.setVisible(false);
        for (Mine m : this.plateau.getMineListe())
            m.setVisible(false);
        this.mode = false;
    }

    @Override
    public void modeLecteur() {
        for (Bombe b : this.plateau.getBombeListe())
            b.setVisible(true);
        for (Mine m : this.plateau.getMineListe())
            m.setVisible(true);
        this.mode = true;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean getMode() {
        return this.mode;
    }
}
