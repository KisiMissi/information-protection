package org.university.pr2;

import java.util.List;

public class DES_ECB_Encrypt {

    private static final List<Integer> WORD_ORDER = ECBData.getWordOrder();

    public String action(String word, String key) {
        String binaryWord = conventToBinary(word); // to binary string

        String binaryWord64 = expansionTo64(binaryWord); // expansion to 64 bits
        System.out.println(binaryWord64);

        String binaryWord64Permutated = initialWordPermutation(binaryWord64);
        System.out.println(binaryWord64Permutated);
        return null;
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


}
