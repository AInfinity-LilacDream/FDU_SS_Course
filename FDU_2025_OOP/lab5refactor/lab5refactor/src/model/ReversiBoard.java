package model;

import data.Constant;

import java.util.Objects;

public class ReversiBoard extends Board {
    private int legalPositionCount;

    @Override
    public void initBoard() {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                board[i][j] = Piece.Empty;
            }
        }
        board[4][4] = board[5][5] = Piece.White;
        board[4][5] = board[5][4] = Piece.Black;
        board[3][4] = board[4][3] = board[5][6] = board[6][5] = Piece.LEGAL;
    }

    @Override
    public int checkGameEnd() {
        int whiteScore = 0, blackScore = 0;

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (isValidMove(Piece.Black, i, j)) return Constant.running;
            }
        }

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (isValidMove(Piece.White, i, j)) return Constant.running;
            }
        }

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board[i][j] == Piece.White) {
                    whiteScore++;
                }
                else if (board[i][j] == Piece.Black) {
                    blackScore++;
                }
            }
        }
        if (whiteScore == blackScore) {
            return Constant.draw;
        }
        else if (whiteScore < blackScore) {
            return Constant.blackWin;
        }
        else {
            return Constant.whiteWin;
        }
    }

    public int getLegalPositionCount() {
        return legalPositionCount;
    }

    private void setLegalPositionCount(int legalPositionCount) {
        this.legalPositionCount = legalPositionCount;
    }

    private boolean isValidMove(Piece current, int cx, int cy) {

        if (board[cx][cy] != Piece.Empty) return false;

        Piece opponent = (current == Piece.Black ? Piece.White : Piece.Black);

        // 八个方向：上下左右 + 四个斜方向
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int dir = 0; dir < 8; dir++) {
            int x = cx + dx[dir];
            int y = cy + dy[dir];
            boolean foundOpponentBetween = false;

            while (x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y] == opponent) {
                x += dx[dir];
                y += dy[dir];
                foundOpponentBetween = true;
            }

            // 如果最终遇到自己的棋子，说明可以吃掉中间的对手棋子
            if (x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y] == current && foundOpponentBetween) {
                return true;
            }
        }

        return false;
    }

    public void updateLegalPosition(Piece current) {
        clearLegalPosition();

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (isValidMove(current, i, j)) {
                    System.out.println("hehe");
                    board[i][j] = Piece.LEGAL;
                    legalPositionCount++;
                }
            }
        }
    }

    private void clearLegalPosition() {
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j<= 8; ++j) {
                if (board[i][j] == Piece.LEGAL) {
                    board[i][j] = Piece.Empty;
                }
            }
        }
    }
}
