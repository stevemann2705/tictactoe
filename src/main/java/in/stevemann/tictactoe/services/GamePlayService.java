package in.stevemann.tictactoe.services;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Move;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.utils.PrintBoardUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class GamePlayService {
    private final GameService gameService;
    private final MoveService moveService;
    private final BoardService boardService;

    public void playGame(Board board){
        playGame(board, false);
    }

    public void playGame(Board board, boolean secondPlayerTurn) {
        Scanner userInputReader = new Scanner(System.in);

        Player firstPlayer = board.getGame().getFirstPlayer();
        Player secondPlayer = board.getGame().getSecondPlayer();

        boolean isGameInProgress = true;
        while (isGameInProgress && !boardService.isBoardFull(board)) {
            PrintBoardUtil.printCurrentBoard(board);
            int position = getPosition(userInputReader, secondPlayerTurn);
            // pause game if position is -10
            if (position == -10) {
                gameService.pauseGame(board.getGame());
                return;
            }

            Player turn = (secondPlayerTurn) ? secondPlayer : firstPlayer;

            boolean isValidPosition = moveService.makeMove(turn, board, position);

            if (!isValidPosition) {
                // TODO: Handle this better
                System.out.println("Position entered is already marked as filled. Please choose another position");
                gameService.pauseGame(board.getGame());
                return;
            }
            PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, turn);

            isGameInProgress = gameService.isGameInProgress(board, playerPieceOnBoard, position);

            secondPlayerTurn = !secondPlayerTurn; // change turn
        }

        if (!isGameInProgress) {
            PrintBoardUtil.printCurrentBoard(board);
            System.out.println(" GAME OVER!!!");
            System.out.println(board.getGame().getStatus());
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

    public boolean isSecondPlayerTurn(Game game) {
        Move move = game.getMoves().stream().max(Comparator.comparing(Move::getCreated)).orElse(null); // getting last move made in game
        boolean secondPlayerTurn = false;
        if (move != null && move.getPlayer().getUsername().equals(game.getFirstPlayer().getUsername())) {
            secondPlayerTurn = true;
        }
        return secondPlayerTurn;
    }
}
