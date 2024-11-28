public class State {

    final int WHITE = 0;
    final int BLACK = 1;
    final int GREEN = 2;
    final int RED = 3;
    final int BLUE = 4;

    private int[][] _mat = new int[3][3];

    /**
     * the constructor get a String represents the start state.
     * @param String start
     */
    public State(String start){
        if(init.length() < 9){
            throw new IllegalArgumentException("Input must be 3x3.");
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


    public void operator()


}

