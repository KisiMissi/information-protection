package org.university.pr1;

public class CaesarCipher {

    public String encrypt(String sourceString, Integer moveValue) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char sourceChar : sourceString.toCharArray()) {
            int characterCode = (int) sourceChar + moveValue;
            stringBuilder.append((char) characterCode);
        }
        return stringBuilder.toString();
    }

}
