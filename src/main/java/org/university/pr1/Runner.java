package org.university.pr1;

import java.util.Scanner;

public class Runner {

    private static final String CIPHER_SELECTION = """
            Шифры:
            1 - Шифр Цезаря
            2 - Квадрат Полибия
            3 - Квадрат Кардано
            4 - Шифр Плейфера
            5 - Aлгоритм двойной перестановки
            0 - Выход
            """;

    private static final String[] CAESAR_TEXT = {
            "Жена Цезаря должна быть вне подозрений",
            "Рубикон перейден. Жребий брошен",
            "И ты, Брут!",
            "Гай Юлий Цезарь"
    };
    private static final Integer[] CAESAR_MOVE = {
            1, 14, 8, 19
    };

    private static final CaesarCipher caesarCipher = new CaesarCipher();
    private static final PolybiusSquare polybiusSquare = new PolybiusSquare();
    private static final CardanoGrid cardanoGrid = new CardanoGrid();
    private static final PlayfairCipher playfairCipher = new PlayfairCipher();
    private static final DoublePermutationAlgorithm doublePermutation = new DoublePermutationAlgorithm();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(CIPHER_SELECTION);
            int mode = scanner.nextInt();
            switch (mode) {
                case 1:
                    caesarEncrypting();
                    continue;
                case 2:
                    polybiusEncryptingAndDecrypting();
                    continue;
                case 3:
                    cardanoCipherEncryptAndDecrypt();
                    continue;
                case 4:
                    playfairCipherEncrypt();
                    continue;
                case 5:
                    doublePermutationAlgorithmEncrypt();
            }
        }

    }

    // Caesar Cipher
    private static void caesarEncrypting() {
        System.out.println("--- ШИФР ЦЕЗАРЯ ---");
        for (int i = 0; i < 4; i++) {
            String sourceString = CAESAR_TEXT[i];
            Integer moveValue = CAESAR_MOVE[i];

            String encrypt = caesarCipher.encrypt(sourceString, moveValue);

            System.out.println(
                    "Source text: " + sourceString + ", Move value: " + moveValue + "\n" +
                    "Encrypt text: " + encrypt + "\n");
        }
    }

    // Polybius Cipher
    private static void polybiusEncryptingAndDecrypting() {
        System.out.println("--- КВАДРАТ ПОЛИБИЯ ---");

        System.out.println("Введите тексе:");
        String text = new Scanner(System.in).nextLine();

        String encrypt = polybiusSquare.encrypt(text);
        System.out.println(
                "Source text: " + text + "\n" +
                "Encrypt text: " + encrypt);

        String decrypt = polybiusSquare.decrypt(encrypt);
        System.out.println(
                "Decrypt text: " + decrypt);

        System.out.println();
    }

    private static void cardanoCipherEncryptAndDecrypt() {
        System.out.println("--- КВАДРАТ КАРДАНО  ---");

        System.out.println("Введите размер квадрата [длина стороны]: ");
        int gridSize = new Scanner(System.in).nextInt();

        int maxLength = (gridSize * gridSize) / 2;
        System.out.println("Введите текст (макс длина = " + maxLength + "): ");
        String text = new Scanner(System.in).nextLine();

        String encrypt = cardanoGrid.encrypt(text, gridSize);
        System.out.println(
                "Source text: " + text + ", стороны квадрата: " + gridSize + "\n" +
                "Encrypt txt: " + encrypt);

        String decrypt = cardanoGrid.decrypt(encrypt);
        System.out.println(
                "Decrypt text: " + decrypt);

        System.out.println();
    }

    // Playfair`s Cipher
    private static void playfairCipherEncrypt() {
        System.out.println("--- ШИФР ПЛЕЙФЕРА ---");

        String sourceText = "ВОТ И СНОВА ОСЕНЬ";
        String keyWord = "КОМАНДИР";
        char emptyChar = 'ф';

        String encrypt = playfairCipher.encrypt(sourceText, keyWord, emptyChar);
        System.out.println(
                "Source text: " +  sourceText + "\n" +
                "Encrypt text: " + encrypt
        );

        System.out.println();
    }

    // Double Permutation Algorithm
    private static void doublePermutationAlgorithmEncrypt() {
        System.out.println("--- АЛГОРИТМ ДВОЙНОЙ ПЕРЕСТАНОВКИ ---");

        String sourceText = "Неясное становится еще более непонятным";
        String encrypt = doublePermutation.encrypt(sourceText);
        System.out.println(
                "Source text: " +  sourceText + "\n" +
                "Encrypt text: " + encrypt
        );

        System.out.println();
    }

}
