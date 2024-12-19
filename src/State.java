import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 *  This class represents a state in the search problem.
 *  It implements an iterable to generate the children one by one,
 *  based on the possible operators.
 *
 */

public class State implements Iterable<State> {

    // Constants representing cell colors
    private static final int WHITE = 0;
    private static final int BLACK = 1;
    private static final int GREEN = 2;
    private static final int RED = 3;
    private static final int BLUE = 4;

    private final int[][] _mat = new int[3][3]; // 3x3 matrix representing the state
    private Operator _operator = null;    // Operator that generated this state
    private State _parent = null;         // Parent state for backtracking
    private int _aggregateCost;               // Accumulated cost to reach this state
    private int _h = -1;                  // Heuristic value
    private int creationTime;               // Used to establish an order relationship between states
    public boolean out = false;             // Flag for the stack in DFS-based algorithms

    // Constructors

    /**
     * Constructor to initialize the state from a string representation.
     *
     * @param start a string of length 9 representing the 3x3 board.
     *              Each character represents a cell:
     *              'R' -> Red, 'B' -> Blue, 'G' -> Green, '_' -> White, 'X' -> Black.
     * @throws IllegalArgumentException if the input is invalid.
     */
    public State(String start) {
        if (start.length() != 9) {
            throw new IllegalArgumentException("Input must represent a 3x3 board (length 9).");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = start.charAt((i * 3) + j);
                _mat[i][j] = mapCharToColor(cell);
            }
        }
    }

    /**
     * Constructor to initialize a new state based on a board and an operator.
     * Assumes the operator is valid.
     *
     * @param mat a 3x3 matrix representing the parent of this state.
     * @param op  the operator that generated this state.
     */
    public State(int[][] mat, Operator op) {
        copyMatrix(mat, this._mat);
        this._operator = op;
        this.move(op); // Apply the operator
    }

    // Public methods

    /**
     * Calculates the heuristic function for this state based on the goal state.
     * The function sums the cost of moving balls that are not in their correct positions.
     * This function is admissible and consistent.
     * @param goal The goal state to compare against.
     * @return The heuristic value.
     */
    public int h(State goal){
        if(this._h >= 0){
            return this._h;
        }
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(_mat[i][j] != goal._mat[i][j]){
                    switch (_mat[i][j]){
                        case GREEN -> sum+=3;
                        case RED -> sum+=10;
                        case BLUE -> sum+=1;
                        default -> {
                        }
                    }
                }
            }
        }
        this._h = sum;
        return _h;
    }

    /**
     * Returns the total cost (G value) to reach this state.
     *
     * @return The accumulated cost.
     */
    public int g(){
        return this._aggregateCost;
    }

    /**
     * Calculates the total estimated cost (F value) to reach the goal state.
     * F = G (cost so far) + H (heuristic cost to goal).
     *
     * @param goal The goal state.
     * @return The F value.
     */
    public int f(State goal){
        return this.h(goal) + this.g();
    }

    /**
     * Prints the current state in a readable 3x3 board format.
     */
    public void printState() {
        for (int i = 0; i < 3; i++) {
            System.out.print("[");
            for (int j = 0; j < 3; j++) {
                System.out.print(mapColorToChar(_mat[i][j]) + (j < 2 ? "," : "]"));
            }
            System.out.println();
        }
    }

    /**
     * Checks if this state is equal to another state, according to the matrices.
     *
     * @param obj The object to compare.
     * @return true if the states are identical, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State other = (State) obj;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this._mat[i][j] != other._mat[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_mat);
    }

    // Getters and setters
    public Operator get_operator() {
        return this._operator;
    }

    public State get_parent() {
        return _parent;
    }

    public void set_parent(State _parent) {
        this._parent = _parent;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }

    // Private helper methods

    private void addCost(int aggregateCost) {
        this._aggregateCost+=aggregateCost;
    }

    /**
     * Moves a color from its source to destination based on the provided operator.
     *
     * @param op the operator defining the move.
     */
    private void move(Operator op) {
        this._mat[op.get_source()[0]][op.get_source()[1]] = WHITE;
        this._mat[op.get_dest()[0]][op.get_dest()[1]] = op.get_color();
        this._aggregateCost+=op.getCost();
    }

    /**
     * Maps a character to its corresponding color.
     * @param cell the character to map.
     * @return the mapped color.
     * @throws IllegalArgumentException if the character is invalid.
     */
    private int mapCharToColor(char cell) {
        return switch (cell) {
            case 'R' -> RED;
            case 'B' -> BLUE;
            case 'G' -> GREEN;
            case '_' -> WHITE;
            case 'X' -> BLACK;
            default -> throw new IllegalArgumentException("Invalid character in input string: " + cell);
        };
    }

    /**
     * Maps a color to its corresponding character.
     *
     * @param color the color to map.
     * @return the mapped character.
     */
    private char mapColorToChar(int color) {
        return switch (color) {
            case RED -> 'R';
            case BLUE -> 'B';
            case GREEN -> 'G';
            case WHITE -> '_';
            case BLACK -> 'X';
            default -> '?'; // Should never happen
        };
    }

    /**
     * Copies a 3x3 matrix into another 3x3 matrix.
     *
     * @param src the source matrix.
     * @param dest the destination matrix.
     */
    private void copyMatrix(int[][] src, int[][] dest) {
        for (int i = 0; i < 3; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, 3);
        }
    }

    /**
     * Returns an iterator over all possible child states.
     *
     * @return an iterator for child states.
     */
    @Override
    public Iterator<State> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private int j = 0;
            private int direction = 0; // 0=left, 1=right, 2=up, 3=down
            private State nextState = null;
            private boolean foundNext = false;

            @Override
            public boolean hasNext() {
                if (!foundNext) {
                    findNext();
                }
                return foundNext;
            }

            @Override
            public State next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                foundNext = false; // Reset flag for the next call
                return nextState;
            }

            private void findNext() {
                while (i < 3) {
                    if (_mat[i][j] > 1) { // Check if there is a ball to move
                        while (direction < 4) {
                            int[] neighbor = getNeighbor(i, j, direction);
                            if (neighbor != null && _mat[neighbor[0]][neighbor[1]] == WHITE) {
                                Operator curr = new Operator(i, j, neighbor[0], neighbor[1], _mat[i][j]);
                                if (_operator == null || !_operator.isInverse(curr)) { // The _operator == null case only occurs in the initial state.
                                    nextState = new State(_mat, curr);
                                    nextState.addCost(_aggregateCost);
                                    direction++;
                                    foundNext = true;
                                    return;
                                }
                            }
                            direction++;
                        }
                    }
                    advancePosition(); // For the next iteration
                }
                nextState = null;
                foundNext = false;
            }

            private void advancePosition() {
                direction = 0; // Reset direction
                j++;
                if (j == 3) {
                    j = 0;
                    i++;
                }
            }

            private int[] getNeighbor(int x, int y, int dir) {
                return switch (dir) {
                    case 0 -> new int[]{x, (y + 2) % 3}; // left
                    case 1 -> new int[]{x, (y + 1) % 3}; // right
                    case 2 -> new int[]{(x + 2) % 3, y}; // up
                    case 3 -> new int[]{(x + 1) % 3, y}; // down
                    default -> null;
                };
            }
        };
    }
}






