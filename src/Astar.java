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
                if (Integer.compare(o1.f(goal), o2.f(goal)) == 0){
                    return Integer.compare(o1.getCreationTime(), o2.getCreationTime());
                }
                return Integer.compare(o1.f(goal), o2.f(goal));
            }
        });
        State myGoal = null; // the goal that the algorithm will find
        Q.add(start);
        openList.add(start);
        while (!Q.isEmpty()){
            //print open list
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
            if(curr.equals(goal)){
                myGoal = curr;
                break;
            }
            closedList.add(curr);
            for(State child : curr) {
                generate++;
                child.setCreationTime(generate);
                if (!openList.contains(child) && !closedList.contains(child)) {
                    child.set_parent(curr);
                    Q.add(child);
                    openList.add(child);
                } else if (openList.contains(child)){
                    for (State s : Q){
                        if(s.equals(child)){
                            if(s.f(goal) > child.f(goal)) {
                                Q.remove(s);
                                Q.add(child);
                                child.set_parent(curr);
                            }
                            break;
                        }
                    }
                }
            }
        }
        if(myGoal == null) {
            this._path.append("no path\n");
        }else{
            cost = myGoal.g();
            this.findPath(myGoal);
        }
    }

    public void findPath(State myGoal){
        Stack<Operator> S = new Stack<>();
        while(myGoal.get_parent()!=null){
            S.add(myGoal.get_operator());
            myGoal = myGoal.get_parent();
        }
        while(!S.isEmpty()){
            _path.append(S.pop().toString()).append(S.isEmpty() ? "\n" : "--");
        }
    }
}
