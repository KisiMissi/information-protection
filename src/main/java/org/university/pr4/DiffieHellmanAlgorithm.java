package org.university.pr4;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellmanAlgorithm {

    private static final Random rand = new Random();

    private int simpleNum;
    private int primeRootOfNum;

    private int secretNumA;
    private int secretNumB;

    public DiffieHellmanAlgorithm(int simpleNum, int primeRootOfNum) {
        System.out.println("\n=== Алгоритм Диффи-Хеллмана ===");
        this.simpleNum = simpleNum;
        this.primeRootOfNum = primeRootOfNum;
    }

    public void run() {
        int openKeyA = getKeyForA();
        int openKeyB = getKeyForB();

        int calculatedKeyA = subscriberBCalculateSecretKey(openKeyA);
        int calculatedKeyB = subscriberACalculateSecretKey(openKeyB);

        System.out.println("Q: " + simpleNum + ", A: " + primeRootOfNum + "\n" +
                           "Open A: " + openKeyA + ", Secret A: " + secretNumA + "\n" +
                           "Open B: " + openKeyB + ", Secret B: " + secretNumB + "\n" +
                           "Calculated secrete key A: " + calculatedKeyA + "\n" +
                           "Calculated secrete key B: " + calculatedKeyB);
    }

    public int subscriberBCalculateSecretKey(int openKeyA) {
        return BigInteger.valueOf(openKeyA).pow(secretNumB).mod(BigInteger.valueOf(simpleNum)).intValue();
    }

    public int subscriberACalculateSecretKey(int openKeyB) {
        return BigInteger.valueOf(openKeyB).pow(secretNumA).mod(BigInteger.valueOf(simpleNum)).intValue();
    }

    public int getKeyForA() {
        secretNumA = rand.nextInt(2, simpleNum);
        return BigInteger.valueOf(primeRootOfNum).pow(secretNumA).mod(BigInteger.valueOf(simpleNum)).intValue();
    }

    public int getKeyForB() {
        secretNumB = rand.nextInt(2, simpleNum);
        return BigInteger.valueOf(primeRootOfNum).pow(secretNumB).mod(BigInteger.valueOf(simpleNum)).intValue();
    }

}
