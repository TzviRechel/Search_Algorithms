import java.util.ArrayList;

public class State {

    final int WHITE = 0;
    final int BLACK = 1;
    final int GREEN = 2;
    final int RED = 3;
    final int BLUE = 4;

    private int[][] _mat = new int[3][3];
    private Operator _operator = new Operator();
    private ArrayList<State> _children = new ArrayList<State>();

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
                char cell = start.charAt(i);
                switch (cell){
                    case 'R':
                        _mat[i][j] = RED;
                        count[0] ++;
                        break;
                    case 'B':
                        _mat[i][j] = BLUE;
                        count[1] ++;
                        break;
                    case 'G':
                        _mat[i][j] = GREEN;
                        count[2] ++;
                        break;
                    case '_':
                        _mat[i][j] = WHITE;
                        break;
                    case 'X':
                        _mat[i][j] = BLACK;
                        break;
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
        this._mat = mat;
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
        return this._mat == st._mat;
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
                        if (!this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                        }
                    }
                    // check right
                    if (_mat[i][right] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, i, right, _mat[i][j]); // create new operator
                        if (!this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                        }
                    }
                    // check up
                    if (_mat[up][j] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, up, j, _mat[i][j]); // create new operator
                        if (!this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                        }
                    }
                    // check down
                    if (_mat[down][j] == WHITE) { //this cell is empty
                        Operator curr = new Operator(i, j, up, down, _mat[i][j]); // create new operator
                        if (!this._operator.isInverse(curr)) { // generate child iff is not inverse move
                            State child = new State(this._mat, curr);
                            this._children.add(child);
                        }
                    }
                } //end if there gula
            } // end for j
        } //end for i
        return this._children;
    }
}

public void printState(){
    for (int i = 0; i < 3; i++) {
        System.out.println("[");
        for (int j = 0; j < 3; j++) {
            System.out.print(i + (j < 2 ? "," : "]"));
        }
        System.out.println();
    }
}

