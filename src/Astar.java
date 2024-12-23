import java.util.*;

public class Astar extends SearchAlgorithm {

    public Astar(State start, State goal, boolean withOpen) {
        super(start, goal, withOpen);

    }

    @Override
    public void search() {

        // Check is the search is possible
        if(!checkValidGame()){
            this._path.append("no path\n");
            return;
        }

        // Check if the start state is the goal
        if (start.equals(goal)) {
            this._path.append("\n");
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

            if(this.with_open){  // Print open list if required
                printOpenList(Q);
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

    private void printOpenList(PriorityQueue<State> q){
        System.out.println("open list:");
        System.out.println("------------------------------------------------------------");
        for(State s : q){
            s.printState();
            System.out.println();
        }
        System.out.println("------------------------------------------------------------");
    }
}
