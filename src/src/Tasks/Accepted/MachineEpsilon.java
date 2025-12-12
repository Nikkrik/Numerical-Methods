package Tasks.Accepted;

public class MachineEpsilon {
    public static void run() {
        System.out.println("=== Машинный эпсилон ===");

        float EPS = 1;
        float prev_epsilon = 0;

        while ((1 + EPS) != 1) {
            prev_epsilon = EPS;
            EPS /= 2;
            System.out.println("Machine Epsilon: " + prev_epsilon);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        run();
    }
}