package in.stevemann.tictactoe.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import in.stevemann.tictactoe.entities.base.BaseEntity;
import in.stevemann.tictactoe.enums.GameStatus;
import in.stevemann.tictactoe.enums.GridType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "code")
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
