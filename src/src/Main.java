import Tasks.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== 1. Машинный эпсилон ===");
        new MachineEpsilon().run();

        System.out.println("\n=== 2. Линейная интерполяция ===");
        new LinearInterpolation().run();

        System.out.println("\n=== 3. Численные производные ===");
        new NumericalDifferentations().run();

        System.out.println("\n=== 4. Интерполяция полиномом Ньютона ===");
        new FirstNewtonInterpolation().run();

        System.out.println("\n=== 5. Метод трапеций ===");
        new Integration().run();
    }
}

