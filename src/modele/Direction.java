package modele;

import java.util.Random;

public enum Direction {
    HAUT, BAS, GAUCHE, DROIT;

    public Direction getRandomDirection() {
        Direction[] values = Direction.values();
        int length = values.length;
        int rand = new Random().nextInt(length);
        return values[rand];
    }
}
