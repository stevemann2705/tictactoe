package in.stevemann.tictactoe.controllers;

import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.pojos.PlayerInputDto;
import in.stevemann.tictactoe.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
@AllArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    public Player savePlayer(@RequestBody PlayerInputDto playerInputDto) {
        return playerService.newPlayer(playerInputDto.getName(), playerInputDto.getUsername());
    }

    @PutMapping
    public Player updatePlayer(@RequestBody PlayerInputDto playerInputDto) {
        return playerService.updatePlayer(playerInputDto);
    }

    @DeleteMapping
    public Player deletePlayer(@RequestParam String username) {
        return playerService.deletePlayer(username);
    }

    @GetMapping
    public Player getPlayer(@RequestParam String username) {
        return playerService.getPlayerByUsername(username);
    }
}
