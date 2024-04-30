package org.university.pr5;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;

public class Handbook {

    private static final List<String> ALPHABET = List.of(
            "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О",
            "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ь", "Ы", "Ъ", "Э", "Ю", "Я",
            " ", "0", "1", "2", "3", "4", "5", "6", "8", "9", "-"
    );

    // Раунд 1
    @Getter private static final List<Integer> K_ORDER_1 = List.of(0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15);
    @Getter private static final List<Integer> S_ORDER_1 = List.of(3, 3, 3, 3, 7, 7, 7, 7, 11, 11, 11, 11, 19, 19, 19, 19);

    // Раунд 2
    @Getter private static final List<Integer> K_ORDER_2 = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    @Getter private static final List<Integer> S_ORDER_2 = List.of(3, 7, 11, 19, 3, 7, 11, 19, 3, 7, 11, 19, 3, 7, 11, 19);

    // Раунд 3
    @Getter private static final List<Integer> K_ORDER_3 = List.of(0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15);
    @Getter private static final List<Integer> S_ORDER_3 = List.of(3, 3, 3, 3, 9, 9, 9, 9, 11, 11, 11, 11, 15, 15, 15, 15);

    private static final String A = "01100111010001010010001100000001";
    private static final String B = "11101111110011011010101110001001";
    private static final String C = "10011000101110101101110011111110";
    private static final String D = "00010000001100100101010001110110";

    private static final String EMPTY_MESSAGE = "00110001110101101100111111100000110100010110101011101001001100011011011100111100010110011101011111100000110000001000100111000000";

    public static HashMap<String, Integer> getEncryptDictionary() {
        HashMap<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < ALPHABET.size(); i++) {
            dict.put(ALPHABET.get(i), i + 1);
        }
        return dict;
    }

    public static HashMap<Integer, String> getDecryptDictionary() {
        HashMap<Integer, String> dict = new HashMap<>();
        for (int i = 0; i < ALPHABET.size(); i++) {
            dict.put(i + 1, ALPHABET.get(i));
        }
        return dict;
    }

    public static List<String> getBuffer() {
        return List.of(A, B, C, D);
    }

    public static HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("", EMPTY_MESSAGE);
        return map;
    }
}
