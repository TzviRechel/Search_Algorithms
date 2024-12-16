import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Stack;

public class DFBnB extends Searching {

    public DFBnB(State start, State goal, boolean withOpen) {
        super(start, goal, withOpen);
        _path.append("no path\n");
    }

    @Override
    public void search() {
        Stack<State> S = new Stack<>();
        HashSet<State> open_list = new HashSet<>();
        S.push(start);
        open_list.add(start);
        int t = Integer.MAX_VALUE;
        while(!S.isEmpty()){
            //print open list
            if(this.with_open){ //print the open list
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
                    N.sort((o1, o2) -> {
                        if (Integer.compare(o1.f(goal), o2.f(goal)) == 0) {
                            return Integer.compare(o1.getCreationTime(), o2.getCreationTime());
                        }
                        return Integer.compare(o1.f(goal), o2.f(goal));
                    });
                }
                //for (State n : N) {
                for (int k = 0; k < N.size(); k++) {
                    if (N.get(k).f(goal) >= t) {
                        // remove n and all after it
                        int i = N.indexOf(N.get(k));
                        N.subList(i, N.size()).clear();
                        break; // Exit the loop as further elements are no longer relevant
                    } else {
                        State inStack = null;
                        if (open_list.contains(N.get(k))) {
                            // in this case, we find the identical state in the stack for to check if it marked as out
                            // end to replace it if needed
                            for (State g : S) {
                                if (g.equals(N.get(k))) {
                                    inStack = g;
                                    break;
                                }
                            }
                            if (inStack == null) {
                                System.out.println("error");
                                return;
                            }
                            if (inStack.out) {
                                N.remove(N.get(k));
                            } else {
                                if (inStack.f(goal) <= N.get(k).f(goal)) {
                                    N.remove(N.get(k));
                                } else {
                                    S.remove(inStack);
                                    open_list.remove(N.get(k));
                                }
                            }

                        } else {
                            if (N.get(k).equals(goal)) {
                                t = N.get(k).f(goal);
                                cost = N.get(k).g();
                                this.findPath(N.get(k), S);
                                // remove n and all after it
                                int i = N.indexOf(N.get(k));
                                N.subList(i, N.size()).clear();
                                break; // Exit the loop as further elements are no longer relevant
                            }
                        }
                    }
                }
                for (int i =N.size()-1; i>=0; i--){
                    State n = N.get(i);
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

}
