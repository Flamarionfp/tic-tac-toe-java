package entities;

import utils.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

public class Cpu extends Player {
    Random random = new Random();

    public Cpu(char symbol) {
        super(symbol);
    }

    private Character getOponentSymbol() {
        return getSymbol() == 'x' ? 'o' : 'x';
    }
    private boolean isCritical(Character[] matchedSymbols) {
        return matchedSymbols.length == 2;
    }

    private int getCriticalPosition(Object[] currentPositions) {
        return Array.findIndex(currentPositions, symbol -> symbol == null);
    }

    private int[] getCriticalHorizontal(Character[][] positions, Character symbolToCompare) {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                Stream<Character> matchedSymbolsStream = Arrays.stream(positions[i]).filter(symbol -> symbol == symbolToCompare);
               Character[] matchedSymbols = matchedSymbolsStream.toArray(Character[]::new);

                if (isCritical(matchedSymbols)) {
                    int columnToPlay = getCriticalPosition(positions[i]);

                    // System.out.printf("%s horizontal: [%d, %d] \n", symbolToCompare == getOponentSymbol() ? "Defendeu" : "Atacou", i, columnToPlay);

                    return columnToPlay == -1 ? new int[]{} : new int[]{i, columnToPlay};
                }
            }
        }

        return new int[]{};
    }

    private int[] getCriticalVertical(Character[][] positions, Character symbolToCompare) {
        List<Character> verticalPos = new ArrayList<>();

        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                verticalPos.add(positions[j][i]);
            }

            // System.out.println("VERTICAL POS" + Arrays.toString(verticalPos.toArray()));

            Stream<Character> matchedSymbolsStream = verticalPos.stream().filter(symbol -> symbol == symbolToCompare);
            Character[] matchedSymbols = matchedSymbolsStream.toArray(Character[]::new);

            if (isCritical(matchedSymbols)) {
                int rowToPlay = getCriticalPosition(verticalPos.toArray());

                // System.out.printf("%s vertical: [%d, %d] \n", symbolToCompare == getOponentSymbol() ? "Defendeu" : "Atacou", rowToPlay, i);

                return rowToPlay == -1 ? new int[]{} : new int[]{rowToPlay, i};
            } else {
                verticalPos.clear();
            }
        }

        return new int[]{};
    }

    private int[] getCriticalDiagonal(Character[][] positions, Character symbolToCompare) {
        List<Character> mainDiagonalPos = new ArrayList<>();

        for (int i = 0; i < positions.length; i++) {
            mainDiagonalPos.add(positions[i][i]);
        }

        Stream<Character> matchedSymbolsStream = mainDiagonalPos.stream().filter(symbol -> symbol == symbolToCompare);
        Character[] matchedSymbols = matchedSymbolsStream.toArray(Character[]::new);

        if (isCritical(matchedSymbols)) {
            int columnAndRowToPlay = getCriticalPosition(mainDiagonalPos.toArray());

            return new int[]{columnAndRowToPlay, columnAndRowToPlay};
        }

        return new int[]{};
    }

    private int[] handleGetBetterPositionToPlay(Character[][] positions) throws Exception {
        int i = 0;
        int[] positionToPlay = new int[]{};

        while (positionToPlay.length == 0) {
            // Menor ? defender : Atacar (inverter isso, pois a prioridade é ganhar)
            Character symbolToCompare = i < Board.DIMENSIONS ? getOponentSymbol() : getSymbol();

            Callable<int[]>[] verifyFunctions = new Callable[]{ // atribuir via laço for os valores
                    () -> getCriticalHorizontal(positions, symbolToCompare),
                    () -> getCriticalVertical(positions, symbolToCompare),
                    () -> getCriticalDiagonal(positions, symbolToCompare)
            };


            positionToPlay = verifyFunctions[i].call();
            i++;

            if (positionToPlay.length == 0 && i == verifyFunctions.length) {
                int randomRow = random.nextInt(Board.DIMENSIONS);
                int randomColumn = random.nextInt(Board.DIMENSIONS);

                // System.out.printf("Jogou random randomRow: %d randomColumn: %d \n", randomRow, randomColumn);

                positionToPlay = new int[]{randomRow, randomColumn};
            }
        }

        return positionToPlay;
    }

    public void play(Board board) {
        try {
            int[] positionToPlay;

            do {
                positionToPlay = handleGetBetterPositionToPlay(board.positions);
            } while (board.isFilledPosition(positionToPlay[0], positionToPlay[1])); // Pegar as posições restantes em vez de "chutar" se necessário

            System.out.println("Debug ia play " + Arrays.toString(positionToPlay));
            super.play(board, positionToPlay[0], positionToPlay[1]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}