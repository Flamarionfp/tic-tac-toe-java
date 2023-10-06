package entities;

import java.util.Arrays;

public class Board {
    public static final int DIMENSIONS = 3;
    Character[][] positions = new Character[DIMENSIONS][DIMENSIONS];

    public Board() {
        init();
        draw();
    }
    private void init() {
        for (int row = 0; row < DIMENSIONS; row++) {
            for (int column = 0; column < DIMENSIONS; column++) {
                this.positions[row][column] = null;
            }
        }
    }

    private Character getFormattedPosition(int row, int column) {
        return positions[row][column] != null ? positions[row][column] : ' ';
    }

    public void draw() {
        System.out.println();
        System.out.println();

        for (int i = 0; i < positions.length; i++) {
            System.out.printf("     %c | %c | %c %n", getFormattedPosition(i, 0), getFormattedPosition(i, 1), getFormattedPosition(i,2));

            if (i == 0 || i == 1) {
                System.out.println("     ----------");
            }
        }

        System.out.println();
        System.out.println();
    }

    public boolean isFilledPosition(int row, int column) {
        return positions[row][column] != null;
    }

    public boolean isAllFilled() {
        for (Character[] position : positions) {
            for (Character character : position) {
                if (character == null) return false;
            }
        }

        return true;
    }

    public boolean mainDiagonalFilled() {
        try {
            char firstChar = positions[0][0];

            for (int i = 1; i < positions.length; i++) {
                char current = positions[i][i];

                if (current != firstChar) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean secondaryDiagonalFilled() {
        return positions[0][2] == positions[1][1] && positions[1][1] == positions[2][0] && positions[0][2] != null;
    }

    public boolean horizontalFilled() {
        for (int row = 0; row < positions.length; row++) {
            if (positions[row][0] == positions[row][1] && positions[row][1] == positions[row][2] && positions[row][0] != null) {
                return true;
            }
        }

        return false;
    }

    public boolean verticalFilled() {
        for (int column = 0; column < positions.length; column++) {
            if (positions[0][column] == positions[1][column] && positions[1][column] == positions[2][column] && positions[0][column] != null) {
                return true;
            }
        }

        return false;
    }


    public void insertSymbol(Character symbol, int row, int column) throws Exception {
        if (row >= DIMENSIONS || column >= DIMENSIONS) {
            throw new Exception("Jogada inválida");
        }

        // if (isFilledPosition(row, column)) throw new Exception("Posição já utilizada");

        positions[row][column] = symbol;
    }

    public String toString() {
        return Arrays.deepToString(positions);
    }
}
