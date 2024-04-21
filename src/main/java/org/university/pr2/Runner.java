package org.university.pr2;

/**
 * Симметричное шифрование. Система шифрования данных DES
 * (шифрование 64 битной последовательности с использованием 64 битным ключом)
 */
public class Runner {
    public static void main(String[] args) {

        String word = "abcde";
        String key = "key";

        String encrypt = new DES_ECB_Encrypt().action(word, key);
        System.out.println("===ENCYPT===\n" + encrypt);

        String decrypt = new DES_ECB_Decrypt().action(encrypt, key);
        System.out.println("===DECRYPT===\n" + decrypt);
    }
}
