import java.util.Arrays;


public class BankersAlgorithm {
    private final int processCount; // Number of processes
    private final int resourceCount; // Number of resources
    private final int[][] max;      // Maximum demand matrix
    private final int[][] alloc;    // Allocation matrix
    private final int[] avail;      // Available resources
    private final int[][] need;     // Need matrix
    private final int[] safeSequence; // To store safe sequence

    public BankersAlgorithm(int[][] max, int[][] alloc, int[] avail) {
        this.processCount = alloc.length;
        this.resourceCount = avail.length;
        this.max = Arrays.copyOf(max, max.length);
        this.alloc = Arrays.copyOf(alloc, alloc.length);
        this.avail = Arrays.copyOf(avail, avail.length);
        this.need = new int[processCount][resourceCount];
        this.safeSequence = new int[processCount];
        calculateNeedMatrix();
    }

    /**
     * Calculates the need matrix (max - allocation)
     */
    private void calculateNeedMatrix() {
        for (int i = 0; i < processCount; i++) {
            for (int j = 0; j < resourceCount; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }
    }

    /**
     * Checks if the system is in a safe state
     * @return true if safe, false otherwise
     */
    public boolean isSafeState() {
        int[] work = Arrays.copyOf(avail, resourceCount);
        boolean[] finished = new boolean[processCount];
        int count = 0;

        while (count < processCount) {
            boolean found = false;

            for (int i = 0; i < processCount; i++) {
                if (!finished[i] && canGrantResources(i, work)) {
                    // Allocate resources
                    for (int j = 0; j < resourceCount; j++) {
                        work[j] += alloc[i][j];
                    }

                    safeSequence[count++] = i;
                    finished[i] = true;
                    found = true;
                }
            }

            if (!found) {
                break; // No safe sequence found
            }
        }

        return count == processCount;
    }

    /**
     * Checks if resources can be granted to a process
     */
    private boolean canGrantResources(int process, int[] work) {
        for (int j = 0; j < resourceCount; j++) {
            if (need[process][j] > work[j]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the safety analysis results
     */
    public void printSafetyAnalysis() {
        if (isSafeState()) {
            System.out.println("System is in safe state.");
            System.out.print("Safe sequence: ");
            for (int i = 0; i < processCount; i++) {
                System.out.print("P" + safeSequence[i]);
                if (i != processCount - 1) {
                    System.out.print(" → ");
                }
            }
            System.out.println();
        } else {
            System.out.println("System is in unsafe state (potential deadlock)");
        }
    }

    public static void main(String[] args) {
        // Test data - same as original example
        int[][] alloc = {
                {0, 1, 0}, // P0
                {2, 0, 0}, // P1
                {3, 0, 2}, // P2
                {2, 1, 1}, // P3
                {0, 0, 2}  // P4
        };

        int[][] max = {
                {7, 5, 3}, // P0
                {3, 2, 2}, // P1
                {9, 0, 2}, // P2
                {2, 2, 2}, // P3
                {4, 3, 3}  // P4
        };

        int[] avail = {3, 3, 2};

        BankersAlgorithm banker = new BankersAlgorithm(max, alloc, avail);
        banker.printSafetyAnalysis();
    }
}