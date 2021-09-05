package in.stevemann.tictactoe.pojos;

import in.stevemann.tictactoe.entities.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    // 0 means empty
    // 1 means First Player (X)
    // 2 means Second Player (O)
    private int[][] board ;
    private Game game;

    public Board(Game game) {
        this.game = game;
        board = new int[game.getGridType().getSize()][game.getGridType().getSize()];
    }
}
