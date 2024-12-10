public abstract class Searching {

   protected boolean with_open;
   protected State start;
   protected State goal;
   protected int generate;
   protected int cost;
   protected StringBuilder _path = new StringBuilder();

    public Searching(State start, State goal, boolean with_open){
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
