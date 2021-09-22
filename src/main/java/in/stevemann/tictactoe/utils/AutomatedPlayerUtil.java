package in.stevemann.tictactoe.utils;

import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.pojos.Board;

// Using Minimax Algorithm
// https://en.wikipedia.org/wiki/Minimax
// Source: https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
public class AutomatedPlayerUtil {

    static Boolean isMovesLeft(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return true;
        return false;
    }

    static int evaluate(Board board, PieceType playerPiece) {
        PieceType opponentPiece = playerPiece.getOpponent();
        int n = board.getGame().getGridType().getSize();
        // Checking for Rows for X or O victory.
        for (int boardRow = 0; boardRow < n; boardRow++) {
            if (board.getBoard()[boardRow][0] == board.getBoard()[boardRow][1] &&
                    board.getBoard()[boardRow][1] == board.getBoard()[boardRow][2]) {
                if (board.getBoard()[boardRow][0] == playerPiece.getValue()) return +10;
                else if (board.getBoard()[boardRow][0] == opponentPiece.getValue()) return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int boardColumn = 0; boardColumn < 3; boardColumn++) {
            if (board.getBoard()[0][boardColumn] == board.getBoard()[1][boardColumn] &&
                    board.getBoard()[1][boardColumn] == board.getBoard()[2][boardColumn]) {
                if (board.getBoard()[0][boardColumn] == playerPiece.getValue()) return +10;
                else if (board.getBoard()[0][boardColumn] == opponentPiece.getValue()) return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (board.getBoard()[0][0] == board.getBoard()[1][1] && board.getBoard()[1][1] == board.getBoard()[2][2]) {
            if (board.getBoard()[0][0] == playerPiece.getValue()) return +10;
            else if (board.getBoard()[0][0] == opponentPiece.getValue()) return -10;
        }

        // Checking for Anti Diagonals for X or O victory.
        if (board.getBoard()[0][2] == board.getBoard()[1][1] && board.getBoard()[1][1] == board.getBoard()[2][0]) {
            if (board.getBoard()[0][2] == playerPiece.getValue())
                return +10;
            else if (board.getBoard()[0][2] == opponentPiece.getValue())
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    static int minimax(Board board, PieceType playerPiece, int depth, Boolean isMax) {
        int score = evaluate(board, playerPiece);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (!isMovesLeft(board.getBoard()))
            return 0;

        // If this maximizer's move
        int best;
        if (isMax) {
            best = -1000;

            // Traverse all cells
            for (int boardRow = 0; boardRow < 3; boardRow++) {
                for (int boardColumn = 0; boardColumn < 3; boardColumn++) {
                    // Check if cell is empty
                    if (board.getBoard()[boardRow][boardColumn] == 0) {
                        // Make the move
                        board.getBoard()[boardRow][boardColumn] = playerPiece.getValue();

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board, playerPiece, depth + 1, false));

                        // Undo the move
                        board.getBoard()[boardRow][boardColumn] = 0;
                    }
                }
            }
        }

        // If this minimizer's move
        else {
            best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board.getBoard()[i][j] == 0) {
                        // Make the move
                        board.getBoard()[i][j] = playerPiece.getOpponent().getValue();

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board, playerPiece,depth + 1, true));

                        // Undo the move
                        board.getBoard()[i][j] = 0;
                    }
                }
            }
        }
        return best;
    }

    // This will return the best possible
// move for the player
    public static int findBestMove(Board board, PieceType playerPiece) {
        int bestVal = -1000;
        int row = -1;
        int col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board.getBoard()[i][j] == 0) {
                    // Make the move
                    board.getBoard()[i][j] = playerPiece.getValue();

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, playerPiece, 0, false);

                    // Undo the move
                    board.getBoard()[i][j] = 0;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        row = i;
                        col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return (col + 1) + (row * board.getGame().getGridType().getSize());
    }
}
