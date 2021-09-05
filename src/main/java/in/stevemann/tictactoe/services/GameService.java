package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.entities.QGame;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.repositories.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Game findGame(String gameCode) {
        return gameRepository.findOne(
                QGame.game.enabled.isTrue()
                        .and(QGame.game.code.eq(gameCode))
        ).orElse(null);
    }

    public Game newGame(Player firstPlayer, Player secondPlayer, GridType gridType) {
        Game game = new Game();

        game.setFirstPlayer(firstPlayer);
        game.setSecondPlayer(secondPlayer);
        game.setGridType(gridType);
        game.setStatus(GameStatus.IN_PROGRESS);

        return gameRepository.save(game);
    }

    public Game updateGameStatus(Game game, PieceType pieceType) {
        if (PieceType.X.equals(pieceType)) {
            game.setStatus(GameStatus.FIRST_PLAYER_WON);
        }
        if (PieceType.Y.equals(pieceType)) {
            game.setStatus(GameStatus.SECOND_PLAYER_WON);
        }
        return gameRepository.save(game);
    }

    // Returns PieceType that won. Else returns null
    public PieceType checkGameOver(Board board, PieceType pieceType, int row, int col) {
        int s = pieceType.getValue();

        //check column for a win
        int n = board.getGame().getGridType().getSize();
        for (int boardColumn = 0; boardColumn < n; boardColumn++) {
            if (board.getBoard()[row][boardColumn] != s)
                break;
            if (boardColumn == n - 1) {
                return pieceType;
            }
        }

        //check row for a win
        for (int boardRow = 0; boardRow < n; boardRow++) {
            if (board.getBoard()[boardRow][col] != s)
                break;
            if (boardRow == n - 1) {
                return pieceType;
            }
        }

        //check diagonal for a win
        if (row == col) {
            for (int diag = 0; diag < n; diag++) {
                if (board.getBoard()[diag][diag] != s)
                    break;
                if (diag == n - 1) {
                    return pieceType;
                }
            }
        }

        //check anti diagonal for a win
        if (row + col == n - 1) {
            for (int diag = 0; diag < n; diag++) {
                if (board.getBoard()[diag][(n - 1) - diag] != s)
                    break;
                if (diag == n - 1) {
                    return pieceType;
                }
            }
        }

        return null;
    }

    public Game pauseGame(Game game) {
        if (game == null || !game.getStatus().equals(GameStatus.IN_PROGRESS))
            throw new RuntimeException("Game not found or game already completed.");

        game.setStatus(GameStatus.PAUSED);
        return gameRepository.save(game);
    }

    public Game resumeGame(String gameCode) {
        return resumeGame(findGame(gameCode));
    }

    public Game resumeGame(Game game) {
        if (game == null || !game.getStatus().equals(GameStatus.PAUSED))
            throw new RuntimeException("Game not found or game not paused.");

        game.setStatus(GameStatus.IN_PROGRESS);
        return gameRepository.save(game);
    }
}
