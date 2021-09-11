package in.stevemann.tictactoe;

import in.stevemann.tictactoe.entities.Player;
import in.stevemann.tictactoe.pojos.PlayerInputDto;
import in.stevemann.tictactoe.services.PlayerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class PlayerTests {

    @Autowired
    private PlayerService playerService;

    private static Player player;

    @BeforeAll
    static void init() {
        player = new Player();
        player.setName("test name");
        player.setUsername("test");
    }

    @Test
    @Order(1)
    void savePlayer() {
        Player savedPlayer = playerService.newPlayer(player.getName(), player.getUsername());
        assertThat(savedPlayer).isNotNull();
        assertThat(savedPlayer.getUsername()).isEqualTo("test");
    }

    @Test
    @Order(2)
    void savePlayerAgain() {
        assertThrows(ResponseStatusException.class, () -> playerService.newPlayer(player.getName(), player.getUsername()));
    }

    @Test
    @Order(3)
    void findPlayer() {
        Player playerFromDB = playerService.findPlayerByUsername(player.getUsername());
        assertThat(playerFromDB).isNotNull();
        assertThat(playerFromDB.getUsername()).isEqualTo(player.getUsername());
        assertThat(playerFromDB.getName()).isEqualTo(player.getName());
    }

    @Test
    @Order(3)
    void getPlayer() {
        Player playerFromDB = playerService.getPlayerByUsername(player.getUsername());
        assertThat(playerFromDB).isNotNull();
        assertThat(playerFromDB.getUsername()).isEqualTo(player.getUsername());
        assertThat(playerFromDB.getName()).isEqualTo(player.getName());
    }

    @Test
    @Order(4)
    void findRandomPlayer() {
        Player playerFromDB = playerService.findPlayerByUsername(RandomStringUtils.random(10));
        assertThat(playerFromDB).isNull();
    }

    @Test
    @Order(4)
    void getRandomPlayer() {
        assertThrows(ResponseStatusException.class, () -> playerService.getPlayerByUsername(RandomStringUtils.random(10)));
    }

    @Test
    @Order(5)
    void checkPlayerExists() {
        boolean playerExistsByUserName = playerService.playerExistsByUserName(player.getUsername());
        assertThat(playerExistsByUserName).isTrue();
    }

    @Test
    @Order(6)
    void checkRandomPlayerExists() {
        String random = RandomStringUtils.random(10);
        boolean playerExistsByUserName = playerService.playerExistsByUserName(random);
        assertThat(playerExistsByUserName).isFalse();
    }

    @Test
    @Order(7)
    void updatePlayer() {
        Player playerFromDB = playerService.updatePlayer(PlayerInputDto.builder().username(player.getUsername()).name("test name updated").build());
        assertThat(playerFromDB).isNotNull();
        assertThat(playerFromDB.getUsername()).isEqualTo(player.getUsername());
        assertThat(playerFromDB.getName()).isNotEqualTo(player.getName());
        assertThat(playerFromDB.getName()).isEqualTo("test name updated");
    }

    @Test
    @Order(7)
    void updateRandomPlayer() {
        assertThrows(ResponseStatusException.class, () -> playerService.updatePlayer(PlayerInputDto.builder().username(RandomStringUtils.random(10)).name("test name updated").build()));
    }

    @Test
    @Order(8)
    void deletePlayer() {
        Player playerFromDB = playerService.deletePlayer(player.getUsername());
        assertThat(playerFromDB).isNotNull();
        assertThat(playerFromDB.getUsername()).isEqualTo(player.getUsername());
        assertThat(playerFromDB.isEnabled()).isFalse();
    }

    @Test
    @Order(8)
    void deletePlayerAgain() {
        assertThrows(ResponseStatusException.class, () -> playerService.deletePlayer(player.getUsername()));

    }

    @Test
    @Order(8)
    void deleteRandomPlayer() {
        assertThrows(ResponseStatusException.class, () -> playerService.deletePlayer(RandomStringUtils.random(10)));
    }
}
