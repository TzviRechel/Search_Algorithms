import java.io.*;
import java.util.*;


public class Ex1 {
    public static void main(String[] args) {

        String filePath = "input.txt";
        Map<String, Object> params = parseInput(filePath); //map to store the parameters in the file

         // Initialize states and parameters
        State start = new State((String) params.get("initial_state"));
        State goal = new State((String) params.get("goal_state"));
        String name = (String)params.get("algorithm");
        boolean openList = (boolean)params.get("open_list");
        boolean time = (boolean)params.get("with_time");

        // Get the algorithm
        SearchAlgorithm algo = AlgorithmFactory.getAlgorithm(name, start, goal, openList);

        // Execute the search
       long startTime = System.currentTimeMillis();
        algo.search();
       long endTime = System.currentTimeMillis();
       double elapsedTime = (endTime - startTime) / 1000.0;

        //write the result to output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(algo.getPath());
            writer.write("Num: " + algo.getNumberOfNodes() + "\n");
            if(algo.getPath().equals("no path\n")){
                writer.write("Cost: inf\n");
            }else {
                writer.write("Cost: " + algo.getCost() + "\n");
            }
            if (time) {
                writer.write(elapsedTime + " seconds\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    private static Map<String, Object> parseInput(String filePath) {

        // Map to store the parsed parameters and states
        Map<String, Object> result = new HashMap<>();

        // Reading the file
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Extracting parameters
        result.put("algorithm", lines.get(0)); // First line: Algorithm
        result.put("with_time", lines.get(1).equals("with time"));
        result.put("open_list", lines.get(2).equals("with open"));

        // Extracting initial state
        StringBuilder initialState = new StringBuilder();
        int i = 3; // Start at line 4 (index 3)
        while (!lines.get(i).equals("Goal state:")) {
            initialState.append(lines.get(i).replace(",", ""));
            i++;
        }
        result.put("initial_state", initialState.toString());

        // Extracting goal state
        StringBuilder goalState = new StringBuilder();
        i++; // Skip the "Goal state:" line
        while (i < lines.size()) {
            goalState.append(lines.get(i).replace(",", ""));
            i++;
        }
        result.put("goal_state", goalState.toString());

        return result;
    }

}
