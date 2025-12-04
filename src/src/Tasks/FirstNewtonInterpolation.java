package Tasks;

public class FirstNewtonInterpolation {
    public static void run() {
        System.out.println("=== Интерполяция полиномом Ньютона ===");

        double[] x = {10, 15, 20, 25};
        double[] y = {3, 7, 11, 17};
        int n = 4;

        double[][] diff = new double[4][4];
        for (int i = 0; i < n; i++) diff[i][0] = y[i];
        for (int j = 1; j < n; j++)
            for (int i = 0; i < n - j; i++)
                diff[i][j] = diff[i+1][j-1] - diff[i][j-1];

        double h = x[1] - x[0];
        double t = (12.5 - x[0]) / h;
        double result = diff[0][0] + t*diff[0][1] + t*(t-1)/2*diff[0][2] + t*(t-1)*(t-2)/6*diff[0][3];

        System.out.println("P(12.5) = " + result);

        double[] x3 = {0, 1, 2, 3};
        double[] y3 = {0, 1, 8, 27};

        double[][] diff3 = new double[4][4];
        for (int i = 0; i < n; i++) diff3[i][0] = y3[i];
        for (int j = 1; j < n; j++)
            for (int i = 0; i < n - j; i++)
                diff3[i][j] = diff3[i+1][j-1] - diff3[i][j-1];

        t = (1.5 - x3[0]) / 1;
        result = diff3[0][0] + t*diff3[0][1] + t*(t-1)/2*diff3[0][2] + t*(t-1)*(t-2)/6*diff3[0][3];

        System.out.println("P(1.5) для x^3 = " + result);
        System.out.println("Точное значение: " + (1.5*1.5*1.5));
        System.out.println();
    }

    // Свой main для отдельного запуска
    public static void main(String[] args) {
        run();
    }
}