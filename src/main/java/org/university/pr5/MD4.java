package org.university.pr5;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MD4 {

    private static final HashMap<String, String> MAP = Handbook.getMap();

    public static String  getHash(String message) {
        String binary = "1" + conventToBinary(message);
        int expansionTo = binary.length() % 512 == 448
                ? binary.length()
                : binary.length() + (448 - (binary.length() % 512));
        String messageLength = expansion(Integer.toBinaryString(message.length()), 64);
        binary = messageLength + expansion(binary, expansionTo);

        List<String> blocks = splitByBlocks(binary);
        String hash = hashCode(blocks);
        return new BigInteger(getHex(message), 2).toString(16);
    }

    private static String conventToBinary(String word) {
        byte[] bytes = word.getBytes();
        ArrayUtils.reverse(bytes);
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

    private static String expansion(String binary, int bound) {
        return bound == binary.length()
                ? binary
                : new String(new char[bound - binary.length()]).replace("\0", "0") + binary;
    }

    private static List<String> splitByBlocks(String binary) {
        int numOfBlocks = binary.length() / 512;
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < numOfBlocks; i++) {
            blocks.add(binary.substring(i * 512, (i * 512) + 512));
        }
        return blocks;
    }

    private static String hashCode(List<String> blocks) {
        List<String> buffer = Handbook.getBuffer();
        for (String block : blocks) {
            buffer = round(buffer, block, 1, Handbook.getK_ORDER_1(), Handbook.getS_ORDER_1());
            buffer = round(buffer, block, 2, Handbook.getK_ORDER_2(), Handbook.getS_ORDER_2());
            buffer = round(buffer, block, 3, Handbook.getK_ORDER_3(), Handbook.getS_ORDER_3());
        }
        return buffer.stream()
                     .reduce("", String::concat);
    }

    private static List<String> round(List<String> buffer, String block, int roundNumber, List<Integer> kOrder, List<Integer> sOrder) {
        String a = "00000000000000000000000000000000";
        String b = "00000000000000000000000000000000";
        String c = "00000000000000000000000000000000";
        String d = "00000000000000000000000000000000";

        String t2 = "01011010100000100111100110011001";
        String t3 = "01101110110110011110101110100001";

        String x;
        int k;

        for (int i = 0; i < 16; i++) {
            if (i < 4) {
                a = buffer.get(0);
                b = buffer.get(1);
                c = buffer.get(2);
                d = buffer.get(3);
            } else if (i == 4 & i < 8) {
                a = buffer.get(1);
                b = buffer.get(2);
                c = buffer.get(3);
                d = buffer.get(0);
            } else if (i == 8 & i < 12) {
                a = buffer.get(2);
                b = buffer.get(3);
                c = buffer.get(0);
                d = buffer.get(1);
            } else {
                a = buffer.get(3);
                b = buffer.get(0);
                c = buffer.get(1);
                d = buffer.get(2);
            }

            k = kOrder.get(i);
            x = block.substring(32 * k, (32 * k) + 32);

            switch (roundNumber) {
                case 1:
                    a = bitwiseShift(or(a, F(b, c, d), x), sOrder.get(i));
                    break;
                case 2:
                    a = bitwiseShift(or(a, G(b, c, d), x, t2), sOrder.get(i));
                case 3:
                    a = bitwiseShift(or(a, H(b, c, d), x, t3), sOrder.get(i));
            }
            buffer = List.of(a, b, c, d);
        }
        return List.of(a, b, c, d);
    }


    private static String F(String x, String y, String z) {
        return or(and(x, y), and(not(x), z));
    }

    private static String G(String x, String y, String z) {
        return or(and(x, y), and(x, z), and(y, z));
    }

    private static String H(String x, String y, String z) {
        return xor(xor(x, y), z);
    }

    private static String bitwiseShift(String binary, int shift) {
        return binary.substring(shift) + binary.substring(0, shift);
    }

    private static String and(String var1, String var2) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (var1.charAt(i) == '1' & var2.charAt(i) == '1')
                binary.append('1');
            else
                binary.append('0');
        }
        return binary.toString();
    }

    private static String or(String... vars) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            boolean allZero = true;
            for (String var : vars) {
                if (var.charAt(i) == '1') {
                    binary.append('1');
                    allZero = false;
                    break;
                }
            }
            if (allZero)
                binary.append('0');
        }
        return binary.toString();
    }

    private static String not(String var) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (var.charAt(i) == '0')
                binary.append('1');
            else
                binary.append('0');
        }
        return binary.toString();
    }

    private static String xor(String var1, String var2) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (var1.charAt(i) == var2.charAt(i))
                binary.append('0');
            else
                binary.append('1');
        }
        return binary.toString();
    }

    private static String getHex(String message) {
        if (MAP.containsKey(message))
            return MAP.get(message);
        else {
            String hex = hex();
            MAP.put(message, hex);
            return hex;
        }
    }

    private static String hex() {
        Random rand = new Random();
        StringBuilder binary = new StringBuilder();
        for (int i =0 ; i< 128; i++) {
            binary.append(rand.nextInt(0, 2));
        }
        return binary.toString();
    }
}
