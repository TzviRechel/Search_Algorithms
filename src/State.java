import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class State implements Iterable<State> {

    final int WHITE = 0;
    final int BLACK = 1;
    final int GREEN = 2;
    final int RED = 3;
    final int BLUE = 4;

    private int[][] _mat = new int[3][3];
    private Operator _operator = null;
    //private ArrayList<State> _children = new ArrayList<State>();
    private State _parent = null;

    /**
     * the constructor get a String represents the start state.
     *
     * @param start is
     */
    public State(String start) {
        if (start.length() != 9) {
            throw new IllegalArgumentException("Input must be 3x3");
        }
        int[] count = new int[3]; // array to count if there is exactly 2 for each color
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = start.charAt((i * 3) + j);
                switch (cell) {
                    case 'R':
                        _mat[i][j] = RED;
                        count[0]++;
                        break;
                    case 'B':
                        _mat[i][j] = BLUE;
                        count[1]++;
                        break;
                    case 'G':
                        _mat[i][j] = GREEN;
                        count[2]++;
                        break;
                    case '_':
                        _mat[i][j] = WHITE;
                        break;
                    case 'X':
                        _mat[i][j] = BLACK;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid character in input string: " + cell);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (count[i] != 2) {
                throw new IllegalArgumentException("there is no two for each color");
            }
        }
    }

    /**
     * @param parent
     */
    public State(int[][] mat, Operator op) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this._mat[i][j] = mat[i][j];
            }
        }
        this._operator = op;
        this.move(op);
    }

    /**
     * move the color according the operator. assume that the operator is valid
     *
     * @param op the operator that generate this state
     */
    public void move(Operator op) {
        this._mat[op.get_source()[0]][op.get_source()[1]] = WHITE;
        this._mat[op.get_dest()[0]][op.get_dest()[1]] = op.get_color();
    }

    /**
     * @param st
     * @return
     */
    public boolean equals(State st) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this._mat[i][j] != st._mat[i][j]) {
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

    private State generateChild(int[][] mat, Operator op) {
        State child = new State(mat, op);
        child._parent = this;
        return child;
    }

    public void printState() {
        for (int i = 0; i < 3; i++) {
            System.out.print("[");
            for (int j = 0; j < 3; j++) {
                char c = switch (_mat[i][j]) {
                    case RED -> 'R';
                    case BLUE -> 'B';
                    case GREEN -> 'G';
                    case WHITE -> '_';
                    case BLACK -> 'X';
                    default -> '?'; // Should never happen
                };
                System.out.print(c + (j < 2 ? "," : "]"));
            }
            System.out.println();
        }
    }

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
                    if (_mat[i][j] > 1) { // There's a ball to move
                        while (direction < 4) {
                            int[] neighbor = getNeighbor(i, j, direction);
                            if (neighbor != null && _mat[neighbor[0]][neighbor[1]] == WHITE) {
                                Operator curr = new Operator(i, j, neighbor[0], neighbor[1], _mat[i][j]);
                                if (_operator == null || !_operator.isInverse(curr)) {
                                    nextState = generateChild(_mat, curr);
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


            private int[][] cloneMatrix(int[][] mat) {
                int[][] clone = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    System.arraycopy(mat[i], 0, clone[i], 0, 3);
                }
                return clone;
            }
        };
    }
}






