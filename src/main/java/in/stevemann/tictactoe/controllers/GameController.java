package in.stevemann.tictactoe.controllers;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Move;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.services.*;
import in.stevemann.tictactoe.utils.PrintBoardUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final BoardService boardService;
    private final GamePlayService gamePlayService;

    @GetMapping("/")
    public void startGame() {
        int random = new Random().nextInt(100);
        Player firstPlayer = playerService.newPlayer("Steve Mann", "stevemann" + random);
        Player secondPlayer = playerService.newPlayer("Test Player", "testplayer" + random);

        Game game = gameService.newGame(firstPlayer, secondPlayer, GridType.X3);
        Board board = boardService.newBoard(game);

        PrintBoardUtil.printPositions(board);

       gamePlayService.playGame(board);
    }

    @GetMapping("/resume/{gameCode}")
    public void resumeGame(@PathVariable String gameCode) {
        Game game = gameService.resumeGame(gameCode);

        Board board = boardService.getBoard(game);

        boolean secondPlayerTurn = gamePlayService.isSecondPlayerTurn(game);

        PrintBoardUtil.printPositions(board);
        gamePlayService.playGame(board, secondPlayerTurn);
    }
}
