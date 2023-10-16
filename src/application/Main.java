package application;

import entities.Game;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Character> symbols = new ArrayList<>(List.of('x', 'o'));
        char multiplayerResponse, randomResponse, playAgainResponse;

        do {
            System.out.println("JOGO DA VELHA");
            System.out.println();

            System.out.println("Deseja jogar com outro jogador? (s/n) ");
            multiplayerResponse = scanner.nextLine().charAt(0);

            System.out.println("Deseja sortear os símbolos? (s/n) ");
            randomResponse = scanner.nextLine().charAt(0);

            if (randomResponse == 's') {
                Collections.shuffle(symbols);
            }

            System.out.println("Começará com " + symbols.toArray()[0]);

            Game game = new Game(symbols, multiplayerResponse == 's');
            game.gameloop();

            System.out.println("Deseja jogar novamente? (s/n) ");
            playAgainResponse = scanner.nextLine().charAt(0);
        } while (playAgainResponse == 's');

        scanner.close();
    }
}
