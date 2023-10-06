package entities;

import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    Board board = new Board();
    Player player = new Player('x');
    Cpu cpu = new Cpu('o');
    Player[] players = new Player[] {player, cpu};
    private Player winner;
    private boolean isGameOver = false;
    private void showGameReport() {
        if (winner == null) {
            System.out.println("Deu Velha");
        } else {
            System.out.printf("Jogador %c ganhou!%n", winner.getSymbol());
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

        do {
            try {
                System.out.println("Digite a linha e coluna que deseja jogar ");
                System.out.println("Linha: ");
                row = scanner.nextInt();
                System.out.println("Coluna: ");
                column = scanner.nextInt();

                player.play(board, row, column);
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
            if (Player.getCurrentPlayerIndex() == 0) {
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
