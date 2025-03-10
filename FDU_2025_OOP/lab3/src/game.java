import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import entities.*;

public class game {
    static boolean isNoLegalStepFlag = false, isAllFilledFlag = false;
    static Scanner sc = new Scanner(System.in);
    static int curX, curY;

    public static void main(String[] args) {
        // create new players
        Player player1 = new Player("张三");
        Player player2 = new Player("李四");

        // create new board1
        Board board1 = new Board();
        Board board2 = new Board();
        Board board3 = new Board();
        board1.init(player1, 1);
        board2.init(player1, 2);
        board3.init(player1, 3);

        mainLoop(player1, player2, board1, board2, board3);
    }

    static void mainLoop(Player player1, Player player2, Board board1, Board board2, Board board3) {
        Board currentBoard = board1;

        while (true) {
            isNoLegalStepFlag = false;
            isAllFilledFlag = true;

            for (int i = 1; i <= 8; ++i) {
                for (int j = 1; j <= 8; ++j) {
                    if (currentBoard.board[i][j] == Piece.EMPTY) {
                        isAllFilledFlag = false;
                        break;
                    }
                }
            }

            if (isAllFilledFlag) {
                endGame(currentBoard);
                return;
            }

            if (!checkLegalStep(currentBoard.currentPlayer == player1 ? Piece.BLACK : Piece.WHITE, currentBoard)) {
                if (!checkLegalStep(currentBoard.currentPlayer == player1 ? Piece.WHITE : Piece.BLACK, currentBoard)) {
                    endGame(currentBoard);
                    return;
                }
                else {
                    currentBoard.currentPlayer = currentBoard.currentPlayer == player1 ? player2 : player1;
                    isNoLegalStepFlag = true;
                }
            }

            // print frame
            printWindow(player1, player2, currentBoard, currentBoard.currentPlayer);
            // Input
            while (true) {
                System.out.printf("请玩家[%s]输入落子位置或者棋盘编号：", currentBoard.currentPlayer == player1 ? player1.getName() : player2.getName());
                if (sc.hasNextLine()) {
                    String str = sc.next();
                    if (str.length() == 1) {
                        int curB = Character.getNumericValue(str.charAt(0));
                        if (curB == 1) currentBoard = board1;
                        if (curB == 2) currentBoard = board2;
                        if (curB == 3) currentBoard = board3;
                        break;
                    }
                    curX = Character.getNumericValue(str.charAt(0));
                    curY = str.charAt(1) - 'a' + 1;

                    if (currentBoard.board[curX][curY] != Piece.EMPTY) {
                        System.out.printf("[%s]已经有棋子了！\n", str);
                        continue;
                    }

                    if (!updateBoard(currentBoard, curX, curY)) {
                        System.out.printf("[%s]是非法的落子位置！\n", str);
                        continue;
                    }

                    currentBoard.currentPlayer = currentBoard.currentPlayer == player1 ? player2 : player1;
                    break;
                }
            }
        }
    }

    static void endGame(Board board1) {
        int blackCnt = 0, whiteCnt = 0;
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board1.board[i][j] == Piece.BLACK) blackCnt++;
                if (board1.board[i][j] == Piece.WHITE) whiteCnt++;
            }
        }

        if (blackCnt > whiteCnt) System.out.print("游戏结束！获胜的一方是：黑棋！\n");
        if (blackCnt == whiteCnt) System.out.print("游戏结束！双方平局！\n");
        if (blackCnt < whiteCnt) System.out.print("游戏结束！获胜的一方是：白棋！\n");
    }

    static boolean checkLegalStep(Piece piece, Board board1) {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (checkPosition(piece, board1, i, j)) return true;
            }
        }
        return false;
    }

    // check if [x, y] is a legal position
    static boolean checkPosition(Piece piece, Board board1, int x, int y) {
        Piece oppositePiece = piece == Piece.BLACK ? Piece.WHITE : Piece.BLACK;

        boolean flipFlag = false;

        // direction enum
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1}
        };

        // check each direction
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            int tx = x + dx;
            int ty = y + dy;

            java.util.ArrayList<int[]> toFlip = new java.util.ArrayList<>();
            boolean canFlip = false;

            while (tx >= 1 && tx <= 8 && ty >= 1 && ty <= 8) {
                if (board1.board[tx][ty] == oppositePiece) {
                    toFlip.add(new int[]{tx, ty});
                } else if (board1.board[tx][ty] == piece && !toFlip.isEmpty()) {
                    canFlip = true;
                    break;
                } else break;
                tx += dx;
                ty += dy;
            }

            if (canFlip) {
                return true;
            }
        }
        return false;
    }

    // after a move is performed, update the whole board1
    static boolean updateBoard(Board board1, int x, int y) {
        Piece currentPiece = Objects.equals(board1.currentPlayer.getName(), "张三") ? Piece.BLACK : Piece.WHITE;
        Piece oppositePiece = Objects.equals(board1.currentPlayer.getName(), "张三") ? Piece.WHITE : Piece.BLACK;

        boolean flipFlag = false;

        // direction enum
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1}
        };

        // check each direction
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            int tx = x + dx;
            int ty = y + dy;

            java.util.ArrayList<int[]> toFlip = new java.util.ArrayList<>();
            boolean canFlip = false;

            while (tx >= 1 && tx <= 8 && ty >= 1 && ty <= 8) {
                if (board1.board[tx][ty] == oppositePiece) {
                    toFlip.add(new int[]{tx, ty});
                } else if (board1.board[tx][ty] == currentPiece && !toFlip.isEmpty()) {
                    canFlip = true;
                    break;
                } else break;
                tx += dx;
                ty += dy;
            }

            if (canFlip) {
                for (int[] pos : toFlip) {
                    board1.board[pos[0]][pos[1]] = currentPiece;
                }
                flipFlag = true;
            }
        }

        if (!flipFlag) return false;

        board1.board[x][y] = currentPiece;
        return true;
    }

    static void printWindow(Player player1, Player player2, Board board1, Player currentPlayer) {
        clearScreen();
        System.out.print("  ");
        for (int i = 1; i <= 8; ++i) {
            System.out.printf("%c ", 'A' + i - 1);
        }
        System.out.println();
        for (int i = 1; i <= 8; ++i) {
            System.out.printf("%d ", i);
            for (int j = 1; j <= 8; ++j) {
                switch(board1.board[i][j]) {
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
            if (i == 3) {
                System.out.printf("\t\t棋盘%d", board1.id);
            }
            if (i == 5) {
                System.out.printf("\t\t玩家[%s]", player1.getName());
                if (currentPlayer == player1) {
                    System.out.println(" ○");
                } else System.out.println();
            } else if (i == 6) {
                System.out.printf("\t\t玩家[%s]", player2.getName());
                if (currentPlayer == player2) {
                    System.out.println(" ●");
                } else System.out.println();
            } else System.out.println();
        }
        if (isNoLegalStepFlag) {
            System.out.println("由于本该落子的玩家无法落子，该玩家本回合弃权!");
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