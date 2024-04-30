package org.university.pr5;

import java.math.BigInteger;
import java.util.Scanner;

public class Runner {

    private static final Scanner sc = new Scanner(System.in);
    private static RSA rsa;

    private static final int p = 23;
    private static final int q = 61;

    public static void main(String[] args) {
        rsa = new RSA(p, q);

        while (true) {
            System.out.println("\n==== Алгоритм цифровой подписи RSA ====");
            System.out.print("Enter message: ");
            String message = sc.nextLine();
            String m = MD4.getHash(message);
            System.out.printf("m = h(%s) = %s,%n", message, m);

            // Create key S
            String s = getS(m);
            System.out.println("S = " + s);

            System.out.println("\n--- RSA ---");
            showKeys();

            System.out.println("\n--- Receiver ---");
            System.out.println("m` = S^E (mod N) = " + restoreM(s));
            System.out.println("m = h(" + message + ") = " + MD4.getHash(message));
        }
    }

    private static void showKeys() {
        rsa.showOpenKey();
        rsa.showCloseKey();
    }

    private static String getS(String m) {
        return new BigInteger(m, 16).modPow(BigInteger.valueOf(rsa.getD()), BigInteger.valueOf(rsa.getN()))
                                    .toString();
    }

    private static String restoreM(String s) {
        return new BigInteger(s).modPow(BigInteger.valueOf(rsa.getE()), BigInteger.valueOf(rsa.getN()))
                                .toString();
    }
}
