package bot;

import java.util.List;
import java.awt.Point;
import modele.*;
import plateau.Plateau;
import vuecontrolleur.Controlleur;

public class EasyBot {

    protected Plateau plateau;
    protected Controlleur controlleur;
    protected List<Personnage> botList;
    protected StrategyBot botType;

    public EasyBot(Plateau plateau, Controlleur controlleur) {
        this.plateau = plateau;
        this.controlleur = controlleur;
    }

    /**
     * Méthode set pour Strategy Pattern
     * 
     * @param newBotType
     */
    public void setBotType(StrategyBot newBotType) {
        this.botType = newBotType;
    }

    public void setBotList() {
        this.botList = this.botType.getJoueurListe(this.plateau);
    }

    /**
     * Méthode d'attendre.
     * 
     * @param ms
     */
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void play() {
        while (this.plateau.win() == null) {
            setBotList();
            System.out.println("Bot is running");
            for (Personnage bot : this.botList) {
                if (bot.getPV() <= 0) {
                    bot.setMunitions(0);
                    this.plateau.getCase(bot.getCase().getPoint()).setPerso(false);
                }

                this.controlleur.bot = bot;
                Direction moveDir = bot.getDirection().getRandomDirection();
                Point end = this.plateau.getMove(bot, moveDir);
                this.plateau.deplacer(end, bot);

                boolean loop = true;

                while (loop) {
                    Direction shootDir = bot.getDirection().getRandomDirection();
                    List<Point> tirListe = this.plateau.getTir(shootDir, bot);
                    this.controlleur.botPos = tirListe;

                    boolean tir = false;
                    boolean release = false;
                    for (int i = 1; i < tirListe.size(); i++) {
                        if (this.plateau.getCase(tirListe.get(i)).estPerso()) {
                            if (bot.getNombreDeMunitions() > 0) {
                                this.plateau.shoot(tirListe, bot);
                                tir = true;
                            } else {
                                tir = false;
                            }
                        } else {
                            loop = false;
                        }
                    }

                    if (tir) {
                        switch (shootDir) {
                            case HAUT:
                                this.controlleur.botHaut = true;
                                loop = true;
                                release = true;
                                break;
                            case BAS:
                                this.controlleur.botBas = true;
                                loop = true;
                                release = true;
                                break;
                            case GAUCHE:
                                this.controlleur.botGauche = true;
                                loop = true;
                                release = true;
                                break;
                            case DROIT:
                                this.controlleur.botDroit = true;
                                loop = true;
                                release = true;
                                break;
                            default:
                                break;
                        }
                    }
                    wait(300);
                    if (release) {
                        switch (shootDir) {
                            case HAUT:
                                this.controlleur.botHaut = false;
                                loop = false;
                                break;
                            case BAS:
                                this.controlleur.botBas = false;
                                loop = false;
                                break;
                            case GAUCHE:
                                this.controlleur.botGauche = false;
                                loop = false;
                                break;
                            case DROIT:
                                this.controlleur.botDroit = false;
                                loop = false;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }
}
