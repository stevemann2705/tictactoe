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
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

@RestController
@RequestMapping("/gameplay")
@AllArgsConstructor
public class GamePlayController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final BoardService boardService;
    private final GamePlayService gamePlayService;

    @GetMapping("/")
    public void startGame(@RequestParam(required = false) String firstUsername,
                          @RequestParam(required = false) String secondUsername,
                          @RequestParam(required = false) GridType gridType) {
        Player firstPlayer;
        if (firstUsername == null) {
            // TODO: Make this an automated player
            firstPlayer = playerService.newPlayer("Test Player 1", "testplayer1" + new Random().nextInt(100));
        } else {
            firstPlayer = playerService.getPlayerByUsername(firstUsername);
        }


        Player secondPlayer;
        if (secondUsername == null) {
            // TODO: Make this an automated player
            secondPlayer = playerService.newPlayer("Test Player 2", "testplayer2" + new Random().nextInt(100));
        } else {
            secondPlayer = playerService.getPlayerByUsername(secondUsername);
        }

        if (gridType == null) {
            gridType = GridType.X3;
        }

        Game game = gameService.newGame(firstPlayer, secondPlayer, gridType);
        Board board = boardService.newBoard(game);

        PrintBoardUtil.printPositions(board);

       gamePlayService.playGame(board);
    }

    @GetMapping("/{gameCode}")
    public void resumeGame(@PathVariable String gameCode) {
        Game game = gameService.resumeGame(gameCode);

        Board board = boardService.getBoard(game);

        boolean secondPlayerTurn = gamePlayService.isSecondPlayerTurn(game);

        PrintBoardUtil.printPositions(board);
        gamePlayService.playGame(board, secondPlayerTurn);
    }
}
