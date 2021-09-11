package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Move;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.GridType;
import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.utils.PrintBoardUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class GamePlayService {
    private final GameService gameService;
    private final MoveService moveService;

    public void playGame(Board board){
        playGame(board, false);
    }

    public void playGame(Board board, boolean secondPlayerTurn) {
        Scanner userInputReader = new Scanner(System.in);

        Player firstPlayer = board.getGame().getFirstPlayer();
        Player secondPlayer = board.getGame().getSecondPlayer();

        boolean isGameInProgress = true;
        while (isGameInProgress) {
            PrintBoardUtil.printCurrentBoard(board);
            Player turnPlayer = (secondPlayerTurn) ? secondPlayer : firstPlayer;

            int position;
            if(turnPlayer.isAutomated()) {
                position = moveService.getRandomEmptyPositionOnBoard(board);
            } else {
                position = getPosition(userInputReader, secondPlayerTurn, board.getGame().getGridType());
            }
            // pause game if position is -10
            if (position == -10) {
                gameService.pauseGame(board.getGame());
                return;
            }

            boolean isValidPosition = moveService.makeMove(turnPlayer, board, position);

            if (!isValidPosition) {
                System.out.println("Position entered is already marked as filled. Please choose another position");
                playGame(board, secondPlayerTurn);
                return;
            }
            PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, turnPlayer);

            isGameInProgress = gameService.isGameInProgress(board, playerPieceOnBoard, position);

            secondPlayerTurn = !secondPlayerTurn; // change turn
        }

        PrintBoardUtil.printCurrentBoard(board);
        System.out.println(" GAME OVER!!!");
        System.out.println(board.getGame().getStatus());
    }

    private int getPosition(Scanner userInputReader, Boolean secondPlayerTurn, GridType gridType) throws NumberFormatException {
        int position;
        System.out.println(((secondPlayerTurn) ? "Second Player. " : "First Player. ") + "Please enter a position or enter -10 to pause:");
        try {
            position = Integer.parseInt(userInputReader.nextLine());
        } catch (NumberFormatException e) {
            return retryInput(userInputReader, secondPlayerTurn, gridType);
        }

        if (position > gridType.getSize() * gridType.getSize()) {
            return retryInput(userInputReader, secondPlayerTurn, gridType);
        }

        return position;
    }

    private int retryInput(Scanner userInputReader, Boolean secondPlayerTurn, GridType gridType) {
        System.out.println("Invalid Input. Try Again.");
        return getPosition(userInputReader, secondPlayerTurn, gridType);
    }

    public boolean isSecondPlayerTurn(Game game) {
        Move move = game.getMoves().stream().max(Comparator.comparing(Move::getCreated)).orElse(null); // getting last move made in game
        boolean secondPlayerTurn = false;
        if (move != null && move.getPlayer().getUsername().equals(game.getFirstPlayer().getUsername())) {
            secondPlayerTurn = true;
        }
        return secondPlayerTurn;
    }
}
