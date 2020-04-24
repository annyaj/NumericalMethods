package ru.omsu.imit.numericalMethods.task2;

public class RichardsonMethod {
    public static int[] orderingParameters(int n) {
        if ((n & (n - 1)) != 0) {
            return null;
        }

        int[] newOrder = new int[n];
        int[] oldOrder = new int[n];
        oldOrder[0] = 1;
        for (int i = 1; i < n; i *= 2) {
            for (int j = 0; j < i; j++) {
                newOrder[j * 2] = oldOrder[j];
                newOrder[j * 2 + 1] = i * 2 + 1 - oldOrder[j];
            }
            System.arraycopy(newOrder, 0, oldOrder, 0, n);
        }
        return newOrder;
    }

    public static double[] getTau(double alpha, double beta, int k) {
        double[] tau = new double[k];
        for (int j = 0; j < k; j++) {
            tau[j] = 2.0 / (alpha + beta + (beta - alpha) * Math.cos(Math.PI * (2 * j + 1) / 2.0 / k));
        }
        return tau;
    }

    public static double getInfNorm(double[] x1, double[] x2) {
        double res = 0;
        for (int i = 0; i < x1.length; i++) {
            res += (x1[i] - x2[i]) * (x1[i] - x2[i]);
        }
        return Math.sqrt(res);
    }

    public static void multiplyByMatrix(double[][] a, double[] x) {
        int size = x.length;
        double[] b = new double[size];
        System.arraycopy(x, 0, b, 0, size);

        for (int i = 0; i < size; i++) {
            x[i] = 0;
            for (int j = 0; j < size; j++) {
                x[i] += a[i][j] * b[j];
            }
        }
    }

    public static void multiplyByNumber(double[] x, double number) {
        for (int i = 0; i < x.length; i++) {
            x[i] *= number;
        }
    }

    public static void subtract(double[] x1, double[] x2) {
        for (int i = 0; i < x1.length; i++) {
            x2[i] = x1[i] - x2[i];
        }
    }

    public static void sum(double[] x1, double[] x2) {
        for (int i = 0; i < x1.length; i++) {
            x2[i] += x1[i];
        }
    }

    public static double[] solve(double alpha, double beta, int k, double[][] a, double[] newX,
                                 double[] f, double epsilon, int maxIteration) {
        double[] tau = RichardsonMethod.getTau(alpha, beta, k);
        int[] order = orderingParameters(k);
        if (order == null) {
            return null;
        }
        double[] oldX = new double[newX.length];
        double[] previousX = new double[newX.length];

        int j = 0;
        do {
            System.arraycopy(newX, 0, previousX, 0, newX.length);
            for (int i = 0; i < k; i++) {
                System.arraycopy(newX, 0, oldX, 0, newX.length);
                multiplyByMatrix(a, newX); // A * x(k - 1)
                subtract(f, newX); // f - A * x(k - 1)
                multiplyByNumber(newX, tau[order[i] - 1]); // tau * (f - A * x(k - 1))
                sum(oldX, newX); // tau * (f - A * x(k - 1)) + x(k-1)
            }
            j++;
        } while (j < maxIteration && getInfNorm(previousX, newX) >= epsilon);
        return newX;
    }
}
