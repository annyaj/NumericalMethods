package ru.omsu.imit.numericalMethods.task1.mgen;

import org.junit.jupiter.api.Test;
import ru.omsu.imit.numericalMethods.task1.Solver;

import java.io.FileNotFoundException;
import java.util.Arrays;

class GenTest {

    @Test
    public void GenTest() throws FileNotFoundException {
        int p = 0;
        int n = 100;
        double alpha = 1.e-1;
        double beta = 1.;

        double t;
        double x_inf;
        double z_inf;

        double[][] a = new double[n][];
        double[][] a_copy = new double[n][];
        double[][] a_inv = new double[n][];

        double[] f = new double[n];
        double[] f_copy = new double[n];

        double[] expectedAnswers = new double[n];
        Solver solver = new Solver();

        do {
            p++;
            System.out.println("Iteration" + p);
            for (int i = 0; i < n; i++) {
                a[i] = new double[n];
                a_copy[i] = new double[n];
                a_inv[i] = new double[n];
                expectedAnswers[i] = Math.random() * 10.;
            }

            Gen g = new Gen();
            // g.mygen(a, a_inv, n, alpha, beta, 1, 2, 0, 1); // симметричная
            g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1); //проостой структуры
            //  g.mygen(a, a_inv, n, alpha, beta, 0, 0, 2, 1); //жорданова клетка

            // g.print_matr(a, n);
            // g.print_matr(a_inv, n);

            for (int i = 0; i < n; i++) {
                t = 0;
                for (int j = 0; j < n; j++) {
                    t += a[i][j] * expectedAnswers[j];
                }
                f[i] = t;
                f_copy[i] = t;

                System.arraycopy(a[i], 0, a_copy[i], 0, n);
            }

            double[] answers = solver.getSolve(a, f);
            z_inf = g.analyse(a_copy, f_copy, expectedAnswers, answers);
            x_inf = Arrays.stream(answers).map(Math::abs).max().orElse(0.0);
            alpha /= 10;
            System.out.println();
        } while (10 * z_inf <= x_inf);

        //double x_inf = Arrays.stream(answers).map(Math::abs).max().orElse(0.0);
    }
}