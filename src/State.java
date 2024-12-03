import java.util.ArrayList;

public class State{

    final int WHITE = 0;
    final int BLACK = 1;
    final int GREEN = 2;
    final int RED = 3;
    final int BLUE = 4;

    private int[][] _mat = new int[3][3];
    private Operator _operator = null;
    private ArrayList<State> _children = new ArrayList<State>();
    private State _parent = null;

    /**
     * the constructor get a String represents the start state.
     * @param start is
     */
    public State(String start){
        if(start.length() != 9){
            throw new IllegalArgumentException("Input must be 3x3");
        }
        int[] count = new int[3]; // array to count if there is exactly 2 for each color
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = start.charAt((i*3)+j);
                switch (cell){
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
     *
     * @param parent
     */
    public State(int[][] mat, Operator op){
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
     * @param op the operator that generate this state
     */
    public void move(Operator op){
        this._mat[op.get_source()[0]][op.get_source()[1]] = WHITE;
        this._mat[op.get_dest()[0]][op.get_dest()[1]] = op.get_color();
    }

    /**
     *
     * @param st
     * @return
     */
    public boolean equale(State st){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this._mat[i][j] != st._mat[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<State> getChildren() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (_mat[i][j] > 1) {  //there is a gula there, check all neighbors
                    int left = (j + 2) % 3;
                    int right = (j + 1) % 3;
                    int up = (i + 2) % 3;
                    int down = (i + 1) % 3;
                    // check left
                    if (_mat[i][left] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, i, left, _mat[i][j]); // create new operator
                        if (this._operator == null || !this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                            child._parent = this;
                        }
                    }
                    // check right
                    if (_mat[i][right] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, i, right, _mat[i][j]); // create new operator
                        if (this._operator == null || !this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                            child._parent = this;
                        }
                    }
                    // check up
                    if (_mat[up][j] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, up, j, _mat[i][j]); // create new operator
                        if (this._operator == null || !this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                            child._parent = this;
                        }
                    }
                    // check down
                    if (_mat[down][j] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, down, j, _mat[i][j]); // create new operator
                        if (this._operator == null || !this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                            child._parent = this;
                        }
                    }
                } //end if there gula
            } // end for j
        } //end for i
        return this._children;
    }

    public Operator get_operator(){
        return this._operator;
    }

    public State get_parent() {
        return _parent;
    }

    public void printState(){
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
}



