package Figures;

import Board.*;

public class Queen extends Figure {

    public Queen(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (row == row1 || col == col1) {
            return BoardUtils.isPathClear(Board.getBoard(), row, col, row1, col1) &&
                    BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1);
        }
        if (Math.abs(row1 - row) == Math.abs(col1 - col)) {
            return BoardUtils.isPathClear(Board.getBoard(), row, col, row1, col1) &&
                    BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1);
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return canMove(row, col, row1, col1);
    }
}