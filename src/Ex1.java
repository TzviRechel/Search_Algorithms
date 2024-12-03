import java.util.ArrayList;

public class Ex1 {
    public static void main(String[] args) {
        State my = new State("RR_" +
                "BB_" +
                "GGX");
        my.printState();
        ArrayList<State> child = my.getChildren();
        for (State state : child) {
            state.printState();
        }
    }
}
