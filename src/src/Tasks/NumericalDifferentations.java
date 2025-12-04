package Tasks;

public class NumericalDifferentations {
    public static void run() {
        System.out.println("=== Численные производные ===");

        double x = 1.0;
        double h = 0.0001;

        System.out.println("(Правая разностная производная) Производная Sin(x) в точке " + x +
                " = " + (Math.sin(x + h) - Math.sin(x)) / h);
        System.out.println("(Левая разностная производная) Производная Sin(x) в точке " + x +
                " = " + (Math.sin(x) - Math.sin(x - h)) / h);
        System.out.println("(Центральная разностная производная) Производная Sin(x) в точке " + x +
                " = " + (Math.sin(x + h) - Math.sin(x - h)) / (2*h));
        System.out.println("(Вторая разностная производная) Производная Sin(x) в точке " + x +
                " = " + (Math.sin(x + h) - (2*Math.sin(x)) + Math.sin(x - h)) / (h*h));
        System.out.println();
    }

    // Свой main для отдельного запуска
    public static void main(String[] args) {
        run();
    }
}