package Figures;

import Board.Board;
import Board.BoardUtils;

public class Knight extends Figure {

    public Knight(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (super.canMove(row, col, row1, col1)) {
            int rowDiff = Math.abs(row1 - row);
            int colDiff = Math.abs(col1 - col);
            return ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2) && BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1));
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return canMove(row, col, row1, col1);
    }
}