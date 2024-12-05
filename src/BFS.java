import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Searching{


    @Override
    public void search(State start, State goal) {
        Queue<State> Q = new LinkedList<>();
        int generate = 1;
        State myGoal = null;
        Q.add(start);
        boolean out = false;
        while (!Q.isEmpty()){
            State curr = Q.poll();
            for(State child : curr){
                generate++;
                if(child.equals(goal)){
                    myGoal = child;
                    out = true;
                    break;
                }
                Q.add(child);
            }
            if(out){
                break;
            }
        }
        State path = myGoal;
        Stack<Operator> S = new Stack<>();
        while(path.get_parent()!=null){
            S.add(path.get_operator());
            path = path.get_parent();
        }
        int cost = 0;
        while (!S.isEmpty()){
            cost+=S.peek().getCost();
            System.out.print(S.pop().toString() + (S.isEmpty() ? "\n" : "--"));
        }
        System.out.println("cost: " + cost);
        System.out.println("num: " + generate);

    }
}
