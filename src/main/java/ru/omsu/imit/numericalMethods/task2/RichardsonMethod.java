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
            tau[j] = 2 / (alpha + beta + (beta - alpha) * Math.cos(Math.PI * (2 * j + 1) / 2 * k));
        }
        return tau;
    }

    public double getInfNorm(double[] x1, double[] x2) {
        double max = 0;
        for (int i = 0; i < x1.length; i++) {
            if (Math.abs(x1[i] - x2[i]) > max) {
                max = Math.abs(x1[i] - x2[i]);
            }
        }
        return max;
    }

    public void multiplyByMatrix(double[][] a, double[] x) {
        int size = x.length;
        double[] b = new double[size];
        System.arraycopy(x, 0, b, 0, size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                x[i] += a[i][j] * b[j];
            }
        }
    }

    public void multiplyByNumber(double[] x, double number) {
        for (int i = 0; i < x.length; i++) {
            x[i] *= number;
        }
    }

    public void subtract(double[] x1, double[] x2) {
        for (int i = 0; i < x1.length; i++) {
            x2[i] = x1[i] - x2[i];
        }
    }

    public void sum(double[] x1, double[] x2) {
        for (int i = 0; i < x1.length; i++) {
            x2[i] += x1[i];
        }
    }

    public double[] solve(double alpha, double beta, int k, double[][] a, double[] newX, double[] f, double epsilon) {
        double[] tau = RichardsonMethod.getTau(alpha, beta, k);
        double[] oldX = new double[newX.length];
        double[] previousX = new double[newX.length];

        do {
            System.arraycopy(newX, 0, previousX, 0, newX.length);
            for (double v : tau) {
                System.arraycopy(newX, 0, oldX, 0, newX.length);
                multiplyByMatrix(a, newX); // A * x(k - 1)
                subtract(f, newX); // f - A * x(k - 1)
                multiplyByNumber(newX, v); // tau * (f - A * x(k - 1))
                sum(oldX, newX); // tau * (f - A * x(k - 1)) + x(k-1)
            }
        } while (getInfNorm(previousX, newX) >= epsilon);
        return newX;
    }
}
