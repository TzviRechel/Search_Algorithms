import java.util.ArrayList;

public class Ex1 {
    public static void main(String[] args) {
        State start = new State("RR_" +
                "BB_" +
                "GGX");
        State goal = new State("GRRBB__GX");

        start.printState();
        goal.printState();
        long startTime = System.nanoTime();
        Algorithms.BFS(start, goal);
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Elapsed time: " + elapsedTime + " seconds");
        }
    }
