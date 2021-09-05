package in.stevemann.tictactoe.repositories;

import in.stevemann.tictactoe.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
}
