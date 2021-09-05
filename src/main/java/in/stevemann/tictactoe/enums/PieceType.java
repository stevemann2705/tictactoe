package in.stevemann.tictactoe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PieceType {
    X(1), O(2);

    private int value;
}
