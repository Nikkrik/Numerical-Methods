package Tasks.Accepted;

import java.util.function.DoubleUnaryOperator;
import java.util.ArrayList;
import java.util.List;

public class NumericalDifferentiation {

    // Исходная функция y = sin(x)
    private static final DoubleUnaryOperator function = Math::sin;

    public static void run() {
        System.out.println("ЧИСЛЕННОЕ ДИФФЕРЕНЦИРОВАНИЕ ФУНКЦИИ y = sin(x)");
        System.out.println("===============================================\n");

        // Создаем точки для вычисления
        double[] x = {0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 1.2};
        double[] y = new double[x.length];

        // Вычисляем значения функции
        System.out.println("1. ВЫЧИСЛЕНИЕ ЗНАЧЕНИЙ ФУНКЦИИ:");
        System.out.println("-------------------------------");
        for (int i = 0; i < x.length; i++) {
            y[i] = function.applyAsDouble(x[i]);
            System.out.printf("x%d = %.1f, y%d = sin(%.1f) = %.6f\n",
                    i, x[i], i, x[i], y[i]);
        }

        // Шаг (предполагаем равномерную сетку)
        double h = x[1] - x[0];
        System.out.printf("\nШаг h = x[i+1] - x[i] = %.1f - %.1f = %.1f\n\n",
                x[1], x[0], h);

        System.out.println("2. ВЫЧИСЛЕНИЕ ПЕРВОЙ ПРОИЗВОДНОЙ:");
        System.out.println("=================================\n");

        // Вычисляем производные для каждой точки
        for (int i = 0; i < x.length; i++) {
            System.out.printf("ТОЧКА %d: x = %.1f, y = sin(%.1f) = %.6f\n",
                    i, x[i], x[i], y[i]);
            System.out.println("Точное значение производной: cos(" + x[i] + ") = " + Math.cos(x[i]));
            System.out.println("-----------------------------------------------------------");

            List<String> availableMethods = new ArrayList<>();

            // 1. Правая разностная производная
            if (i < x.length - 1) {
                System.out.println("а) Правая разностная производная:");
                System.out.println("   f'(x" + i + ") ≈ (y" + (i+1) + " - y" + i + ") / h");
                System.out.printf("   f'(%.1f) ≈ (%.6f - %.6f) / %.1f\n",
                        x[i], y[i+1], y[i], h);
                double result = (y[i+1] - y[i]) / h;
                System.out.printf("   f'(%.1f) ≈ %.6f / %.1f = %.6f\n",
                        x[i], y[i+1] - y[i], h, result);
                System.out.printf("   Погрешность: |%.6f - %.6f| = %.6f\n\n",
                        Math.cos(x[i]), result, Math.abs(Math.cos(x[i]) - result));
                availableMethods.add("правая разность");
            }

            // 2. Левая разностная производная
            if (i > 0) {
                System.out.println("б) Левая разностная производная:");
                System.out.println("   f'(x" + i + ") ≈ (y" + i + " - y" + (i-1) + ") / h");
                System.out.printf("   f'(%.1f) ≈ (%.6f - %.6f) / %.1f\n",
                        x[i], y[i], y[i-1], h);
                double result = (y[i] - y[i-1]) / h;
                System.out.printf("   f'(%.1f) ≈ %.6f / %.1f = %.6f\n",
                        x[i], y[i] - y[i-1], h, result);
                System.out.printf("   Погрешность: |%.6f - %.6f| = %.6f\n\n",
                        Math.cos(x[i]), result, Math.abs(Math.cos(x[i]) - result));
                availableMethods.add("левая разность");
            }

            // 3. Центральная разностная производная
            if (i > 0 && i < x.length - 1) {
                System.out.println("в) Центральная разностная производная:");
                System.out.println("   f'(x" + i + ") ≈ (y" + (i+1) + " - y" + (i-1) + ") / (2h)");
                System.out.printf("   f'(%.1f) ≈ (%.6f - %.6f) / (2*%.1f)\n",
                        x[i], y[i+1], y[i-1], h);
                System.out.printf("   f'(%.1f) ≈ %.6f / %.2f\n",
                        x[i], y[i+1] - y[i-1], 2*h);
                double result = (y[i+1] - y[i-1]) / (2 * h);
                System.out.printf("   f'(%.1f) ≈ %.6f\n", x[i], result);
                System.out.printf("   Погрешность: |%.6f - %.6f| = %.6f\n\n",
                        Math.cos(x[i]), result, Math.abs(Math.cos(x[i]) - result));
                availableMethods.add("центральная разность");
            }

            // 4. Формула с тремя точками
            if (i < x.length - 2) {
                System.out.println("г) Формула с тремя точками:");
                System.out.println("   f'(x" + i + ") ≈ (-y" + (i+2) + " + 4y" + (i+1) + " - 3y" + i + ") / (2h)");
                System.out.printf("   f'(%.1f) ≈ (-%.6f + 4*%.6f - 3*%.6f) / (2*%.1f)\n",
                        x[i], y[i+2], y[i+1], y[i], h);
                double numerator = -y[i+2] + 4*y[i+1] - 3*y[i];
                System.out.printf("   Числитель: -%.6f + %.6f - %.6f = %.6f\n",
                        y[i+2], 4*y[i+1], 3*y[i], numerator);
                System.out.printf("   Знаменатель: 2*%.1f = %.1f\n", h, 2*h);
                double result = numerator / (2 * h);
                System.out.printf("   f'(%.1f) ≈ %.6f / %.1f = %.6f\n",
                        x[i], numerator, 2*h, result);
                System.out.printf("   Погрешность: |%.6f - %.6f| = %.6f\n\n",
                        Math.cos(x[i]), result, Math.abs(Math.cos(x[i]) - result));
                availableMethods.add("три точки");
            }

            System.out.printf("Для точки x = %.1f доступны методы: %s\n\n",
                    x[i], String.join(", ", availableMethods));
            System.out.println("=".repeat(60) + "\n");
        }

        System.out.println("3. ВЫЧИСЛЕНИЕ ВТОРОЙ ПРОИЗВОДНОЙ:");
        System.out.println("=================================\n");

        for (int i = 1; i < x.length - 1; i++) {
            System.out.printf("ТОЧКА %d: x = %.1f, y = sin(%.1f) = %.6f\n",
                    i, x[i], x[i], y[i]);
            System.out.println("Точное значение второй производной: -sin(" + x[i] + ") = " + (-Math.sin(x[i])));
            System.out.println("-----------------------------------------------------------");

            System.out.println("Формула второй разностной производной:");
            System.out.println("   f''(x" + i + ") ≈ (y" + (i+1) + " - 2y" + i + " + y" + (i-1) + ") / h²");
            System.out.printf("   f''(%.1f) ≈ (%.6f - 2*%.6f + %.6f) / (%.1f)²\n",
                    x[i], y[i+1], y[i], y[i-1], h);
            System.out.printf("   f''(%.1f) ≈ (%.6f - %.6f + %.6f) / %.2f\n",
                    x[i], y[i+1], 2*y[i], y[i-1], h*h);

            double numerator = y[i+1] - 2*y[i] + y[i-1];
            System.out.printf("   Числитель: %.6f - %.6f + %.6f = %.6f\n",
                    y[i+1], 2*y[i], y[i-1], numerator);
            System.out.printf("   Знаменатель: h² = %.2f\n", h*h);

            double result = numerator / (h * h);
            System.out.printf("   f''(%.1f) ≈ %.6f / %.2f = %.6f\n",
                    x[i], numerator, h*h, result);
            System.out.printf("   Погрешность: |%.6f - %.6f| = %.6f\n\n",
                    -Math.sin(x[i]), result, Math.abs(-Math.sin(x[i]) - result));
            System.out.println("=".repeat(60) + "\n");
        }

        System.out.println("4. СРАВНЕНИЕ ТОЧНОСТИ МЕТОДОВ:");
        System.out.println("=============================\n");

        // Выберем точку в середине для сравнения
        int testPoint = 3; // x = 0.6
        System.out.printf("Сравнение точности в точке x = %.1f (y = %.6f):\n",
                x[testPoint], y[testPoint]);
        System.out.printf("Точное значение f'(%.1f) = cos(%.1f) = %.6f\n\n",
                x[testPoint], x[testPoint], Math.cos(x[testPoint]));

        // Вычисляем всеми доступными методами
        if (testPoint < x.length - 1) {
            double rightDiff = (y[testPoint+1] - y[testPoint]) / h;
            System.out.printf("1. Правая разность:  %.6f (погрешность: %.6f)\n",
                    rightDiff, Math.abs(Math.cos(x[testPoint]) - rightDiff));
        }

        if (testPoint > 0) {
            double leftDiff = (y[testPoint] - y[testPoint-1]) / h;
            System.out.printf("2. Левая разность:   %.6f (погрешность: %.6f)\n",
                    leftDiff, Math.abs(Math.cos(x[testPoint]) - leftDiff));
        }

        if (testPoint > 0 && testPoint < x.length - 1) {
            double centralDiff = (y[testPoint+1] - y[testPoint-1]) / (2 * h);
            System.out.printf("3. Центральная:      %.6f (погрешность: %.6f)\n",
                    centralDiff, Math.abs(Math.cos(x[testPoint]) - centralDiff));
        }

        if (testPoint < x.length - 2) {
            double threePointDiff = (-y[testPoint+2] + 4*y[testPoint+1] - 3*y[testPoint]) / (2 * h);
            System.out.printf("4. Три точки:        %.6f (погрешность: %.6f)\n",
                    threePointDiff, Math.abs(Math.cos(x[testPoint]) - threePointDiff));
        }

        System.out.println("\nВЫВОД: Центральная разностная формула обычно дает наименьшую погрешность!");

        // ВЫЗЫВАЕМ ДОПОЛНИТЕЛЬНЫЙ МЕТОД!
        calculateAtPoint(0.5, 0.1);
    }

    // Дополнительный метод для демонстрации работы с произвольной точкой
    public static void calculateAtPoint(double xValue, double h) {
        System.out.println("\n\n5. РАСЧЕТ ДЛЯ ПРОИЗВОЛЬНОЙ ТОЧКИ:");
        System.out.println("================================\n");

        // Создаем три точки вокруг заданной
        double[] xPoints = {xValue - h, xValue, xValue + h};
        double[] yPoints = new double[3];

        System.out.printf("Для точки x = %.2f с шагом h = %.2f:\n", xValue, h);
        for (int i = 0; i < 3; i++) {
            yPoints[i] = Math.sin(xPoints[i]);
            System.out.printf("  x%d = %.4f, y%d = sin(%.4f) = %.6f\n",
                    i, xPoints[i], i, xPoints[i], yPoints[i]);
        }

        // Центральная разностная производная в средней точке
        System.out.printf("\nЦентральная разностная производная в x = %.2f:\n", xValue);
        System.out.printf("  Формула: f'(x) ≈ (f(x+h) - f(x-h)) / (2h)\n");
        System.out.printf("  f'(%.2f) ≈ (sin(%.2f) - sin(%.2f)) / (2*%.2f)\n",
                xValue, xValue + h, xValue - h, h);
        System.out.printf("  f'(%.2f) ≈ (%.6f - %.6f) / %.2f\n",
                xValue, yPoints[2], yPoints[0], 2*h);

        double difference = yPoints[2] - yPoints[0];
        System.out.printf("  Разность: %.6f - %.6f = %.6f\n",
                yPoints[2], yPoints[0], difference);

        double denominator = 2 * h;
        System.out.printf("  Знаменатель: 2 * %.2f = %.2f\n", h, denominator);

        double centralResult = difference / denominator;
        System.out.printf("  f'(%.2f) ≈ %.6f / %.2f = %.6f\n",
                xValue, difference, denominator, centralResult);

        double exactValue = Math.cos(xValue);
        System.out.printf("\nТочное значение: cos(%.2f) = %.6f\n", xValue, exactValue);
        System.out.printf("Погрешность: |%.6f - %.6f| = %.6f\n",
                exactValue, centralResult, Math.abs(exactValue - centralResult));

        // Также покажем правую и левую разности для сравнения
        System.out.println("\nДля сравнения:");
        System.out.printf("  Правая разность: (sin(%.2f) - sin(%.2f)) / %.2f = %.6f\n",
                xValue + h, xValue, h, (yPoints[2] - yPoints[1]) / h);
        System.out.printf("  Левая разность:  (sin(%.2f) - sin(%.2f)) / %.2f = %.6f\n",
                xValue, xValue - h, h, (yPoints[1] - yPoints[0]) / h);
    }
    public static void main(String[] args) {
        run();
    }
}