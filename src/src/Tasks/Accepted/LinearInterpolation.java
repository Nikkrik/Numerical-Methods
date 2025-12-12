package Tasks.Accepted;

public class LinearInterpolation {

    // Статический метод с максимальной защитой
    public static double safeLinearInterpolation(double[] x, double[] y, double targetX) {
        // 1. Проверка входных данных
        if (x == null || y == null) {
            throw new NullPointerException("Массивы не могут быть null");
        }
        if (x.length != y.length) {
            throw new IllegalArgumentException("Длины массивов x и y должны совпадать");
        }
        if (x.length < 2) {
            throw new IllegalArgumentException("Нужно минимум 2 точки для интерполяции");
        }

        // 2. Проверка сортировки массива x
        for (int i = 1; i < x.length; i++) {
            if (x[i] <= x[i-1]) {
                throw new IllegalArgumentException("Массив x должен быть строго возрастающим");
            }
        }

        // 3. Проверка на NaN и бесконечность
        if (Double.isNaN(targetX) || Double.isInfinite(targetX)) {
            throw new IllegalArgumentException("Некорректное значение targetX: " + targetX);
        }

        // 4. Проверка границ
        if (targetX < x[0]) {
            throw new IndexOutOfBoundsException(
                    "Точка " + targetX + " находится слева от интервала [" +
                            x[0] + ", " + x[x.length-1] + "]");
        }
        if (targetX > x[x.length-1]) {
            throw new IndexOutOfBoundsException(
                    "Точка " + targetX + " находится справа от интервала [" +
                            x[0] + ", " + x[x.length-1] + "]");
        }

        // 5. Поиск интервала (бинарный поиск для эффективности)
        int left = 0;
        int right = x.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (Math.abs(targetX - x[mid]) < 1e-15) {
                // Точно попали в узел
                return y[mid];
            } else if (targetX < x[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // left теперь указывает на правый индекс интервала
        int i = left;

        // 6. Дополнительная проверка (на всякий случай)
        if (i <= 0 || i >= x.length) {
            throw new IllegalStateException("Ошибка при поиске интервала");
        }

        // 7. Вычисление интерполяции
        return y[i-1] + (y[i] - y[i-1]) * (targetX - x[i-1]) / (x[i] - x[i-1]);
    }

    public static void run() {
        System.out.println("=== Линейная интерполяция ===");

        double[] x = {0, 1, 2, 3, 4};
        double[] y = {0, 1, 4, 9, 16};
        double targetX = 2.5;

        try {
            double result = safeLinearInterpolation(x, y, targetX);
            System.out.println("f(" + targetX + ") = " + result);

            // Тест с граничными значениями
            System.out.println("\nТест граничных значений:");
            System.out.println("f(" + x[0] + ") = " + safeLinearInterpolation(x, y, x[0]));
            System.out.println("f(" + x[x.length-1] + ") = " +
                    safeLinearInterpolation(x, y, x[x.length-1]));

            // Тест с ошибкой (можно раскомментировать для проверки)
            // System.out.println("f(5.0) = " + safeLinearInterpolation(x, y, 5.0));

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();
    }
    public static void main(String[] args) {
        run();
    }
}