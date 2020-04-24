package ru.omsu.imit.numericalMethods.task2;

import org.junit.jupiter.api.Test;
import ru.omsu.imit.numericalMethods.task1.mgen.Gen;

import java.util.Arrays;

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
        double alpha;
        double beta;
        int maxIteration = 10000;
        double eps = 1.e-10;


        double[][] a = new double[n][];
        double[][] a_inv = new double[n][];

        double[] f = new double[n];

        double[] expectedAnswers = new double[n];

        generateExpectedAnswers(expectedAnswers);
        for (int i = 0; i < n; i++) {
            a[i] = new double[n];
            a_inv[i] = new double[n];
        }

        Gen g = new Gen();
        double[] x0 = new double[n];

        for (alpha = 1.e-1, beta = 1.; alpha >= 1.e-10; alpha /= 10) {
            getResult_CycleIteration(a, a_inv, f, expectedAnswers, x0, maxIteration, eps, g, n,
                    alpha, beta, "rich_simple.csv", 1);
            getResult_CycleIteration(a, a_inv, f, expectedAnswers, x0, maxIteration, eps, g, n,
                    alpha, beta, "rich_symmetric.csv", 2);
        }

        for (alpha = 1., beta = 1.e+1; beta <= 1.e+10; beta *= 10) {
            getResult_CycleIteration(a, a_inv, f, expectedAnswers, x0, maxIteration, eps, g, n,
                    alpha, beta, "rich_simple.csv", 1);
            getResult_CycleIteration(a, a_inv, f, expectedAnswers, x0, maxIteration, eps, g, n,
                    alpha, beta, "rich_symmetric.csv", 2);
        }
    }

    private void getResult_CycleIteration(double[][] a, double[][] a_inv, double[] f, double[] expectedAnswers,
                                          double[] x0, int maxIteration, double eps,
                                          Gen g, int n, double alpha, double beta,
                                          String file, int matrType) {
        zeroMatrix(a);
        zeroMatrix(a_inv);
        if (matrType == 1) {
            g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1, file); //проостой структуры
        } else if (matrType == 2) {
            g.mygen(a, a_inv, n, alpha, beta, 1, 2, 0, 1, file); // симметричная
        } else {
            g.mygen(a, a_inv, n, alpha, beta, 0, 0, 2, 1, file); //жорданова клетка
        }

        calculateF(a, expectedAnswers, f, n);

        double[] answers = RichardsonMethod.solve(alpha, beta, 16, a, x0, f, eps, maxIteration);
        g.analyse(a, f, expectedAnswers, answers, file);
        System.out.println();
    }

    private void calculateF(double[][] a, double[] expectedAnswers, double[] f, int n) {
        double t;
        for (int i = 0; i < n; i++) {
            t = 0;
            for (int j = 0; j < n; j++) {
                t += a[i][j] * expectedAnswers[j];
            }
            f[i] = t;
        }
    }

    private void generateExpectedAnswers(double[] expectedAnswers) {
        for (int i = 0; i < expectedAnswers.length; i++) {
            expectedAnswers[i] = Math.sin(i) * Math.sqrt(i);
        }
    }

    private void zeroMatrix(double[][] a) {
        for (double[] doubles : a) {
            Arrays.fill(doubles, 0);
        }
    }
}