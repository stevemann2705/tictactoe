package in.stevemann.tictactoe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GridType {
    X3(3), X4(4), X5(5);

    private int size;
}
