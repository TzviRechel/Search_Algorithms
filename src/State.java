import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a state in a 3x3 grid-based puzzle. Each cell can have a specific color or state.
 * Provides functionality to generate new states by applying moves and to iterate over possible child states.
 */
public class State implements Iterable<State> {

    // Color constants
    private static final int WHITE = 0;
    private static final int BLACK = 1;
    private static final int GREEN = 2;
    private static final int RED = 3;
    private static final int BLUE = 4;

    private final int[][] _mat = new int[3][3]; // 3x3 grid representing the state
    private Operator _operator = null;    // The operator that generated this state
    private State _parent = null;         // Parent state for backtracking
    private int _aggregateCost;
    private int _h = -1;

    /**
     * Constructor to initialize the state from a string representation.
     *
     * @param start a string of length 9 representing the 3x3 grid.
     *              Each character represents a cell:
     *              'R' -> Red, 'B' -> Blue, 'G' -> Green, '_' -> White, 'X' -> Black.
     * @throws IllegalArgumentException if the input is invalid.
     */
    public State(String start) {
        if (start.length() != 9) {
            throw new IllegalArgumentException("Input must represent a 3x3 grid (length 9).");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = start.charAt((i * 3) + j);
                _mat[i][j] = mapCharToColor(cell);
            }
        }
    }

    /**
     * Constructor to initialize a new state based on a matrix and an operator.
     * Assumes the operator is valid.
     *
     * @param mat a 3x3 matrix representing the state.
     * @param op  the operator that generated this state.
     */
    public State(int[][] mat, Operator op) {
        copyMatrix(mat, this._mat);
        this._operator = op;
        this.move(op);
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
     * Checks if this state is equal to another state, according to the matrices.
     *
     * @param other the state to compare with.
     * @return true if the states are identical, false otherwise.
     */
    public boolean equals(State other) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this._mat[i][j] != other._mat[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Operator get_operator() {
        return this._operator;
    }

    public State get_parent() {
        return _parent;
    }

    public void set_parent(State _parent) {
        this._parent = _parent;
    }

    /**
     * Prints the current state in a readable 3x3 grid format.
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

    private void addCost(int aggregateCost) {
        this._aggregateCost+=aggregateCost;
    }

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
                        case BLUE -> sum+=4;
                        default -> {
                        }
                    }
                }
            }
        }
        this._h = sum;
        return _h;
    }

    public int g(){
        return this._aggregateCost;
    }

    public int huristic(State goal){
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(_mat[i][j] != goal._mat[i][j]){
                    switch (_mat[i][j]){
                        case GREEN -> sum+=3;
                        case RED -> sum+=10;
                        case BLUE -> sum+=4;
                        default -> {
                            break;
                        }
                    }
                }
            }
        }
        this._h = sum;
        return sum;
    }

    // Private helper methods

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
                                if (_operator == null || !_operator.isInverse(curr)) {
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
                    advancePosition();
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

    private int minSteps(int i, int j, int x, int y){
        int colDist = y-j;
        int rowDist = x-i;
        int horizon = Math.min(
                Math.abs(colDist), Math.min(
                        Math.abs(colDist - 3), Math.abs(colDist + 3))
        );
        int vertical = Math.min(
                Math.abs(rowDist), Math.min(
                        Math.abs(rowDist - 3), Math.abs(rowDist + 3))
        );

        return horizon+vertical;
    }


}






