package bot;

import java.util.ArrayList;
import java.util.List;

import modele.Personnage;
import plateau.Plateau;

public class PlayWithBotImpl implements StrategyBot {

    @Override
    public List<Personnage> getJoueurListe(Plateau plateau) {
        List<Personnage> botList = new ArrayList<>();
        for (Personnage p : plateau.getJoueurListe())
            botList.add(p);
        botList.remove(0);
        return botList;
    }

}
