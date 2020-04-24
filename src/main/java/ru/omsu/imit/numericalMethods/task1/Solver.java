package ru.omsu.imit.numericalMethods.task1;

public class Solver {

    public static double[] getSolve(double[][] matrix, double[] f) {
        int size = f.length;

        int[] colTransp = new int[size];
        double[] lengthSquares = new double[size];
        for (int i = 0; i < size; i++) {
            colTransp[i] = i;
            lengthSquares[i] = getVectorLengthSquares(matrix, i);
        }

        for (int j = 0; j < size - 1; j++) {
            // make transposition
            int indMaxCol = getIndexMaxVector(lengthSquares, j);
            swapColumns(matrix, j, indMaxCol);
            swap(colTransp, j, indMaxCol);
            swap(lengthSquares, j, indMaxCol);

            int maxRow = getIndexMaxRow(matrix, j + 1);
            swapRows(matrix, j + 1, maxRow);
            swap(f, j + 1, maxRow);

            // rotate
            for (int i = j + 1; i < size; i++) {
                rotate(matrix, f, j, i);
            }

            // recalculate length squares
            for (int k = j + 1; k < size; k++) {
                lengthSquares[k] -= matrix[j][k] * matrix[j][k];
            }
        }

        //find y
        double[] x = new double[size];
        for (int i = size - 1; i > -1; i--) {
            x[i] = f[i];
            for (int j = i + 1; j < size; j++) {
                x[i] -= x[j] * matrix[i][j];
            }
            x[i] /= matrix[i][i];
        }

        // find x
        for (int i = 0; i < size; i++) {
            if (colTransp[i] != i) {
                swap(x, colTransp[i], i);
                swap(colTransp, colTransp[i], i);
                i--;
            }
        }

        return x;
    }

    private static double getVectorLengthSquares(double[][] matrix, int ind) {
        double lenSq = 0.0;
        for (double[] doubles : matrix) {
            lenSq += doubles[ind] * doubles[ind];
        }
        return lenSq;
    }

    private static int getIndexMaxVector(double[] lengthSquares, int step) {
        int index = step;
        for (int i = step + 1; i < lengthSquares.length; i++) {
            if (lengthSquares[i] > lengthSquares[index]) {
                index = i;
            }
        }
        return index;
    }

    private static int getIndexMaxRow(double[][] matrix, int step) {
        int index = step;
        for (int i = step; i < matrix.length - 1; i++) {
            if (matrix[i][step - 1] > matrix[index][step - 1]) {
                index = i;
            }
        }
        return index;
    }

    private static void swapColumns(double[][] matrix, int i, int j) {
        if (i != j) {
            double buf;
            for (int k = 0; k < matrix.length; k++) {
                buf = matrix[k][i];
                matrix[k][i] = matrix[k][j];
                matrix[k][j] = buf;
            }
        }
    }

    private static void swapRows(double[][] matrix, int i, int j) {
        if (i != j) {
            double buf;
            for (int k = 0; k < matrix.length; k++) {
                buf = matrix[i][k];
                matrix[i][k] = matrix[j][k];
                matrix[j][k] = buf;
            }
        }
    }

    private static void swap(double[] array, int i, int j) {
        double buf = array[i];
        array[i] = array[j];
        array[j] = buf;
    }

    private static void swap(int[] array, int i, int j) {
        int buf = array[i];
        array[i] = array[j];
        array[j] = buf;
    }

    public static void rotate(double[][] matrix, double[] f, int j, int i) {
        int size = matrix.length;
        double z = Math.max(Math.abs(matrix[i][j]), Math.abs(matrix[j][j]));
        double l = Math.min(Math.abs(matrix[i][j]), Math.abs(matrix[j][j]));

        if (Double.compare(z, 0.0) == 0) {
            return;
        }
        double aj = matrix[j][j] / z;
        double ai = matrix[i][j] / z;
        double al = l / z;

        double denominator = Math.sqrt(1.0 + al * al);
        double s = -ai / denominator;
        double c = aj / denominator;

        double[] copyI = new double[size];
        double[] copyJ = new double[size];
        System.arraycopy(matrix[i], 0, copyI, 0, size);
        System.arraycopy(matrix[j], 0, copyJ, 0, size);

        for (int k = 0; k < size; k++) {
            matrix[j][k] = c * copyJ[k] - s * copyI[k];
            matrix[i][k] = s * copyJ[k] + c * copyI[k];
        }

        double firstElem = c * f[j] - s * f[i];
        double secondElem = s * f[j] + c * f[i];
        f[j] = firstElem;
        f[i] = secondElem;
    }
}
