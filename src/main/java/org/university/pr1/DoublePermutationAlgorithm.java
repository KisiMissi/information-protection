package org.university.pr1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoublePermutationAlgorithm {

    public String encrypt(String sourceText) {
        int tableSide = (int) Math.round(Math.sqrt(sourceText.length())) + 1;

        char[][] table = createTable(sourceText, tableSide);
        table = swappingRows(table, tableSide);
        table = swappingColumns(table, tableSide);

        return makeWord(table, tableSide);
    }

    private char[][] createTable(String sourceText, Integer side) {
        char[][] table = new char[side][side];

        sourceText = sourceText.replace(" ", "?");

        int charIndex = 0;

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (charIndex < sourceText.length()) {
                    table[i][j] = sourceText.charAt(charIndex);
                    charIndex++;
                    continue;
                }
                table[i][j] = '?';
            }
        }
        return table;
    }

    private void printMatrix(char[][] matrix, Integer side) {
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private char[][] swappingRows(char[][] table, Integer side) {
        char[][] shuffledTable = new char[side][side];
        List<Integer> order = new ArrayList<>();
        for (int i=0; i<side; i++) {
            order.add(i);
        }
        Collections.shuffle(order);

        for(int i=0; i < side; i++){
            shuffledTable[i] = table[order.get(i)];
        }
        return shuffledTable;
    }

    private char[][] swappingColumns(char[][] table, Integer side) {
        char[][] shuffledTable = new char[side][side];
        List<Integer> order = new ArrayList<>();
        for (int i=0; i<side; i++) {
            order.add(i);
        }
        Collections.shuffle(order);

        for(int i=0; i < side; i++){
            for (int j = 0; j < side; j++) { // I used the fact that matrix is square here
                shuffledTable[j][i] = table[j][order.get(i)];
            }
        }
        return shuffledTable;
    }

    private String makeWord(char[][] table, Integer side) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < side; i++) {
            stringBuilder.append(table[i]);
        }
        return stringBuilder.toString();
    }
}
