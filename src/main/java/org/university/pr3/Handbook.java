package org.university.pr3;

import java.util.HashMap;
import java.util.List;

public class Handbook {

    private static final List<String> ALPHABET = List.of(
            "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О",
            "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ь", "Ы", "Ъ", "Э", "Ю", "Я",
            " ", "0", "1", "2", "3", "4", "5", "6", "8", "9", "-"
    );

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
}
