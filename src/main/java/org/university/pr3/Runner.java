package org.university.pr3;

import java.util.Arrays;
import java.util.List;

public class Runner {

    private static final Integer p = 23;
    private static final Integer q = 61;
    private static final String message = "КАО-ДЕН";
    private static final String message2 = "КАФСИ";

    public static void main(String[] args) {
        RSA rsa = new RSA(p, q);

        rsa.showOpenKey();
        rsa.showCloseKey();

        List<Integer> encrypt = rsa.encrypt(message);
        String decrypt = rsa.decrypt(encrypt);

        System.out.println("Source message: " + message + "\nEncrypted: " + encrypt + "\nDecrypted: " + decrypt);

        List<Integer> testEncrypt = List.of(1071, 1, 606, 449, 1215, 472);
        String testDecrypt = rsa.decrypt(testEncrypt);
        System.out.println("Test encrypt: " + testEncrypt + "\nTest decrypt: " + testDecrypt);

    }

}
