package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Move;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.repositories.MoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoveService {
    private final MoveRepository moveRepository;
    private final GameService gameService;

    public Move saveMove(Player player, Board board, int row, int col, PieceType pieceType) {
        Move move = new Move();

        move.setPlayer(player);
        move.setGame(board.getGame());
        move.setBoardRow(row);
        move.setBoardColumn(col);
        move.setPieceType(pieceType);

        PieceType wonBy = gameService.checkGameOver(board, pieceType, row, col);

        if (wonBy != null) {
            board.setGame(gameService.updateGameStatus(board.getGame(), wonBy));
        }

        return moveRepository.save(move);
    }

    public boolean makeMove(Player player, Board board, int position) {
        int row = (position - 1) / board.getGame().getGridType().getSize();
        int col = (position - (row * board.getGame().getGridType().getSize())) - 1;
        return makeMove(player, board, row, col);
    }

    public boolean makeMove(Player player, Board board, int row, int col) {
        PieceType playerPieceOnBoard = getPlayerPieceOnBoard(board, player);
        return makeMove(player, board, row, col, playerPieceOnBoard, true);
    }

    public boolean makeMove(Player player, Board board, int row, int col, PieceType pieceType, boolean save) {
        if (board.getBoard()[row][col] == 0) {
            if (PieceType.X.equals(pieceType)) board.getBoard()[row][col] = 1;
            if (PieceType.Y.equals(pieceType)) board.getBoard()[row][col] = 2;
            if (save) saveMove(player, board, row, col, pieceType);
            return true; // true means move was made
        }
        return false; // false means move was not made because position already take. // TODO: Will need error handling later
    }

    public PieceType getPlayerPieceOnBoard(Board board, Player player) {
        boolean isFirstPlayer = board.getGame().getFirstPlayer().getUsername().equals(player.getUsername());
        if (isFirstPlayer) {
            return PieceType.X;
        } else {
            return PieceType.Y;
        }
    }
}
