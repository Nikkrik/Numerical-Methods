import java.util.Arrays;
import java.util.Scanner;

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

        // Проверка на нулевые диагональные элементы
        for (int i = 0; i < n; i++) {
            if (Math.abs(A[i][i]) < 1e-15) { // Проверка на близкое к нулю значение
                throw new IllegalArgumentException(
                    String.format("Диагональный элемент A[%d][%d] = %.6f близок к нулю или равен нулю. " +
                                  "Метод Зейделя не может быть применен (деление на ноль).", 
                                  i, i, A[i][i]));
            }
        }

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

                // Деление на диагональный элемент с дополнительной проверкой
                double divisor = A[i][i];
                if (Math.abs(divisor) < 1e-15) {
                    throw new ArithmeticException(
                        String.format("Деление на ноль (или близкое к нулю значение) на итерации %d, " +
                                      "строка %d: A[%d][%d] = %.6f", 
                                      iteration, i, i, i, divisor));
                }
                
                x[i] = sum / divisor;
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

    // Метод для безопасного ввода числа
    public static double safeInputDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                // Убираем запятые для поддержки формата "1,5" как "1.5"
                input = input.replace(',', '.');
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число (например: 5 или 3.14)");
            }
        }
    }

    public static void run() {
        System.out.println("=== Метод Зейделя ===");
        Scanner scanner = new Scanner(System.in);
        
        // Запрос у пользователя выбора: тестовая система или ручной ввод
        System.out.print("Использовать тестовую систему (1) или ввести вручную (2)? ");
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1 || choice == 2) break;
                System.out.print("Введите 1 или 2: ");
            } catch (NumberFormatException e) {
                System.out.print("Введите 1 (тест) или 2 (ручной ввод): ");
            }
        }

        double[][] A;
        double[] b;

        if (choice == 1) {
            // Тестовая система 4x4 с диагональным преобладанием
            A = new double[][]{
                    {10, -1, 2, 0},
                    {-1, 11, -1, 3},
                    {2, -1, 10, -1},
                    {0, 3, -1, 8}
            };
            b = new double[]{6, 25, -11, 15};
        } else {
            // Ручной ввод системы
            System.out.print("Введите размерность системы (n): ");
            int n;
            while (true) {
                try {
                    n = Integer.parseInt(scanner.nextLine().trim());
                    if (n > 0) break;
                    System.out.print("Размерность должна быть положительным числом: ");
                } catch (NumberFormatException e) {
                    System.out.print("Введите целое положительное число: ");
                }
            }
            
            A = new double[n][n];
            b = new double[n];
            
            System.out.println("\nВведите элементы матрицы A:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = safeInputDouble(scanner, 
                        String.format("A[%d][%d] = ", i+1, j+1));
                }
            }
            
            System.out.println("\nВведите элементы вектора правых частей b:");
            for (int i = 0; i < n; i++) {
                b[i] = safeInputDouble(scanner, String.format("b[%d] = ", i+1));
            }
        }

        System.out.println("\nМатрица коэффициентов A:");
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
        double eps = 0.001; // Точность
        int maxIterations = 100; // Максимальное число итераций

        System.out.printf("\nПараметры метода: точность = %.1e, максимальное число итераций = %d%n",
                eps, maxIterations);

        try {
            // Решение методом Зейделя
            double[] solution = solveSeidel(A, b, eps, maxIterations);

            System.out.println("\nПриближенное решение:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x[%d] = %.8f%n", i + 1, solution[i]);
            }

            // Проверка решения
            checkSolution(A, b, solution);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("\nОшибка при выполнении метода Зейделя: " + e.getMessage());
            System.out.println("Попробуйте изменить исходные данные.");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        run();
    }
}