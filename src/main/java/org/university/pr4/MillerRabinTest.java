package org.university.pr4;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabinTest {

    private static final Random rand = new Random();

    public MillerRabinTest() {
        System.out.println("\n=== Тест Миллера — Рабина ===");
    }

    public void isComposite(int m) {
        int r = getR(m);
        int t = m - 1;
        int s = 0;
        while (t % 2 == 0) {
            t /= 2;
            s++;
        }

        boolean flag;
        for (int i = 0; i < r; i++) {
            flag = false;

            int a = rand.nextInt(2, m - 1);
            int x = (new BigInteger(String.valueOf(a))).pow(t)
                                                       .mod(BigInteger.valueOf(m))
                                                       .intValue();

            if (x == 1 || x == m - 1) continue;

            for (int j = s; j > 0; j--) {
                x = (int) Math.pow(x, 2) % m;
                if (x == 1) {
                    System.out.println("Число " + m + " составное");
                    return;
                } else if (x == m - 1) {
                    flag = true;
                    break;
                }
            }
            if (flag) continue;
            System.out.println("Число " + m + " составное");
            return;
        }
        System.out.println("Число " + m + " вероятно простое");
    }

    private Integer getR(int m) {
        return (int) Math.sqrt(m) + 1;
    }


}
