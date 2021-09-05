package in.stevemann.tictactoe.entities;

import in.stevemann.tictactoe.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Player extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
}
