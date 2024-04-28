package org.university.pr4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EratosthenesSieve {

    public EratosthenesSieve() {
        System.out.println("\n=== Алгоритм «Решето Эратосфена» ===");
    }

    public List<Integer> getPrimeNumbers(int n) {
        List<Integer> numList = Stream.iterate(2, num -> num + 1)
                                      .limit(n - 1)
                                      .toList();
        return removeNotPrime(numList);
    }

    private List<Integer> removeNotPrime(List<Integer> numList) {
        List<Integer> resultList = new ArrayList<>();
        int firstValue = numList.getFirst();
        while (firstValue * firstValue < numList.getLast()) {
            resultList.add(firstValue);
            int finalFirstValue = firstValue;
            numList = numList.stream()
                             .filter(num -> num % finalFirstValue != 0)
                             .toList();

            firstValue = numList.getFirst();
        }
        return Stream.concat(resultList.stream(), numList.stream())
                     .toList();
    }

}
