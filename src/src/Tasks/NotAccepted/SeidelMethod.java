package Tasks.NotAccepted;

import java.util.Arrays;

public class SeidelMethod {

    // Проверка условия диагонального преобладания
    public static boolean hasDiagonalDominance(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            double diagonal = Math.abs(matrix[i][i]);
            double sum = 0;
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    sum += Math.abs(matrix[i][j]);
                }
            }
            if (diagonal <= sum) {
                return false;
            }
        }
        return true;
    }

    // Метод Зейделя для решения СЛАУ
    public static double[] solveSeidel(double[][] A, double[] b, double eps, int maxIterations) {
        int n = A.length;
        double[] x = new double[n]; // Текущее приближение
        double[] xPrev = new double[n]; // Предыдущее приближение
        Arrays.fill(x, 0); // Начальное приближение - нулевой вектор

        int iteration = 0;
        double error = eps + 1;

        System.out.println("Начальное приближение: " + Arrays.toString(x));

        while (iteration < maxIterations && error > eps) {
            // Копируем текущее приближение в предыдущее
            System.arraycopy(x, 0, xPrev, 0, n);

            // Вычисляем новое приближение
            for (int i = 0; i < n; i++) {
                double sum = b[i];

                // Вычитаем сумму произведений уже вычисленных новых значений
                for (int j = 0; j < i; j++) {
                    sum -= A[i][j] * x[j];
                }

                // Вычитаем сумму произведений старых значений
                for (int j = i + 1; j < n; j++) {
                    sum -= A[i][j] * xPrev[j];
                }

                x[i] = sum / A[i][i];
            }

            // Вычисляем погрешность
            error = 0;
            for (int i = 0; i < n; i++) {
                error = Math.max(error, Math.abs(x[i] - xPrev[i]));
            }

            iteration++;
            System.out.printf("Итерация %d: x = %s, погрешность = %.6f%n",
                    iteration, Arrays.toString(x), error);
        }

        System.out.printf("Выполнено %d итераций%n", iteration);
        return x;
    }

    // Проверка решения (подстановка в исходную систему)
    public static void checkSolution(double[][] A, double[] b, double[] x) {
        int n = A.length;
        double[] residual = new double[n];
        double maxResidual = 0;

        System.out.println("\nПроверка решения:");
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            residual[i] = Math.abs(sum - b[i]);
            maxResidual = Math.max(maxResidual, residual[i]);
            System.out.printf("Уравнение %d: |(Ax)[%d] - b[%d]| = %.6e%n",
                    i + 1, i, i, residual[i]);
        }
        System.out.printf("Максимальная невязка: %.6e%n", maxResidual);
    }

    public static void run() {
        System.out.println("=== Метод Зейделя ===");
        // Тестовая система 4x4 с диагональным преобладанием
        double[][] A = {
                {10, -1, 2, 0},
                {-1, 11, -1, 3},
                {2, -1, 10, -1},
                {0, 3, -1, 8}
        };

        double[] b = {6, 25, -11, 15};

        System.out.println("Матрица коэффициентов A:");
        for (int i = 0; i < A.length; i++) {
            System.out.println(Arrays.toString(A[i]));
        }

        System.out.println("\nВектор правых частей b: " + Arrays.toString(b));

        // Проверка диагонального преобладания
        System.out.println("\nПроверка диагонального преобладания:");
        if (hasDiagonalDominance(A)) {
            System.out.println("Условие диагонального преобладания выполняется");
        } else {
            System.out.println("Внимание: условие диагонального преобладания не выполняется!");
            System.out.println("Сходимость метода не гарантирована");
        }

        // Параметры метода
        double eps = 1e-6; // Точность
        int maxIterations = 100; // Максимальное число итераций

        System.out.printf("\nПараметры метода: точность = %.1e, максимальное число итераций = %d%n",
                eps, maxIterations);

        // Решение методом Зейделя
        double[] solution = solveSeidel(A, b, eps, maxIterations);

        System.out.println("\nПриближенное решение:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("x[%d] = %.8f%n", i + 1, solution[i]);
        }

        // Проверка решения
        checkSolution(A, b, solution);

        // Пример системы без диагонального преобладания
        System.out.println("\n\n--- Пример системы БЕЗ диагонального преобладания ---");
        double[][] A2 = {
                {2, 10, -1},
                {10, 1, 2},
                {1, 5, 10}
        };

        double[] b2 = {11, 10, 12};

        System.out.println("Матрица A2:");
        for (int i = 0; i < A2.length; i++) {
            System.out.println(Arrays.toString(A2[i]));
        }

        System.out.println("Проверка диагонального преобладания: " +
                (hasDiagonalDominance(A2) ? "выполняется" : "не выполняется"));
    }

    public static void main(String[] args) {
        run();
    }
}