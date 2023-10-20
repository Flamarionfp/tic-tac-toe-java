package entities;

import java.util.List;
import java.util.Scanner;

/**
 * @author Flamarion Fagundes
 */
public class Game {
    Scanner scanner = new Scanner(System.in);
    Board board = new Board();
    List<Character> symbols;
    Player player1;
    Player player2;
    Cpu cpu;
    Player[] players;
    private Player winner;
    private final boolean isMultiplayer;
    private boolean isGameOver = false;

    public Game(List<Character> symbols, boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
        this.symbols = symbols;

        player1 = new Player(symbols.get(0));

        if (isMultiplayer) {
            player2 = new Cpu(symbols.get(1));
        } else {
            cpu = new Cpu(symbols.get(1));
        }

        players = new Player[]{player1, isMultiplayer ? player2 : cpu};
    }

    private void showGameReport() {
        if (winner == null) {
            System.out.println("Deu Velha");
        } else {
            System.out.printf("Jogador %c ganhou! %n", winner.getSymbol());
        }
    }

    private boolean hasWinner() {
        return board.mainDiagonalFilled() || board.secondaryDiagonalFilled() || board.horizontalFilled() || board.verticalFilled();
    }

    private void verifyGameOver() {
        if (hasWinner()) {
            int winnerIndex = Player.getCurrentPlayerIndex() == 0 ? 1 : 0;

            setWinner(players[winnerIndex]);
            setGameOver();
        }

        if (board.isAllFilled()) {
            setGameOver();
        }
    }

    private void handleMakePlay() {
        int row, column;
        boolean invalidPlay = false;

        if (board.isAllFilled()) return;

        do {
            try {
                System.out.println("Jogador " + symbols.get(Player.getCurrentPlayerIndex())  + ", digite a linha e coluna que deseja jogar ");
                System.out.println("Linha: ");
                row = scanner.nextInt();
                System.out.println("Coluna: ");
                column = scanner.nextInt();

                players[Player.getCurrentPlayerIndex()].play(board, row, column);
            } catch (Exception e) {
                invalidPlay = true;
                System.out.println(e.getMessage());
            }
        } while (invalidPlay);
    }

    public void setGameOver() {
        this.isGameOver = true;
    }

    public void setWinner(Player player) {
        this.winner = player;
    }

    public void gameloop() {
        while (!isGameOver) {
            if (Player.getCurrentPlayerIndex() == 0 || isMultiplayer) {
                handleMakePlay();
            } else {
                cpu.play(board);
            }

            verifyGameOver();
            // System.out.println(board);
        }

        showGameReport();
    }
}
