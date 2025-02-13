package Figures;
import Board.Board;
import Board.BoardUtils;

public class King extends Figure {

    public King(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if(super.canMove(row, col, row1, col1)){
            if(Math.abs(row1 - row) < 2 &&  Math.abs(col1 - col) < 2){
                return (BoardUtils.isPathClear(Board.getBoard(), row, col, row1, col1) &&
                        BoardUtils.isNotLeadCheck(Board.getBoard(), row, col, row1, col1));
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return canMove(row, col, row1, col1);
    }

}