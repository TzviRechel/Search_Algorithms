# Project: Solving the Tile Puzzle Using Search Algorithms

### Course: "Solving Problems Using Search" (פתרון בעיות באמצעות חיפוש)

## Project Description

This project implements a variety of search algorithms to solve the **3x3 Tile Puzzle** (also known as the colored tile problem). The goal is to move colored tiles on a 3x3 board to transform the initial state into the goal state while minimizing the cost of the moves.

The problem involves tiles of three different colors:

- **Red (2 tiles)**
- **Blue (2 tiles)**
- **Green (2 tiles)**

Each move has a specific cost, depending on the tile color:

- Moving a **blue tile** costs **1**.
- Moving a **green tile** costs **2**.
- Moving a **red tile** costs **3**.

The program applies several search algorithms to find the optimal solution to the problem, including:

- **BFS** (Breadth-First Search)
- **DFID** (Depth-First Iterative Deepening)
- **A**\* (A-Star)
- **IDA**\* (Iterative Deepening A-Star)
- **DFBnB** (Depth-First Branch-and-Bound)

The program also measures and outputs relevant information such as the solution path, number of nodes expanded, total cost, and runtime.

---

## Problem Explanation

The tile puzzle consists of a **3x3 grid** with a combination of red, blue, and green tiles. Each tile can move **horizontally** or **vertically** into adjacent empty cells (a blank square on the grid). The goal is to rearrange the tiles starting from a given **initial state** to match a specified **goal state**.

- **Initial State**: The starting arrangement of tiles on the grid.
- **Goal State**: The desired arrangement of tiles on the grid.
- **Move Cost**: The cost of each move is determined by the color of the tile being moved.

### Example Move Cost

- **Blue tile**: 1 unit
- **Green tile**: 2 units
- **Red tile**: 3 units

The objective is to find the **minimum cost path** to transform the grid from the initial state to the goal state.

---

## Input Format

The program reads input from a file named `input.txt`. The file format is as follows:

1. **Algorithm Name** (BFS, DFID, A\*, IDA\*, DFBnB)
2. **Time Measurement** ("with time" / "no time")
3. **Open List Display** ("with open" / "no open")
4. **Initial State** (Grid format with tiles and an empty square)
5. **Goal State** (Grid format representing the goal configuration)

### Example Input File

```
A*
with time
no open
B,G,R
_,G,B
R,_,_
Goal state:
B,G,R
B,G,R
_,_,_
```

- **First line**: Algorithm to use (e.g., A\*)
- **Second line**: Whether to measure time ("with time")
- **Third line**: Whether to display the open list ("with open")
- **Fourth section**: Initial state of the grid
- **Goal State section**: Target state of the grid
- Empty cells are represented by an underscore (**\_**).

---

## Output Format

The program writes the results to a file named `output.txt` and prints them to the console.

### Output Includes:

1. **Solution Path**: The sequence of moves to reach the goal state.
2. **Number of Nodes Expanded**: Total nodes visited during the search.
3. **Cost**: Total cost of the solution path.
4. **Runtime** (optional): Time taken to find the solution.

### Example Output File

```
(1,1):R:(1,3)--(3,1):G:(1,1)
Num: 11
Cost: 13
0.002 seconds
```

- **First line**: Solution path as a sequence of moves (Right, Left, etc.).
- **Num**: Total number of nodes expanded during the search.
- **Cost**: Total cost of the solution.
- **Time**: Total runtime (only if "with time" is specified).

If no solution is found, the output will be:

```
no path
Num:
Cost: inf
```

---

## How to Run the Project

1. **Compile the Program**:

   ```
   javac Ex1.java
   ```

2. **Run the Program**:

   ```
   java Ex1
   ```

   Ensure the `input.txt` file is in the same directory as the program.

3. **View the Output**:

   - Check the console for the solution.
   - View the output written to the `output.txt` file.

---

## Algorithms Implemented

1. **BFS**: Breadth-First Search explores the graph level by level.
2. **DFID**: Depth-First Iterative Deepening combines DFS with iterative deepening.
3. **A**\*: A-Star uses a heuristic function to find the optimal path efficiently.
4. **IDA**\*: Iterative Deepening A-Star enhances A\* with iterative cost-bound searches.
5. **DFBnB**: Depth-First Branch-and-Bound optimizes DFS by pruning non-promising paths.

---

## Notes

- The program supports time measurement and open-list display for debugging purposes.
- The solution path and total cost are essential outputs for analyzing the efficiency of each algorithm.
- This project highlights the differences in performance between various search algorithms when solving the tile puzzle.

---

## Author

This project was developed as part of the course **"Solving Problems Using Search" (פתרון בעיות באמצעות חיפוש)**.

---

