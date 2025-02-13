import Board.*;

import java.util.Scanner;

public class Main {
    private static int[] getMove(Scanner in) {
        System.out.print("Введите ход (формат: row col row1 col1): ");
        try {
            String inputLine = in.nextLine();
            String[] coords = inputLine.split(" ");
            if (coords.length != 4) throw new IllegalArgumentException("Неверный формат ввода!");
            int[] move = new int[4];
            for (int i = 0; i < 4; i++) {
                move[i] = Integer.parseInt(coords[i]);
            }
            return move;
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.init();
        board.setColorGame('w');

        boolean isGame = true;
        Scanner in = new Scanner(System.in);

        while (isGame) {
            board.print_board();
            System.out.println();

            char color = board.getColorGame();
            System.out.println(color == 'w' ? "Ход белых" : "Ход черных");

            if (BoardUtils.isCheck(Board.getBoard(), color)) {
                if (BoardUtils.isMate(color)) {
                    System.out.println(color == 'w' ? "Победили черные" : "Победили белые");
                    break;
                }
                System.out.println("Ваш король под шахом! Защитите его!");
            }
            if (BoardUtils.isStalemate(color)){
                System.out.println("Победила дружба");
                break;
            }

            int[] move = null;
            while (move == null) {
                move = getMove(in);
                if (move == null) {
                    System.out.println("Повторите ввод.");
                }
            }

            if (!BoardUtils.simulateMoveAndCheck(move[0], move[1], move[2], move[3], color) ||
                    color != Board.getBoard()[move[0]][move[1]].getColor()) {
                System.out.println("Ход недействителен. Повторите ввод.");
                continue;
            }

            BoardUtils.applyMove(Board.getBoard(),move[0], move[1], move[2], move[3]);
            board.setColorGame(color == 'w' ? 'b' : 'w');
        }
        in.close();
    }
}