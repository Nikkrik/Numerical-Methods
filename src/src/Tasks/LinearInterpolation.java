package Tasks;

public class LinearInterpolation {
    public static void run() {
        System.out.println("=== Линейная интерполяция ===");

        double[] x = {0, 1, 2, 3, 4};
        double[] y = {0, 1, 4, 9, 16};
        int n = 5;

        double targetX = 2.5;
        double result = 0;

        for (int i = 1; i < n; i++) {
            if (targetX <= x[i]) {
                result = y[i-1] + (y[i] - y[i-1]) * (targetX - x[i-1]) / (x[i] - x[i-1]);
                break;
            }
        }

        System.out.println("f(" + targetX + ") = " + result);
        System.out.println();
    }

    // Свой main для отдельного запуска
    public static void main(String[] args) {
        run();
    }
}