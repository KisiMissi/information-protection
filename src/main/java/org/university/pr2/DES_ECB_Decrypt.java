package org.university.pr2;

import java.util.List;

public class DES_ECB_Decrypt {

    private static final List<Integer> WORD_ORDER = ECBData.getWordOrder();
    private static final List<Integer> EXPANSION_ORDER = ECBData.getExpansionOrder();
    private static final List<List<List<Integer>>> S_TABLES = ECBData.getSTables();
    private static final List<Integer> F_FINAL_ORDER = ECBData.getFFinalOrder();
    private static final List<Integer> FINAL_ORDER = ECBData.getFinalOrder();

    public String action(String binaryString, String key) {
        // отмена последней перестановки
        String s1 = reverseFinalPermutation(binaryString);

        // перевод ключа в двоичную и расширение до 64
        String k = conventToBinary(key);
        String k2 = expansionTo64(k);

        // обратное разделение и смена 16 -> 1
        String s2 = reverseSeparateAndSwap(s1, k2);

        // обратная начальная перестановка слова
        String s3 = reverseInitialPermutation(s2);

        return binaryToWord(s3);
    }

    private String reverseFinalPermutation(String binaryString) {
        char[] builder = new char[64];
        for (int i = 0; i < 64; i++) {
            builder[FINAL_ORDER.get(i) - 1] = binaryString.charAt(i);
        }
        return String.valueOf(builder);
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

    private String reverseSeparateAndSwap(String binaryWord, String key) {
        String right0 = "";
        String left0 = "";

        String left1 = binaryWord.substring(0, 32);
        String right1 = binaryWord.substring(32, 64);

        for (int i = 15; i >= 0; i--) {
            System.out.println("---ITERATION " + i + "---");
            String key4 = key.substring(4 * i, (4 * i) + 4);

            left0 = xor(right1, F(left1, key4), 32);
            right0 = left1;

            left1 = left0;
            right1 = right0;
        }
        return left0 + right0;
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
        int row = Integer.parseInt(new String(new char[]{binary6.charAt(0), binary6.charAt(5)}), 2);
        int column = Integer.parseInt(binary6.substring(1, 5), 2);
        System.out.println("ROW : " + row + ", COLUMN : " + column);
        String numInBinary = Integer.toBinaryString(sTable.get(row).get(column));
        return numInBinary.length() == 4 ? numInBinary : new String(new char[4 - numInBinary.length()]).replace("\0", "0") + numInBinary;
    }

    private String finalFPermutation(String binaryString32) {
        StringBuilder binary32 = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            binary32.append(binaryString32.charAt(F_FINAL_ORDER.get(i) - 1));
        }
        return binary32.toString();
    }

    private String reverseInitialPermutation(String binaryString) {
        char[] builder = new char[64];
        for (int i = 0; i < 64; i++) {
            builder[WORD_ORDER.get(i) - 1] = binaryString.charAt(i);
        }
        return String.valueOf(builder);
    }

    private String binaryToWord(String binaryString) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<8; i++) {
            String emptyChar = "";
            String substring = binaryString.substring(8 * i, (8 * i) + 8);
            if (substring.equals("00000000"))
                builder.append(emptyChar);
            else
                builder.append((char) Integer.parseInt(substring, 2));
        }
        return builder.toString();
    }
}
