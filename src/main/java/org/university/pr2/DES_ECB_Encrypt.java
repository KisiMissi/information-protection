package org.university.pr2;

import java.util.List;

public class DES_ECB_Encrypt {

    private static final List<Integer> WORD_ORDER = ECBData.getWordOrder();
    private static final List<Integer> EXPANSION_ORDER = ECBData.getExpansionOrder();
    private static final List<List<List<Integer>>> S_TABLES = ECBData.getSTables();
    private static final List<Integer> F_FINAL_ORDER = ECBData.getFFinalOrder();
    private static final List<Integer> FINAL_ORDER = ECBData.getFinalOrder();

    public String action(String word, String key) {
        // перевод слова и ключа в двоичную UTF-8
        String s = conventToBinary(word);
        String k = conventToBinary(key);

        // расширение слова и ключа до 64 бит
        String s2 = expansionTo64(s);
        String k2 = expansionTo64(k);
        System.out.println("---EXPANSION 64---\nWord: " + s + ", " + s2 + "\nKey: " + k + ", " + k2);

        // начальная перестановка слова, расширенного до 64 бит
        String s3 = initialWordPermutation(s2);
        System.out.println("---WORD INITIAL PERMUTATION---\n" + s3);

        // процесс разделения и смены правой и левой частей
        String s4 = separateAndSwap(s3, k2);
        System.out.println("---WORD AFTER SPLIT AND SWAP---\n" + s4);

        // финальная перестановка полученного значения
        return finalWordPermutation(s4);
    }

    private String conventToBinary(String word) {
        byte[] bytes = word.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        System.out.println("'" + word + "' to binary: " + binary);
        return binary.toString();
    }

    private String expansionTo64(String binaryWord) {
        int wordLen = binaryWord.length();
        if (wordLen == 64) return binaryWord;

        return new String(new char[64 - wordLen]).replace("\0", "0") + binaryWord;
    }

    private String initialWordPermutation(String binaryWord) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 64; i++) {

            binary.append(binaryWord.charAt(WORD_ORDER.get(i) - 1));
        }
        return binary.toString();
    }

    private String separateAndSwap(String binaryWord, String key) {
        String resultRight = "";
        String resultLeft = "";

        String left = binaryWord.substring(0, 32);
        String right = binaryWord.substring(32, 64);

        // цикл из 16 итераций
        for (int i = 0; i < 16; i++) {
            System.out.println("---ITERATION " + i + "---");
            String key4 = key.substring(4 * i, (4 * i) + 4);

            resultLeft = right;
            resultRight = xor(left, F(right, key4), 32);

            right = resultRight;
            left = resultLeft;
        }
        return resultLeft + resultRight;
    }

    private String F(String right32, String key4) {
        String right48 = expansionRight32To48(right32); // расширение правой части с 32 до 48 бит
        System.out.println("Right48: " + right48 + " " + right48.length());

        String key48 = expansionKey4To48(key4);
        System.out.println("Key4: " + key4 + ", key48: " + key48);

        String rightXorKey = xor(right48, key48, 48);
        System.out.println("XOR: " + rightXorKey);

        String compress48To32 = compress48To32(rightXorKey);
        System.out.println(compress48To32);

        return finalFPermutation(compress48To32);
    }

    private String expansionRight32To48(String right) {
        StringBuilder binary48 = new StringBuilder();
        for (int i = 0; i < 48; i++) {
            binary48.append(right.charAt(EXPANSION_ORDER.get(i) - 1));
        }
        return binary48.toString();
    }

    private String expansionKey4To48(String key4) {
        return new String(new char[44]).replace("\0", "0") + key4;
    }

    private String xor(String val1, String val2, int len) {
        StringBuilder binary48 = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char v1 = val1.charAt(i);
            char v2 = val2.charAt(i);

            if (v1 == '0' & v2 == '0')
                binary48.append('0');
            else if (v1 == '0' & v2 == '1')
                binary48.append('1');
            else if (v1 == '1' & v2 == '0')
                binary48.append('1');
            else
                binary48.append('0');
        }
        return binary48.toString();
    }

    private String compress48To32(String rightXorKey) {
        StringBuilder binary32 = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String s = sPermutation(rightXorKey.substring(6 * i, (6 * i) + 6), S_TABLES.get(i));
            binary32.append(s);
        }
        return binary32.toString();
    }

    private String sPermutation(String binary6, List<List<Integer>> sTable) {
        int row = Integer.parseInt(new String(new char[] {binary6.charAt(0), binary6.charAt(5)}), 2);
        int column = Integer.parseInt(binary6.substring(1, 5), 2);
        System.out.println("ROW : " + row + ", COLUMN : " + column);
        String numInBinary = Integer.toBinaryString(sTable.get(row).get(column));
        return numInBinary.length() == 4 ? numInBinary : new String(new char[4 - numInBinary.length()]).replace("\0", "0") + numInBinary;
    }

    private String finalFPermutation(String binaryString32) {
        StringBuilder binary32 = new StringBuilder();
        for (int i=0; i<32; i++) {
            binary32.append(binaryString32.charAt(F_FINAL_ORDER.get(i) - 1));
        }
        return binary32.toString();
    }

    private String finalWordPermutation(String binaryString) {
        StringBuilder binary64 = new StringBuilder();
        for (int i =0; i<64; i++) {
            binary64.append(binaryString.charAt(FINAL_ORDER.get(i) - 1));
        }
        return binary64.toString();
    }
}
