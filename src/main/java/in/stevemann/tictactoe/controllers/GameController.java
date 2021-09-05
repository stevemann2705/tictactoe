package in.stevemann.tictactoe.controllers;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.services.BoardService;
import in.stevemann.tictactoe.services.GameService;
import in.stevemann.tictactoe.services.MoveService;
import in.stevemann.tictactoe.services.PlayerService;
import in.stevemann.tictactoe.utils.PrintBoardUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Scanner;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final BoardService boardService;
    private final MoveService moveService;

    @GetMapping("/")
    public void startGame() {
        Scanner userInputReader = new Scanner(System.in);

        int random = new Random().nextInt(100);
        Player firstPlayer = playerService.newPlayer("Steve Mann", "stevemann" + random);
        Player secondPlayer = playerService.newPlayer("Test Player", "testplayer" + random);

        Game game = gameService.newGame(firstPlayer, secondPlayer, GridType.X3);
        Board board = boardService.newBoard(game);

        PrintBoardUtil.printPositions(board);

        boolean isGameInProgress = true;
        boolean secondPlayerTurn = false;
        while (isGameInProgress && !boardService.isBoardFull(board)) {
            PrintBoardUtil.printCurrentBoard(board);
            int position = getPosition(userInputReader, secondPlayerTurn);
            // pause game if position is -10
            if (position == -10) {
                gameService.pauseGame(game);
                return;
            }

            Player turn = (secondPlayerTurn) ? secondPlayer : firstPlayer;

            boolean isValidPosition = moveService.makeMove(turn, board, position);

            if (!isValidPosition) {
                System.out.println("Position entered is already marked as filled. Please choose another position");
                System.out.println("Pausing the game now. You can resume the game using game code: " + game.getCode());
                gameService.pauseGame(game);
                return;
            }
            isGameInProgress = board.getGame().getStatus().equals(GameStatus.IN_PROGRESS);

            secondPlayerTurn = !secondPlayerTurn; // change turn
        }

        if (!isGameInProgress) {
            PrintBoardUtil.printCurrentBoard(board);
            System.out.println(" GAME OVER!!!");
            System.out.println(game.getStatus());
        } else {
            PrintBoardUtil.printCurrentBoard(board);
        }
    }

    private int getPosition(Scanner userInputReader, Boolean secondPlayerTurn) throws NumberFormatException {
        int position;
        System.out.println(((secondPlayerTurn) ? "Second Player. " : "First Player. ") + "Please enter a position or enter -10 to pause:");
        try {
            position = Integer.parseInt(userInputReader.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input. Try Again.");
            return getPosition(userInputReader, secondPlayerTurn);
        }
        return position;
    }
}
