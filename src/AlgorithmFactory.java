public class AlgorithmFactory {
    public static Searching getAlgorithm(String algorithmName, State start, State goal, boolean with_open) {
        return switch (algorithmName) {
            case "BFS" -> new BFS(start, goal, with_open);
            case "DFID" -> new DFID(start, goal, with_open);
            case "A*" -> new Astar(start, goal, with_open);
           // case "IDA*" -> new IDAStar(start, goal, with_open);
            //case "DFBNB" -> new DFBnB(start, goal, with_open);
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        };
    }
}
