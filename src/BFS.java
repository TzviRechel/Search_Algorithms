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
        if (start.equals(goal)) {
            _path.append("start is the goal\n");
            cost = 0;
            return;
        }
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
                System.out.println("------------------------------------------------------------");
                for(State s : Q){
                    s.printState();
                    System.out.println();
                }
                System.out.println("------------------------------------------------------------");
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
        if(myGoal == null) {
            this._path.append("no path");
        }else{
            cost = myGoal.g();
            this.findPath(myGoal);
            }
    }

    public void findPath(State myGoal){
        //cost = 0;
        Stack<Operator> S = new Stack<>();
        while(myGoal.get_parent()!=null){
            S.add(myGoal.get_operator());
            myGoal = myGoal.get_parent();
        }
        while(!S.isEmpty()){
            //cost += S.peek().getCost();
            _path.append(S.pop().toString()).append(S.isEmpty() ? "\n" : "--");
        }
    }

}

