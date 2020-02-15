package ru.omsu.imit.numericalMethods.task1.mgen;

import org.junit.jupiter.api.Test;
import ru.omsu.imit.numericalMethods.task1.Solver;

class GenTest {

    private int N = 100;
    private double ALPHA = 1.;
    private double BETA = 1.e+3;

    @Test
    public void GenTest() {
        int n = N;
        double alpha = ALPHA;
        double beta = BETA;

        double[][] a = new double[n][];
        double[][] a_copy = new double[n][];
        double[][] a_inv = new double[n][];

        double[] expectedAnswers = new double[n];

        for (int i = 0; i < n; i++) {
            a[i] = new double[n];
            a_copy[i] = new double[n];
            a_inv[i] = new double[n];
            expectedAnswers[i] = Math.random() * 10.;
        }

        Gen g = new Gen();
        // g.mygen(a, a_inv, n, alpha, beta, 1, 2, 0, 1); // симметричная
        //	g.mygen ( a, a_inv, n, alpha, beta, 1, 2, 1, 1 ); //проостой структуры
        g.mygen(a, a_inv, n, alpha, beta, 0, 0, 2, 1); //жорданова клетка

        g.print_matr(a, n);
        g.print_matr(a_inv, n);


        double[] f = new double[n];
        double[] f_copy = new double[n];

        double t;
        for (int i = 0; i < n; i++) {
            t = 0;
            for (int j = 0; j < n; j++) {
                t += a[i][j] * expectedAnswers[j];
            }
            f[i] = t;
            f_copy[i] = t;

            System.arraycopy(a[i], 0, a_copy[i], 0, n);
        }

        Solver solver = new Solver();
        double[] answers = solver.getSolve(a, f);

        g.analyse(a_copy, f_copy, expectedAnswers, answers);
    }
}