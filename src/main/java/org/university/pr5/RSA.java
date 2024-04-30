package org.university.pr5;

import lombok.Getter;

import java.util.HashMap;

public class RSA {

    private final Integer p;
    private final Integer q;

    @Getter private Integer n;
    @Getter private Integer e;
    @Getter private Integer d;

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
        int e = p - 1;
        while (recursiveGCD(e, eulerFuncValue) != 1) {
            e--;
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
        int d = 0;
        while ((d * e) % eulerFuncValue != 1) {
            d++;
        }
        return d;
    }

}
