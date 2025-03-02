import java.io.IOException;
import java.util.Scanner;

import entities.*;

public class game {
    static boolean playerFlag = true, isNoLegalStepFlag = false, isAllFilledFlag = false;
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
            isNoLegalStepFlag = false;
            isAllFilledFlag = true;

            for (int i = 1; i <= 8; ++i) {
                for (int j = 1; j <= 8; ++j) {
                    if (board.board[i][j] == Piece.EMPTY) {
                        isAllFilledFlag = false;
                        break;
                    }
                }
            }

            if (isAllFilledFlag) {
                endGame(board);
                return;
            }

            if (!checkLegalStep(playerFlag ? Piece.BLACK : Piece.WHITE, board)) {
                if (!checkLegalStep(playerFlag ? Piece.WHITE : Piece.BLACK, board)) {
                    endGame(board);
                    return;
                }
                else {
                    playerFlag = !playerFlag;
                    isNoLegalStepFlag = true;
                }
            }

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

                    if (!updateBoard(board, curX, curY)) {
                        System.out.printf("[%s]是非法的落子位置！\n", str);
                        continue;
                    }
                    break;
                }
            }

            playerFlag = !playerFlag;
        }
    }

    static void endGame(Board board) {
        int blackCnt = 0, whiteCnt = 0;
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board.board[i][j] == Piece.BLACK) blackCnt++;
                if (board.board[i][j] == Piece.WHITE) whiteCnt++;
            }
        }

        if (blackCnt > whiteCnt) System.out.print("游戏结束！获胜的一方是：黑棋！\n");
        if (blackCnt == whiteCnt) System.out.print("游戏结束！双方平局！\n");
        if (blackCnt < whiteCnt) System.out.print("游戏结束！获胜的一方是：白棋！\n");
    }

    static boolean checkLegalStep(Piece piece, Board board) {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (checkPosition(piece, board, i, j)) return true;
            }
        }
        return false;
    }

    // check if [x, y] is a legal position
    static boolean checkPosition(Piece piece, Board board, int x, int y) {
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
                if (board.board[tx][ty] == oppositePiece) {
                    toFlip.add(new int[]{tx, ty});
                } else if (board.board[tx][ty] == piece && !toFlip.isEmpty()) {
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

    // after a move is performed, update the whole board
    static boolean updateBoard(Board board, int x, int y) {
        Piece currentPiece = playerFlag ? Piece.BLACK : Piece.WHITE;
        Piece oppositePiece = playerFlag ? Piece.WHITE : Piece.BLACK;

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
                if (board.board[tx][ty] == oppositePiece) {
                    toFlip.add(new int[]{tx, ty});
                } else if (board.board[tx][ty] == currentPiece && !toFlip.isEmpty()) {
                    canFlip = true;
                    break;
                } else break;
                tx += dx;
                ty += dy;
            }

            if (canFlip) {
                for (int[] pos : toFlip) {
                    board.board[pos[0]][pos[1]] = currentPiece;
                }
                flipFlag = true;
            }
        }

        if (!flipFlag) return false;

        board.board[x][y] = currentPiece;
        return true;
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