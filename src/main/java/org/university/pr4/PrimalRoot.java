package org.university.pr4;

import java.util.ArrayList;
import java.util.List;

public class PrimalRoot {

    public PrimalRoot() {
        System.out.println("\n=== Первообразный корень ===");
    }

    public int findPrimitiveRoot(int p) {
        List<Integer> primes = findPrimes(p);

        for (int q : primes) {
            if (isPrimitiveRoot(q, p)) {
                return q;
            }
        }

        return -1; // Если первообразный корень не найден
    }

    private List<Integer> findPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] isComposite = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (int j = i * i; j <= n; j += i) {
                    isComposite[j] = true;
                }
            }
        }
        return primes;
    }

    private boolean isPrimitiveRoot(int q, int p) {
        int phi = p - 1;
        List<Integer> primeDivisors = findPrimeDivisors(phi);
        for (int k : primeDivisors) {
            if (Math.pow(q, phi / k) % p == 1) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> findPrimeDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                while (n % i == 0) {
                    n /= i;
                }
            }
        }
        if (n > 1) {
            divisors.add(n);
        }
        return divisors;
    }
}
