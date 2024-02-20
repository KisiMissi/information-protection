package org.university.pr1;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PlayfairCipher {

    private static final Integer PLAYFAIR_GRID_ROW = 4;
    private static final Integer PLAYFAIR_GRID_COLUMN = 8;
    private static final String ABC_RUS = "абвгдежзийклмнопрстуфхцчшщъыьэюя";

    public String encrypt(String sourceString, String keyWord, char emptyChar) {
        sourceString = sourceString.toLowerCase();
        keyWord = keyWord.toLowerCase();

        char[][] grid = createGrid(keyWord);
        char[][] bigrams = textIntoBigrams(sourceString, emptyChar);

        return encrypting(grid, bigrams);
    }

    private char[][] textIntoBigrams(String sourceText, char emptyChar) {
        sourceText = sourceText.replaceAll(" ", "");
        List<char[]> bigrams = new ArrayList<>();

        for (int i = 0; i < sourceText.length(); i++) {
            char ch1 = sourceText.charAt(i);
            if (i + 1 == sourceText.length()) {
                bigrams.add(new char[]{ch1, emptyChar});
                break;
            }

            char ch2 = sourceText.charAt(i + 1);
            if (ch1 == ch2) {
                bigrams.add(new char[]{ch1, emptyChar});
            } else {
                bigrams.add(new char[]{ch1, ch2});
                i++;
            }
        }

        int index = 0;
        char[][] filteredBigrams = new char[bigrams.size()][2];
        for (char[] bigram : bigrams) {
            filteredBigrams[index] = bigram;
            index++;
        }

        return filteredBigrams;
    }

    private char[][] createGrid(String keyWord) {
        char[][] grid = new char[PLAYFAIR_GRID_ROW][PLAYFAIR_GRID_COLUMN];

        keyWord = deletingDuplicateChars(keyWord);

        char[] charArray = keyWord.toCharArray();
        int keyWordLength = keyWord.length();
        int keyWordIndex = 0;
        int abcIndex = 0;

        String abc = ABC_RUS.replaceAll("[" + keyWord + "]", "");

        for (int i = 0; i < PLAYFAIR_GRID_ROW; i++) {
            for (int j = 0; j < PLAYFAIR_GRID_COLUMN; j++) {

                if (keyWordIndex < keyWordLength) {
                    grid[i][j] = charArray[keyWordIndex];
                    keyWordIndex++;
                    continue;
                }

                grid[i][j] = abc.charAt(abcIndex);
                abcIndex++;
            }
        }
        return grid;
    }

    private String deletingDuplicateChars(String string) {
        char[] chars = string.toCharArray();
        Set<Character> charSet = new LinkedHashSet<Character>();
        for (char c : chars) {
            charSet.add(c);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (char character : charSet) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    private void printMatrix(char[][] grid) {
        for (int i = 0; i < PLAYFAIR_GRID_ROW; i++) {
            for (int j = 0; j < PLAYFAIR_GRID_COLUMN; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private String encrypting(char[][] grid, char[][] bigrams) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] bigram : bigrams) {
            stringBuilder.append(encryptBigram(grid, bigram));
        }
        return stringBuilder.toString();
    }

    private String encryptBigram(char[][] grid, char[] bigram) {
        String encrypt = "";
        int row1 = 0;
        int column1 = 0;
        int row2 = 0;
        int column2 = 0;

        for (int i = 0; i < PLAYFAIR_GRID_ROW; i++) {
            for (int j = 0; j < PLAYFAIR_GRID_COLUMN; j++) {
                if (grid[i][j] == bigram[0]) {
                    row1 = i;
                    column1 = j;
                    continue;
                }

                if (grid[i][j] == bigram[1]) {
                    row2 = i;
                    column2 = j;
                }
            }
        }

        if (row1 == row2) {
            if (column1 == PLAYFAIR_GRID_COLUMN - 1) {
                encrypt = String.valueOf(grid[row1][0]) + grid[row2][column2 + 1];
            } else if (column2 == PLAYFAIR_GRID_COLUMN + 1) {
                encrypt = String.valueOf(grid[row1][column1 - 1]) + grid[row2][0];
            } else {
                encrypt = String.valueOf(grid[row1][column1 + 1]) + grid[row2][column2 + 1];
            }
        } else if (column1 == column2) {
            if (row1 == PLAYFAIR_GRID_ROW - 1) {
                encrypt = String.valueOf(grid[0][column1]) + grid[row2 + 1][column2];
            } else if (row2 == PLAYFAIR_GRID_ROW - 1) {
                encrypt = String.valueOf(grid[row1 + 1][column1]) + grid[0][column2];
            } else {
                encrypt = String.valueOf(grid[row1 + 1][column1]) + grid[row2 + 1][column2];
            }
        } else {
            encrypt = String.valueOf(grid[row1][column2]) + grid[row2][column1];
        }

        return encrypt;
    }
}
