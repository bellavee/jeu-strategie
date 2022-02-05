package plateau;

import viewmode.ViewModeImpl;

public class PlateauGrand extends Plateau {

    public PlateauGrand(int size, int nombreJoueur) {
        super(size, nombreJoueur);
        this.plateauType = new PlateauGrandImpl();
        this.plateauType.init(this);
        this.creerJoueur();
        this.viewMode = new ViewModeImpl(this);
        this.viewMode.modeJoueur();
    }

}
