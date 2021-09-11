package in.stevemann.tictactoe;

import in.stevemann.tictactoe.entities.Game;
import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
import in.stevemann.tictactoe.enums.PieceType;
import in.stevemann.tictactoe.pojos.Board;
import in.stevemann.tictactoe.services.BoardService;
import in.stevemann.tictactoe.services.GameService;
import in.stevemann.tictactoe.services.MoveService;
import in.stevemann.tictactoe.services.PlayerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class GameTests {
    private static Player firstPlayer;
    private static Player secondPlayer;
    private static GridType gridType;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private GameService gameService;

    private static Game savedGame;

    @BeforeAll
    static void init() {
        firstPlayer = new Player();
        firstPlayer.setName("first player");
        firstPlayer.setUsername("firstPlayer");

        secondPlayer = new Player();
        secondPlayer.setName("second player");
        secondPlayer.setUsername("secondPlayer");

        gridType = GridType.X3;
    }

    @Test
    @Order(1)
    void newGameWithPlayerObjects() {
        firstPlayer = playerService.newPlayer(firstPlayer.getName(), firstPlayer.getUsername());
        secondPlayer = playerService.newPlayer(secondPlayer.getName(), secondPlayer.getUsername());

        Game savedGame = gameService.newGame(firstPlayer, secondPlayer, gridType);
        assertThat(savedGame).isNotNull();

        // Check first player
        assertThat(savedGame.getFirstPlayer()).isNotNull();
        assertThat(savedGame.getFirstPlayer().getUsername()).isEqualTo(firstPlayer.getUsername());
        assertThat(savedGame.getFirstPlayer().getName()).isEqualTo(firstPlayer.getName());

        // Check second player
        assertThat(savedGame.getSecondPlayer()).isNotNull();
        assertThat(savedGame.getSecondPlayer().getUsername()).isEqualTo(secondPlayer.getUsername());
        assertThat(savedGame.getSecondPlayer().getName()).isEqualTo(secondPlayer.getName());

        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(savedGame.getGridType()).isEqualTo(gridType);
    }

    @Test
    @Order(2)
    void newGameWithUsernames() {
        Game savedGame = gameService.newGame(firstPlayer.getUsername(), secondPlayer.getUsername(), gridType);
        assertThat(savedGame).isNotNull();

        // Check first player
        assertThat(savedGame.getFirstPlayer()).isNotNull();
        assertThat(savedGame.getFirstPlayer().getUsername()).isEqualTo(firstPlayer.getUsername());
        assertThat(savedGame.getFirstPlayer().getName()).isEqualTo(firstPlayer.getName());

        // Check second player
        assertThat(savedGame.getSecondPlayer()).isNotNull();
        assertThat(savedGame.getSecondPlayer().getUsername()).isEqualTo(secondPlayer.getUsername());
        assertThat(savedGame.getSecondPlayer().getName()).isEqualTo(secondPlayer.getName());

        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(savedGame.getGridType()).isEqualTo(gridType);

        GameTests.savedGame = savedGame;
    }

    @Test
    @Order(3)
    void findGame() {
        Game gameFound = gameService.findGame(savedGame.getCode());
        assertThat(gameFound).isNotNull();
        // Check first player
        assertThat(gameFound.getFirstPlayer()).isNotNull();
        assertThat(gameFound.getFirstPlayer().getUsername()).isEqualTo(firstPlayer.getUsername());
        assertThat(gameFound.getFirstPlayer().getName()).isEqualTo(firstPlayer.getName());

        // Check second player
        assertThat(gameFound.getSecondPlayer()).isNotNull();
        assertThat(gameFound.getSecondPlayer().getUsername()).isEqualTo(secondPlayer.getUsername());
        assertThat(gameFound.getSecondPlayer().getName()).isEqualTo(secondPlayer.getName());

        assertThat(gameFound.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(gameFound.getGridType()).isEqualTo(gridType);
    }

    @Test
    @Order(3)
    void findRandomGame() {
        Game gameFound = gameService.findGame(UUID.randomUUID().toString());
        assertThat(gameFound).isNull();
    }

    @Test
    @Order(4)
    void getGame() {
        Game gameFound = gameService.getGame(savedGame.getCode());
        assertThat(gameFound).isNotNull();
        // Check first player
        assertThat(gameFound.getFirstPlayer()).isNotNull();
        assertThat(gameFound.getFirstPlayer().getUsername()).isEqualTo(firstPlayer.getUsername());
        assertThat(gameFound.getFirstPlayer().getName()).isEqualTo(firstPlayer.getName());

        // Check second player
        assertThat(gameFound.getSecondPlayer()).isNotNull();
        assertThat(gameFound.getSecondPlayer().getUsername()).isEqualTo(secondPlayer.getUsername());
        assertThat(gameFound.getSecondPlayer().getName()).isEqualTo(secondPlayer.getName());

        assertThat(gameFound.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(gameFound.getGridType()).isEqualTo(gridType);
    }

    @Test
    @Order(4)
    void getRandomGame() {
        assertThrows(ResponseStatusException.class, () -> gameService.getGame(UUID.randomUUID().toString()));
    }

    @Test
    @Order(5)
    void makeGameMoves() {
        Board board = boardService.getBoard(savedGame);

        moveService.makeMove(firstPlayer, board, 1);
        moveService.makeMove(secondPlayer, board, 2);
        moveService.makeMove(firstPlayer, board, 5);

        assertThat(savedGame.getMoves()).isNotNull();
        assertThat(savedGame.getMoves().size()).isEqualTo(3);
    }

    @Test
    @Order(6)
    void pauseGame() {
        savedGame = gameService.pauseGame(savedGame);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.PAUSED);
    }

    @Test
    @Order(7)
    void pauseGameAgain() {
        assertThrows(ResponseStatusException.class, () -> gameService.pauseGame(savedGame));
    }

    @Test
    @Order(7)
    void pauseNullGame() {
        assertThrows(ResponseStatusException.class, () -> gameService.pauseGame(null));
    }

    @Test
    @Order(7)
    void pauseRandomGame() {
        assertThrows(ResponseStatusException.class, () -> gameService.pauseGameByCode(UUID.randomUUID().toString()));
    }

    @Test
    @Order(8)
    void resumeGame() {
        savedGame = gameService.resumeGame(savedGame);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    @Order(9)
    void pauseGameAfterResume() {
        savedGame = gameService.pauseGame(savedGame);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.PAUSED);
    }

    @Test
    @Order(9)
    void resumeGameByGameCode() {
        savedGame = gameService.resumeGame(savedGame.getCode());
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    @Order(9)
    void resumeRandomGame() {
        assertThrows(ResponseStatusException.class, () -> gameService.resumeGame(UUID.randomUUID().toString()));
    }

    @Test
    @Order(10)
    void isGameInProgress() {
        Board board = boardService.getBoard(savedGame);
        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, firstPlayer);
        boolean gameInProgress = gameService.isGameInProgress(board, playerPieceOnBoard, 1);
        assertThat(gameInProgress).isTrue();
    }

    @Test
    @Order(11)
    void completeGame() {
        Board board = boardService.getBoard(savedGame);

        moveService.makeMove(secondPlayer, board, 4);
        moveService.makeMove(firstPlayer, board, 9);

        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, firstPlayer);
        gameService.isGameInProgress(board, playerPieceOnBoard, 9);

        assertThat(savedGame.getMoves()).isNotNull();
        assertThat(savedGame.getMoves().size()).isEqualTo(5);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON);
    }

    @Test
    @Order(12)
    void isGameStillInProgress() {
        Board board = boardService.getBoard(savedGame);
        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, firstPlayer);
        boolean gameInProgress = gameService.isGameInProgress(board, playerPieceOnBoard, 9);
        assertThat(gameInProgress).isFalse();
    }

    @Test
    @Order(13)
    void deleteGame() {
        savedGame = gameService.deleteGame(savedGame.getCode());
        assertThat(savedGame.isEnabled()).isFalse();
    }

    @Test
    @Order(14)
    void deleteGameAgain() {
        assertThrows(ResponseStatusException.class, () -> gameService.deleteGame(savedGame.getCode()));
    }

    @Test
    @Order(14)
    void deleteRandomGame() {
        assertThrows(ResponseStatusException.class, () -> gameService.deleteGame(UUID.randomUUID().toString()));
    }

    @Test
    @Order(15)
    void newGameForSecondPlayer() {
        Game savedGame = gameService.newGame(firstPlayer.getUsername(), secondPlayer.getUsername(), gridType);
        assertThat(savedGame).isNotNull();
        GameTests.savedGame = savedGame;
    }

    @Test
    @Order(16)
    void makeSecondPlayerWin() {
        Board board = boardService.getBoard(savedGame);

        moveService.makeMove(firstPlayer, board, 4);
        moveService.makeMove(secondPlayer, board, 1);
        moveService.makeMove(firstPlayer, board, 7);
        moveService.makeMove(secondPlayer, board, 2);
        moveService.makeMove(firstPlayer, board, 6);
        moveService.makeMove(secondPlayer, board, 3);

        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, secondPlayer);
        gameService.isGameInProgress(board, playerPieceOnBoard, 3);

        assertThat(savedGame.getMoves()).isNotNull();
        assertThat(savedGame.getMoves().size()).isEqualTo(6);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON);
    }

    @Test
    @Order(17)
    void newGameForDraw() {
        Game savedGame = gameService.newGame(firstPlayer.getUsername(), secondPlayer.getUsername(), gridType);
        assertThat(savedGame).isNotNull();
        GameTests.savedGame = savedGame;
    }

    @Test
    @Order(18)
    void checkDraw() {
        Board board = boardService.newBoard(savedGame);

        moveService.makeMove(firstPlayer, board, 1);
        moveService.makeMove(secondPlayer, board, 3);
        moveService.makeMove(firstPlayer, board, 2);
        moveService.makeMove(secondPlayer, board, 4);
        moveService.makeMove(firstPlayer, board, 6);
        moveService.makeMove(secondPlayer, board, 5);
        moveService.makeMove(firstPlayer, board, 7);
        moveService.makeMove(secondPlayer, board, 8);

        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, secondPlayer);
        gameService.isGameInProgress(board, playerPieceOnBoard, 8);

        assertThat(savedGame.getMoves()).isNotNull();
        assertThat(savedGame.getMoves().size()).isEqualTo(8);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.DRAW);
    }

    @Test
    @Order(20)
    void newGameForDuplicateMove() {
        Game savedGame = gameService.newGame(firstPlayer.getUsername(), secondPlayer.getUsername(), gridType);
        assertThat(savedGame).isNotNull();
        GameTests.savedGame = savedGame;
    }

    @Test
    @Order(21)
    void duplicateMove() {
        Board board = boardService.getBoard(savedGame);

        moveService.makeMove(firstPlayer, board, 1);
        boolean wasMoveSuccessful = moveService.makeMove(secondPlayer, board, 1);
        assertThat(wasMoveSuccessful).isFalse();
    }

    @Test
    @Order(22)
    void newGameColumnWin() {
        Game savedGame = gameService.newGame(firstPlayer.getUsername(), secondPlayer.getUsername(), gridType);
        assertThat(savedGame).isNotNull();
        GameTests.savedGame = savedGame;
    }

    @Test
    @Order(23)
    void columnWin() {
        Board board = boardService.getBoard(savedGame);
        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, firstPlayer);

        moveService.makeMove(firstPlayer, board, 7);
        gameService.isGameInProgress(board, playerPieceOnBoard, 7);

        moveService.makeMove(secondPlayer, board, 5);
        moveService.makeMove(firstPlayer, board, 4);
        moveService.makeMove(secondPlayer, board, 3);
        moveService.makeMove(firstPlayer, board, 1);

        gameService.isGameInProgress(board, playerPieceOnBoard, 1);

        assertThat(savedGame.getMoves()).isNotNull();
        assertThat(savedGame.getMoves().size()).isEqualTo(5);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON);
    }

    @Test
    @Order(24)
    void newGameAntiDiagonalWin() {
        Game savedGame = gameService.newGame(firstPlayer.getUsername(), secondPlayer.getUsername(), gridType);
        assertThat(savedGame).isNotNull();
        GameTests.savedGame = savedGame;
    }

    @Test
    @Order(25)
    void antiDiagonalWin() {
        Board board = boardService.getBoard(savedGame);

        moveService.makeMove(firstPlayer, board, 7);
        moveService.makeMove(secondPlayer, board, 2);
        moveService.makeMove(firstPlayer, board, 5);
        moveService.makeMove(secondPlayer, board, 1);
        moveService.makeMove(firstPlayer, board, 3);

        PieceType playerPieceOnBoard = moveService.getPlayerPieceOnBoard(board, firstPlayer);
        gameService.isGameInProgress(board, playerPieceOnBoard, 3);

        assertThat(savedGame.getMoves()).isNotNull();
        assertThat(savedGame.getMoves().size()).isEqualTo(5);
        assertThat(savedGame.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON);
    }
}
