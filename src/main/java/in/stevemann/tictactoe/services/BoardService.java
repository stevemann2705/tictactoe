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
