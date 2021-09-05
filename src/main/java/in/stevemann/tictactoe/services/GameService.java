package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.entities.QGame;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
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
}
