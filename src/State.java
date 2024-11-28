import java.util.ArrayList;

public class State {

    final int WHITE = 0;
    final int BLACK = 1;
    final int GREEN = 2;
    final int RED = 3;
    final int BLUE = 4;

    private int[][] _mat = new int[3][3];
    private Operator operator = new Operator();
    private ArrayList<State> children = new ArrayList<State>();

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
    public State(State parent, Operator op){
        this._mat = parent._mat;
        this.operator = op;
        this.move(op);
    }

    /**
     * move the color according the operator. assume that the operator is valid
     * @param op the oprator that generate this state
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
                if(_mat[i][j] > 1){ //there is a gula there
                    if(_mat[])

            }

        }
        return children;
    }
}

