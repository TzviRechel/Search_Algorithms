import java.util.HashSet;
import java.util.Stack;

public class DFID extends SearchAlgorithm {


    public DFID(State start, State goal, boolean withOpen) {
        super(start, goal, withOpen);
    }

    @Override
    public void search() {

        // Check is the search is possible
        if(!checkValidGame()){
            _path.append("no path\n");
            return;
        }

        // Check if the start state is the goal
        if (start.equals(goal)) {
            this._path.append("\n");
            return;
        }

        for (int depth = 1; ; depth++) {
            HashSet<State> visited = new HashSet<>();
            String result = limitedDFS(start, goal, depth, visited);
            if (!result.equals("cutoff")) {
                if (result.equals("fail")) {
                    _path.append("no path\n");
                }
                break;
            }
        }
    }


    private String limitedDFS(State current, State goal, int limit, HashSet<State> visited) {
        if (current.equals(goal)) {
            cost = current.g();
            findPath(current); // reconstruct the path
            return "success";
        } else if (limit == 0) {
            return "cutoff";
        } else {
            visited.add(current);
            boolean isCutoff = false;

            for (State child : current) {
                if (visited.contains(child)) {
                    continue; // loop avoidance
                }
                child.set_parent(current); // Set parent for path reconstruction
                generate++; // Count generated nodes

                if(this.with_open){ // Print open list if required
                    printOpenList(visited);
                }

                String result = limitedDFS(child, goal, limit - 1, visited);

                if (result.equals("cutoff")) {
                    isCutoff = true;
                } else if (result.equals("success")) {
                    return "success";
                }
            }

            visited.remove(current);
            return isCutoff ? "cutoff" : "fail";
        }
    }

    private void findPath(State goalState) {
        Stack<Operator> stack = new Stack<>();
        while (goalState.get_parent() != null) {
            stack.add(goalState.get_operator());
            goalState = goalState.get_parent();
        }
        while (!stack.isEmpty()) {
            Operator op = stack.pop();
            _path.append(op.toString()).append(stack.isEmpty() ? "\n" : "--");
        }
    }

    private void printOpenList(HashSet<State> visited){
        System.out.println("open list:");
        System.out.println("------------------------------------------------------------");
        for(State s : visited){
            s.printState();
            System.out.println();
        }
        System.out.println("------------------------------------------------------------");

    }
}
