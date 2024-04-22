package org.university.pr3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RSA {

    private final Integer p;
    private final Integer q;

    private Integer n;
    private Integer e;
    private Integer d;

    private final HashMap<String, Integer> ENCRYPT_MAP = Handbook.getEncryptDictionary();
    private final HashMap<Integer, String> DECRYPT_MAP = Handbook.getDecryptDictionary();

    public RSA(int p, int q) {
        this.p = p;
        this.q = q;
        createKeys();
    }

    public void showOpenKey() {
        System.out.println("Open key: (e, n) = (" + e + ", " + n + ")");
    }

    public void showCloseKey() {
        System.out.println("Close key: (d, n) = (" + d + ", " + n + ")");
    }

    public List<Integer> encrypt(String message) {
        List<Integer> encrypt = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            Integer charNum = ENCRYPT_MAP.get(message.substring(i, i + 1));
            BigInteger var = (new BigInteger(String.valueOf(charNum))).pow(e)
                                                                      .mod(BigInteger.valueOf(n));
            encrypt.add(var.intValue());
        }
        return encrypt;
    }

    public String decrypt(List<Integer> encryptedList) {
        StringBuilder builder = new StringBuilder();
        for (Integer integer : encryptedList) {
            BigInteger var = (new BigInteger(String.valueOf(integer))).pow(d)
                                                                      .mod(BigInteger.valueOf(n));
            builder.append(DECRYPT_MAP.get(var.intValue()));
        }
        return builder.toString();
    }

    private void createKeys() {
        n = p * q;
        int eulerFuncValue = EulerFunction(p, q);
        System.out.println("P: " + p + ", Q = " + q + "\nN = " + n + ", EulerFunc = " + eulerFuncValue);

        e = getE(eulerFuncValue);
        d = getD(eulerFuncValue, e);
    }


    private Integer EulerFunction(int p, int q) {
        return (p - 1) * (q - 1);
    }

    private int getE(int eulerFuncValue) {
        int e = 0;
        while (recursiveGCD(e, eulerFuncValue) != 1) {
            e++;
        }
        return e;
    }

    int recursiveGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (a < b) {
            return recursiveGCD(b, a);
        }
        return recursiveGCD(b, a % b);
    }


    private Integer getD(int eulerFuncValue, int e) {
        double d = 0d;
        int k = 1;
        while (true) {
            d = (double) (k * eulerFuncValue + 1) / e;
            if (d % 1 == 0) {
                break;
            }
            k++;
        }
        return (int) d;
    }

}
