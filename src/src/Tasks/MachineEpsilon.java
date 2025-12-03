package Tasks;

public class MachineEpsilon {
    void machineEp(float EPS) {
        float prev_epsilon = 0;
        while ((1 + EPS) != 1) {
            prev_epsilon = EPS;
            EPS /= 2;
            System.out.println("Machine Epsilon: " + prev_epsilon);
        }
    }

    public void run() {
        machineEp(1);
    }
}
