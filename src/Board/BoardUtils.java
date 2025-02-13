package Board;

import Figures.*;

import java.util.Objects;
import java.util.Scanner;

public class BoardUtils {
    private static final int BOARD_SIZE = 8;

    public static boolean isPathClear(Figure[][] board, int row, int col, int row1, int col1) {
        int rowDirection = Integer.signum(row1 - row);
        int colDirection = Integer.signum(col1 - col);

        int currentRow = row + rowDirection;
        int currentCol = col + colDirection;

        while (currentRow != row1 || currentCol != col1) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
        return board[row1][col1] == null || board[row][col].getColor() != board[row1][col1].getColor();
    }

    public static boolean isNotLeadCheck(Figure[][] board, int row, int col, int row1, int col1) {
        Figure figure = board[row][col];

        if (figure == null) {
            System.out.println("Ошибка: Пустая клетка, не может быть хода!");
            return false;
        }

        Figure originalTargetCell = board[row1][col1];

        board[row1][col1] = figure;
        board[row][col] = null;

        boolean isSafe = true;

        try {
            if (isCheck(Board.getBoard(),figure.getColor())) {
                isSafe = false;
            }
        } finally {
            board[row][col] = figure;
            board[row1][col1] = originalTargetCell;
        }

        return isSafe;
    }

    public static boolean isCheck(Figure[][] Board,char color){
        int[] kingPos = findKing(Board,color);
        int kingRow = kingPos[0] , kingCol = kingPos[1];

        char opponentColor = (color == 'w') ? 'b' : 'w';

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Figure figure = Board[row][col];

                if (figure != null && figure.getColor() == opponentColor) {
                    if (figure.canAttack(row, col, kingRow, kingCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isMate(char color) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Figure figure = Board.getBoard()[row][col];

                if (figure != null && figure.getColor() == color) {

                    for (int row1 = 0; row1 < BOARD_SIZE; row1++) {
                        for (int col1 = 0; col1 < BOARD_SIZE; col1++) {
                            if (figure.canMove(row, col, row1, col1)) {
                                Figure[][] tempBoard = cloneBoard();
                                makeMove(tempBoard, row, col, row1, col1);

                                if (!isCheck(tempBoard, color)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private static int[] findKing(Figure[][] board, char color) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Figure figure = board[row][col];
                if (figure != null && figure.getName().equals("K") && figure.getColor() == color) {
                    return new int[]{row, col};
                }
            }
        }
        Board.printBoard(board);
        throw new IllegalStateException("Король цвета " + color + " не найден на доске!");
    }

    private static Figure[][] cloneBoard() {
        Figure[][] board = Board.getBoard();
        Figure[][] clonedBoard = new Figure[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.arraycopy(board[row], 0, clonedBoard[row], 0, BOARD_SIZE);
        }
        return clonedBoard;
    }

    private static void makeMove(Figure[][] board, int row, int col, int row1, int col1) {
        if (board[row][col] == null) {
            throw new IllegalStateException("Ошибка: Пустая клетка, нельзя сделать ход!");
        }
        board[row1][col1] = board[row][col];
        board[row][col] = null;
    }

    public static boolean simulateMoveAndCheck(int row, int col, int row1, int col1, char color) {
        if (row == row1 && col == col1) {
            return false;
        }

        Figure[][] tempBoard = cloneBoard();
        Figure figure = tempBoard[row][col];

        if (figure == null) {
            return false;
        }

        if (figure.canMove(row, col, row1, col1)) {
            makeMove(tempBoard, row, col, row1, col1);
            return !isCheck(tempBoard, color);
        }

        return false;
    }

    public static void applyMove(Figure[][] Board, int row, int col, int row1, int col1) {
        Board[row1][col1] = Board[row][col];
        Board[row][col] = null;

        if(row1 == 7 && (Objects.equals(Board[row1][col1].getName(), "P"))){
            Board[row1][col1] = invokeFigure(Board[row1][col1]);
        }
    }

    public static boolean isStalemate(char color) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Figure figure = Board.getBoard()[row][col];

                if (figure != null && figure.getColor() == color) {

                    for (int row1 = 0; row1 < BOARD_SIZE; row1++) {
                        for (int col1 = 0; col1 < BOARD_SIZE; col1++) {

                            if (figure.canMove(row, col, row1, col1)) {

                                Figure[][] tempBoard = cloneBoard();
                                makeMove(tempBoard, row, col, row1, col1);
                                if (!isCheck(tempBoard, color)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private static Figure invokeFigure(Figure figure) {
        if (Objects.equals(figure.getName(), "P")) {
            Scanner in = new Scanner(System.in);
            System.out.print("Введите название фигуры (формат: Q, N, B, R): ");

            while (true) {
                String input = in.nextLine().toUpperCase();

                switch (input) {
                    case "Q":
                        return new Queen("Q", figure.getColor());
                    case "N":
                        return new Knight("N", figure.getColor());
                    case "B":
                        return new Bishop("B", figure.getColor());
                    case "R":
                        return new Rook("R", figure.getColor());
                    default:
                        System.out.print("Неверный ввод! Повторите (Q, N, B, R): ");
                }
            }
        }
        return figure;
    }

}