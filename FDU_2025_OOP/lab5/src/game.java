import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import entities.*;

public class game {
    // the max amount of objects
    static final int MAXN = 100009;

    static boolean isNoLegalStepFlag = false, isAllFilledFlag = false;
    static Scanner sc = new Scanner(System.in);
    static int curX, curY, boardTot = 0;
    static int currentBoard = 1;

    public static void main(String[] args) {
        // create new players
        Player player1 = new Player("Tom");
        Player player2 = new Player("Jerry");

        Board[] board = new Board[MAXN];

        // create new board1
        board[++boardTot] = new Board();
        board[++boardTot] = new Board();
        board[++boardTot] = new Board();
        board[1].initBoard(player1, 1, "peace");
        board[2].initBoard(player1, 2, "reversi");
        board[3].initBoard(player1, 3, "gomoku");

        mainLoop(player1, player2, board);
    }

    static void mainLoop(Player player1, Player player2, Board[] board) {

        while (true) {
            // check whether the board has been filled first
            isAllFilledFlag = true;

            for (int i = 1; i <= 8; ++i) {
                for (int j = 1; j <= 8; ++j) {
                    if (board[currentBoard].board[i][j] == Piece.EMPTY) {
                        isAllFilledFlag = false;
                        break;
                    }
                }
            }

            if (isAllFilledFlag) {
                printWindow(player1, player2, board[currentBoard], board[currentBoard].currentPlayer, board[currentBoard].mode, board);
                endGame(board[currentBoard]);
            }

            if (Objects.equals(board[currentBoard].mode, "reversi")) {
                isNoLegalStepFlag = false;

                if (checkLegalStep(board[currentBoard].currentPlayer == player1 ? Piece.BLACK : Piece.WHITE, board[currentBoard])) {
                    if (checkLegalStep(board[currentBoard].currentPlayer == player1 ? Piece.WHITE : Piece.BLACK, board[currentBoard]) &&
                        !isAllFilledFlag) {
                        endGame(board[currentBoard]);
                    }
                    else {
                        board[currentBoard].currentPlayer = board[currentBoard].currentPlayer == player1 ? player2 : player1;
                        isNoLegalStepFlag = true;
                    }
                }

                int score1 = 0, score2 = 0;
                for (int i = 1; i <= 8; ++i) {
                    for (int j = 1; j <= 8; ++j) {
                        if (board[currentBoard].board[i][j] == Piece.BLACK) {
                            score1++;
                        }
                        else if (board[currentBoard].board[i][j] == Piece.WHITE) {
                            score2++;
                        }
                    }
                }
                player1.setScore(score1);
                player2.setScore(score2);

                // print frame
                if (!isAllFilledFlag) printWindow(player1, player2, board[currentBoard], board[currentBoard].currentPlayer, "reversi", board);

                // Input
                while (true) {
                    if (isNoLegalStepFlag) System.out.println("玩家[%s]无棋可下！");
                    System.out.printf("请玩家[%s]输入落子位置，棋盘编号或新建棋盘指令：\n", board[currentBoard].currentPlayer == player1 ? player1.getName() : player2.getName());
                    System.out.println("要新建棋盘，请键入 reversi, peace 或 gomoku.");
                    String str = null;
                    if (sc.hasNextLine()) {
                        str = sc.next();
                    }
                    int flag = stringOpt(str, player1, player2, board);
                    if (flag == -1) continue;
                    else if (flag == 1) break;

                    curX = Character.getNumericValue(str.charAt(0));
                    curY = str.charAt(1) - 'a' + 1;

                    if (board[currentBoard].board[curX][curY] != Piece.EMPTY &&
                            board[currentBoard].board[curX][curY] != Piece.LEGAL) {
                        System.out.printf("[%s]已经有棋子了！\n", str);
                        continue;
                    }

                    if (!updateBoard(board[currentBoard], curX, curY)) {
                        System.out.printf("[%s]是非法的落子位置！\n", str);
                        continue;
                    }

                    board[currentBoard].currentPlayer = board[currentBoard].currentPlayer == player1 ? player2 : player1;
                    break;
                }
            }
            else if (Objects.equals(board[currentBoard].mode, "peace")) {
                if (!isAllFilledFlag) printWindow(player1, player2, board[currentBoard], board[currentBoard].currentPlayer, "peace", board);
                while (true) {
                    System.out.printf("请玩家[%s]输入落子位置，棋盘编号或新建棋盘指令：\n", board[currentBoard].currentPlayer == player1 ? player1.getName() : player2.getName());
                    System.out.println("要新建棋盘，请键入 reversi, peace 或 gomoku.");
                    String str = null;
                    if (sc.hasNextLine()) {
                        str = sc.next();
                    }
                    int flag = stringOpt(str, player1, player2, board);
                    if (flag == -1) continue;
                    else if (flag == 1) break;

                    curX = Character.getNumericValue(str.charAt(0));
                    curY = str.charAt(1) - 'a' + 1;

                    if (board[currentBoard].board[curX][curY] != Piece.EMPTY) {
                        System.out.printf("[%s]已经有棋子了！\n", str);
                        continue;
                    }

                    board[currentBoard].board[curX][curY] = board[currentBoard].currentPlayer == player1 ? Piece.BLACK : Piece.WHITE;
                    board[currentBoard].currentPlayer = board[currentBoard].currentPlayer == player1 ? player2 : player1;
                    break;
                }
            }
            else if (Objects.equals(board[currentBoard].mode, "gomoku")) {
                Player p = checkGomokuWin(board[currentBoard], player1, player2);

                if (!isAllFilledFlag) printWindow(player1, player2, board[currentBoard], board[currentBoard].currentPlayer, "gomoku", board);
                while (true) {
                    if (p != null) {
                        if (p != Player.dummy) {
                            System.out.printf("游戏结束！获胜方是：%s!\n", p.getName());
                        }
                        else {
                            System.out.println("游戏结束！双方平局!");
                        }
                        board[currentBoard].endFlag = true;
                    }

                    System.out.printf("请玩家[%s]输入落子位置，棋盘编号或新建棋盘指令：\n", board[currentBoard].currentPlayer == player1 ? player1.getName() : player2.getName());
                    System.out.println("要新建棋盘，请键入 reversi, peace 或 gomoku.");
                    String str = null;
                    if (sc.hasNextLine()) {
                        str = sc.next();
                    }
                    int flag = stringOpt(str, player1, player2, board);
                    if (flag == -1) continue;
                    else if (flag == 1) break;

                    curX = Character.getNumericValue(str.charAt(0));
                    curY = str.charAt(1) - 'a' + 1;

                    if (board[currentBoard].board[curX][curY] != Piece.EMPTY) {
                        System.out.printf("[%s]已经有棋子了！\n", str);
                        continue;
                    }

                    board[currentBoard].board[curX][curY] = board[currentBoard].currentPlayer == player1 ? Piece.BLACK : Piece.WHITE;
                    board[currentBoard].currentPlayer = board[currentBoard].currentPlayer == player1 ? player2 : player1;
                    board[currentBoard].round++;
                    break;
                }
            }
        }
    }

    static void endGame(Board board1) {
        if (Objects.equals(board1.mode, "reversi")) {
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
        else if (Objects.equals(board1.mode, "peace")) {
            System.out.print("游戏结束！\n");
        }
        board1.endFlag = true;
    }

    static boolean checkLegalStep(Piece piece, Board board1) {
        boolean noLegalStep = true;

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board1.board[i][j] == Piece.BLACK ||
                    board1.board[i][j] == Piece.WHITE) {
                    continue;
                }

                board1.board[i][j] = Piece.EMPTY;
                if (checkPosition(piece, board1, i, j)) {
                    noLegalStep = false;
                    board1.board[i][j] = Piece.LEGAL;
                }
            }
        }
        return noLegalStep;
    }

    // check if [x, y] is a legal position
    static boolean checkPosition(Piece piece, Board board1, int x, int y) {
        Piece oppositePiece = piece == Piece.BLACK ? Piece.WHITE : Piece.BLACK;

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

    // do operations base on input
    // if opt successes, returns 1
    // if opt failed, return -1
    // if you need further opts return 0
    static int stringOpt(String str, Player player1, Player player2, Board[] board) {
        if (str.matches("\\d+")) {
            int curB = Character.getNumericValue(str.charAt(0));
            if (curB > boardTot) {
                System.out.println("棋盘不存在！");
                return -1;
            }
            currentBoard = curB;
            return 1;
        }
        else if (str.equals("reversi")) { // create reversi board
            board[++boardTot] = new Board();
            board[boardTot].initReversiBoard(player1, boardTot, "reversi");
            return 1;
        }
        else if (str.equals("peace")) { // create peace board
            board[++boardTot] = new Board();
            board[boardTot].initPeaceBoard(player1, boardTot, "peace");
            return 1;
        }
        else if (str.equals("gomoku")) {
            board[++boardTot] = new Board();
            board[boardTot].initGomokuBoard(player1, boardTot, "gomoku");
            return 1;
        }
        else if (str.equals("quit")) {
            System.exit(0);
        }
        else if (str.equals("pass")) {
            if (!isNoLegalStepFlag || board[currentBoard].mode == "peace") {
                System.out.println("您不能弃权，因为有合法的落子位置！");
                return -1;
            }
            else {
                board[currentBoard].currentPlayer = board[currentBoard].currentPlayer == player1 ? player2 : player1;
                return 1;
            }
        }
        else if (str.length() != 2 ||
            !(str.charAt(0) >= '1' && str.charAt(0) <= '8') ||
            !(str.charAt(1) >= 'a' && str.charAt(1) <= 'h')) {
            System.out.println("输入格式错误！");
            return -1;
        }
        else if (board[currentBoard].endFlag) {
            System.out.println("游戏已经结束，无法继续落子！");
            return -1;
        }
        return 0;
    }

    // after a move is performed, update the whole board
    static boolean updateBoard(Board board1, int x, int y) {
        Piece currentPiece = Objects.equals(board1.currentPlayer.getName(), "Tom") ? Piece.BLACK : Piece.WHITE;
        Piece oppositePiece = Objects.equals(board1.currentPlayer.getName(), "Tom") ? Piece.WHITE : Piece.BLACK;

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

    static void printWindow(Player player1, Player player2, Board board1, Player currentPlayer, String mode, Board[] board) {
        clearScreen();
        System.out.print("  ");
        for (int i = 1; i <= 8; ++i) {
            System.out.printf("%c ", 'A' + i - 1);
        }
        System.out.println();
        for (int i = 1; i <= Math.max(8, boardTot + 1); ++i) {
            if (i <= 8) {
                System.out.printf("%d ", i);
                for (int j = 1; j <= 8; ++j) {
                    switch (board1.board[i][j]) {
                        case Piece.BLACK:
                            System.out.print("○ ");
                            break;
                        case Piece.WHITE:
                            System.out.print("● ");
                            break;
                        case Piece.EMPTY:
                            System.out.print("· ");
                            break;
                        case Piece.LEGAL:
                            System.out.print("+ ");
                            break;
                    }
                }
            }
            switch (i) {
                case 1:
                    System.out.print("\t\t\t\t\tGame list");
                    break;
                case 3:
                    System.out.printf("\t\t棋盘%d\t\t\t", board1.id);
                    break;
                case 5:
                    System.out.printf("\t\t玩家[%s]", player1.getName());
                    if (currentPlayer == player1) {
                        System.out.print(" ○");
                    } else {
                        System.out.print("  ");
                    }
                    if (Objects.equals(mode, "reversi")) {
                        System.out.printf(" %d\t\t", player1.getScore());
                    }
                    else System.out.print("\t\t");
                    break;
                case 6:
                    System.out.printf("\t\t玩家[%s]", player2.getName());
                    if (currentPlayer == player2) {
                        System.out.print(" ●");
                    } else {
                        System.out.print("  ");
                    }
                    if (Objects.equals(mode, "reversi")) {
                        System.out.printf(" %d\t\t", player2.getScore());
                    }
                    else System.out.print("\t\t");
                    break;
                case 7:
                    if (Objects.equals(mode, "gomoku")) {
                        System.out.printf(" \t\tCurrent round: %d\t", board1.round);
                        break;
                    }
                default:
                    System.out.print("\t\t\t\t\t");
                    if (i > 8) System.out.print("\t\t");
            }
            if (i >= 2 && i <= boardTot + 1) {
                System.out.printf("%d.\t%s", board[i - 1].id, board[i - 1].mode);
            }
            System.out.println();
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

    public static Player checkGomokuWin(Board board, Player player1, Player player2) {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board.board[i][j] == Piece.EMPTY) {
                    continue;
                }
                if (checkDirection(board, i, j, 1, 0) ||
                    checkDirection(board, i, j, 0, 1) ||
                    checkDirection(board, i, j, 1, 1) ||
                    checkDirection(board, i, j, 1, -1)) {
                    return board.board[i][j] == Piece.BLACK ? player1 : player2;
                }
            }
        }

        return isAllFilledFlag ? Player.dummy : null;
    }

    private static boolean checkDirection(Board board, int row, int col, int dr, int dc) {
        Piece currentPiece = board.board[row][col];
        int count = 1;

        for (int i = 1; i <= 4; ++i) {
            int newRow = row + i * dr;
            int newCol = col + i * dc;
            if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8 || board.board[newRow][newCol] != currentPiece) {
                break;
            }
            count++;
        }

        for (int i = 1; i <= 4; ++i) {
            int newRow = row - i * dr;
            int newCol = col - i * dc;

            if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8 || board.board[newRow][newCol] != currentPiece) {
                break;
            }
            count++;
        }

        return count >= 5;
    }
}