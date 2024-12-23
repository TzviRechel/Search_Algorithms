import java.util.HashSet;
import java.util.Stack;

public class IDAstar extends SearchAlgorithm {

    public IDAstar(State start, State goal, boolean withOpen) {
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

        Stack<State> S = new Stack<>();
        HashSet<State> open_list = new HashSet<>();
        int t = start.h(goal);
        int INF = Integer.MAX_VALUE;
        while (t != INF){
            start.out = false;
            int minF = INF;
            S.push(start);
            open_list.add(start);
            while(!S.isEmpty()){

                if(this.with_open){ // Print open list if required
                    printOpenList(S);
                }

                State current = S.pop();
                if(current.out){ // out is the flag indicate that we finish with this state
                    open_list.remove(current);
                }else{
                    current.out = true;
                    S.push(current);
                    for(State child : current){
                        generate++;
                        if(child.f(goal) > t){
                            minF = Math.min(minF, child.f(goal));
                            continue;
                        }
                        // loop avoidance
                        State inStack = null; // pointer to the same state in the stack
                        if(open_list.contains(child)){
                            // in this case, we find the identical state in the stack for to check if it marked as out
                            // end to replace it if needed
                            for(State n : S){
                                if(n.equals(child)){
                                    inStack = n;
                                    break;
                                }
                            }
                            if(inStack == null) {
                                System.out.println("error");
                                return;
                            }
                            if(inStack.out){
                                continue;
                            }else{
                                if(inStack.f(goal) > child.f(goal)){
                                    S.remove(inStack);
                                    open_list.remove(child);
                                }else{
                                    continue;
                                }
                            }

                        }
                        if(child.equals(goal)){
                            cost = child.g();
                            this.findPath(child, S);
                            return;
                        }
                        S.push(child);
                        open_list.add(child);
                    }
                }
            }
            t = minF;
        }
        _path.append("no path\n");
    }

    private void findPath(State child, Stack<State> s) {
        for (State p : s){
            if(p.out && p.get_operator() != null){
                _path.append(p.get_operator().toString()).append("--");
            }
        }
        _path.append(child.get_operator().toString()).append("\n");
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
