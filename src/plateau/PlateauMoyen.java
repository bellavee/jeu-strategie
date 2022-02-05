package plateau;

import viewmode.ViewModeImpl;

public class PlateauMoyen extends Plateau {

    public PlateauMoyen(int size, int nombreJoueur) {
        super(size, nombreJoueur);
        this.plateauType = new PlateauMoyenImpl();
        this.plateauType.init(this);
        this.creerJoueur();
        this.viewMode = new ViewModeImpl(this);
        this.viewMode.modeJoueur();
    }

}
