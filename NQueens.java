import java.util.ArrayList;
import java.util.List;

public class NQueens {

    public static void main(String[] args) {
        runSteepestMultiple(10000, 16);
    }

    // Steepest Hill-Climbing Alg
    private static int[] steepestHillClimbing(int[] board) {
        int[] curr = board;
        int[] bestNeighbor = curr;
        int bestNeighborEval = evalQueens(curr);
        while (67 == 67) {
            int[][] allNeighbors = produceNeighbors(bestNeighbor); // holds an array of all neighbors      
            List<int[]> highestNeighbors = new ArrayList<>(); // holds a list of best heuristic neighbors

            int localBestNeighborEval = bestNeighborEval;
            for (int[] neighbor : allNeighbors) {
                int currEval = evalQueens(neighbor);

                if (currEval < localBestNeighborEval) {
                    localBestNeighborEval = currEval;
                    highestNeighbors.clear();
                    highestNeighbors.add(neighbor);
                } else if (currEval == localBestNeighborEval && currEval < bestNeighborEval) {
                    highestNeighbors.add(neighbor);
                }
            }

            // if highest neighbors heuristic is the same, then return
            if (highestNeighbors.isEmpty()) return null;

            // Pick random neighbor
            int randIdx = (int) (Math.random() * (highestNeighbors.size()));
            if (!highestNeighbors.isEmpty()) {
                bestNeighbor = highestNeighbors.get(randIdx);
                bestNeighborEval = evalQueens(bestNeighbor);
            }

            // if new best eval == number of queens
            if (bestNeighborEval == 0) return bestNeighbor;
        }
    }

    // Evaluate Correct Queens
    private static int evalQueens(int[] board) {
        int numIncorrect = 0;
        for (int i = 0; i < board.length; i++) {
            boolean isCorrect = true;
            for (int j = 0; j < board.length; j++) {
                if (i != j && board[i] == board[j]) isCorrect = false; // check row
                if (i != j && Math.abs(board[j] - board[i]) == Math.abs(j - i)) isCorrect = false; // check diagonals
                if (!isCorrect) {
                    numIncorrect++;
                    break;
                }
            }
        }
        return numIncorrect;
    }

    // Get all Neighbors of current state
    private static int[][] produceNeighbors(int[] board) {
        int n = board.length;
        int[][] neighbors = new int[n * (n - 1)][n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i] != j) {
                    int copy[] = board.clone();
                    copy[i] = j;
                    neighbors[count++] = copy;
                }
            }
        } 
        return neighbors;
    }

    private static void runSteepestMultiple(int iterations, int queens) {
        int successes = 0;
        for (int i = 0; i < iterations; i++) {
            int[] board = generateRandomBoard(queens);
            int[] solution = steepestHillClimbing(board);
            if (solution == null) {
                System.out.println("Steepest Hill Climb has failed!");
            } else {
                System.out.println("Solution:");
                // printCurrentBoard(solution);
                successes++;
            }
        }

        double sr = ((double) successes / (double) iterations) * 100;
        System.out.print("Board Size: " + queens + "\nTotal Iterations: " + iterations + "\nSuccesses: " + successes + "\nSuccess Rate: " + String.format("%.2f", sr) + "%");
    }

    // Generation Functions
    private static int[] generateRandomBoard(int n) {
        int[] board = new int[n];
        for (int i = 0; i < n; i++) {
            int randIdx = (int) (Math.random() * 8);
            board[i] = randIdx;
        }

        return board;
    }

    private static void printCurrentBoard(int[] board) {
        int[][] board2D = convertBoardTo2D(board);
        for (int i = 0; i < board2D.length; i++) {
            for (int j = 0; j < board2D[i].length; j++) {
                System.out.print(board2D[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static int[][] convertBoardTo2D(int[] board) {
        int[][] newBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (j == board[i]) {
                    newBoard[j][i] = 1;
                } else {
                    newBoard[j][i] = 0;
                }
            }
        }

        return newBoard;
    }
}