import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DFBnB extends SearchAlgorithm {

    public DFBnB(State start, State goal, boolean withOpen) {
        super(start, goal, withOpen);
        _path.append("no path\n");
    }

    @Override
    public void search() {

        // Check is the search is possible
        if(!checkValidGame()){
            return;
        }

        // Check if the start state is the goal
        if (start.equals(goal)) {
            _path.setLength(0); // clear
            this._path.append("\n");
            return;
        }

        Stack<State> S = new Stack<>();
        HashSet<State> open_list = new HashSet<>();
        S.push(start);
        open_list.add(start);
        int t = Integer.MAX_VALUE;

        while(!S.isEmpty()){

            // Print open list if required
            if(this.with_open){
                printOpenList(S);
            }

            State current = S.pop();

            if(current.out){
                open_list.remove(current);
            }else {
                current.out = true;
                S.push(current);

                ArrayList<State> N = new ArrayList<>();
                for (State child : current) {
                    generate++;
                    child.setCreationTime(generate);
                    N.add(child);
                }

                    // Sort children
                    N.sort((o1, o2) -> {
                        if (Integer.compare(o1.f(goal), o2.f(goal)) == 0) {
                            return Integer.compare(o1.getCreationTime(), o2.getCreationTime());
                        }
                        return Integer.compare(o1.f(goal), o2.f(goal));
                    });

                // filter N array
                ArrayList<State> filteredN = new ArrayList<>();

                for (State child : N) {
                    if (child.f(goal) >= t) {
                        break; // Exit the loop as further elements are no longer relevant
                    }

                    // Loop avoidance
                    State inStack = null;
                    if (open_list.contains(child)) {
                        // in this case, we find the identical state in the stack for to check if it marked as out
                        // end replace it if needed
                        for (State g : S) {
                            if (g.equals(child)) {
                                inStack = g;
                                break;
                            }
                        }
                        if (inStack == null) {
                            System.out.println("error");
                            return;
                        }
                            if (inStack.out) {
                                continue; // remove from N
                            } else {
                                if (inStack.f(goal) <= child.f(goal)) {
                                    continue;
                                } else {
                                    S.remove(inStack);
                                    open_list.remove(child);
                                    filteredN.add(child);
                                }
                            }

                    } else  if (child.equals(goal)) {
                            t = child.f(goal);
                            cost = child.g();
                            this.findPath(child, S);
                            break; // Exit the loop as further elements are no longer relevant
                    }else{
                        filteredN.add(child);
                    }

                }

                // Push children to the stack in reverse order
                for (int i = filteredN.size() - 1; i >= 0; i--) {
                    State n = filteredN.get(i);
                    S.push(n);
                    open_list.add(n);
                }
            }
        }
    }

    private void findPath(State n, Stack<State> s) {
        _path = new StringBuilder();
        for (State p : s){
            if(p.out && p.get_operator() != null){
                _path.append(p.get_operator().toString()).append("--");
            }
        }
        _path.append(n.get_operator().toString()).append("\n");
    }

    private void printOpenList(Stack<State> S){
        System.out.println("open list:");
        System.out.println("------------------------------------------------------------");
        for(State s : S){
            if(!s.out) {
                s.printState();
                System.out.println();
            }
        }
        System.out.println("------------------------------------------------------------");
    }
}
