package org.university.pr4;

import java.util.Arrays;
import java.util.List;

public class Runner {

    private static final int SIMPLE_NUM = 83;

    public static void main(String[] args) {
        // Алгоритм "Решето Эратосфена"
        int n = 100;
        List<Integer> primeNumbers = new EratosthenesSieve().getPrimeNumbers(n);
        System.out.println("Prime numbers from 2 - " + n + "\n" + Arrays.toString(primeNumbers.toArray()));

        // Тест Миллера-Рабина для числа 83
        new MillerRabinTest().isComposite(SIMPLE_NUM);

        int primitiveRoot = new PrimalRoot().findPrimitiveRoot(SIMPLE_NUM);
        System.out.println("Primitive root: " + primitiveRoot);

        DiffieHellmanAlgorithm algorithm = new DiffieHellmanAlgorithm(SIMPLE_NUM, primitiveRoot);
        algorithm.run();

    }

}
