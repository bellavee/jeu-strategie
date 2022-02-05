package plateau;

import viewmode.ViewModeImpl;

public class PlateauPetit extends Plateau {

    public PlateauPetit(int size, int nombreJoueur) {
        super(size, nombreJoueur);
        this.plateauType = new PlateauPetitImpl();
        this.plateauType.init(this);
        this.creerJoueur();
        this.viewMode = new ViewModeImpl(this);
        this.viewMode.modeJoueur();
    }
}
