import java.util.Arrays;

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

    public boolean checkValidGame() {
        int[][] matStart = start.get_mat();
        int[][] matGoal = goal.get_mat();
        int[] countBallStart = new int[3]; // Counts for ball types 2, 3, 4
        int[] countBallGoal = new int[3];

        // Iterate through each cell in the boards
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int cellStart = matStart[i][j];
                int cellGoal = matGoal[i][j];

                // Count the balls (values 2-4)
                if (cellStart >= 2 && cellStart <= 4) {
                    countBallStart[cellStart - 2]++;
                }
                if (cellGoal >= 2 && cellGoal <= 4) {
                    countBallGoal[cellGoal - 2]++;
                }

                // Check if block cells (value 1) are in the same positions
                if ((cellStart == 1 && cellGoal != 1) || (cellGoal == 1 && cellStart != 1)) {
                    return false;
                }
            }
        }

        // Check if the ball counts match
        return Arrays.equals(countBallStart, countBallGoal);
    }

    public abstract void search();
}
