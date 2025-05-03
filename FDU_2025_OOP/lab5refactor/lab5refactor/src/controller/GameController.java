package controller;

import data.Constant;
import model.*;

import java.util.Objects;
import java.util.Scanner;

public class GameController {
    private final Board board;
    private Player currentPlayer;
    private Player alternatePlayer;

    private int blackScore;
    private int whiteScore;
    private int currentRound;

    private Boolean endFlag = false;

    private final String gameType;

    // constructor. player1 is considered as the first.
    public GameController(Board board, Player currentPlayer, Player alternatePlayer, String gameType) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.alternatePlayer = alternatePlayer;
        this.gameType = gameType;
        this.currentRound = 1;
    }

    public Piece[][] getBoard() {
        return board.getBoard();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getAlternatePlayer() {
        return alternatePlayer;
    }

    public String getGameType() {
        return gameType;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public Boolean getEndFlag() {
        return endFlag;
    }

    public void setEndFlag() {
        endFlag = true;
    }

    // 落子并翻转敌方棋子
    private void makeMove(Player currentPlayer, int cx, int cy, String gameType) {
        Piece opponent = (Objects.equals(currentPlayer.getName(), Constant.name1P)) ? Piece.White : Piece.Black;
        Piece current = (Objects.equals(currentPlayer.getName(), Constant.name2P)) ? Piece.White : Piece.Black;

        if (Objects.equals(gameType, Constant.Reversi)) {
            // 八个方向：上下左右 + 四个斜方向
            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int dir = 0; dir < 8; dir++) {
                int x = cx + dx[dir];
                int y = cy + dy[dir];
                boolean hasOpponentBetween = false;

                // 沿着该方向查找是否有对方棋子，并最终能碰到自己的棋子
                while (x >= 0 && x < 8 && y >= 0 && y < 8 && board.getBoard()[x][y] == opponent) {
                    x += dx[dir];
                    y += dy[dir];
                    hasOpponentBetween = true;
                }

                // 如果找到一个自己的棋子并且中间有敌方棋子，则翻转它们
                if (x >= 0 && x < 8 && y >= 0 && y < 8 && board.getBoard()[x][y] == current && hasOpponentBetween) {
                    x = cx + dx[dir];
                    y = cy + dy[dir];

                    while (x >= 0 && x < 8 && y >= 0 && y < 8 && board.getBoard()[x][y] == opponent) {
                        board.getBoard()[x][y] = current;
                        x += dx[dir];
                        y += dy[dir];
                    }
                }
            }
        }

        // 最后在当前位置放置玩家棋子
        board.getBoard()[cx][cy] = current;
    }

    // 下一步棋。有可能下在已经有棋子或者非法的位置，需要自行判断。
    public Boolean makeNextMove(String input) {

        if (endFlag) {
            System.out.println("游戏已经结束！");
            return false;
        }

        // 判断无需更新棋盘的动作
        if (Objects.equals(input, Constant.Pass)) {
            if (!gameType.equals(Constant.Reversi) || ((ReversiBoard) board).getLegalPositionCount() > 0) {
                System.out.println("你不能放弃本轮行棋！");
                return false;
            }
            else {
                Player tmp;
                tmp = currentPlayer;
                currentPlayer = alternatePlayer;
                alternatePlayer = tmp;
                return true;
            }
        }

        int wx = input.charAt(0) - '0';
        int wy = input.charAt(1) - 'a' + 1;
        if (board.getBoard()[wx][wy] != Piece.Empty && board.getBoard()[wx][wy] != Piece.LEGAL) {
            System.out.printf("[%s]已经有棋子了！\n", input);
            return false;
        }
        if (Objects.equals(gameType, Constant.Reversi) && board.getBoard()[wx][wy] != Piece.LEGAL) {
            System.out.println("非法的落子位置！");
            return false;
        }

        // 更新棋盘
        makeMove(currentPlayer, wx, wy, gameType);

        // 交换行棋顺序
        Player tmp;
        tmp = currentPlayer;
        currentPlayer = alternatePlayer;
        alternatePlayer = tmp;

        // 更新分数以及合法落子位置
        if (gameType.equals(Constant.Reversi)) {
            ((ReversiBoard) board).updateLegalPosition(Objects.equals(currentPlayer.getName(), Constant.name1P) ? Piece.Black : Piece.White);
        }
        else if (gameType.equals(Constant.Gomoku)) {
            currentRound++;
        }
        blackScore = 0;
        whiteScore = 0;
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board.getBoard()[i][j] == Piece.Black) {
                    blackScore++;
                }
                else if (board.getBoard()[i][j] == Piece.White) {
                    whiteScore++;
                }
            }
        }
        return true;
    }

    public int checkGameEnd() {
        return board.checkGameEnd();
    }
}