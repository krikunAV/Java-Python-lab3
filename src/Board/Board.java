package Board;

import Figures.*;

public class Board {
    private static final int BOARD_SIZE = 8;
    private static final char WHITE_COLOR = 'w';
    private static final char BLACK_COLOR = 'b';
    private char colorGame;

    private static final char[] GameColor = new char[] {WHITE_COLOR, BLACK_COLOR};

    private static final Figure[][] fields = new Figure[BOARD_SIZE][BOARD_SIZE];

    public void replaceRow(Figure[][] board, int startRow, Figure[][] newFigures) {
        board[startRow] = (startRow <= 1) ? newFigures[0] : newFigures[1];
        board[startRow + 1] = (startRow <= 1) ? newFigures[1] : newFigures[0];

    }

    private Figure[][] initFigures(char color){
        Figure[][] figuresOneSide = new Figure[2][BOARD_SIZE];

        figuresOneSide[0][0] = new Rook("R", color);
        figuresOneSide[0][7] = new Rook("R", color);

        figuresOneSide[0][1] = new Knight("N", color);
        figuresOneSide[0][6] = new Knight("N", color);

        figuresOneSide[0][2] = new Bishop("B", color);
        figuresOneSide[0][5] = new Bishop("B", color);

        figuresOneSide[0][3] = new Queen("Q", color);
        figuresOneSide[0][4] = new King("K", color);

        for(int i = 0; i < BOARD_SIZE; i++){
            figuresOneSide[1][i] = new Pawn("P", color);
        }

        return figuresOneSide;
    }

    public void init(){
        for(char color: GameColor){
            if (color == WHITE_COLOR){
                replaceRow(fields, 0, initFigures(color));
            }
            else
                replaceRow(fields, 6, initFigures(color));
        }
    }

    public String getCell(int row, int col){
        Figure figure = fields[row][col];
        if (figure == null){
            return "    ";
        }
        return  " " + figure.getColor()+figure.getName()+" ";
    }

    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row);
            for (int col=0; col<8; col++){
                System.out.print("|" + getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    " + col);
        }
    }

    public void setColorGame(char w) {
        colorGame = w;
    }
    public char getColorGame() {
        return colorGame;
    }

    public static Figure[][] getBoard() {
        return fields;
    }

    static void printBoard(Figure[][] board) {
        System.out.println("Текущее состояние доски:");
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Figure figure = board[row][col];
                System.out.print((figure != null ? figure.getName() : ".") + " ");
            }
            System.out.println();
        }
    }

}