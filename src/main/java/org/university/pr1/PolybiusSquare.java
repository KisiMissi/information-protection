package org.university.pr1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolybiusSquare {

    private static final Pattern rusPattern = Pattern.compile("[абвгдеёжзийклмнопрстуфхцчшщъыьэюя ]+");
    private static final Pattern engPattern = Pattern.compile("[abcdefghijklmnopqrstuvwxyz ]+");
    private static final char[][] POLYBIUS_SQUARE_ENG = {
            {'a', 'b', 'c', 'd', 'e'},
            {'f', 'g', 'h', 'i', 'j'},
            {'k', 'l', 'm', 'n', 'o'},
            {'p', 'q', 'r', 's', 't'},
            {'u', 'v', 'w', 'x', 'y'},
            {'z', ' ', ' ', ' ', ' '}
    };
    private static final char[][] POLYBIUS_SQUARE_RUS = {
            {'а', 'б', 'в', 'г', 'д', 'е'},
            {'ё', 'ж', 'з', 'и', 'й', 'к'},
            {'л', 'м', 'н', 'о', 'п', 'р'},
            {'с', 'т', 'у', 'ф', 'х', 'ц'},
            {'ч', 'ш', 'щ', 'ъ', 'ы', 'ь'},
            {'э', 'ю', 'я', ' ', ' ', ' '}
    };

    public String encrypt(String sourceString) {
        StringBuilder stringBuilder = new StringBuilder();

        char[][] encryptSquare = {};
        Matcher engMatcher = engPattern.matcher(sourceString);
        Matcher rusMatcher = rusPattern.matcher(sourceString);
        if (engMatcher.find()) {
            encryptSquare = POLYBIUS_SQUARE_ENG;
        } else if (rusMatcher.find()) {
            encryptSquare = POLYBIUS_SQUARE_RUS;
        }

        for (int i = 0; i < sourceString.length(); i++) {
            String symbolCipher = symbolToCipher(sourceString.charAt(i), encryptSquare);
            stringBuilder.append(symbolCipher);
        }

        return stringBuilder.toString();
    }

    public String decrypt(String encryptString) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < encryptString.length(); i += 2) {
            String symbolCode = encryptString.substring(i, i + 2);
            stringBuilder.append(cipherToSymbol(symbolCode));
        }
        return stringBuilder.toString();
    }

    private String symbolToCipher(char var, char[][] square) {
        String cipher = "";

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (var == square[i][j])
                    cipher = String.valueOf(i) + j;
            }
        }
        return cipher;
    }

    private char cipherToSymbol(String rowAndColumn) {
        int row = Integer.parseInt(rowAndColumn.substring(0, 1));
        int column = Integer.parseInt(rowAndColumn.substring(1, 2));

        return POLYBIUS_SQUARE_ENG[row][column];
    }

}
