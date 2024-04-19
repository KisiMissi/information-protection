package org.university.pr2;

import java.math.BigInteger;
import java.util.List;

public class ECB {

    private static final Integer WORD_SEQUENCE_LENGTH = 64;
    private static final Integer KEY_SEQUENCE_LENGTH = 48;
    private static final Integer EXPANSION_LENGTH = 48;
    private static final Integer PERMUTATION_NUMBER = 16;

    private static final List<Integer> WORD_ORDER = ECBData.getWordOrder();
    private static final List<Integer> EXPANSION_ORDER = ECBData.getExpansionOrder();
    private static final List<Integer> KEY_ORDER = ECBData.getKeyOrder();
    private static final List<Integer> FINAL_ORDER = ECBData.getFinalOrder();
    private static final List<List<List<Integer>>> S = ECBData.getSTables();

    public String encrypt(String var, String key) {
        // начальная перестановка битов
        var = permutationOfValues(var, WORD_ORDER, WORD_SEQUENCE_LENGTH);
        // перестановка правой и левой частей
        String permutation = permutation(var, key);
        // конечная перестановка битов
        String result = permutationOfValues(permutation, FINAL_ORDER, WORD_SEQUENCE_LENGTH);
        return binaryStringToCharacters(result);
    }

    // перестановка правой и левой частей
    private String permutation(String bits, String key) {
        // разделение на правую и левую части
        String right = bits.substring(0, 32);
        String left = bits.substring(32, 63);

        for (int i = 0; i < PERMUTATION_NUMBER; i++) {
            // начальное положение битов для ключа
            key = permutationOfValues(key, KEY_ORDER, KEY_SEQUENCE_LENGTH);
            // шифровка правой части
            String encryptedRight = encryptFunction(right, key);

            // смена правой и левой части местами
            String tempLeft = left; // сохраняем для того, чтобы соединить с правой частью
            left = right;



            BigInteger encryptedRightBits = new BigInteger(encryptedRight, 2);
            right = expansionTo32(new BigInteger(tempLeft, 2).xor(encryptedRightBits));
        }
        return left + right;
    }

    // F(R(i-1), K(i)), i=1,2,3..16
    private String encryptFunction(String bits, String key) {
        StringBuilder builder = new StringBuilder();
        Integer sTableNum = 0;

        bits = expansion32To48(bits); // расширение значения от 32 до 48

        // XOR значения и ключа
        BigInteger keyBits = new BigInteger(key, 2);
        BigInteger xor = new BigInteger(bits, 2).xor(keyBits);
        String binaryString = expansionTo48(xor);

        // деление 48 битовой последовательности на 8 6-битовых частей
        for (int i = 0; i < 48; i += 6) {
            // преобразование 6 -> 4 бита
            String compressed = compression6To4(binaryString.substring(i, i + 5), sTableNum);
            builder.append(compressed);
            sTableNum++;
        }
        return builder.toString();
    }

    private String leftRightConnection(String left, String right) {
        StringBuilder builder = new StringBuilder();
        byte[] leftBytes = left.getBytes();
        byte[] rightBytes = right.getBytes();

        for (int i = 0; i<4; i++) {

        }
        return null;
    }

    // начальная перестановка битов
    private String permutationOfValues(String var, List<Integer> order, Integer sequenceLength) {
        StringBuilder builder = new StringBuilder();
        String binary = expansionTo64(var);

        char[] charArray = binary.toCharArray();

        // перестановка битов
        for (int i = 0; i < sequenceLength; i++) {
            builder.append(charArray[order.get(i) - 1]);
        }
        return builder.toString();
    }

    private String expansion32To48(String bits) {
        StringBuilder builder = new StringBuilder();
        char[] charArray = bits.toCharArray();
        for (int i = 0; i < EXPANSION_LENGTH; i++) {
            builder.append(charArray[EXPANSION_ORDER.get(i) - 1]);
        }
        return builder.toString();
    }

    private static String expansionTo64(String var) {
        // если длина бинарной строки каждого байта меньше 8, то дописываются нули до длины 8
        StringBuilder builder = new StringBuilder();
        for (byte symbol : var.getBytes()) {
            String binaryString = Integer.toBinaryString(symbol);
            if (binaryString.length() < 8) {
                String zeros = new String(new char[8 - binaryString.length()]).replace("\0", "0");
                binaryString = zeros + binaryString;
            }
            builder.append(binaryString);
        }
        return builder.toString();
    }

    private static String expansionTo48(BigInteger var) {
        // если количество битов меньше 48 происходит добавление нулей
        StringBuilder binary = new StringBuilder(new BigInteger(var.toByteArray()).toString(2)).reverse();
        while (binary.length() < 48) {
            binary.append("0");
        }
        return binary.toString();
    }

    private static String expansionTo32(BigInteger var) {
        // если количество битов меньше 32 происходит добавление нулей
        StringBuilder binary = new StringBuilder(new BigInteger(var.toByteArray()).toString(2)).reverse();
        while (binary.length() < 48) {
            binary.append("0");
        }
        return binary.toString();
    }

    private String compression6To4(String bits, Integer sNum) {
        int row = Integer.parseInt(bits.substring(0, 1) + bits.substring(4, 5), 2);
        int column = Integer.parseInt(bits.substring(1, 5), 2);
        return Integer.toBinaryString(S.get(sNum)
                                       .get(row)
                                       .get(column));
    }

    private String binaryStringToCharacters(String binaryString) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < WORD_SEQUENCE_LENGTH; i += 8) {
            builder.append((char) Integer.parseInt(binaryString.substring(i, i + 8)));
        }
        return builder.toString();
    }
}
