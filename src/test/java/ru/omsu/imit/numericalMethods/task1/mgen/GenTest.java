package ru.omsu.imit.numericalMethods.task1.mgen;

import org.junit.jupiter.api.Test;
import ru.omsu.imit.numericalMethods.task1.Solver;

import java.util.Arrays;

class GenTest {

    @Test
    public void GenTest() {
        int n = 100;
        double alpha;
        double beta;

        double[][] a = new double[n][];
        double[][] a_copy = new double[n][];
        double[][] a_inv = new double[n][];

        for (int i = 0; i < n; i++) {
            a[i] = new double[n];
            a_copy[i] = new double[n];
            a_inv[i] = new double[n];
        }

        double[] f = new double[n];
        double[] f_copy = new double[n];

        double[] expectedAnswers = new double[n];
        generateExpectedAnswers(expectedAnswers);

        Gen g = new Gen();

        for (alpha = 1.e-1, beta = 1.; alpha >= 1.e-10; alpha /= 10) {
            getResult_CycleIteration(a, a_inv, a_copy, f, f_copy, expectedAnswers, g, n,
                    alpha, beta, "simple_form.csv", 1);
            getResult_CycleIteration(a, a_inv, a_copy, f, f_copy, expectedAnswers, g, n,
                    alpha, beta, "symmetric.csv", 2);
            getResult_CycleIteration(a, a_inv, a_copy, f, f_copy, expectedAnswers, g, n,
                    alpha, beta, "zhord.csv", 3);
        }

        for (alpha = 1., beta = 1.e+1; beta <= 1.e+10; beta *= 10) {
            getResult_CycleIteration(a, a_inv, a_copy, f, f_copy, expectedAnswers, g, n,
                    alpha, beta, "simple_form.csv", 1);
            getResult_CycleIteration(a, a_inv, a_copy, f, f_copy, expectedAnswers, g, n,
                    alpha, beta, "symmetric.csv", 2);
            getResult_CycleIteration(a, a_inv, a_copy, f, f_copy, expectedAnswers, g, n,
                    alpha, beta, "zhord.csv", 3);
        }
    }

    private void getResult_CycleIteration(double[][] a, double[][] a_inv, double[][] a_copy,
                                          double[] f, double[] f_copy, double[] expectedAnswers,
                                          Gen g, int n, double alpha, double beta,
                                          String file, int matrType) {
        zeroMatrix(a);
        zeroMatrix(a_inv);
        zeroMatrix(a_copy);
        if (matrType == 1) {
            g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1, file); //проостой структуры
        } else if (matrType == 2) {
            g.mygen(a, a_inv, n, alpha, beta, 1, 2, 0, 1, file); // симметричная
        } else {
            g.mygen(a, a_inv, n, alpha, beta, 0, 0, 2, 1, file); //жорданова клетка
        }

        calculateF(a, expectedAnswers, f, n);
        copyMatrix(a, a_copy, f, f_copy);

        double[] answers = Solver.getSolve(a, f);
        g.analyse(a_copy, f_copy, expectedAnswers, answers, file);
        System.out.println();
    }

    private void copyMatrix(double[][] a, double[][] a_copy, double[] f, double[] f_copy) {
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, a_copy[i], 0, a[i].length);
        }
        System.arraycopy(f, 0, f_copy, 0, f.length);
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
            //  expectedAnswers[i] = Math.sin(i) * Math.sqrt(i);
            expectedAnswers[i] = Math.random() * 10.;
        }
    }

    private void zeroMatrix(double[][] a) {
        for (double[] doubles : a) {
            Arrays.fill(doubles, 0);
        }
    }
}