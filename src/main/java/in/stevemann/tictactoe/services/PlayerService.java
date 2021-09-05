package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.entities.QPlayer;
import in.stevemann.tictactoe.pojos.PlayerInputDto;
import in.stevemann.tictactoe.repositories.PlayerRepository;
import in.stevemann.tictactoe.utils.CopyNonNullUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Player getPlayerByUsername(String username) {
        Player player = findPlayerByUsername(username);
        if (player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found.");
        return player;
    }

    public boolean playerExistsByUserName(String username) {
        return playerRepository.exists(
                QPlayer.player.enabled.isTrue()
                        .and(QPlayer.player.username.eq(username))
        );
    }

    public Player newPlayer(String name, String username) {
        if (playerExistsByUserName(username)) throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.");

        Player player = new Player();
        player.setName(name);
        player.setUsername(username);
        return playerRepository.save(player);
    }

    public Player deletePlayer(String username) {
        Player player = getPlayerByUsername(username);
        player.setEnabled(false);
        return playerRepository.save(player);
    }

    public Player updatePlayer(PlayerInputDto playerInputDto) {
        Player player = getPlayerByUsername(playerInputDto.getUsername());
        CopyNonNullUtil.copyNonNullProperties(playerInputDto, player);
        return playerRepository.save(player);
    }
}
