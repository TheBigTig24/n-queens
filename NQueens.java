import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NQueens {

    public static void main(String[] args) {
        System.out.println("Steepest Ascent Hill Climbing Multiple");
        runSteepestMultiple(10000, 8);
        System.out.println("Steepest Ascent Hill Climbing Multiple");
        runSteepestMultiple(10000, 12);
        System.out.println("Steepest Ascent Hill Climbing Multiple");
        runSteepestMultiple(10000, 16);

        System.out.println("Min Conflicts Algorithm Multiple");
        runMinConflictsMultiple(10000, 8, 10000);
        System.out.println("Min Conflicts Algorithm Multiple");
        runMinConflictsMultiple(10000, 12, 10000);
        System.out.println("Min Conflicts Algorithm Multiple");
        runMinConflictsMultiple(10000, 16, 10000);

        runOne(8, 1);
        runOne(12, 1);
        runOne(16, 1);
        runOne(8, 2);
        runOne(12, 2);
        runOne(16, 2);
    }

    // Steepest Hill-Climbing Alg
    private static Response steepestHillClimbing(int[] board) {
        long time = System.nanoTime();
        int[] curr = board;
        int[] bestNeighbor = curr;
        int bestNeighborEval = evalQueens(curr);
        int steps = 0;

        if (evalQueens(board) == 0) {
            return new Response(board, steps, System.nanoTime() - time);
        }
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
            steps++;
            // if highest neighbors heuristic is the same, then return
            if (highestNeighbors.isEmpty()) return null;

            // Pick random neighbor
            int randIdx = (int) (Math.random() * (highestNeighbors.size()));
            if (!highestNeighbors.isEmpty()) {
                bestNeighbor = highestNeighbors.get(randIdx);
                bestNeighborEval = evalQueens(bestNeighbor);
            }

            // if new best eval == number of queens
            if (bestNeighborEval == 0) return new Response(bestNeighbor, steps, System.nanoTime() - time);
        }
    }

    // Min-Conflicts Alg
    private static Response minConflictsAlg(int[] board, int maxSteps) {
        long time = System.nanoTime();
        // get frequency arrays
        int[] rows = new int[board.length];
        int[] topLeftDiagonals = new int[board.length * 2];
        int[] bottomLeftDiagonals = new int[board.length * 2];

        for (int i = 0; i < board.length; i++) {
            int row = board[i];
            rows[row]++;
            topLeftDiagonals[row - i + board.length - 1]++;
            bottomLeftDiagonals[row + i]++;
        }

        int steps = 0;
        while (maxSteps >= 0) {
            // check if current state is done
            if (evalQueens(board) == 0) {
                long endTime = System.nanoTime() - time;
                return new Response(board, steps, endTime);
            }

            // get a queen to move
            List<Integer> conflictingQueens = getConflictingQueens(board);
            int randIdx = (int) (Math.random() * conflictingQueens.size());
            int queenToMove = conflictingQueens.get(randIdx);

            // move a queen
            int prevRow = board[queenToMove];
            rows[prevRow]--;
            topLeftDiagonals[prevRow - queenToMove + board.length - 1]--;
            bottomLeftDiagonals[prevRow + queenToMove]--;

            List<Integer> leastConflictingRows = new ArrayList<>();
            int leastConflicts = board.length + 1;
            for (int i = 0; i < board.length; i++) {
                int currConflicts = rows[i] + topLeftDiagonals[i - queenToMove + board.length - 1] + bottomLeftDiagonals[i + queenToMove];
                if (currConflicts < leastConflicts) {
                    leastConflictingRows.clear();
                    leastConflictingRows.add(i);
                    leastConflicts = currConflicts;
                } else if (currConflicts == leastConflicts) {
                    leastConflictingRows.add(i);
                }
            }

            randIdx = (int) (Math.random() * leastConflictingRows.size());
            int chosenRow = leastConflictingRows.get(randIdx);
            board[queenToMove] = chosenRow;

            rows[chosenRow]++;
            topLeftDiagonals[chosenRow - queenToMove + board.length - 1]++;
            bottomLeftDiagonals[chosenRow + queenToMove]++;

            maxSteps--;
            steps++;
        }
        return null;
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

    // check which queens are in conflict
    private static List<Integer> getConflictingQueens(int[] board) {
        List<Integer> conflicting = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (countConflicts(board, i, board[i]) > 1) conflicting.add(i);
        }

        return conflicting;
    }

    // count conflicts
    private static int countConflicts(int[] board, int row, int col) {
        int conflicts = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == row || Math.abs(col - board[i]) == Math.abs(row - i)) conflicts++;
        }

        return conflicts;
    }

    // run steepest ascent a custom number of times
    private static void runSteepestMultiple(int iterations, int queens) {
        int successes = 0;
        int totalSteps = 0;
        long at = 0;
        for (int i = 0; i < iterations; i++) {
            int[] board = generateRandomBoard(queens);
            Response solution = steepestHillClimbing(board);
            if (solution != null) {
                successes++;
                totalSteps += solution.getSteps();
                at += solution.getTime();
            }
        }

        at /= successes;
        double ms = at / 1000000.0;
        double as = (double) totalSteps / (double) successes;
        double sr = ((double) successes / (double) iterations) * 100;
        System.out.print("Board Size: " + queens + "\nTotal Iterations: " + iterations + "\nSuccesses: " + successes + 
            "\nSuccess Rate: " + String.format("%.2f", sr) + "%\nAverage Steps: " + String.format("%.2f", as) + "\nAvg. Time Taken (in ms): " + ms + "\n\n");
    }

    // run min conflicts a custom number of times
    private static void runMinConflictsMultiple(int iterations, int queens, int maxSteps) {
        int successes = 0;
        int totalSteps = 0;
        long at = 0;
        for (int i = 0; i < iterations; i++) {
            int[] board = generateRandomBoard(queens);
            Response solution = minConflictsAlg(board, maxSteps);
            if (solution != null) {
                successes++;
                totalSteps += solution.getSteps();
                at += solution.getTime();
            }
        }
        at /= successes;
        double ms = at / 1000000.0;
        double as = (double) totalSteps / (double) successes;
        double sr = ((double) successes / (double) iterations) * 100;
        System.out.print("Board Size: " + queens + "\nTotal Iterations: " + iterations + "\nSuccesses: " + successes + 
            "\nSuccess Rate: " + String.format("%.2f", sr) + "%\nAverage Steps: " + String.format("%.2f", as) + "\nAvg. Time Taken (in ms): " + ms + "\n\n");
    }

    private static void runOne(int size, int algType) {
        if (algType == 1) {
            System.out.println("Steepest Ascent Hill Climbing, Size: " + size);
        } else {
            System.out.println("Min Conflicts, Size: " + size);
        }
        int[] board = null;
        Response res = null;
        while (res == null) {
            board = generateRandomBoard(size);
            if (algType == 1) {
                res = steepestHillClimbing(board);
            } else {
                res = minConflictsAlg(board, 10000);
            }
        }
        printCurrentBoard(board);
        System.out.println("Solution:");
        printCurrentBoard(res.getBoard());
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

    private static class Response {
        private int[] board;
        private int steps;
        private long time;

        public Response(int[] board, int steps, long time) {
            this.board = board;
            this.steps = steps;
            this.time = time;
        }

        public int[] getBoard() {
            return this.board;
        }

        public int getSteps() {
            return this.steps;
        }

        public long getTime() {
            return this.time;
        }
    }
}