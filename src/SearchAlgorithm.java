public abstract class SearchAlgorithm {

   protected boolean with_open; // Flag indicating whether to print the open list during the search
   protected State start;
   protected State goal;
   protected int generate; // Counts the number of nodes that generated in the search
   protected int cost; // Cost of the path found by the algorithm
   protected StringBuilder _path = new StringBuilder(); // Stores the path found by the algorithm

    public SearchAlgorithm(State start, State goal, boolean with_open){
        this.start = start;
        this.goal = goal;
        this.with_open = with_open;
    }

    public int getCost() {
        return cost;
    }

    public int getNumberOfNodes(){
        return generate;
    }

    public String getPath(){
        return _path.toString();
    }

    public abstract void search();
}
