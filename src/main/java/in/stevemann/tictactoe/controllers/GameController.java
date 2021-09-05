package in.stevemann.tictactoe.controllers;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.pojos.GameInputDto;
import in.stevemann.tictactoe.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public Game saveGame(@RequestBody GameInputDto gameInputDto) {
        return gameService.newGame(gameInputDto.getFirstPlayerUsername(), gameInputDto.getSecondPlayerUsername(), gameInputDto.getGridType());
    }

    @DeleteMapping
    public Game deleteGame(@RequestParam String gameCode) {
        return gameService.deleteGame(gameCode);
    }

    @GetMapping
    public Game getGame(@RequestParam String gameCode) {
        return gameService.getGame(gameCode);
    }
}
