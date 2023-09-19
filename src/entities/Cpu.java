package entities;

import java.util.Random;

public class Cpu extends Player {
    public Cpu(char symbol) {
        super(symbol);
    }
    public void play(Board board) {
        Random random = new Random();

        try {
            int row, column;

            do {
              row = random.nextInt(Board.DIMENSIONS);
              column = random.nextInt(Board.DIMENSIONS);
          }  while (board.isFilledPosition(row, column));

          super.play(board, row, column);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
