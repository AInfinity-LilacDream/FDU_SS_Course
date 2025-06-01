package com.oop.pj1.model;

import com.oop.pj1.data.Constant;

import java.io.Serializable;

public class GomokuBoard extends Board implements Serializable {
    @Override
    public void initBoard() {
        setCol(15);
        setRow(15);
        for (int i = 1; i <= getRow(); ++i) {
            for (int j = 1; j <= getCol(); ++j) {
                board[i][j] = Piece.Empty;
            }
        }
        board[3][6] = board[8][7] = board[9][6] = board[12][11] = Piece.BARRIER;
    }

    @Override
    public int checkGameEnd() {
        // 四个方向的偏移量：横、竖、正斜、反斜
        int[][] directions = {
                {0, 1},   // 横向
                {1, 0},   // 纵向
                {1, 1},   // 正对角线（从左上到右下）
                {1, -1}   // 反对角线（从右上到左下）
        };

        Piece piece = Piece.Black;
        for (int i = 1; i <= getRow(); ++i) {
            for (int j = 1; j <= getCol(); ++j) {
                if (board[i][j] == piece) {
                    for (int[] dir : directions) {
                        if (countInDirection(i, j, dir[0], dir[1], piece) >= 5) {
                            return Constant.blackWin;
                        }
                    }
                }
            }
        }

        piece = Piece.White;
        for (int i = 1; i <= getRow(); ++i) {
            for (int j = 1; j <= getCol(); ++j) {
                if (board[i][j] == piece) {
                    for (int[] dir : directions) {
                        if (countInDirection(i, j, dir[0], dir[1], piece) >= 5) {
                            return Constant.whiteWin;
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= getRow(); ++i) {
            for (int j = 1; j <= getCol(); ++j) {
                if (board[i][j] == Piece.Empty) {
                    return Constant.running;
                }
            }
        }

        return Constant.draw;
    }

    // 在某个方向上统计连续相同颜色棋子的数量
    private int countInDirection(int x, int y, int dx, int dy, Piece piece) {
        int count = 0;
        while (x >= 1 && x <= getRow() && y >= 1 && y <= getCol() && board[x][y] == piece) {
            count++;
            x += dx;
            y += dy;
        }
        return count;
    }
}
