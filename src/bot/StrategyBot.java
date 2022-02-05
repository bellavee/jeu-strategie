package bot;

import java.util.List;

import modele.Personnage;
import plateau.Plateau;

public interface StrategyBot {

    public List<Personnage> getJoueurListe(Plateau plateau);

}
