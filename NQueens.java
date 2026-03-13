public class NQueens {

    public static void main(String[] args) {
        int[] board = generateRandomBoard(8);
        steepestHillClimbing(board, evalQueens(board));
    }

    // Steepest Hill-Climbing Alg
    private static int[][] steepestHillClimbing(int[] board, int numCorrect) {
        int[] curr = board;
        boolean isDone = false;
        while (!isDone) {
            int[] mostAmazingNeighbor = null;
            int mostExquisiteNeighborEvaluation = evalQueens(curr);
            

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
        return board.length - numIncorrect;
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