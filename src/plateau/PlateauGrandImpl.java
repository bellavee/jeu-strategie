package plateau;

import modele.Direction;

public class PlateauGrandImpl implements StrategyPlateau {

    /**
     * @param plateau
     */
    @Override
    public void init(Plateau plateau) {
        if (plateau.size >= 10)
            plateau.nombreMur = plateau.size / 4;
        if (plateau.size > 15)
            plateau.nombreMur = plateau.size / 6 + plateau.size / 4;
        if (plateau.size == 20)
            plateau.nombreMur = plateau.size / 2;

        plateau.nombreBombe = plateau.size + plateau.size / 4;
        plateau.nombreMine = plateau.size + plateau.size / 4;
        plateau.nombreBouclier = plateau.size + plateau.size / 4;
        plateau.creerPlateau();
        plateau.creerMurs(Direction.DROIT);
        plateau.creerMurs(Direction.BAS);
        plateau.placerBombe();
        plateau.placerMine();
        plateau.placerBouclier();
    }

}
