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
            State curr = Q.poll();
            openList.remove(curr);
            System.out.println("state number " + generate + ":");
            curr.printState();
            closedList.add(curr);
            for(State child : curr) {
                generate++;
                if (!openList.contains(child) && !closedList.contains(child)) {
                    if (child.equals(goal)) {
                        myGoal = child;
                        out = true;
                        break;
                    }
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
        while (!S.isEmpty()){
            cost+=S.peek().getCost();
            System.out.print(S.pop().toString() + (S.isEmpty() ? "\n" : "--"));
        }
        System.out.println("cost: " + cost);
        System.out.println("num: " + generate);

    }
}
