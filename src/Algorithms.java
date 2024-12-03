import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Algorithms {

    public static void BFS(State start, State goal){
        Queue<State> Q = new LinkedList<>();
        State myGoal = null;
        Q.add(start);
        boolean out = false;
        while (!Q.isEmpty()){
           State curr = Q.poll();
           ArrayList<State> children = curr.getChildren();
           for(State child : children){
               if(child.equale(goal)){
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
        while (!S.isEmpty()){
            System.out.print(S.pop().toString() + (S.isEmpty() ? "\n" : "--"));
        }
    }
}
