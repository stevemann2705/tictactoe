package in.stevemann.tictactoe.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.stevemann.tictactoe.enums.GridType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameInputDto {
    private String firstPlayerUsername;
    private String secondPlayerUsername;
    private GridType gridType;
}
