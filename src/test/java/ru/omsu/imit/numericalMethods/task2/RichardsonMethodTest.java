package ru.omsu.imit.numericalMethods.task2;

import org.junit.jupiter.api.Test;
import ru.omsu.imit.numericalMethods.task1.mgen.Gen;

import static org.junit.jupiter.api.Assertions.*;

class RichardsonMethodTest {

    @Test
    void orderingParameters1() {
        int[] exp = {1, 8, 4, 5, 2, 7, 3, 6};
        assertArrayEquals(exp, RichardsonMethod.orderingParameters(8));
    }

    @Test
    void orderingParameters2() {
        int[] exp = {1, 16, 8, 9, 4, 13, 5, 12, 2, 15, 7, 10, 3, 14, 6, 11};
        assertArrayEquals(exp, RichardsonMethod.orderingParameters(16));
    }

    @Test
    public void GenTest() {
        int n = 100;
        double alpha = 1.e-5;
        double beta = 1.;

        double[][] a = new double[n][];
        double[][] a_inv = new double[n][];

        double[] f = new double[n];

        double[] expectedAnswers = new double[n];

        for (int i = 0; i < n; i++) {
            a[i] = new double[n];
            a_inv[i] = new double[n];
            expectedAnswers[i] = Math.random() * 10.;
        }

        Gen g = new Gen();
        g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1); //проостой структуры
    //    g.mygen(a, a_inv, n, alpha, beta, 1, 2, 0, 1); // симметричная
      //  g.print_matr(a);

        double t;
        for (int i = 0; i < n; i++) {
            t = 0;
            for (int j = 0; j < n; j++) {
                t += a[i][j] * expectedAnswers[j];
            }
            f[i] = t;
        }

        double[] x0 = new double[n];
        double eps = 1.;
        double[] answers = RichardsonMethod.solve(alpha, beta, 2, a, x0, f, eps);
        g.analyse(a, f, expectedAnswers, answers);
    }
}