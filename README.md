# Search Algorithms implementaion


## Description

This project implements several informed and uninformed search algorithms to find the optimal path between
a given start state and a goal state on a 3x3 board.
The board features six marbles, two of each color,
and allows circular movement. The cost of a move varies depending on the marble's color.
The project also includes a heuristic function to enhance the performance of informed search algorithms.

Implemented Search Algorithms:

- **BFS** (Breadth-First Search) 
- **DFID** (Depth-First Iterative Deepening)
- **A**\* (A-Star)
- **IDA**\* (Iterative Deepening A-Star)
- **DFBnB** (Depth-First Branch-and-Bound)

---


## Input Format

The program reads input from a file named `input.txt`.


### Example Input File

```
BFS
with time
no open
R,R,_
B,B,_
G,G,X
Goal state:
G,R,R
B,B,_
_,G,X
```

- **First line**: Algorithm to use (e.g., A\*)
- **Second line**: Whether to measure time ("with time")
- **Third line**: Whether to display the open list ("with open")
- **Fourth section**: Initial state of the board
- **Goal State section**:  The desired target state of the board
- Empty cells are represented by an underscore (**\_**).
- Blocked cells are represented by X.

---

## Output Format

The program writes the results to a file named `output.txt`.


### Example Output File

```
(1,1):R:(1,3)--(3,1):G:(1,1)
Num: 10
Cost: 13
0.002 seconds
```


- **Num**: Total number of nodes expanded during the search- **First line**: Solution path as a sequence of moves(`R` and `G` represent the color).
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
   javac *.java
   ```

2. **Run the Program**:

   ```
   java Ex1
   ```

   Ensure the `input.txt` file is in the same directory as the program.


---

## Notes

- The program supports time measurement and open-list display for debugging purposes.
- BFS and DFID are uninformed search algorithms and may not find the optimal solution in terms of cost, as they do not consider the cost of color changes.



