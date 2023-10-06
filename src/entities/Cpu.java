package entities;

import java.util.Arrays;
import java.util.Random;

public class Cpu extends Player {
    public Cpu(char symbol) {
        super(symbol);
    }
    public void play(Board board) {
        Random random = new Random();

        try {
            super.play(board, 0, 1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
