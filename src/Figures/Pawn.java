package Figures;

import Board.*;

public class Pawn extends Figure {
    public Pawn(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (this.isFirstMove) {
            if (((((row + 2 == row1) || (row + 1 == row1)) && this.getColor() == 'w') ||
                    (((row - 2 == row1) || (row - 1 == row1)) && this.getColor() == 'b')) && (col == col1)) {
                if (Board.getBoard()[row1][col1] == null && BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1)) {
                    this.isFirstMove = false;
                    return true;
                }
            }
        } else {
            if (((row + 1 == row1) && this.getColor() == 'w') || ((row - 1 == row1) && this.getColor() == 'b')) {
                return (col == col1) && BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1);
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        if (Math.abs(row - row1) == 1 && Math.abs(col - col1) == 1) {
            return BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1);
        }
        return false;
    }
}