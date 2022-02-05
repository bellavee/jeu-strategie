package bot;

import plateau.Plateau;
import vuecontrolleur.Controlleur;

public class PlayWithBot extends EasyBot {

    public PlayWithBot(Plateau plateau, Controlleur controlleur) {
        super(plateau, controlleur);
        this.botType = new PlayWithBotImpl();
        this.play();
    }

}
