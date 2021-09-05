package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Move;
import in.stevemann.tictactoe.pojos.Board;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final MoveService moveService;

    public void printPositions(Board board) {
        int gridSize = board.getGame().getGridType().getSize();
        for(int rowNumber = 0; rowNumber < gridSize * 2 + 1; rowNumber++) {
            printRow(gridSize, rowNumber);
        }
    }

    private void printRow(int gridSize, int row) {
        int colStartNumber = (row / 2) * gridSize + 1;
        if (row % 2 == 0) {
            for (int col = 0; col < gridSize; col++) {
                System.out.print(" + ");
                System.out.print("-");
            }
        } else {
            for (int j = colStartNumber; j < colStartNumber+ gridSize; j++) {
                System.out.print(" | ");
                System.out.print(j);
            }
        }
        if (row % 2 == 0) {
            System.out.println(" +");
        } else {
            System.out.println(" |");
        }
    }

    public Board newBoard(Game game) {
        return new Board(game);
    }

    // Get board from an existing game. This will be useful when we pause and resume a game
    public Board getBoard(Game game) {
        Board board = new Board(game);
        List<Move> moves = game.getMoves();
        for (Move move : moves) {
            moveService.makeMove(move.getPlayer(), board, move.getBoardRow(), move.getBoardColumn(), move.getPieceType());
        }
        return board;
    }
}
