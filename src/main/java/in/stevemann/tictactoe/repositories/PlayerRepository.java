package in.stevemann.tictactoe.repositories;

import in.stevemann.tictactoe.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, QuerydslPredicateExecutor<Player> {
}
