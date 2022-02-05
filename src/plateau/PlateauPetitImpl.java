package plateau;

import modele.Direction;

public class PlateauPetitImpl implements StrategyPlateau {

    /**
     * @param plateau
     */
    @Override
    public void init(Plateau plateau) {
        if (plateau.size >= 10)
            plateau.nombreMur = plateau.size / 5;
        if (plateau.size > 15)
            plateau.nombreMur = plateau.size / 4;

        plateau.nombreBombe = plateau.size / 2;
        plateau.nombreMine = plateau.size / 2;
        plateau.nombreBouclier = plateau.size / 2;
        plateau.creerPlateau();
        plateau.creerMurs(Direction.DROIT);
        plateau.creerMurs(Direction.BAS);
        plateau.placerBombe();
        plateau.placerMine();
        plateau.placerBouclier();
    }

}
