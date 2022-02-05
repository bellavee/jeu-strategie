package plateau;

import modele.*;
import viewmode.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe représentant le plateau de jeu entier, définit par : la taille du
 * plateau (paramétrable initialement), un tableau de case (qui représente
 * chacune des cases du plateau de jeu par l'objet Case), une liste des joueurs
 * (= objet Personnage) de ce plateau de jeu, ainsi que du joueur ayant la
 * vision de ce plateau de jeu
 */

public abstract class Plateau {
    protected int size;
    protected Case[][] plateau;
    protected List<Personnage> joueurListe;
    protected List<Bombe> bombeListe;
    protected List<Mine> mineListe;
    protected Personnage joueur;
    protected ViewMode viewMode;
    protected int nombreMur;
    protected int nombreBombe;
    protected int nombreMine;
    protected int nombreJoueur;
    protected int nombreBouclier;
    protected StrategyPlateau plateauType;

    public Plateau(int size, int nombreJoueur) {
        if (size < 10)
            size = 10;
        if (size > 20)
            size = 20;
        this.size = size;
        this.nombreJoueur = nombreJoueur;
        this.plateau = new Case[size][size];
    }

    /**
     * @return int
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Get case par un point p
     * 
     * @param p
     * @return Case
     */
    public Case getCase(Point p) {
        return this.plateau[p.x][p.y];
    }

    /**
     * Get case par un point de x et un point de y.
     * 
     * @param x
     * @param y
     * @return Case
     */
    public Case getCase(int x, int y) {
        return this.plateau[x][y];
    }

    /**
     * @return List<Personnage>
     */
    public List<Personnage> getJoueurListe() {
        return this.joueurListe;
    }

    /**
     * @return List<Bombe>
     */
    public List<Bombe> getBombeListe() {
        return this.bombeListe;
    }

    /**
     * @return List<Mine>
     */
    public List<Mine> getMineListe() {
        return this.mineListe;
    }

    /**
     * @return Personnage
     */
    public Personnage getJoueur() {
        return this.joueur;
    }

    /**
     * Méthode créant un plateau d'instances de case vide selon sa taille.
     */
    public void creerPlateau() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.plateau[i][j] = new Case(new Point(i, j));
            }
        }
    }

    /**
     * Méthode permettant de simplifier l'obtention d'un nombre entier aléatoire.
     * 
     * @param size
     * @return Point
     */
    public Point getRandom(int size) {
        Random random = new Random();
        return new Point(random.nextInt(size - 1) + 1, random.nextInt(size - 1) + 1);
    }

    /**
     * Méthode permettant d'obtenir une instance de la classe Point possédant des
     * valeurs de coordonnées aléatoires selon les objets déjà présents sur le
     * plateau de jeu, ceci selon la taille donnée du plateau de jeu.
     * 
     * @param size
     * @return Point
     */
    public Point randomPoint(int size) {
        Point p = getRandom(size);
        while (this.getCase(p).estPerso() || this.getCase(p).estBombe()
                || this.getCase(p).estMine() || this.getCase(p).estMur()
                || this.getCase(p).estBouclier() || !isValidPoint(p))
            p = getRandom(size);
        return p;
    }

    /**
     * Méthode permettant la création simple d'une instance de Personnage à une
     * position aléatoire sur le plateau de jeu courant.
     */
    public void creerJoueur() {
        this.joueurListe = new ArrayList<>();
        for (int i = 0; i < this.nombreJoueur; i++) {
            Point p = randomPoint(this.size);
            this.joueurListe.add(new Personnage(this.getCase(p), Direction.GAUCHE));
        }
        this.joueur = this.joueurListe.get(0);
    }

    /**
     * Méthode permettant la création simple d'une instance de Bombe à une position
     * aléatoire sur le plateau de jeu courant.
     */
    public void placerBombe() {
        this.bombeListe = new ArrayList<>();
        for (int i = 0; i < this.nombreBombe; i++) {
            Point p = randomPoint(this.size);
            this.bombeListe.add(new Bombe(this.getCase(p)));
        }
    }

    /**
     * Méthode permettant la création simple d'un bonus Bouclier à une position
     * aléatoire sur le plateau de jeu courant.
     */
    public void placerMine() {
        this.mineListe = new ArrayList<>();
        for (int i = 0; i < this.nombreMine; i++) {
            Point p = randomPoint(this.size);
            this.mineListe.add(new Mine(this.getCase(p)));
        }
    }

    /**
     * Placer bouclier aléatoire sur le plateau.
     */
    public void placerBouclier() {
        for (int i = 0; i < this.nombreBouclier; i++) {
            Point p = randomPoint(this.size);
            this.getCase(p).setBouclier(true);
        }
    }

    /**
     * Méthode retournant True si les coordonnées d'une instance de Point
     * donnée en entrée sont valide sur le plateau de jeu, False sinon.
     * 
     * @param point
     * @return boolean
     */
    public boolean isValidPoint(Point point) {
        return point.x >= 0 && point.y >= 0 && point.x < size && point.y < size;
    }

    /**
     * Méthode retournant une nouvelle instance de Point selon une une direction
     * donnée sur le plateau de jeu courant, retourne null sinon.
     * 
     * @param start     point actuel
     * @param direction 4 directions des navigateurs
     * @return Point
     */
    public Point calculNextPoint(Point start, Direction direction) {
        Point end = null;

        switch (direction) {
            case HAUT:
                end = new Point(start.x, start.y - 1);
                break;
            case BAS:
                end = new Point(start.x, start.y + 1);
                break;
            case GAUCHE:
                end = new Point(start.x - 1, start.y);
                break;
            case DROIT:
                end = new Point(start.x + 1, start.y);
                break;
            default:
                break;
        }

        return end;
    }

    /**
     * Trouver un joueur dans la liste des joueurs par rapport à un point.
     * 
     * @param p
     * @return Personnage
     */
    public Personnage trouverPerso(Point p) {
        for (Personnage perso : this.joueurListe) {
            if (perso.getCase().getPoint().equals(p))
                return perso;
        }
        return null;
    }

    /**
     * Trouver bombe dans la liste des bombes par rapport à un point.
     * 
     * @param p
     * @return Bombe
     */
    public Bombe trouverBombe(Point p) {
        for (Bombe bombe : this.bombeListe) {
            if (bombe.getCase().getPoint().equals(p))
                return bombe;
        }
        return null;
    }

    /**
     * Trouver mine dans la liste des mines par rapport à un point.
     * 
     * @param p
     * @return Mine
     */
    public Mine trouverMine(Point p) {
        for (Mine mine : this.mineListe) {
            if (mine.getCase().getPoint().equals(p))
                return mine;
        }
        return null;
    }

    /**
     * Méthode vérifiant si le point sur lequel le joueur souhaite se déplacer est
     * disponible ou non.
     * 
     * @param joueur
     * @param direction 4 directions des navigateurs
     * @return Point point sur lequel le joueur se deplace.
     */
    public Point getMove(Personnage joueur, Direction direction) {
        Point start = joueur.getCase().getPoint();
        Point end = calculNextPoint(start, direction);
        if (!isValidPoint(end))
            return start;
        if (this.getCase(end).estMur() || this.getCase(end).estPerso()) {
            return start;
        }
        joueur.setDirection(direction);
        return end;
    }

    /**
     * Changer les coordonnées actuelles (la case) d'un joueur.
     * Bombe explose au bout d’un certain délai t.
     * Mine explose lorsqu’un combattant se place sur la case qu’elle occupe.
     * 
     * @param end    point sur lequel le joueur se déplace
     * @param joueur
     */
    public void deplacer(Point end, Personnage joueur) {
        joueur.getCase().setPerso(false);

        if (!joueur.getBouclier() && this.getCase(end).estBouclier()) {
            joueur.setBouclier(true);
            this.getCase(end).setBouclier(false);
            joueur.setCase(getCase(end));
            joueur.getCase().setPerso(true);
        }

        if (joueur.getBouclier() && this.getCase(end).estBouclier()) {
            joueur.setCase(getCase(end));
            joueur.getCase().setPerso(true);

        }

        if (this.getCase(end).estBombe()) {
            Bombe bombe = trouverBombe(end);
            joueur.setCase(getCase(end));
            joueur.getCase().setPerso(true);
            if (bombe.getExploseStat() > 0) {
                bombe.setExploseStat();
            }

            else {
                if (joueur.getBouclier()) {
                    joueur.setBouclier(false);
                    this.bombeListe.remove(bombe);
                    bombe.getCase().setBombe(false);
                }

                else {
                    this.bombeListe.remove(bombe);
                    bombe.getCase().setBombe(false);
                    joueur.perdrePV(bombe.getDegat());
                    die(joueur, end);
                }
            }
        }

        if (this.getCase(end).estMine()) {
            Mine mine = trouverMine(end);
            this.mineListe.remove(mine);
            mine.getCase().setMine(false);
            joueur.setCase(getCase(end));
            joueur.getCase().setPerso(true);
            if (joueur.getBouclier())
                joueur.setBouclier(false);
            else
                joueur.perdrePV(mine.getDegat());
        }

        this.die(joueur, end);

        joueur.setCase(getCase(end));
        joueur.getCase().setPerso(true);
    }

    /**
     * Crée un liste de point sur lesquels le tir s'effectue en fonction de la
     * portée dans
     * la variable d'instance du joueur.
     * Le joueur peut faire un tir à un mur ou un joueur qui a bouclier.
     * Prise en compte de la portée de l'arme.
     * 
     * @param direction 4 directions des navigateurs
     * @param joueur    joueur tire
     * @return ArrayList<Point> liste des points de munitions
     */
    public List<Point> getTir(Direction direction, Personnage joueur) {
        int portee = joueur.getPortee();
        Point start = joueur.getCase().getPoint();
        Point end = calculNextPoint(joueur.getCase().getPoint(), direction);
        List<Point> pos = new ArrayList<>();
        if (!isValidPoint(end)) {
            pos.add(start);
            return pos;
        }

        for (int i = 0; i <= portee; i++) {
            if (this.getCase(end).estMur())
                break;

            pos.add(start);
            start = end;
            end = calculNextPoint(start, direction);
            if (!isValidPoint(end)) {
                break;
            }
        }

        joueur.setDirection(direction);
        pos.add(start);
        return pos;
    }

    /**
     * Le personnage enemy prend un dégât de joueur.
     * Un joueur touché perd les PV correspondant au dégats infligés ou perd son
     * bouclier.
     * Si les points de vie de enemy sont à 0 alors, le joueur enemy est éliminé.
     * 
     * @param tirListe
     * @param joueur
     */
    public void shoot(List<Point> tirListe, Personnage joueur) {
        for (Point p : tirListe) {
            Personnage enemy = trouverPerso(p);
            if (this.getCase(p).estPerso() && !this.getCase(p).equals(joueur.getCase())) {
                if (enemy != null) {
                    enemy.prendreDegat(joueur);
                    if (enemy.getBouclier())
                        enemy.setBouclier(false);
                    this.die(enemy, p);
                }
            }
        }
        joueur.perdreMunitions();
    }

    /**
     * Méthode pour éliminer un joueur du plateau sur une coordonnée p en
     * reinitialisant la case correspondante sur le plateau.
     * 
     * @param joueur
     * @param p
     */
    public void die(Personnage joueur, Point p) {
        if (joueur.getPV() <= 0)
            this.getCase(p).setPerso(false);
    }

    /**
     * Permet de créer aléatoirement des murs sur le plateau.
     * Un mur peut placer être horizontal ou vertical.
     * Un mur vertical ne peut pas être placé en haut et en bas du plateau.
     * Un mur horizontal ne peut pas placer à droit et à gauche du plateau.
     * 2 murs ne peuvent pas être placés l'un à côté de l'autre.
     * un mur horizontal et un mur vertical peuvent être placés l'un à côté de
     * l'autre.
     * 
     * @param direction
     */
    public void creerMurs(Direction direction) {
        int longueur = 3;

        switch (direction) {
            case BAS:
                for (int x = 0; x < this.nombreMur; x++) {
                    Point p = randomPoint(size - 3);
                    while (checkMurs(p) || checkMurs(new Point(p.x + longueur - 1, p.y)))
                        p = randomPoint(size - 3);
                    for (int i = 0; i < longueur; i++)
                        this.plateau[p.x + i][p.y].setMurs();
                }
                break;

            case DROIT:
                for (int x = 0; x < this.nombreMur; x++) {
                    Point p = randomPoint(size - 3);
                    while (checkMurs(p) || checkMurs(new Point(p.x, p.y + longueur - 1)))
                        p = randomPoint(size - 3);
                    for (int i = 0; i < longueur; i++)
                        this.plateau[p.x][p.y + i].setMurs();
                }
                break;

            default:
                break;
        }

    }

    /**
     * Permet de vérifier dans les 8 directions d'un point, si la case est un mur ou
     * non.
     * 
     * @param start point actuel
     * @return boolean
     */
    public boolean checkMurs(Point start) {
        if (this.plateau[start.x - 1][start.y].estMur() || this.plateau[start.x + 1][start.y].estMur()
                || this.plateau[start.x - 1][start.y - 1].estMur() || this.plateau[start.x - 1][start.y + 1].estMur()
                || this.plateau[start.x + 1][start.y - 1].estMur() || this.plateau[start.x + 1][start.y + 1].estMur()
                || this.plateau[start.x][start.y - 1].estMur() || this.plateau[start.x][start.y + 1].estMur()) {
            return true;
        }
        return false;
    }

    /**
     * Permet de vérifier le gagnant du jeu puis de faire une condition de terminer
     * le jeu.
     * 
     * @return Personnage winner
     */
    public Personnage win() {
        int cpt = 0;
        Personnage winner = this.joueur;
        for (Personnage perso : this.joueurListe) {
            if (perso.getPV() <= 0)
                cpt++;
            if (perso.getPV() > 0)
                winner = perso;
        }

        if (cpt == nombreJoueur - 1)
            return winner;

        return null;
    }

    /**
     * Redémarre le jeu en réinitialisant le plateau.
     */

    public void restart() {
        creerPlateau();
        creerMurs(Direction.DROIT);
        creerMurs(Direction.BAS);
        placerBombe();
        placerMine();
        placerBouclier();
        creerJoueur();
    }
}
