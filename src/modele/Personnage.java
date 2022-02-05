package modele;

import java.util.Random;

/**
 * Classe représentant l'objet Personnage (joueur), définit par ses points de
 * vie, sa portée, ses dégats, son nompbre de munitions, son énergie, son
 * arme...
 */

public class Personnage {
    private Case cas;

    public enum Arme {
        AMOS, BOREAS, PRIMODIAL, UNFORGED, SKYWARD, MISPLITTER;

        public static Arme getRandomArme() {
            Arme[] values = Arme.values();
            int length = values.length;
            int rand = new Random().nextInt(length);
            return values[rand];
        }
    };

    private Arme arme;
    private int nombreDeMunitions;
    private int pv;
    private boolean bouclier;
    private int portee;
    private int degat;
    private Direction direction;

    public Personnage(Case cas, Direction direction) {
        this.cas = cas;
        this.cas.setPerso(true);
        this.arme = Arme.getRandomArme();
        this.pv = 100;
        creerArme(arme);
        this.bouclier = false;
        this.direction = direction;
    }

    /**
     * @return Case
     */
    public Case getCase() {
        return this.cas;
    }

    /**
     * @return Arme
     */
    public Arme getArme() {
        return this.arme;
    }

    /**
     * @return int
     */
    public int getPV() {
        return this.pv;
    }

    /**
     * @return int
     */
    public int getNombreDeMunitions() {
        return this.nombreDeMunitions;
    }

    /**
     * @return int
     */
    public int getPortee() {
        return this.portee;
    }

    /**
     * @return int
     */
    public int getDegat() {
        return this.degat;
    }

    /**
     * @return boolean
     */
    public boolean getBouclier() {
        return this.bouclier;
    }

    /**
     * @return Direction
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * @param pv
     */
    public void setPV(int pv) {
        this.pv = pv;
    }

    /**
     * @param cas
     */
    public void setCase(Case cas) {
        this.cas = cas;
    }

    /**
     * @param x
     */
    public void setMunitions(int x) {
        this.nombreDeMunitions = x;
    }

    /**
     * @param portee
     */
    public void setPortee(int portee) {
        this.portee = portee;
    }

    /**
     * @param degat
     */
    public void setDegat(int degat) {
        this.degat = degat;
    }

    /**
     * @param bool
     */
    public void setBouclier(boolean bool) {
        this.bouclier = bool;
    }

    /**
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * méthode permettant la création d'une arme et de ses dégats selon le nom de
     * l'arme choisie
     * 
     * @param arme
     */
    public void creerArme(Arme arme) {
        switch (arme) {
            case AMOS:
                this.nombreDeMunitions = 50;
                this.portee = 5;
                this.degat = 5;
                break;
            case BOREAS:
                this.nombreDeMunitions = 40;
                this.portee = 4;
                this.degat = 10;
                break;
            case MISPLITTER:
                this.nombreDeMunitions = 30;
                this.portee = 3;
                this.degat = 15;
                break;
            case PRIMODIAL:
                this.nombreDeMunitions = 30;
                this.portee = 3;
                this.degat = 20;
                break;
            case SKYWARD:
                this.nombreDeMunitions = 30;
                this.portee = 3;
                this.degat = 25;
                break;
            case UNFORGED:
                this.nombreDeMunitions = 7;
                this.portee = 1;
                this.degat = 35;
                break;
            default:
                break;
        }
    }

    /**
     * Méthode permettant de retirer un nombre de points de vie au personnage
     * courant par un personnage donné en entrée.
     * 
     * @param perso
     */
    public void prendreDegat(Personnage perso) {
        this.pv -= perso.getDegat();
    }

    /**
     * @param degat
     */
    public void perdrePV(int degat) {
        this.pv -= degat;
    }

    public void perdreMunitions() {
        this.nombreDeMunitions--;
    }

}
