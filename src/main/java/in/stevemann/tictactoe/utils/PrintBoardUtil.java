package in.stevemann.tictactoe.utils;

import in.stevemann.tictactoe.pojos.Board;

public class PrintBoardUtil {
    public static void printPositions(Board board) {
        int gridSize = board.getGame().getGridType().getSize();
        for(int rowNumber = 0; rowNumber < gridSize * 2 + 1; rowNumber++) {
            printRow(board, gridSize, rowNumber, true);
        }
    }

    public static void printCurrentBoard(Board board) {
        int gridSize = board.getGame().getGridType().getSize();
        for(int rowNumber = 0; rowNumber < gridSize * 2 + 1; rowNumber++) {
            printRow(board, gridSize, rowNumber, false);
        }
    }

    private static void printRow(Board board, int gridSize, int row, boolean printPositions) {
        int colStartNumber = (row / 2) * gridSize + 1;
        if (row % 2 == 0) {
            for (int col = 0; col < gridSize; col++) {
                System.out.print(" + ");
                System.out.print("-");
            }
        } else {
            for (int j = colStartNumber; j < colStartNumber + gridSize; j++) {
                System.out.print(" | ");
                if (printPositions) {
                    System.out.print(j);
                } else {
                    if (board.getBoard()[row][j-colStartNumber] == 0) System.out.print(" ");
                    if (board.getBoard()[row][j-colStartNumber] == 1) System.out.print("X");
                    if (board.getBoard()[row][j-colStartNumber] == 2) System.out.print("O");
                }
            }
        }
        if (row % 2 == 0) {
            System.out.println(" +");
        } else {
            System.out.println(" |");
        }
    }
}
