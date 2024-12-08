public abstract class Searching {

   protected boolean with_open;
   protected State start;
   protected State goal;
   protected int generate;
   protected int cost;

    public Searching(State start, State goal, boolean with_open){
        this.start = start;
        this.goal = goal;
        this.with_open = with_open;
    }
    public abstract void search();
}
