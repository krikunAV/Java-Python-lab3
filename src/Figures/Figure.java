package Figures;

public abstract class Figure {
    private  String name;
    private  char color;
    protected boolean isFirstMove = true;

    public Figure(String name, char color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    private static boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public boolean canMove(int row, int col, int row1, int col1) {
        return isWithinBoard(row, col) && isWithinBoard(row1, col1) &&
                !(row == row1 && col == col1);
    }

    public boolean canAttack(int row, int col, int row1, int col1){
        return this.canMove(row, col, row1, col1);
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}