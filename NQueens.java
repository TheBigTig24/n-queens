public class NQueens {

    public static void main(String[] args) {
        int[][] board = generateRandomBoard(8);
        printCurrentBoard(board);
    }

    // Generation Functions
    private static int[][] generateRandomBoard(int n) {
        int[][] board = new int[n][n];
        for (int i = 0; i < n; i++) {
            int randIdx = (int) (Math.random() * 8);
            for (int j = 0; j < n; j++) {
                if (j == randIdx) {
                    board[j][i] = 1;
                } else {
                    board[j][i] = 0;
                }
            }
        }

        return board;
    }

    private static void printCurrentBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }
}