package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Move;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.repositories.MoveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoveService {
    private final MoveRepository moveRepository;

    public Move saveMove(Player player, Game game, int row, int col, PieceType pieceType) {
        Move move = new Move();

        move.setPlayer(player);
        move.setGame(game);
        move.setBoardRow(row);
        move.setBoardColumn(col);
        move.setPieceType(pieceType);

        return moveRepository.save(move);
    }
}
