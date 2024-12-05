public class AlgorithmFactory {
    public static Searching getAlgorithm(String algorithmName) {
        return switch (algorithmName.toUpperCase()) {
            case "BFS" -> new BFS();
            //case "DFID" -> new DFID();
           // case "A*" -> new AStar();
           // case "IDA*" -> new IDAStar();
            //case "DFBNB" -> new DFBnB();
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        };
    }
}
