package org.university.pr1;

import java.util.ArrayList;
import java.util.List;

public class CardanoGrid {

    private static final String ABC_ENG = "abcdefghijklmnopqrstuvwxyz";

    private char[][] GRID = new char[][]{};

    public String encrypt(String sourceText, Integer gridSize) {
        List<List<Integer>> order = generateOrder(sourceText.length());
        GRID = createGrid(sourceText, gridSize, order);

        GRID = fillEmptyCells(GRID, gridSize);

        printSquare(GRID);
        return toCipher(order);
    }

    private char[][] createGrid(String text, Integer size, List<List<Integer>> order) {
        char[][] grid = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '0';
            }
        }

        for (int i = 0; i < text.length(); i++) {
            List<Integer> position = order.get(i);
            grid[position.get(0)][position.get(1)] = text.charAt(i);
        }

        return grid;
    }

    private List<List<Integer>> generateOrder(Integer size) {
        List<List<Integer>> order = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            List<Integer> position = null;
            while (position == null || order.contains(position)) {
                position = getPosition(size);
            }
            order.add(position);
        }
        return order;
    }

    private List<Integer> getPosition(Integer size) {
        int row = (int) (Math.random() * size);
        int column = (int) (Math.random() * size);

        return List.of(row, column);
    }

    private char[][] fillEmptyCells(char[][] grid, Integer size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '0') {
                    grid[i][j] = ABC_ENG.charAt((int) (Math.random() * (ABC_ENG.length() - 1)));
                }
            }
        }
        return grid;
    }

    private void printSquare(char[][] square) {
        for (char[] row : square) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private String toCipher(List<List<Integer>> order) {
        StringBuilder stringBuilder = new StringBuilder();

        for (List<Integer> position : order) {
            stringBuilder.append(position.get(0))
                         .append(position.get(1));
        }
        return stringBuilder.toString();
    }

    public String decrypt(String cipher) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i=0; i<cipher.length(); i+=2) {
            int row = cipher.charAt(i) - '0';
            int column = cipher.charAt(i+1) - '0';
            stringBuilder.append(GRID[row][column]);
        }
        return stringBuilder.toString();
    }
}
