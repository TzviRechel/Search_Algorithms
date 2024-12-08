import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS extends Searching{


    public BFS(State start, State goal, boolean withOpen) {
        super(start, goal, withOpen);
    }

    @Override
    public void search() {
        HashSet<State> openList = new HashSet<>();
        HashSet<State> closedList = new HashSet<>();
        Queue<State> Q = new LinkedList<>();
        generate = 1;
        State myGoal = null; // the goal that the algorithm will find
        Q.add(start);
        openList.add(start);
        boolean out = false; // flag if find the goal state
        while (!Q.isEmpty()){
            if(this.with_open){ //print the open list
                System.out.println("open list:");
                for(State s : Q){
                    s.printState();
                }
            }
            State curr = Q.poll();
            openList.remove(curr);
            closedList.add(curr);
            for(State child : curr) {
                generate++;
                if (!openList.contains(child) && !closedList.contains(child)) {
                    if (child.equals(goal)) {
                        child.set_parent(curr);
                        myGoal = child;
                        out = true;
                        break;
                    }
                    child.set_parent(curr);
                    Q.add(child);
                    openList.add(child);
                }
            }
            if(out){
                break;
            }
        }
        if(myGoal == null){
            System.out.println("no path");
            return;
        }
        State path = myGoal;
        Stack<Operator> S = new Stack<>();
        while(path.get_parent()!=null){
            S.add(path.get_operator());
            path = path.get_parent();
        }
        cost = 0;
       // try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            while (!S.isEmpty()) {
                cost += S.peek().getCost();
                System.out.print(S.pop().toString() + (S.isEmpty() ? "\n" : "--"));
                //writer.print(S.pop().toString() + (S.isEmpty() ? "\n" : "--"));
            }
            System.out.println("cost: " + cost);
            System.out.println("num: " + generate);
           // writer.println("cost: " + cost);
           // writer.println("num: " + generate);
       // }
       // catch (IOException e) {
       //     e.printStackTrace(); // Handle exceptions if the file cannot be written
       // }
    }
}
