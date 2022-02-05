package bot;

import plateau.Plateau;
import vuecontrolleur.Controlleur;

public class BotPlay extends EasyBot {

    public BotPlay(Plateau plateau, Controlleur controlleur) {
        super(plateau, controlleur);
        this.botType = new BotPlayImpl();
        this.play();
    }

}
