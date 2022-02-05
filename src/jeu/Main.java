package jeu;

import java.util.Scanner;

import javax.swing.*;

import bot.*;
import plateau.*;
import vuecontrolleur.*;

public class Main extends JFrame {

    private static Controlleur controlleur;

    public Main(Plateau plateau) {
        super("Jeu Combat");
        Vue vue = new Vue(plateau);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        add(vue);
        pack();
        repaint();
        setVisible(true);
        vue.startGameThread();
        this.controlleur = vue.getControlleur();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("---------- Bienvenue au Jeu de Combat ----------\n");
        System.out.println("Déplacement avec le clavier\n");
        System.out.println("Z-Haut\tS-Bas\tQ-Gauche\tD-Droit\n");
        System.out.println("Tirez avec les flèches de navigation\n");
        System.out.println("V-Changer de mode de vue (bombes et mines visibles ou non)\n");
        System.out.println("Choisissez la taille du plateau (10 à 20 cases)");
        int taille = input.nextInt();
        while (taille < 10 || taille > 20){
            System.out.println("Choisissez une taille entre 10 et 20 inclus !");
            taille = input.nextInt();
        }
        System.out.println("Choisissez la quantité d'obstacles sur le plateau\n");
        System.out.println("1-Peu\t2-Moyen\t3-Beaucoup");
        int choix = input.nextInt();
        while (choix != 1 && choix != 2 && choix != 3){
            System.out.println("Choisissez 1, 2, ou 3 !");
            choix = input.nextInt();
        }
        System.out.println("Choisissez un mode du jeu\n");
        System.out.println("1-Jouer avec les bot\t2-Bot uniquement ");
        int mode = input.nextInt();
        while (mode != 1 && mode != 2){
            System.out.println("Choisissez le mode 1 ou 2 !");
            mode = input.nextInt();
        }
        System.out.println("Choisissez le nombre de joueurs (4-10)\n");
        int nombreJoueur = input.nextInt();
        while (nombreJoueur < 4 || nombreJoueur > 10){
            System.out.println("Choisissez un nombre entre 4 et 10 inclus !");
            nombreJoueur = input.nextInt();
        }

        Plateau plateau;
        EasyBot bot;

        switch (choix) {
            case 1:
                plateau = new PlateauPetit(taille, nombreJoueur);
                new Main(plateau);
                switch (mode) {
                    case 1:
                        bot = new PlayWithBot(plateau, controlleur);
                        break;
                    case 2:
                        bot = new BotPlay(plateau, controlleur);
                        break;
                }
                break;
            case 2:
                plateau = new PlateauMoyen(taille, nombreJoueur);
                new Main(plateau);
                switch (mode) {
                    case 1:
                        bot = new PlayWithBot(plateau, controlleur);
                        break;
                    case 2:
                        bot = new BotPlay(plateau, controlleur);
                        break;
                }
                break;
            case 3:
                plateau = new PlateauGrand(taille, nombreJoueur);
                new Main(plateau);
                switch (mode) {
                    case 1:
                        bot = new PlayWithBot(plateau, controlleur);
                        break;
                    case 2:
                        bot = new BotPlay(plateau, controlleur);
                        break;
                }
                break;
        }
    }
}
