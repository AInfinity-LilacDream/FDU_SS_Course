package com.oop.pj1.controller;

import com.oop.pj1.Game;
import com.oop.pj1.data.Constant;
import com.oop.pj1.model.Board;
import com.oop.pj1.model.Piece;
import com.oop.pj1.model.Player;
import com.oop.pj1.model.ReversiBoard;
import com.oop.pj1.view.Messenger;

import java.io.Serializable;
import java.util.Objects;

public class GameController implements Serializable {
    private final Board board;
    private Player currentPlayer;
    private Player alternatePlayer;

    private int blackScore;
    private int whiteScore;
    private int currentRound;

    private int blackBombCount = 2;
    private int whiteBombCount = 3;
    private Boolean endFlag = false;

    private final String gameType;

    // constructor. player1 is considered as the first.
    public GameController(Board board, Player currentPlayer, Player alternatePlayer, String gameType) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.alternatePlayer = alternatePlayer;
        this.gameType = gameType;
        this.currentRound = 1;
        this.blackScore = this.whiteScore = 2;
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

    public void setEndFlag() {
        endFlag = true;
    }

    public int getRow() {
        return board.getRow();
    }

    public int getCol() {
        return board.getCol();
    }

    public int getBlackBombCount() {
        return blackBombCount;
    }

    public int getWhiteBombCount() {
        return whiteBombCount;
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
                while (x >= 1 && x <= board.getRow() && y >= 1 && y <= board.getCol() && board.getBoard()[x][y] == opponent) {
                    x += dx[dir];
                    y += dy[dir];
                    hasOpponentBetween = true;
                }

                // 如果找到一个自己的棋子并且中间有敌方棋子，则翻转它们
                if (x >= 1 && x <= board.getRow() && y >= 1 && y <= board.getCol() && board.getBoard()[x][y] == current && hasOpponentBetween) {
                    x = cx + dx[dir];
                    y = cy + dy[dir];

                    while (x >= 1 && x <= board.getRow() && y >= 1 && y <= board.getCol() && board.getBoard()[x][y] == opponent) {
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
    public void makeNextMove(String input) {

        if (endFlag) {
            Messenger.error("游戏已经结束！");
            return;
        }

        // 判断无需更新棋盘的动作
        if (Objects.equals(input, Constant.Pass)) {
            if (!gameType.equals(Constant.Reversi) || ((ReversiBoard) board).getLegalPositionCount() > 0) {
                Messenger.error("你不能放弃本轮行棋！");
            }
            else {
                updateGameInfo();
            }
            return;
        }

        int wx = (input.charAt(0) >= '1' && input.charAt(0) <= '9') ? input.charAt(0) - '0' : input.charAt(0) - 'a' + 10;
        int wy = input.charAt(1) - 'a' + 1;
        if (wx <= board.getRow() && wy <= board.getCol() && (board.getBoard()[wx][wy] == Piece.Black || board.getBoard()[wx][wy] == Piece.White)) {
            Messenger.error("[%s]已经有棋子了！", input);
            return;
        }
        if (wx <= board.getRow() && wy <= board.getCol() && (board.getBoard()[wx][wy] == Piece.BARRIER || board.getBoard()[wx][wy] == Piece.BOMB)) {
            Messenger.error("你不能在[%s]行棋！", input);
            return;
        }
        if ((!Objects.equals(gameType, Constant.Gomoku) && (wx >= 9 || wy >= 9)) ||
                (Objects.equals(gameType, Constant.Reversi) && board.getBoard()[wx][wy] != Piece.LEGAL)) {
            Messenger.error("非法的落子位置！");
            return;
        }

        // 更新棋盘
        makeMove(currentPlayer, wx, wy, gameType);

        updateGameInfo();
        blackScore = 0;
        whiteScore = 0;
        for (int i = 1; i <= board.getRow(); ++i) {
            for (int j = 1; j <= board.getCol(); ++j) {
                if (board.getBoard()[i][j] == Piece.Black) {
                    blackScore++;
                }
                else if (board.getBoard()[i][j] == Piece.White) {
                    whiteScore++;
                }
            }
        }

        Game.refreshGame();

        // 判断游戏胜利
        switch(board.checkGameEnd()) {
            case Constant.draw:
                Messenger.success("游戏结束！双方平局！");
                setEndFlag();
                break;
            case Constant.blackWin:
                Messenger.success("\n游戏结束！[%s]获胜！\n", Constant.name1P);
                setEndFlag();
                break;
            case Constant.whiteWin:
                Messenger.success("\n游戏结束！[%s]获胜！\n", Constant.name2P);
                setEndFlag();
                break;
        }

    }

    private void updateGameInfo() {
        Player tmp;
        tmp = currentPlayer;
        currentPlayer = alternatePlayer;
        alternatePlayer = tmp;

        if (gameType.equals(Constant.Reversi)) {
            ((ReversiBoard) board).updateLegalPosition(Objects.equals(currentPlayer.getName(), Constant.name1P) ? Piece.Black : Piece.White);
        }
        else if (gameType.equals(Constant.Gomoku)) {
            currentRound++;
        }
    }

    public void setBomb(String input) {
        if (!Objects.equals(gameType, Constant.Gomoku)) {
            Messenger.error("非五子棋模式，无法投掷炸弹！");
            return;
        }
        if ((Objects.equals(currentPlayer.getName(), Constant.name1P) && blackBombCount == 0) ||
                (Objects.equals(currentPlayer.getName(), Constant.name2P) && whiteBombCount == 0)) {
            Messenger.error("炸弹数量不足！");
            return;
        }
        int wx = (input.charAt(1) >= '1' && input.charAt(1) <= '9') ? input.charAt(1) - '0' : input.charAt(1) - 'a' + 10;
        int wy = input.charAt(2) - 'a' + 1;
        if (board.getBoard()[wx][wy] != (Objects.equals(currentPlayer.getName(), Constant.name1P) ? Piece.White : Piece.Black)) {
            Messenger.error("该位置无法使用炸弹！");
            return;
        }

        board.getBoard()[wx][wy] = Piece.BOMB;
        if (Objects.equals(currentPlayer.getName(), Constant.name1P)) {
            blackBombCount--;
        }
        else {
            whiteBombCount--;
        }

        updateGameInfo();
    }
}