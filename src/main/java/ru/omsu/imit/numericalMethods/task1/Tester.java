package ru.omsu.imit.numericalMethods.task1;

import ru.omsu.imit.numericalMethods.task1.mgen.Gen;

import java.util.Arrays;

public class Tester {
    public static void analize(double[][] mtrx, double[] f, double[] expectedAnswers, double[] answers) {
        double z_infinity = get_z_infinity(answers, expectedAnswers);
        double x_infinity = Arrays.stream(expectedAnswers).map(Math::abs).max().orElse(0.0);
        double f_infinity = Arrays.stream(f).map(Math::abs).max().orElse(0.0);
        double dzeta = z_infinity / x_infinity;
        double r_infinity = get_r_infinity(mtrx, f, answers);
        double ro = r_infinity / f_infinity;

        System.out.println("||z|| = " + z_infinity);
        System.out.println("dzeta = " + dzeta);
        System.out.println("||r|| = " + r_infinity);
        System.out.println("ro = " + ro);
    }

    private static double get_z_infinity(double[] answers, double[] expectedAnsvers) {
        int n = answers.length;
        double[] z = new double[n];

        for (int i = 0; i < n; i++) {
            z[i] = answers[i] - expectedAnsvers[i];
        }
        return Arrays.stream(z).map(Math::abs).max().orElse(0.0);
    }

    private static double get_r_infinity(double[][] mtrx, double[] f, double[] answers) {
        int n = answers.length;
        double[] r = new double[n];
        double t;
        for (int i = 0; i < n; i++) {
            t = 0;
            for (int j = 0; j < n; j++) {
                t += mtrx[i][j] * answers[j];
            }
            r[i] = t - f[i];
        }

        return Arrays.stream(r).map(Math::abs).max().orElse(0.0);
    }

    public static void main(String[] src) {
        int n = 20;
        double alpha = 1.;
        double beta = 1.e+1;

        double[][] a = new double[n][];
        for (int i = 0; i < n; i++) a[i] = new double[n];

        double[][] a_inv = new double[n][];
        for (int i = 0; i < n; i++) a_inv[i] = new double[n];

        Gen g = new Gen();
        //g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1); //проостой структуры
        g.mygen(a, a_inv, n, alpha, beta, 0, 0, 2, 1); //жорданова клетка


        double[] f = new double[n];
        double[] f_copy = new double[n];
        double[] expectedAnswers = new double[n];

        for (int i = 0; i < n; i++) {
            expectedAnswers[i] = Math.random() * 10.;
        }

        for (int i = 0; i < n; i++) {
            double t = 0;
            for (int j = 0; j < n; j++) {
                t += a[i][j] * expectedAnswers[j];
            }
            f[i] = t;
            f_copy[i] = t;
        }

        Solver solver = new Solver();
        double[] answers = solver.getSolve(a, f);

        //g.mygen(a, a_inv, n, alpha, beta, 1, 2, 1, 1); //проостой структуры
           g.mygen(a, a_inv, n, alpha, beta, 0, 0, 2, 1); //жорданова клетка
        analize(a, f_copy, expectedAnswers, answers);
    }
}
