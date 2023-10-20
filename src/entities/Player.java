package entities;

/**
 * @author Flamarion Fagundes
 */
public class Player {
    private static int currentPlayerIndex;
    private final Character symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    private void toggleCurrentPlayerIndex() {
        currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
    }

    public Character getSymbol() {
        return symbol;
    }

    public void play (Board board, int row, int column) throws Exception {
        board.insertSymbol(symbol, row, column);
        board.draw();
        toggleCurrentPlayerIndex();
    }
}
