package in.stevemann.tictactoe.entities;

import in.stevemann.tictactoe.entities.base.BaseEntity;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
import in.stevemann.tictactoe.enums.PieceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Game extends BaseEntity {
    @ManyToOne
    private Player firstPlayer;

    @ManyToOne
    private Player secondPlayer;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Enumerated(EnumType.STRING)
    private GridType gridType;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Move> moves;
}
