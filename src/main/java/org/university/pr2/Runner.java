package org.university.pr2;

/**
 * Симметричное шифрование. Система шифрования данных DES
 * (шифрование 64 битной последовательности с использованием 64 битным ключом)
 */
public class Runner {
    public static void main(String[] args) {

//        String word = "abcABC";
//        for (byte symbol : word.getBytes()) {
//            System.out.println(symbol);
//            String binaryString = Integer.toBinaryString(symbol);
//            if (binaryString.length() < 8) {
//                String replace = new String(new char[8 - binaryString.length()]).replace("\0", "0");
//                binaryString =  replace + binaryString;
//                System.out.println(Integer.parseInt(binaryString, 2));
//            }
//        }


//        ECB ecb = new ECB();
//        System.out.println( ecb.encrypt("h", "k"));



        new DES_ECB_Encrypt().action("ab", "key");
    }
}
