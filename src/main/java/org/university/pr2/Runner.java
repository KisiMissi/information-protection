package org.university.pr2;

/**
 * Симметричное шифрование. Система шифрования данных DES
 * (шифрование 64 битной последовательности с использованием 64 битным ключом)
 */
public class Runner {
    public static void main(String[] args) {

        String encrypt = new DES_ECB_Encrypt().action("ab", "key");
        System.out.println(encrypt);

    }
}
