import java.util.ArrayList;

public class Ex1 {
    public static void main(String[] args) {
        State start = new State("RR_" +
                "BB_" +
                "GGX");
        State goal = new State("GRRBB__GX");

        start.printState();
        goal.printState();
        Algorithms.BFS(start, goal);
        }
    }
