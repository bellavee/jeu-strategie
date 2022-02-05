package vuecontrolleur;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.imageio.ImageIO;

import modele.*;
import plateau.Plateau;

public class Vue extends JPanel implements Runnable {

    private Plateau plateau;
    private static final int PADDING = 35;
    private static final int BOXSIZE = 40;
    final int screenSize;
    final int tabSize;

    static Color tabColor = new Color(247, 255, 246);
    static Color backgroundColor = new Color(147, 180, 139);
    static Color wallColor = new Color(147, 180, 139);
    static Color red = new Color(189, 37, 34);
    static Color blue = new Color(28, 119, 195);
    static Color yellow = new Color(236, 159, 5);
    static Color green = new Color(17, 39, 11);

    private Thread gameThread;
    private Controlleur controlleur;
    private Personnage winner;

    private int FPS = 60;
    private int joueurX;
    private int joueurY;

    private Image mainDroit;
    private Image mainGauche;
    private Image joueurDroit;
    private Image joueurGauche;
    private Image bombe;
    private Image mine;
    private Image bouclier;
    private Image mur;
    private Image bulletVertical;
    private Image bulletHorizontal;

    public Vue(Plateau plateau) {
        this.plateau = plateau;
        this.screenSize = BOXSIZE * plateau.getSize() + PADDING * 2;
        this.tabSize = BOXSIZE * plateau.getSize() / 3 + PADDING * 2;
        this.controlleur = new Controlleur(plateau);
        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(this.screenSize + this.tabSize, this.screenSize));
        this.setFocusTraversalKeysEnabled(false);
        this.addKeyListener(controlleur);
        this.setFocusable(true);

        try {
            mainDroit = ImageIO.read(new File("./img/joueur-1-droit.png"));
            mainGauche = ImageIO.read(new File("./img/joueur-1-gauche.png"));
            joueurDroit = ImageIO.read(new File("./img/joueur-2-droit.png"));
            joueurGauche = ImageIO.read(new File("./img/joueur-2-gauche.png"));
            bombe = ImageIO.read(new File("./img/bombe.png"));
            mine = ImageIO.read(new File("./img/mine.png"));
            bouclier = ImageIO.read(new File("./img/bouclier.png"));
            mur = ImageIO.read(new File("./img/mur.png"));
            bulletVertical = ImageIO.read(new File("./img/bullet-vertical.png"));
            bulletHorizontal = ImageIO.read(new File("./img/bullet-horizontal.png"));
        } catch (IOException e) {
            System.out.println("Not found!");
        }
    }

    /**
     * @return Controlleur
     */
    public Controlleur getControlleur() {
        return this.controlleur;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                if (this.plateau.win() != null)
                    this.winner = this.plateau.win();

                repaint();
                delta--;
                drawCount++;
            }

            if (timer > 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Méthode de dessiner.
     * 
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(tabColor);
        g2d.fillRect(PADDING, PADDING, BOXSIZE * plateau.getSize(), BOXSIZE * plateau.getSize());

        int portee = plateau.getJoueur().getPortee() + 1;

        /**
         * Écrire les textes
         */

        g.setColor(green);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("PV : " + plateau.getJoueur().getPV(), BOXSIZE * plateau.getSize() + PADDING * 2,
                PADDING + BOXSIZE);
        g.drawString("Munitions : " + plateau.getJoueur().getNombreDeMunitions(),
                BOXSIZE * plateau.getSize() + PADDING * 2,
                PADDING + BOXSIZE * 2);
        g.drawString("Dégâts : " + plateau.getJoueur().getDegat(), BOXSIZE * plateau.getSize() + PADDING * 2,
                PADDING + BOXSIZE * 3);
        g.drawString("Portée : " + portee, BOXSIZE * plateau.getSize() + PADDING * 2,
                PADDING + BOXSIZE * 4);

        if (plateau.getJoueur().getBouclier())
            g.drawImage(bouclier, BOXSIZE * plateau.getSize() + PADDING * 2, PADDING + BOXSIZE * 5, null);

        if (plateau.getJoueur().equals(winner))
            g.drawString("You win !", BOXSIZE * plateau.getSize() + PADDING * 2, PADDING + BOXSIZE * 7);

        if (plateau.getJoueur().getPV() <= 0 || (!plateau.getJoueur().equals(winner) && plateau.win() != null))
            g.drawString("You lose !", BOXSIZE * plateau.getSize() + PADDING * 2, PADDING + BOXSIZE * 7);

        if (this.plateau.getSize() <= 10)
            g.setFont(new Font("serif", Font.BOLD, 18));
        else if (this.plateau.getSize() <= 15)
            g.setFont(new Font("serif", Font.BOLD, 24));
        else if (this.plateau.getSize() > 15)
            g.setFont(new Font("serif", Font.BOLD, 30));

        g.drawString("Enter space to reset", BOXSIZE * plateau.getSize() + PADDING * 2,
                PADDING + BOXSIZE * plateau.getSize());

        /**
         * Dessiner le plateau
         */
        for (int i = 0; i < plateau.getSize(); i++) {
            for (int j = 0; j < plateau.getSize(); j++) {
                if (plateau.getCase(i, j) instanceof Case)
                    dessinerCarree(g, wallColor, i, j);
            }
        }

        /**
         * Dessiner des obstacles
         */
        for (int i = 0; i < plateau.getSize(); i++) {
            for (int j = 0; j < plateau.getSize(); j++) {
                if (plateau.getCase(i, j).estMur())
                    g.drawImage(mur, PADDING + i * BOXSIZE, PADDING + j * BOXSIZE, null);
                if (plateau.getCase(i, j).estBouclier())
                    g.drawImage(bouclier, PADDING + i * BOXSIZE + 5, PADDING + j * BOXSIZE + 5, null);
            }
        }

        for (Bombe b : plateau.getBombeListe()) {
            int x = b.getCase().getX();
            int y = b.getCase().getY();
            boolean visible = b.getVisible();
            if (visible)
                g.drawImage(bombe, PADDING + x * BOXSIZE + 5, PADDING + y * BOXSIZE + 5, null);
        }

        for (Mine m : plateau.getMineListe()) {
            int x = m.getCase().getX();
            int y = m.getCase().getY();
            boolean visible = m.getVisible();
            if (visible)
                g.drawImage(mine, PADDING + x * BOXSIZE + 5, PADDING + y * BOXSIZE + 5, null);
        }

        /**
         * Dessiner les joueurs
         */
        for (Personnage p : plateau.getJoueurListe()) {
            int x = p.getCase().getX();
            int y = p.getCase().getY();
            int pv = p.getPV();
            if (pv > 0) {
                Direction dir = p.getDirection();
                switch (dir) {
                    case HAUT:
                        g.drawImage(joueurDroit, PADDING + x * BOXSIZE + 10, PADDING + y * BOXSIZE +
                                5, null);
                        break;
                    case BAS:
                        g.drawImage(joueurGauche, PADDING + x * BOXSIZE + 10, PADDING + y * BOXSIZE +
                                5, null);
                        break;
                    case GAUCHE:
                        g.drawImage(joueurGauche, PADDING + x * BOXSIZE + 10, PADDING + y * BOXSIZE +
                                5, null);
                        break;
                    case DROIT:
                        g.drawImage(joueurDroit, PADDING + x * BOXSIZE + 10, PADDING + y * BOXSIZE +
                                5, null);
                        break;
                    default:
                        break;
                }
            }
        }

        this.joueurX = this.plateau.getJoueur().getCase().getX();
        this.joueurY = this.plateau.getJoueur().getCase().getY();

        if (this.plateau.getJoueur().getPV() > 0) {
            Direction direction = this.plateau.getJoueur().getDirection();
            switch (direction) {
                case HAUT:
                    g.drawImage(mainDroit, PADDING + joueurX * BOXSIZE + 10, PADDING +
                            joueurY * BOXSIZE + 5,
                            null);
                    break;
                case BAS:
                    g.drawImage(mainGauche, PADDING + joueurX * BOXSIZE + 10, PADDING +
                            joueurY * BOXSIZE + 5,
                            null);
                    ;
                    break;
                case GAUCHE:
                    g.drawImage(mainGauche, PADDING + joueurX * BOXSIZE + 10, PADDING +
                            joueurY * BOXSIZE + 5,
                            null);
                    break;
                case DROIT:
                    g.drawImage(mainDroit, PADDING + joueurX * BOXSIZE + 10, PADDING +
                            joueurY * BOXSIZE + 5,
                            null);
                    break;
                default:
                    break;
            }
        }

        /**
         * Dessiner les munitions du joueur
         */
        if (controlleur.haut) {
            for (int i = 1; i < controlleur.pos.size(); i++)
                g.drawImage(bulletVertical, PADDING + joueurX * BOXSIZE + 5,
                        PADDING + controlleur.pos.get(i).y * BOXSIZE,
                        null);
        }

        if (controlleur.bas) {
            for (int i = 1; i < controlleur.pos.size(); i++)
                g.drawImage(bulletVertical, PADDING + joueurX * BOXSIZE + 5,
                        PADDING + controlleur.pos.get(i).y * BOXSIZE,
                        null);
        }

        if (controlleur.gauche) {
            for (int i = 1; i < controlleur.pos.size(); i++)
                g.drawImage(bulletHorizontal, PADDING + controlleur.pos.get(i).x * BOXSIZE,
                        PADDING + joueurY * BOXSIZE + 5,
                        null);
        }
        if (controlleur.droit) {
            for (int i = 1; i < controlleur.pos.size(); i++)
                g.drawImage(bulletHorizontal, PADDING + controlleur.pos.get(i).x * BOXSIZE,
                        PADDING + joueurY * BOXSIZE + 5,
                        null);
        }

        /**
         * Dessiner les munitions de bot
         */
        if (controlleur.botHaut) {
            for (int i = 1; i < controlleur.botPos.size(); i++)
                g.drawImage(bulletVertical, PADDING + controlleur.bot.getCase().getX() * BOXSIZE + 5,
                        PADDING + controlleur.botPos.get(i).y * BOXSIZE,
                        null);
        }

        if (controlleur.botBas) {
            for (int i = 1; i < controlleur.botPos.size(); i++)
                g.drawImage(bulletVertical, PADDING + controlleur.bot.getCase().getX() * BOXSIZE + 5,
                        PADDING + controlleur.botPos.get(i).y * BOXSIZE,
                        null);
        }

        if (controlleur.botGauche) {
            for (int i = 1; i < controlleur.botPos.size(); i++)
                g.drawImage(bulletHorizontal, PADDING + controlleur.botPos.get(i).x * BOXSIZE,
                        PADDING + controlleur.bot.getCase().getY() * BOXSIZE + 5,
                        null);
        }

        if (controlleur.botDroit) {
            for (int i = 1; i < controlleur.botPos.size(); i++)
                g.drawImage(bulletHorizontal, PADDING + controlleur.botPos.get(i).x * BOXSIZE,
                        PADDING + controlleur.bot.getCase().getY() * BOXSIZE + 5,
                        null);
        }
    }

    /**
     * Dessiner un carrée
     * 
     * @param g
     * @param c
     * @param x
     * @param y
     */
    private void dessinerCarree(Graphics g, Color c, int x, int y) {
        g.setColor(c);
        g.drawRect(PADDING + x * BOXSIZE, PADDING + y * BOXSIZE, BOXSIZE, BOXSIZE);
    }

}
