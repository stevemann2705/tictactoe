package in.stevemann.tictactoe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PieceType {
    X(1), O(2);

    private final int value;

    public PieceType getOpponent() {
        return (X.equals(this)) ? O : X;
    }
}
