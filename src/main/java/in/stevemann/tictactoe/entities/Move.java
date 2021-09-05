package in.stevemann.tictactoe.entities;

import in.stevemann.tictactoe.entities.base.BaseEntity;
import in.stevemann.tictactoe.enums.PieceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"player_id", "game_id", "boardRow", "boardColumn"})
})
public class Move extends BaseEntity {
    @ManyToOne
    private Player player;

    @ManyToOne
    private Game game;

    @Column(nullable = false)
    private Integer boardRow;

    @Column(nullable = false)
    private Integer boardColumn;

    @Enumerated(EnumType.STRING)
    private PieceType pieceType;
}
