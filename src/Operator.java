public class Operator {

    private int[] _source = new int[2]; //
    private int[] _dest = new int[2];
    private int _color = 0;


    public Operator(){
    }

    public Operator(int x1, int y1, int x2, int y2, int color){
        if(color < 2 || color > 4){
            throw new IllegalArgumentException("color is not legal");
        }
        _source[0] = x1; _source[1] = y1;
        _dest[0] = x2; _dest[1] = y2;
        _color = color;
    }

    public int get_color(){
        return this._color;
    }

    public int[] get_dest() {
        return _dest;
    }

    public int[] get_source() {
        return _source;
    }

    public int getCost(){
        return switch (get_color()) {
            case 2 -> 3;
            case 3 -> 10;
            case 4 -> 1;
            default -> 0;
        };
    }

    public String toString(){
        String s = "";
        //find the color
        String color = switch (get_color()) {
            case 2 -> "G";
            case 3 -> "R";
            case 4 -> "B";
            default -> "";
        };
        s += "(" + this._source[0] + "," + this._source[1] + ")" + ":" + color + ":" + "(" + this._dest[0] +"," + this._dest[1] + ")";
        return s;
    }


    public boolean isInverse(Operator op){
        return this._source == op._dest && this._dest == op._source && this._color != op._color;
    }
}
