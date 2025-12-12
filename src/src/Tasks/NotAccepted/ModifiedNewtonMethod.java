package Tasks.NotAccepted;

import java.util.function.Function;

public class ModifiedNewtonMethod {

    // Исходная функция: f(x) = x^4 + x - 4
    private static final Function<Double, Double> f = x -> Math.pow(x, 4) + x - 4;

    // Производная: f'(x) = 4x^3 + 1
    private static final Function<Double, Double> fPrime = x -> 4 * Math.pow(x, 3) + 1;

    /**
     * Модифицированный метод Ньютона
     * ПРОИЗВОДНАЯ ВЫЧИСЛЯЕТСЯ ТОЛЬКО ОДИН РАЗ в начальной точке
     */
    public static double solveModifiedNewton(double x0, double eps, int maxIter) {
        System.out.println("\n=== ЗАПУСК МОДИФИЦИРОВАННОГО МЕТОДА НЬЮТОНА ===");
        System.out.println("Особенность: производная вычисляется ТОЛЬКО ОДИН РАЗ");
        System.out.println("в начальной точке x0 = " + x0);

        // ВОТ ГЛАВНАЯ МОДИФИКАЦИЯ:
        // Производную вычисляем ОДИН РАЗ и запоминаем
        double fixedDerivative = fPrime.apply(x0);
        System.out.println("f'(x0) = " + fixedDerivative + " (будет использоваться во всех итерациях)");

        double x = x0;

        System.out.println("\nИтерационный процесс:");
        System.out.println("№ |      x_n      |     f(x_n)    |    x_{n+1}   | разность");
        System.out.println("---|---------------|---------------|--------------|----------");

        for (int i = 0; i < maxIter; i++) {
            double fx = f.apply(x);

            // Используем ОДНУ И ТУ ЖЕ производную, вычисленную в начале
            double xNew = x - fx / fixedDerivative;

            System.out.printf("%2d| %13.8f | %13.8f | %12.8f | %.2e\n",
                    i + 1, x, fx, xNew, Math.abs(xNew - x));

            // Проверка условия остановки
            if (Math.abs(xNew - x) < eps) {
                System.out.printf("\nСошлось за %d итераций\n", i + 1);
                return xNew;
            }

            x = xNew;
        }

        System.out.println("\nДостигнут лимит итераций: " + maxIter);
        return x;
    }

    /**
     * Классический метод Ньютона для сравнения
     * Здесь производная вычисляется на КАЖДОЙ итерации
     */
    public static double solveClassicalNewton(double x0, double eps, int maxIter) {
        System.out.println("\n=== КЛАССИЧЕСКИЙ МЕТОД НЬЮТОНА (для сравнения) ===");
        System.out.println("Особенность: производная вычисляется на КАЖДОЙ итерации");

        double x = x0;

        System.out.println("\nИтерационный процесс:");
        System.out.println("№ |      x_n      |     f(x_n)    |    f'(x_n)   |    x_{n+1}   | разность");
        System.out.println("---|---------------|---------------|--------------|--------------|----------");

        for (int i = 0; i < maxIter; i++) {
            double fx = f.apply(x);
            // Здесь производная вычисляется КАЖДЫЙ РАЗ - это дороже!
            double derivative = fPrime.apply(x);
            double xNew = x - fx / derivative;

            System.out.printf("%2d| %13.8f | %13.8f | %12.8f | %12.8f | %.2e\n",
                    i + 1, x, fx, derivative, xNew, Math.abs(xNew - x));

            if (Math.abs(xNew - x) < eps) {
                System.out.printf("\nСошлось за %d итераций\n", i + 1);
                return xNew;
            }

            x = xNew;
        }

        System.out.println("\nДостигнут лимит итераций: " + maxIter);
        return x;
    }

    public static void run() {
        System.out.println("=== Модифицированный метод Ньютона ===");

        double a = 0.0;
        double b = 2.0;
        double eps = 1e-8;
        int maxIter = 100;

        System.out.println("==============================================");
        System.out.println("РЕШЕНИЕ УРАВНЕНИЯ: x^4 + x - 4 = 0");
        System.out.println("ОТРЕЗОК: [" + a + ", " + b + "]");
        System.out.println("==============================================");

        // Проверяем, что на отрезке есть корень
        if (f.apply(a) * f.apply(b) > 0) {
            System.out.println("Внимание: f(a)*f(b) > 0, возможно нет корня на отрезке!");
        }

        // Выбираем начальное приближение
        double x0 = 2.0;
        System.out.println("\nНачальное приближение: x0 = " + x0);

        // Запускаем МОДИФИЦИРОВАННЫЙ метод
        System.out.println("\n" + "=".repeat(50));
        long startTime = System.nanoTime();
        double rootModified = solveModifiedNewton(x0, eps, maxIter);
        long endTime = System.nanoTime();
        long modifiedTime = endTime - startTime;

        System.out.println("\nРезультат модифицированного метода:");
        System.out.printf("Корень: x = %.10f\n", rootModified);
        System.out.printf("f(x) = %.2e\n", f.apply(rootModified));
        System.out.printf("Время выполнения: %.3f мс\n", modifiedTime / 1_000_000.0);

        // Для сравнения запускаем КЛАССИЧЕСКИЙ метод
        System.out.println("\n" + "=".repeat(50));
        startTime = System.nanoTime();
        double rootClassical = solveClassicalNewton(x0, eps, maxIter);
        endTime = System.nanoTime();
        long classicalTime = endTime - startTime;

        System.out.println("\nРезультат классического метода:");
        System.out.printf("Корень: x = %.10f\n", rootClassical);
        System.out.printf("f(x) = %.2e\n", f.apply(rootClassical));
        System.out.printf("Время выполнения: %.3f мс\n", classicalTime / 1_000_000.0);

        // Сравниваем
        System.out.println("\n" + "=".repeat(50));
        System.out.println("СРАВНЕНИЕ МЕТОДОВ:");
        System.out.printf("Разница в результатах: %.2e\n", Math.abs(rootModified - rootClassical));
        System.out.printf("Классический метод быстрее в %.2f раз\n", (double)classicalTime/modifiedTime);
        System.out.println("\nМодифицированный метод требует меньше вычислений,");
        System.out.println("но может сходиться медленнее (линейная сходимость)");

        // Проверяем точность
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ПРОВЕРКА ТОЧНОСТИ:");
        double exactValue = 1.284724;
        System.out.printf("Известное приближение: %.6f\n", exactValue);
        System.out.printf("Наша погрешность: %.2e\n", Math.abs(rootModified - exactValue));

        if (Math.abs(f.apply(rootModified)) < eps) {
            System.out.println("✓ Точность достигнута!");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        run();
    }
}