package application;

import entities.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("JOGO DA VELHA");
        System.out.println();

        Game game = new Game();
        game.gameloop();
    }
}
