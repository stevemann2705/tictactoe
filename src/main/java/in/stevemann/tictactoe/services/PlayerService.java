package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.entities.QPlayer;
import in.stevemann.tictactoe.repositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player findPlayerByUsername(String username) {
        return playerRepository.findOne(
                QPlayer.player.enabled.isTrue()
                        .and(QPlayer.player.username.eq(username))
        ).orElse(null);
    }

    public boolean playerExistsByUserName(String username) {
        return playerRepository.exists(
                QPlayer.player.enabled.isTrue()
                        .and(QPlayer.player.username.eq(username))
        );
    }

    public Player newPlayer(String name, String username) {
        if (playerExistsByUserName(username)) {
            throw new RuntimeException("Username already exists.");
        }

        Player player = new Player();
        player.setName(name);
        player.setUsername(username);
        return playerRepository.save(player);
    }
}
