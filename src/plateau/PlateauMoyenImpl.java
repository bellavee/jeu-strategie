package plateau;

import modele.Direction;

public class PlateauMoyenImpl implements StrategyPlateau {

    /**
     * @param plateau
     */
    @Override
    public void init(Plateau plateau) {
        if (plateau.size >= 10)
            plateau.nombreMur = plateau.size / 4;
        if (plateau.size > 15)
            plateau.nombreMur = plateau.size / 4;

        plateau.nombreBombe = plateau.size;
        plateau.nombreMine = plateau.size;
        plateau.nombreBouclier = plateau.size;
        plateau.creerPlateau();
        plateau.creerMurs(Direction.DROIT);
        plateau.creerMurs(Direction.BAS);
        plateau.placerBombe();
        plateau.placerMine();
        plateau.placerBouclier();
    }

}
