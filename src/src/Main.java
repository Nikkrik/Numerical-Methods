import Tasks.Accepted.FirstNewtonInterpolation;
import Tasks.Accepted.LinearInterpolation;
import Tasks.Accepted.MachineEpsilon;
import Tasks.Accepted.NumericalDifferentiation;
import Tasks.NotAccepted.Integration;
import Tasks.NotAccepted.ModifiedNewtonMethod;
import Tasks.NotAccepted.SeidelMethod;


public class Main {
    public static void main(String[] args) {

        System.out.println("=== 1. Машинный эпсилон ===");
        MachineEpsilon.run();

        System.out.println("\n=== 2. Линейная интерполяция ===");
        LinearInterpolation.run();

        System.out.println("\n=== 3. Численное дифференцирование ===");
        NumericalDifferentiation.run();

        System.out.println("\n=== 4. Интерполяция полиномом Ньютона ===");
        FirstNewtonInterpolation.run();

        System.out.println("\n=== 5. Метод трапеций ===");
        Integration.run();

        System.out.println("\n=== 6. Модифицированный метод Ньютона ===");
        ModifiedNewtonMethod.run();

        System.out.println("\n=== 7. Метод Зейделя ===");
        SeidelMethod.run();
    }
}

