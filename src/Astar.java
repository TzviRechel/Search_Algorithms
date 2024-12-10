import java.util.*;

public class Astar extends Searching{

    public Astar(State start, State goal, boolean withOpen) {
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
        PriorityQueue<State> Q = new PriorityQueue<State>(new Comparator<State>(){

            @Override
            public int compare(State o1, State o2) {
                return Integer.compare(o1.g() + o1.h(goal), o2.g() + o2.h(goal));
            }
        });
        generate = 1;
        State myGoal = null; // the goal that the algorithm will find
        Q.add(start);
        openList.add(start);
        while (!Q.isEmpty()){
            if(this.with_open){ //print the open list
                System.out.println("open list:");
                for(State s : Q){
                    s.printState();
                }
            }
            State curr = Q.poll();
            openList.remove(curr);
            if(curr.equals(goal)){
                myGoal = curr;
                break;
            }
            closedList.add(curr);
            for(State child : curr) {
                generate++;
                if (!openList.contains(child) && !closedList.contains(child)) {
                    child.set_parent(curr);
                    Q.add(child);
                    openList.add(child);
                } else if (openList.contains(child)){
                    for (State s : Q){
                        if(s.equals(child)){
                            if(Integer.compare(s.g() + s.h(goal), child.g() + child.h(goal)) == 1) {
                                Q.remove(s);
                                Q.add(child);
                            }
                            break;
                        }
                    }
                }
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
