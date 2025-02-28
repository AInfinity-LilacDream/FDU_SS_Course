import java.io.IOException;
import java.util.Scanner;

import entities.*;

public class game {
    static boolean playerFlag = true;
    static Scanner sc = new Scanner(System.in);
    static int curX, curY;

    public static void main(String[] args) {
        // create new players
        Player player1 = new Player("张三");
        Player player2 = new Player("李四");

        // create new board
        Board board = new Board();
        board.init();

        mainLoop(player1, player2, board);
    }

    static void mainLoop(Player player1, Player player2, Board board) {
        while (true) {
            // print frame
            printWindow(player1, player2, board, playerFlag);

            // Input
            while (true) {
                System.out.printf("请玩家[%s]输入落子位置：", playerFlag ? player1.getName() : player2.getName());
                if (sc.hasNextLine()) {
                    String str = sc.next();
                    curX = Character.getNumericValue(str.charAt(0));
                    curY = str.charAt(1) - 'A' + 1;

                    if (board.board[curX][curY] != Piece.EMPTY) {
                        System.out.printf("[%s]已经有棋子了！\n", str);
                        continue;
                    }
                    break;
                }
            }

            board.board[curX][curY] = playerFlag ? Piece.BLACK : Piece.WHITE;
            updateBoard(board, curX, curY);

            playerFlag = !playerFlag;
        }
    }

    // after a move is performed, update the whole board
    static void updateBoard(Board board, int x, int y) {
        int mx, my, tx, ty;
        Piece piece = playerFlag ? Piece.BLACK : Piece.WHITE;

        tx = x; ty = y; mx = -1; my = -1;
        while (tx >= 1) {
            if (board.board[tx][ty] == piece)
                mx = tx;
            tx--;
        }
        if (mx != -1) {
            for (int i = mx; i <= x; ++i) board.board[i][y] = piece;
        }

        tx = x;
        mx = -1;
        while (tx <= 8) {
            if (board.board[tx][ty] == piece)
                mx = tx;
            tx--;
        }
        if (mx != -1) {
            for (int i = mx; i >= x; --i) board.board[i][y] = piece;
        }

        tx = x;
        mx = -1;
        while (tx <= 8) {
            if (board.board[tx][ty] == piece)
                mx = tx;
            tx--;
        }
        if (mx != -1) {
            for (int i = mx; i >= x; --i) board.board[i][y] = piece;
        }

        // TODO: complete the function
    }

    static void printWindow(Player player1, Player player2, Board board, boolean flag) {
        clearScreen();
        System.out.print("  ");
        for (int i = 1; i <= 8; ++i) {
            System.out.printf("%c ", 'A' + i - 1);
        }
        System.out.println();
        for (int i = 1; i <= 8; ++i) {
            System.out.printf("%d ", i);
            for (int j = 1; j <= 8; ++j) {
                switch(board.board[i][j]) {
                    case BLACK:
                        System.out.print("○ ");
                        break;
                    case WHITE:
                        System.out.print("● ");
                        break;
                    case EMPTY:
                        System.out.print("· ");
                }
            }
            if (i == 4) {
                System.out.printf("\t\t玩家[%s]", player1.getName());
                if (flag) {
                    System.out.println(" ○");
                } else System.out.println();
            } else if (i == 5) {
                System.out.printf("\t\t玩家[%s]", player2.getName());
                if (!flag) {
                    System.out.println(" ●");
                } else System.out.println();
            } else System.out.println();
        }
    }

    // [clearScreen] clear the screen using ANSI commands.
    // immediately flush the screen to ensure the effect.
    static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}