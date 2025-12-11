package Tasks;

public class Integration {

    static double f1(double x) { return 2*x + 1; }
    static double f2(double x) { return Math.sin(x); }

    interface Function {
        double apply(double x);
    }

    static double trapezoidal_rule(Function f, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (f.apply(a) + f.apply(b));

        for (int i = 1; i < n; ++i) {
            double x = a + i * h;
            sum += f.apply(x);
        }

        return sum * h;
    }

    public static void run() {
        System.out.println("=== Метод трапеций ===");

        final double a1 = 0, b1 = 2;
        final double a2 = 0, b2 = Math.PI;
        final double exact1 = 6.0;
        final double exact2 = 2.0;
        int n = 8;

        System.out.printf("∫(2x+1)dx от 0 до 2:%n");

        double I_n1 = trapezoidal_rule(Integration::f1, a1, b1, n);
        double I_2n1 = trapezoidal_rule(Integration::f1, a1, b1, 2*n);
        double error_runge1 = (I_2n1 - I_n1) / 3;
        double actual_error1 = I_2n1 - exact1;

        System.out.printf("При n=%d: %.8f%n", n, I_n1);
        System.out.printf("При 2n=%d: %.8f%n", 2*n, I_2n1);
        System.out.printf("Точное значение: %.8f%n", exact1);
        System.out.printf("Оценка погрешности (Рунге): %.8f%n", error_runge1);
        System.out.printf("Реальная погрешность: %.8f%n%n", actual_error1);

        System.out.printf("∫sin(x)dx от 0 до π:%n");

        double I_n2 = trapezoidal_rule(Integration::f2, a2, b2, n);
        double I_2n2 = trapezoidal_rule(Integration::f2, a2, b2, 2*n);
        double error_runge2 = (I_2n2 - I_n2) / 3;
        double actual_error2 = I_2n2 - exact2;

        System.out.printf("При n=%d: %.8f%n", n, I_n2);
        System.out.printf("При 2n=%d: %.8f%n", 2*n, I_2n2);
        System.out.printf("Точное значение: %.8f%n", exact2);
        System.out.printf("Оценка погрешности (Рунге): %.8f%n", error_runge2);
        System.out.printf("Реальная погрешность: %.8f%n", actual_error2);
        System.out.println();
    }

    public static void main(String[] args) {
        run();
    }
}